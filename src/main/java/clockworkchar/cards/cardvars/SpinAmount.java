package clockworkchar.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import clockworkchar.cards.AbstractCrankyCard;

import static clockworkchar.CrankyMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class SpinAmount extends DynamicVariable {

    @Override
    public String key() {
        return makeID("s");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).isSpinAmountModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).spinAmount;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractCrankyCard) {
            ((AbstractCrankyCard) card).isSpinAmountModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).baseSpinAmount;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).upgradedSpinAmount;
        }
        return false;
    }
}