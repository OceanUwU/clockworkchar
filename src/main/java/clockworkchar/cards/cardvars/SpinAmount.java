package clockworkchar.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import clockworkchar.cards.AbstractEasyCard;

import static clockworkchar.ClockworkChar.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class SpinAmount extends DynamicVariable {

    @Override
    public String key() {
        return makeID("s");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).isSpinAmountModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).spinAmount;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractEasyCard) {
            ((AbstractEasyCard) card).isSpinAmountModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).baseSpinAmount;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).upgradedSpinAmount;
        }
        return false;
    }
}