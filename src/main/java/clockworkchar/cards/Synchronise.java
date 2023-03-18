package clockworkchar.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import clockworkchar.actions.LetGoAction;
import clockworkchar.actions.WindUpAction;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class Synchronise extends AbstractEasyCard {
    public final static String ID = makeID("Synchronise");

    public Synchronise() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 18;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new LetGoAction(spent -> {}));
        atb(new WindUpAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(6);
    }
}