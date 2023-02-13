package clockworkchar.cards;

import clockworkchar.actions.WindUpAction;
import clockworkchar.powers.BatteryPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class MunchBattery extends AbstractEasyCard {
    public final static String ID = makeID("MunchBattery");

    public MunchBattery() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 1;
        baseThirdMagic = thirdMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new WindUpAction(magicNumber));
        applyToSelf(new DexterityPower(p, secondMagic));
        applyToSelf(new BatteryPower(p, thirdMagic));
    }

    public void upp() {
        upgradedThirdMagic(1);
    }
}