package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class GearRatio extends AbstractEasyCard {
    public final static String ID = makeID("GearRatio");

    public GearRatio() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseSpinAmount = spinAmount = 4;
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) {
                baseMagicNumber += secondMagic;
                magicNumber = baseMagicNumber;
            }
        }));
    }

    public void upp() {
        upgradeDamage(2);
    }
}