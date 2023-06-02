package clockworkchar.ui;

import clockworkchar.ClockworkChar;
import clockworkchar.characters.TheClockwork;
import clockworkchar.multiplayer.ModManager;
import clockworkchar.multiplayer.NetworkCranky;
import clockworkchar.relics.FloppyDisk;
import clockworkchar.relics.SevenSegmentDisplay;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import spireTogether.SpireTogetherMod;
import spireTogether.network.P2P.P2PManager;

import static clockworkchar.ClockworkChar.makeID;

public class Winder {
    private static Texture BASE_TEXTURE = ImageMaster.loadImage(ClockworkChar.modID + "Resources/images/ui/winder/base.png");
    private static Texture GRIP1_TEXTURE = ImageMaster.loadImage(ClockworkChar.modID + "Resources/images/ui/winder/frontgrip.png");
    private static Texture ROD_TEXTURE = ImageMaster.loadImage(ClockworkChar.modID + "Resources/images/ui/winder/rod.png");
    private static Texture GRIP2_TEXTURE = ImageMaster.loadImage(ClockworkChar.modID + "Resources/images/ui/winder/backgrip.png");
    private static float size = BASE_TEXTURE.getWidth();
    private static float halfSize = size / 2.0F;
    private static int STARTING_COGWHEELS = 2;
    private static float COGWHEELS_FONT_SCALE = 0.6f;

