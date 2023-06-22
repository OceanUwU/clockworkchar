package clockworkchar.cards;

import clockworkchar.actions.WindUpAction;
import clockworkchar.powers.AbstractEasyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Inertia extends AbstractCrankyCard {
    public final static String ID = makeID("Inertia");

    public Inertia() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new InertiaPower(p, magicNumber));
        atb(new WindUpAction(secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    public static class InertiaPower extends AbstractEasyPower {
        public final static String POWER_ID = makeID("InertiaPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

        private int strengthApplied = 0;

        public InertiaPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + strengthApplied + powerStrings.DESCRIPTIONS[2];
        }

        public void onWindUp(int amountWoundUp) {
            if (strengthApplied < amount) {
                int toApply = amount - strengthApplied;
                applyToSelf(new StrengthPower(owner, toApply));
                strengthApplied += toApply;
            }
            updateDescription();
        }

        public void onSpin(int cost) {
            if (strengthApplied > 0) {
                applyToSelf(new StrengthPower(owner, -strengthApplied));
                strengthApplied = 0;
            }
            updateDescription();
        }
    }
}