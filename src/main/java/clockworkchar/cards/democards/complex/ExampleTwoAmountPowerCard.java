package clockworkchar.cards.democards.complex;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.applyToSelf;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import clockworkchar.cards.AbstractEasyCard;
import clockworkchar.powers.ExampleTwoAmountPower;

public class ExampleTwoAmountPowerCard extends AbstractEasyCard {
    public final static String ID = makeID(ExampleTwoAmountPowerCard.class.getSimpleName());

    public ExampleTwoAmountPowerCard() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ExampleTwoAmountPower(p, magicNumber, secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }
}