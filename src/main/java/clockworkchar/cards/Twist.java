package clockworkchar.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import clockworkchar.actions.UseToolAction;
import clockworkchar.actions.WindUpAction;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Twist extends AbstractEasyCard {
    public final static String ID = makeID("Twist");

    public Twist() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = 8;
        baseSecondMagic = secondMagic = 3;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new WindUpAction(magicNumber));
        atb(new UseToolAction());
    }

    public void activate() {
        att(new WindUpAction(secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(4);
        upgradeSecondMagic(3);
    }
}