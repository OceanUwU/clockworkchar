package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import clockworkchar.actions.LetGoAction;
import clockworkchar.powers.AbstractEasyPower;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Reinforce extends AbstractEasyCard {
    public final static String ID = makeID("Reinforce");

    public Reinforce() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ReinforcedPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    public static class ReinforcedPower extends AbstractEasyPower {
        public final static String POWER_ID = makeID("ReinforcedPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

        public ReinforcedPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }

        public void atEndOfTurn(boolean isPlayer) {
            atb(new LetGoAction(spent -> att(new GainBlockAction(owner, owner, spent * amount))));
        }
    }
}