    private static String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("WinderElement")).TEXT;

    public float offset = 0.0F;
    public float x = 0.0F;
    public float y = 0.0F;
    private Hitbox hb = new Hitbox(size, size);

    private int toTwistForward = 0;
    private int toTwistBack = 0;
    private float angle = 0.0F;
    public float widthMult = 1f;
    private float fontScale = 1.0F;
    private float cogwheelsFontScale = COGWHEELS_FONT_SCALE;
    private float scale;
    private boolean showTooltip = false;
    private boolean attachToEnergyOrb = false;
    private boolean displayCogwheels = false;

    public boolean shouldRender = false;
    public int charge = 0;
    public int chargeGained = 0;
    public int cogwheels = 0;
    private BitmapFont font;

    public Winder() {
        this(1.0f, true, true, true, null);
    }

    public Winder(float scale, boolean showTooltip, boolean attachToEnergyOrb, boolean displayCogwheels, BitmapFont font) {
        this.scale = scale;
        hb.width *= scale * Settings.scale;
        hb.height *= scale * Settings.scale;
        this.showTooltip = showTooltip;
        this.attachToEnergyOrb = attachToEnergyOrb;
        this.displayCogwheels = displayCogwheels;
        this.font = font;
    }

    public void update() {
        if (toTwistForward > 0) {
            angle += Gdx.graphics.getDeltaTime() * 240.0F * toTwistForward;
            if (angle >= 180.0F) {
                angle -= 180.0F;
                toTwistForward--;
            }
        } else if (toTwistBack > 0) {
            angle -= Gdx.graphics.getDeltaTime() * 320.0F * toTwistBack;
            if (angle <= -180.0F) {
                angle += 180.0F;
                toTwistBack--;
            }
        }

        if (fontScale != 1.0F)
            fontScale = MathHelper.scaleLerpSnap(fontScale, 1.0F); 
        if (cogwheelsFontScale != COGWHEELS_FONT_SCALE)
            cogwheelsFontScale = MathHelper.scaleLerpSnap(cogwheelsFontScale, COGWHEELS_FONT_SCALE); 
        
        offset = - halfSize * scale * Settings.scale;
        if (attachToEnergyOrb) {
            x = AbstractDungeon.overlayMenu.energyPanel.current_x + offset;
            y = AbstractDungeon.overlayMenu.energyPanel.current_y + 150.0F * Settings.scale + offset;
        }
        hb.update(x, y);
        if (showTooltip && hb.hovered)
            TipHelper.renderGenericTip(50.0F * Settings.scale, y + 275.0F * Settings.scale, TEXT[0], TEXT[1]);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        widthMult = Math.abs(MathUtils.cosDeg(angle));
        if (attachToEnergyOrb && AbstractDungeon.player instanceof TheClockwork)
            spinSkinWinder(((TheClockwork)AbstractDungeon.player).winderBone);
        float gripX = x + (halfSize + widthMult * (1 - size) * 0.5F) * scale * Settings.scale;
        sb.draw(BASE_TEXTURE, x, y, size * scale * Settings.scale, size * scale * Settings.scale);
        sb.draw(GRIP2_TEXTURE, gripX, y, size * widthMult * scale * Settings.scale, size * scale * Settings.scale);
        sb.draw(ROD_TEXTURE, x, y, size * scale * Settings.scale, size * scale * Settings.scale);
        sb.draw(GRIP1_TEXTURE, gripX, y, size * widthMult * scale * Settings.scale, size * scale * Settings.scale);
        BitmapFont f = font == null ? AbstractDungeon.player.getEnergyNumFont() : font;
        if (displayCogwheels) {
            f.getData().setScale(cogwheelsFontScale * scale);
            FontHelper.renderFontCentered(sb, f, "+"+String.valueOf(cogwheels), x-offset, y-offset*0.5f, Color.WHITE);
        }
        f.getData().setScale(fontScale * scale);
        FontHelper.renderFontCentered(sb, f, String.valueOf(charge), x-offset, y-offset, Color.WHITE);
    }

    public void spinSkinWinder(Bone winderBone) {
        winderBone.setScaleX(widthMult);
    }

    public void reset() {
        shouldRender = AbstractDungeon.player instanceof TheClockwork;
        cogwheels = STARTING_COGWHEELS;
        toTwistForward = 0;
        toTwistBack = 0;
        angle = 0.0F;
        charge = 0;
        chargeChanged();
        cogwheelsChanged();
        if (AbstractDungeon.player.hasRelic(FloppyDisk.ID))
            charge += AbstractDungeon.player.getRelic(FloppyDisk.ID).counter;
        chargeGained = 0;
    }

    public void gainCharge(int amount, boolean fromWindUp) {
        charge += amount;
        if (fromWindUp) {
            shouldRender = true;
            chargeGained += amount;
        }
        if (charge > 999)
            charge = 999;
        if (toTwistBack > 0) {
            toTwistBack = 0;
            angle = 0.0F;
        }
        toTwistForward += amount;
        toTwistForward = Math.min(toTwistForward, 20);
        if (amount != 0)
            fontScale = 2.0F;
        chargeChanged();
    }

    public boolean useCharge(int amount) {
        if (charge >= amount) {
            charge -= amount;
            if (toTwistForward > 0) {
                toTwistForward = 0;
                angle = 0.0F;
            }
            toTwistBack += amount;
            toTwistBack = Math.min(toTwistBack, 20);
            if (amount != 0)
                fontScale = 2.0F; 
            chargeChanged();
            return true;
        }
        return false;
    }

    public void gainCogwheels(int amount) {
        cogwheels += amount;
        if (amount != 0) {
            shouldRender = true;
            cogwheelsFontScale *= 2f;
            cogwheelsChanged();
        }
    }

    public void chargeChanged() {
        if (AbstractDungeon.player.hasRelic(SevenSegmentDisplay.ID))
            ((SevenSegmentDisplay)AbstractDungeon.player.getRelic(SevenSegmentDisplay.ID)).updateDisplay();
        if (ModManager.isMultiplayerLoaded && SpireTogetherMod.isConnected)
            P2PManager.SendData(NetworkCranky.REQUEST_CHANGE_CHARGE, charge);
    }

    public void cogwheelsChanged() {
        if (ModManager.isMultiplayerLoaded && SpireTogetherMod.isConnected)
            P2PManager.SendData(NetworkCranky.REQUEST_CHANGE_COGWHEELS, cogwheels);
    }

    public int useAllCharge() {
        int chargeUsed = charge;
        useCharge(chargeUsed);
        return chargeUsed;
    }

    public void triggerCogwheels() {
        gainCharge(cogwheels, false);
    }

    public void setNonPlayerCharge(int newCharge, boolean pulseText) {
        if (newCharge > charge) {
            toTwistForward = newCharge - charge;
            toTwistBack = 0;
            angle = 0f;
            if (pulseText)
                fontScale = 2.0F;
        } else if (newCharge < charge) {
            toTwistBack = charge - newCharge;
            toTwistForward = 0;
            angle = 0f;
            if (pulseText)
                fontScale = 2.0F;
        }
        charge = newCharge;
    }

    public void setNonPlayerCogwheels(int newCogwheels, boolean pulseText) {
        if (newCogwheels != cogwheels && pulseText)
            cogwheelsFontScale *= 2f;
        cogwheels = newCogwheels;
    }
}