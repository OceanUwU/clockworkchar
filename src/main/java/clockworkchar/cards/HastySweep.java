package clockworkchar.cards;

import basemod.BaseMod;
import basemod.interfaces.PostRenderSubscriber;
import clockworkchar.actions.SpinAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import java.util.ArrayList;
import java.util.Collections;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.CrankyMod.makeImagePath;
import static clockworkchar.util.Wiz.*;

public class HastySweep extends AbstractCrankyCard {
    public final static String ID = makeID("HastySweep");

    public HastySweep() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseSpinAmount = spinAmount = 3;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new HastySweepAction(p, m, damage, damageTypeForTurn));
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) {
                ArrayList<AbstractCard> group = (ArrayList<AbstractCard>)p.drawPile.group.clone();
                Collections.sort(group);
                att(new SelectCardsAction(group, magicNumber, cardStrings.EXTENDED_DESCRIPTION[0], true, c -> true, cards -> cards.stream().forEach(c -> att(new ExhaustSpecificCardAction(c, p.drawPile, true)))));
            }
                
        }));
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }

    public static class HastySweepAction extends AbstractGameAction implements PostRenderSubscriber {
        private static TextureRegion IMG = new TextureRegion(new Texture(makeImagePath("vfx/broom.png")));
        private static float W = IMG.getTexture().getWidth() / 2;
        private static float H = IMG.getTexture().getHeight() / 2;
        private static final float DURATION = 0.3f;

        private AbstractCreature source, target;
        private float x, y, rotation, alpha;
        private int damage;
        private DamageInfo.DamageType damageType;

        public HastySweepAction(AbstractPlayer source, AbstractMonster target, int damage, DamageInfo.DamageType damageType) {
            this.source = source;
            this.target = target;
            this.damage = damage;
            this.damageType = damageType;
            duration = 0f;
            BaseMod.subscribe(this);
        }

        public void update() {
            duration += Gdx.graphics.getDeltaTime();
            if (duration > DURATION) {
                isDone = true;
                BaseMod.unsubscribeLater(this);
                for (int i = 0; i < 2; i++)
                    att(new DamageAction(target, new DamageInfo(source, damage, damageType), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                vfxTop(new VerticalImpactEffect(target.hb.cX + target.hb.width / 4f, target.hb.cY - target.hb.height / 4f));
                vfxTop(new BroomFlyEffect(x, y, rotation));
                return;
            }
            float progress = duration / DURATION;
            alpha = Math.min(progress * 4, 1f);
            x = source.hb.cX + (target.hb.cX - source.hb.cX) * progress * 0.9f;
            y = source.hb.cY + (target.hb.cY - source.hb.cY) * progress;
            rotation = progress * 360f * 0.8f;
        }

        public void receivePostRender(SpriteBatch sb) {
            sb.setColor(new Color(1f, 1f, 1f, alpha));
            sb.draw(IMG, x - W, y - H, W, H, W*2, H*2, Settings.scale, Settings.scale, rotation);
        }

        public static class BroomFlyEffect extends AbstractGameEffect {
            private float x, y, rotation, alpha;
            private static final float DURATION = 0.8f;

            public BroomFlyEffect(float x, float y, float rotation) {
                duration = -0.3f;
                this.x = x;
                this.y = y;
                this.rotation = rotation;
                alpha = 1f;
            }

            public void update() {
                duration += Gdx.graphics.getDeltaTime();
                if (duration > 0f) {
                    x -= Gdx.graphics.getDeltaTime() * Settings.scale * 450f;
                    y += Gdx.graphics.getDeltaTime() * Settings.scale * 600f;
                    rotation += Gdx.graphics.getDeltaTime() * 360f;
                    alpha = 1 - (duration / DURATION);
                    if (duration > DURATION)
                        isDone = true;
                }
            }

            public void render(SpriteBatch sb) {
                sb.setColor(new Color(1f, 1f, 1f, alpha));
                sb.draw(IMG, x - W, y - H, W, H, W*2, H*2, Settings.scale, Settings.scale, rotation);
            }

            public void dispose() {}
        }
    }
} 