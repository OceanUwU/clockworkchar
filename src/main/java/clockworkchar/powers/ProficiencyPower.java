package clockworkchar.powers;

import clockworkchar.ClockworkChar;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static clockworkchar.ClockworkChar.makeID;

public class ProficiencyPower extends AbstractEasyPower {
    public static String POWER_ID = makeID("Proficiency");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public ProficiencyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
    }
    
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }

    public void onInitialApplication() {
        ClockworkChar.toolSlot.tool.applyPowers();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        ClockworkChar.toolSlot.tool.applyPowers();
    }

    public void reducePower(int stackAmount) {
        super.reducePower(stackAmount);
        ClockworkChar.toolSlot.tool.applyPowers();
    }
}