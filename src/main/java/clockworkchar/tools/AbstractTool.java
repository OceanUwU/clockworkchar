package clockworkchar.tools;

import clockworkchar.powers.Blinded;
import clockworkchar.powers.ProficiencyPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.util.Wiz.*;

public abstract class AbstractTool {
    private static final float SIZE = 98.0f;
    private static final float CENTRE = SIZE / 2.0f;
    private static final float NUM_X_OFFSET = 25.0F * Settings.scale;
    private static final float NUM_Y_OFFSET = 25.0F * Settings.scale;
    private static final Color DAMAGE_COLOR = Color.SALMON.cpy();
    private static final Color BLOCK_COLOR = Color.CYAN.cpy();
    private static final Color PASSIVE_COLOR = Color.ORANGE.cpy();
    private static final Color DEQUIP_COLOR = Color.CHARTREUSE.cpy();
    private static final float yAcceleration = -500f;
    public static final int DEQUIP_USE_TIMES = 2;
    private TextureRegion texture;
    public String id;
    public String name;
    public String description;
    public float cX = 0.0f;
    public float cY = 0.0f;
    public float fontScale = 0.01F;
    public Hitbox hb;
    public float angle;
    public float vfxTimer = 0.0F;
    public Color color = Color.WHITE.cpy();
    public boolean dequipping = false;
    public boolean dequipped = false;
    public boolean done = false;
    private float xVel;
    private float yVel;
    private float dequipSpinSpeed;

    public int baseBlock, baseDamage, basePassiveAmount, block, damage, passiveAmount;
    protected AbstractMonster target;

    public AbstractTool(String id, String name, Texture texture, int baseBlock, int baseDamage, int basePassiveAmount) {
        this.id = id;
        this.name = name;
        this.baseBlock = baseBlock;
        this.baseDamage = baseDamage;
        this.basePassiveAmount = basePassiveAmount;
        updateDescription();
        applyPowers();
        color.a = 0.0f;
        this.texture = new TextureRegion(texture);
        if (CardCrawlGame.isInARun()) {
            cX = 120.0f * Settings.scale + AbstractDungeon.player.drawX;
            cY = 120.0f * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
            hb = new Hitbox(cX - SIZE * Settings.scale / 2.0F, cY - SIZE * Settings.scale / 2.0F, SIZE * Settings.scale, SIZE * Settings.scale);
        }
    }

    public AbstractMonster getRandomTarget() {
        return AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
    }

    public int getDamage(AbstractCreature target) {
        return damage + pwrAmt(target, Blinded.POWER_ID);
    }

    protected void blckTop() {
        att(new GainBlockAction(adp(), block, true));
    }

    protected void dmgTop() {
        target = getRandomTarget();
        if (target != null)
            att(new DamageAction(target, new DamageInfo(adp(), damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true));
    }

    public void use() {
        dmgTop();
        blckTop();
    };

    public void dequip() {
        dequipped = true;
        xVel = MathUtils.random(-100f, 100f);
        yVel = MathUtils.random(-25f, 300f);
        dequipSpinSpeed = MathUtils.random(50f, 130f) * (MathUtils.random(0, 1) * 2 - 1);
    };

    public void showDequipValue() {
        dequipping = true;
        fontScale = 1.5f;
    }

    public void hideDequipValue() {
        dequipping = false;
    }

    public void applyPowers() {
        damage = baseDamage;
        block = baseBlock;
        passiveAmount = basePassiveAmount;
        if (CardCrawlGame.isInARun() && AbstractDungeon.player.hasPower(ProficiencyPower.POWER_ID)) {
            int proficiency = AbstractDungeon.player.getPower(ProficiencyPower.POWER_ID).amount;
            damage += proficiency;
            block += proficiency;
        }
        updateDescription();
    };

    public abstract void updateDescription();

    public void update() {
        hb.update();
        if (hb.hovered)
            TipHelper.renderGenericTip(hb.x + 96.0F * Settings.scale, hb.y + 64.0F * Settings.scale, name, description);
        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
    };

    public void updateAnimation() {
        vfxTimer += Gdx.graphics.getDeltaTime();
        color.a = MathHelper.fadeLerpSnap(color.a, 1.0f);
        if (dequipped) {
            cX += xVel * Gdx.graphics.getDeltaTime() * Settings.scale;
            cY += yVel * Gdx.graphics.getDeltaTime() * Settings.scale;
            if (cY < -500f)
                done = true;
            angle += dequipSpinSpeed * Gdx.graphics.getDeltaTime();
            yVel += yAcceleration * Gdx.graphics.getDeltaTime();
        }
    };

    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.draw(texture, cX - CENTRE, cY - CENTRE, CENTRE, CENTRE, SIZE, SIZE, Settings.scale, Settings.scale, angle);
    };

    public void renderText(SpriteBatch sb) {
        if (passiveAmount > 0)
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(passiveAmount), hb.cX, hb.cY + NUM_Y_OFFSET, dequipping ? DEQUIP_COLOR : PASSIVE_COLOR, fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(block), hb.cX - NUM_X_OFFSET, hb.cY - NUM_Y_OFFSET, dequipping ? DEQUIP_COLOR : BLOCK_COLOR, fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.damage), hb.cX + NUM_X_OFFSET, hb.cY - NUM_Y_OFFSET, dequipping ? DEQUIP_COLOR : DAMAGE_COLOR, fontScale);
    }

	public AbstractTool makeCopy() {
		try{
			return this.getClass().newInstance();
		} catch(InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("BaseMod failed to auto-generate makeCopy for tool: " + id);
		}
	}
}
