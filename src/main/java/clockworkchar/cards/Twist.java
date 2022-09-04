package clockworkchar.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import clockworkchar.actions.WindUpAction;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class Twist extends AbstractEasyCard {
    public final static String ID = makeID("Twist");

    public Twist() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new WindUpAction(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}