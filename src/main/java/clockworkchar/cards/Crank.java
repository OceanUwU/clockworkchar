package clockworkchar.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import clockworkchar.actions.WindUpAction;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Crank extends AbstractCrankyCard {
    public final static String ID = makeID("Crank");

    public Crank() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 25;
        baseSecondMagic = secondMagic = 5;
        part = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new WindUpAction(magicNumber));
    }

    public void activate() {
        att(new WindUpAction(secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(10);
    }
}