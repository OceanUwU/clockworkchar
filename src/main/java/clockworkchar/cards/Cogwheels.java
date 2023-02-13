package clockworkchar.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import clockworkchar.powers.CogwheelPower;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.applyToSelf;

public class Cogwheels extends AbstractEasyCard {
    public final static String ID = makeID("Cogwheels");

    public Cogwheels() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CogwheelPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}