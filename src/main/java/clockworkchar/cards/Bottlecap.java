package clockworkchar.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;

public class Bottlecap extends AbstractEasyCard {
    public final static String ID = makeID("Bottlecap");

    public Bottlecap() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseBlock = 8;
        trinket = true;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return canPlayUnplayablePart();
    }

    public void activate() {
        blckTop();
    }

    public void upp() {
        upgradeBlock(4);
    }
}