package clockworkchar.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import clockworkchar.cards.AbstractCrankyCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static clockworkchar.CrankyMod.makeID;

public class SecondMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("m2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).secondMagic;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractCrankyCard) {
            ((AbstractCrankyCard) card).isSecondMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).baseSecondMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).upgradedSecondMagic;
        }
        return false;
    }
}