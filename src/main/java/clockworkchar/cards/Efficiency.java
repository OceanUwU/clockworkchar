package clockworkchar.cards;

import clockworkchar.powers.AbstractEasyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.applyToSelf;

public class Efficiency extends AbstractEasyCard {
    public final static String ID = makeID("Efficiency");

    public Efficiency() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new EfficiencyPower(p, magicNumber));
    }

    public void upp() {
        upgradeBaseCost(1);
    }

    public static class EfficiencyPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("EfficiencyPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public EfficiencyPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount;
            if (amount == 1)
                description += powerStrings.DESCRIPTIONS[1];
            else
                description += powerStrings.DESCRIPTIONS[2];
        }
    }
}