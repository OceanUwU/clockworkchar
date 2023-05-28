package clockworkchar.cards;

import clockworkchar.actions.WindUpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.att;

public class Fidget extends AbstractEasyCard {
    public final static String ID = makeID("Fidget");

    public Fidget() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        selfRetain = true;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return canPlayUnplayablePart();
    }

    public void activate() {
        att(new WindUpAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}