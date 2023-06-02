package clockworkchar.cards;

import clockworkchar.actions.GainCogwheelsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;


public class Cogwheels extends AbstractEasyCard {
    public final static String ID = makeID("Cogwheels");

    public Cogwheels() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainCogwheelsAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}