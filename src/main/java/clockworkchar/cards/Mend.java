package clockworkchar.cards;

import clockworkchar.actions.UseToolAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Mend extends AbstractEasyCard {
    public final static String ID = makeID("Mend");

    public Mend() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new UseToolAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}