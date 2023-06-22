package clockworkchar.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import clockworkchar.cards.AbstractCrankyCard;

import static clockworkchar.CrankyMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class ThirdMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("m3");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).isThirdMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).thirdMagic;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractCrankyCard) {
            ((AbstractCrankyCard) card).isThirdMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).baseThirdMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).upgradedThirdMagic;
        }
        return false;
    }
}