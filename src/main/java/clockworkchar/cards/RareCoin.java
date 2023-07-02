package clockworkchar.cards;

import clockworkchar.actions.UseToolAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class RareCoin extends AbstractCrankyCard {
    public final static String ID = makeID("RareCoin");

    public RareCoin() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseMagicNumber = magicNumber = 3;
        trinket = true;
        part = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return canPlayUnplayablePart();
    }

    public void activate() {
        att(new UseToolAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}