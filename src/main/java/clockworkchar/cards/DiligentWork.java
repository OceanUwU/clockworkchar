package clockworkchar.cards;

import clockworkchar.actions.EasyXCostAction;
import clockworkchar.actions.UseToolAction;
import clockworkchar.powers.AbstractEasyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class DiligentWork extends AbstractCrankyCard {
    public final static String ID = makeID("DiligentWork");

    public DiligentWork() {
        super(ID, -1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = -1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            if (effect+magicNumber > 0)
                applyToSelfTop(new DiligentWorkPower(p, effect+magicNumber));
            return true;
        }));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    public static class DiligentWorkPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("DiligentWork");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public DiligentWorkPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }

        public void atStartOfTurnPostDraw() {
            flash();
            atb(new UseToolAction(amount));
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[amount == 1 ? 1 : 2];
        }
    }
}