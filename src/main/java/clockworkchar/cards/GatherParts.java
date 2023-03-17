package clockworkchar.cards;

import basemod.ReflectionHacks;
import clockworkchar.ClockworkChar;
import clockworkchar.util.TexLoader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class GatherParts extends AbstractEasyCard {
    public final static String ID = makeID("GatherParts");

    public GatherParts() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 16;
        baseMagicNumber = magicNumber = 6;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GatherPartsAction(m, new DamageInfo(p, damage, damageTypeForTurn), magicNumber, this));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }

    private static class GatherPartsAction extends AbstractGameAction {
        private static final float DURATION = 0.1F;

        private int healing;
        private DamageInfo info;
        private GatherParts source;
        
        public GatherPartsAction(AbstractCreature target, DamageInfo info, int healingAmount, GatherParts source) {
            this.info = info;
            this.source = source;
            setValues(target, info);
            healing = healingAmount;
            actionType = ActionType.DAMAGE;
            duration = DURATION;
        }

        public void update() {
            if (this.duration == DURATION && this.target != null) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AttackEffect.SLASH_HEAVY));
                target.damage(info);
                if ((target.isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower("Minion")) {
                    for (int i = 0; i < healing; i++)
                        AbstractDungeon.effectList.add(new GainPartEffect(target));
                    AbstractDungeon.player.heal(healing);
                    for (AbstractGameAction action : AbstractDungeon.actionManager.actions) {
                        if (action instanceof UseCardAction) {
                            UseCardAction useAction = (UseCardAction)action;
                            if (ReflectionHacks.getPrivate(useAction, UseCardAction.class, "targetCard") == source) {
                                useAction.exhaustCard = true;
                                break;
                            }
                        }
                    }
                }
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                    AbstractDungeon.actionManager.clearPostCombatActions();
            }
            tickDuration();
        }

        private static class GainPartEffect extends GainPennyEffect {
            private static TextureRegion IMAGE = new TextureRegion(TexLoader.getTexture(ClockworkChar.modID + "Resources/images/vfx/part.png"));

            public GainPartEffect(AbstractCreature target) {
                super(AbstractDungeon.player, target.hb.cX, target.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, false);
                ReflectionHacks.setPrivate(this, GainPennyEffect.class, "img", IMAGE);
            }
            
            @Override
            public void render(SpriteBatch sb) {
                if ((float)ReflectionHacks.getPrivate(this, GainPennyEffect.class, "staggerTimer") > 0.0F)
                    return; 
                sb.setColor(this.color);
                sb.draw(IMAGE, (float)ReflectionHacks.getPrivate(this, GainPennyEffect.class, "x"), (float)ReflectionHacks.getPrivate(this, GainPennyEffect.class, "y"), IMAGE.getRegionWidth() / 2.0F, IMAGE.getRegionHeight() / 2.0F, (float)IMAGE.getRegionWidth(), (float)IMAGE.getRegionHeight(), scale, scale, rotation);
            }
        }
    }
}