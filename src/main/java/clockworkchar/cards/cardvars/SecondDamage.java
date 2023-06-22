package clockworkchar.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import clockworkchar.cards.AbstractCrankyCard;

import static clockworkchar.CrankyMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class SecondDamage extends DynamicVariable {

    @Override
    public String key() {
        return makeID("sd");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).isSecondDamageModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractCrankyCard) {
            ((AbstractCrankyCard) card).isSecondDamageModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).secondDamage;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).baseSecondDamage;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractCrankyCard) {
            return ((AbstractCrankyCard) card).upgradedSecondDamage;
        }
        return false;
    }
}