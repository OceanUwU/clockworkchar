package clockworkchar.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;

public abstract class AbstractTool {
    private static final float SIZE = 98.0f;
    private static final float CENTRE = SIZE / 2.0f;
    private static final float NUM_X_OFFSET = 20.0F * Settings.scale;
    private static final float NUM_Y_OFFSET = -12.0F * Settings.scale;
    private static final Color USE_COLOR = Settings.CREAM_COLOR;
    private static final Color DEQUIP_COLOR = new Color(0.2F, 1.0F, 1.0F, 1.0F);
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

    public int passiveAmount;

    public AbstractTool(String id, String name, Texture texture) {
        this.id = id;
        this.name = name;
        updateDescription();
        applyPowers();
        color.a = 0.0f;
        this.texture = new TextureRegion(texture);
        if (AbstractDungeon.player != null) {
            cX = 120.0f * Settings.scale + AbstractDungeon.player.drawX;
            cY = 120.0f * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
            hb = new Hitbox(cX - SIZE / 2.0F, cY - SIZE / 2.0F, SIZE, SIZE);
        }
    }

    public abstract void use();

    public void dequip() {
        dequipped = true;
        xVel = MathUtils.random(-100f, 100f);
        yVel = MathUtils.random(-25f, 300f);
        dequipSpinSpeed = MathUtils.random(50f, 130f) * (MathUtils.random(0, 1) * 2 - 1);
    };

    public void applyPowers() {
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
        sb.draw(texture, cX - CENTRE, cY - CENTRE, CENTRE, CENTRE, SIZE, SIZE, 1.0f, 1.0f, angle);
    };

    public void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount)+(dequipping ? "x"+Integer.toString(DEQUIP_USE_TIMES) : ""), hb.cX + NUM_X_OFFSET, hb.cY + NUM_Y_OFFSET, dequipping ? DEQUIP_COLOR : USE_COLOR, fontScale);
    }

	public AbstractTool makeCopy() {
		try{
			return this.getClass().newInstance();
		} catch(InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("BaseMod failed to auto-generate makeCopy for tool: " + id);
		}
	}
}
