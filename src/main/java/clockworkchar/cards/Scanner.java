package clockworkchar.cards;

import clockworkchar.powers.AbstractEasyPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

import clockworkchar.actions.AttuneAction;

public class Scanner extends AbstractEasyCard {
    public final static String ID = makeID("Scanner");

    public Scanner() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ScannerPower(p, magicNumber));
    }

    public void upp() {
        upgradeBaseCost(2);
    }

    public static class ScannerPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("ScannerPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public ScannerPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount;
            if (amount == 1)
                description += powerStrings.DESCRIPTIONS[1];
            else
                description += powerStrings.DESCRIPTIONS[2];
        }

        public void onUseCard(AbstractCard c, UseCardAction action) {
            atb(new AttuneAction(c, amount, true));
            flash();
        }
    }
}