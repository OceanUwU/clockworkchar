package clockworkchar.cards;

import clockworkchar.powers.AbstractEasyPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

import basemod.ReflectionHacks;

public class Mesmerise extends AbstractEasyCard {
    public final static String ID = makeID("Mesmerise");

    public Mesmerise() {
        super(ID, 3, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseBlock = 10;
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        forAllMonstersLiving(q -> {
            applyToEnemy(q, new WeakPower(q, magicNumber, false));
            applyToEnemy(q, new DizzyPower(q, secondMagic));
        });
        blck();
    }

    public void upp() {
        upgradeBaseCost(2);
    }

    public static class DizzyPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("Dizzy");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public DizzyPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.DEBUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }

        public void onInitialApplication() {
            doSpinEffect();
        }

        public void stackPower(int stackAmount) {
            super.stackPower(stackAmount);
            doSpinEffect();
        }

        private void doSpinEffect() {
            for (AbstractGameEffect e : AbstractDungeon.effectList) 
                if (e instanceof SpinCreatureEffect && ((SpinCreatureEffect)e).target == owner) {
                    ((SpinCreatureEffect)e).duration = SpinCreatureEffect.DURATION;
                    e.update();
                }
            AbstractDungeon.effectList.add(new SpinCreatureEffect(owner));
        };
  
        public int onAttacked(DamageInfo info, int damageAmount) {
            if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != owner) {
                flash();
                applyToEnemy(owner, new StrengthPower(owner, -amount));
                if (!owner.hasPower("Artifact"))
                    applyToEnemy(owner, new GainStrengthPower(owner, amount));
            }
            return damageAmount;
        }

        public static class SpinCreatureEffect extends AbstractGameEffect {
            public AbstractCreature target;
            private static float DURATION = 1.5f;
            private Bone rootBone;
            private float initialScale;

            public SpinCreatureEffect(AbstractCreature target) {
                this.target = target;
                duration = 0f;
                if (target != null) {
                    Skeleton skeleton = ReflectionHacks.getPrivate(target, AbstractCreature.class, "skeleton");
                    if (skeleton != null) {
                        rootBone = skeleton.getRootBone();
                        if (rootBone != null)
                            initialScale = skeleton.getRootBone().getScaleX();
                    }
                }
            }

            public void update() {
                if (rootBone == null) {
                    isDone = true;
                    return;
                }
                duration += Gdx.graphics.getDeltaTime();
                float progress = duration / DURATION;
                if (progress < 1f) {
                    float x = progress * 6f - 6.14f;
                    float scale = (float)Math.cos(x/(2f/x));
                    rootBone.setScaleX(initialScale * scale);
                } else {
                    isDone = true;
                    rootBone.setScaleX(initialScale * scale);
                }
            }

            public void render(SpriteBatch sb) {}
            public void dispose() {}
        }
    }
}