package clockworkchar.cards;

import clockworkchar.powers.AbstractEasyPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class GreasedCogs extends AbstractCrankyCard {
    public final static String ID = makeID("GreasedCogs");

    public GreasedCogs() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GreasedCogsPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    public static class GreasedCogsPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("GreasedCogsPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public GreasedCogsPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }

        public void onSpin(int cost) {
            atb(new GainBlockAction(adp(), amount));
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }
    }
        
    /*public static class TwistyPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("Twisty");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        
        public TwistyPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }

        public void atStartOfTurn() {
            atb(new RemoveSpecificPowerAction(owner, owner, this));
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + (amount + 1) + powerStrings.DESCRIPTIONS[1];
        }
    }*/
}