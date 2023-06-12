package clockworkchar.cards;

import clockworkchar.actions.WindUpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class LittleCranky extends AbstractEasyCard {
    public final static String ID = makeID("LittleCranky");

    public LittleCranky() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseMagicNumber = magicNumber = 8;
        trinket = true;
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
        upgradeMagicNumber(4);
    }
}