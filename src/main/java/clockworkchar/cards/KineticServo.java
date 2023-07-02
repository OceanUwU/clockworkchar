package clockworkchar.cards;

import clockworkchar.actions.WindUpAction;
import clockworkchar.powers.AbstractEasyPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class KineticServo extends AbstractCrankyCard {
    public final static String ID = makeID("KineticServo");

    public KineticServo() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 12;
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new WindOnHit(p, magicNumber));
    }

    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(1);
    }

    public static class WindOnHit extends AbstractEasyPower {
        public final static String POWER_ID = makeID("WindOnHit");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

        public WindOnHit(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }

        public int onAttacked(DamageInfo info, int damageAmount) {
            if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
                flash();
                att(new WindUpAction(amount));
            }
            return damageAmount;
        }

        public void atStartOfTurn() {
            atb(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, POWER_ID));
        }
    }
}