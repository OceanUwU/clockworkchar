package clockworkchar.tools;

import clockworkchar.ClockworkChar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.WallopEffect;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Spanner extends AbstractTool {
    private static String TOOL_ID = makeID("Spanner");
    private static OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(TOOL_ID);
    private static Texture SPANNER_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/spanner.png"));
    private static float SPIN_SPEED = 40.0F;
    private static float FLY_SPIN_SPEED = 280.0F;

    private boolean flyingTowardEnemy = false;
    public boolean returning = false;

    public Spanner() {
        super(TOOL_ID, orbStrings.NAME, SPANNER_TEXTURE, 1, 3, 0);
    }

    public void use() {
        att(new ChuckSpannerAction(this, damage));
        blckTop();
    }

    public void updateDescription() {
        description = orbStrings.DESCRIPTION[0] + block + orbStrings.DESCRIPTION[1] + damage + orbStrings.DESCRIPTION[2];
    }

    public void updateAnimation() {
        super.updateAnimation();
        if (!dequipped)
            angle += Gdx.graphics.getDeltaTime() * (flyingTowardEnemy ? FLY_SPIN_SPEED : SPIN_SPEED);
    }

    private static class ChuckSpannerAction extends AbstractGameAction {
        private static float DURATION = 0.2F;

        private float timer = 0.0F;
        private float length = DURATION;
        private Spanner spanner;
        private int damage;
        private AbstractMonster target;
        private float oX; //original x
        private float oY; //original y
        private float tX; //target x
        private float tY; //target y

        public ChuckSpannerAction(Spanner spanner, int damage) {
            this.spanner = spanner;
            this.damage = damage;
        }

        public void update() {
            if (timer == 0f) {
                target = spanner.getRandomTarget();
                if (target == null) {
                    isDone = true;
                    return;
                }
                oX = spanner.cX;
                oY = spanner.cY;
                tX = target.hb.cX;
                tY = target.hb.cY;
                if (spanner.returning)
                    length /= 3f;
            }
            spanner.flyingTowardEnemy = true;
            timer += Gdx.graphics.getDeltaTime();
            if (timer >= length) {
                timer = length;
                spanner.flyingTowardEnemy = false;
                vfxTop(new WallopEffect(damage, tX, tY));
                att(new DamageAction(target, new DamageInfo(source, spanner.getDamage(target), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true));
                vfxTop(new SpannerReturnEffect(spanner));
                CardCrawlGame.sound.play("BLUNT_FAST");
                isDone = true;
            }
            spanner.cX = oX + (tX - oX) * (timer / length);
            spanner.cY = oY + (tY - oY) * (timer / length);
        }

        private static class SpannerReturnEffect extends AbstractGameEffect {
            private static final float DURATION = 0.9f;
            private static final float JUMP_HEIGHT = 80.0f;

            private Spanner spanner;
            private float oX; //original x
            private float oY; //original y
            private float tX; //target x
            private float tY; //target y
            private float cY; //centerpoint y
            private float timer = 0.0f;

            public SpannerReturnEffect(Spanner spanner) {
                this.spanner = spanner;
                oX = spanner.cX;
                oY = spanner.cY;
                tX = spanner.hb.cX;
                tY = spanner.hb.cY;
                cY = tY + JUMP_HEIGHT * 2f * Settings.scale;
            }

            public void update() {
                spanner.returning = true;
                if (spanner.flyingTowardEnemy || spanner.dequipped) {
                    isDone = true;
                    return;
                }
                timer += Gdx.graphics.getDeltaTime();
                if (timer >= DURATION) {
                    timer = DURATION;
                    spanner.returning = false;
                    isDone = true;
                }
                float t = timer / DURATION; //time elapsed from 0 (just started) to 1 (just completed)
                spanner.cX = oX + (tX - oX) * t;
                t *= 2f;
                if (t > 1f) {
                    t -= 1f;
                    spanner.cY = cY + (tY - cY) * (1 - (float)Math.cos((t * Math.PI / 2)));
                } else
                    spanner.cY = oY + (cY - oY) * ((float)Math.sin((t * Math.PI / 2)));
            }

            public void render(SpriteBatch sb) {};
            public void dispose() {};
        }
    }
}