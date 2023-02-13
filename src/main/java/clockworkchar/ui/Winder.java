package clockworkchar.ui;

import clockworkchar.ClockworkChar;
import clockworkchar.characters.TheClockwork;
import clockworkchar.relics.FloppyDisk;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;

public class Winder {
    private static Texture BASE_TEXTURE = ImageMaster.loadImage(ClockworkChar.modID + "Resources/images/ui/winder/base.png");
    private static Texture GRIP1_TEXTURE = ImageMaster.loadImage(ClockworkChar.modID + "Resources/images/ui/winder/frontgrip.png");
    private static Texture ROD_TEXTURE = ImageMaster.loadImage(ClockworkChar.modID + "Resources/images/ui/winder/rod.png");
    private static Texture GRIP2_TEXTURE = ImageMaster.loadImage(ClockworkChar.modID + "Resources/images/ui/winder/backgrip.png");
    private static float size = BASE_TEXTURE.getWidth();
    private static float halfSize = size / 2.0F;
    private static float fontScale = 1.0F;

    private static String[] TEXT = CardCrawlGame.languagePack.getUIString(ClockworkChar.modID + ":WinderElement").TEXT;

    private float offset = 0.0F;
    private float x = 0.0F;
    private float y = 0.0F;
    private Hitbox hb = new Hitbox(size, size);

    private int toTwistForward = 0;
    private int toTwistBack = 0;
    private float angle = 0.0F;

    public boolean shouldRender = false;
    public int charge = 0;
    public int chargeGained = 0;

    public Winder() {

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
        
        offset = - halfSize * Settings.scale;
        x = AbstractDungeon.overlayMenu.energyPanel.current_x + offset;
        y = AbstractDungeon.overlayMenu.energyPanel.current_y + 150.0F * Settings.scale + offset;
        hb.update(x, y);
        if (hb.hovered)
            TipHelper.renderGenericTip(50.0F * Settings.scale, y + 275.0F * Settings.scale, TEXT[0], TEXT[1]);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        float widthMult = Math.abs(MathUtils.cosDeg(angle));
        if (AbstractDungeon.player instanceof TheClockwork)
            ((TheClockwork)AbstractDungeon.player).winderBone.setScaleX(widthMult);
        float gripX = x + halfSize + widthMult * (1 - size) * 0.5F;
        sb.draw(BASE_TEXTURE, x, y);
        sb.draw(GRIP2_TEXTURE, gripX, y, size * widthMult, size);
        sb.draw(ROD_TEXTURE, x, y);
        sb.draw(GRIP1_TEXTURE, gripX, y, size * widthMult, size);
        AbstractDungeon.player.getEnergyNumFont().getData().setScale(fontScale);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), String.valueOf(charge), x-offset, y-offset, Color.WHITE);
    }

    public void reset() {
        shouldRender = AbstractDungeon.player instanceof TheClockwork;
        toTwistForward = 0;
        toTwistBack = 0;
        angle = 0.0F;
        if (AbstractDungeon.player.hasRelic(FloppyDisk.ID))
            charge = AbstractDungeon.player.getRelic(FloppyDisk.ID).counter();
        else
            charge = 0;
        chargeGained = 0;
    }

    public void gainCharge(int amount) {
        shouldRender = true;
        charge += amount;
        chargeGained += amount;
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
            return true;
        }
        return false;
    }

    public int useAllCharge() {
        int chargeUsed = charge;
        useCharge(chargeUsed);
        return chargeUsed;
    }
}