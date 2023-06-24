package clockworkchar.powers;

import clockworkchar.CrankyMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static clockworkchar.CrankyMod.makeID;

public class ProficiencyPower extends AbstractEasyPower {
    public static String POWER_ID = makeID("Proficiency");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public ProficiencyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        canGoNegative = true;
    }
    
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[amount > 0 ? 0 : 1] + Math.abs(amount) + powerStrings.DESCRIPTIONS[2];
        type = amount > 0 ? PowerType.BUFF : PowerType.DEBUFF;
    }

    public void onInitialApplication() {
        CrankyMod.toolSlot.tool.applyPowers();
    }

    public void stackPower(int stackAmount) {
        fontScale = 8f;
        amount += stackAmount;
        amount = Math.max(-999, Math.min(999, amount));
        if (this.amount == 0)
            addToTop((AbstractGameAction)new RemoveSpecificPowerAction(owner, owner, this));
        CrankyMod.toolSlot.tool.applyPowers();
    }

    public void reducePower(int stackAmount) {
        fontScale = 8f;
        amount -= stackAmount;
        amount = Math.max(-999, Math.min(999, amount));
        if (this.amount == 0)
            addToTop((AbstractGameAction)new RemoveSpecificPowerAction(owner, owner, this)); 
        CrankyMod.toolSlot.tool.applyPowers();
    }
}