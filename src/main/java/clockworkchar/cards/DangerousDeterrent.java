package clockworkchar.cards;

import clockworkchar.vfx.BreakEffect;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class DangerousDeterrent extends AbstractEasyCard {
    public final static String ID = makeID("DangerousDeterrent");

    public DangerousDeterrent() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        baseSecondMagic = secondMagic = 4;
        exhaust = true;
        cardsToPreview = new Spinner();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        vfx(new BreakEffect());
        atb(new LoseHPAction(p, p, secondMagic));
        makeInHand(new Spinner(), magicNumber);
    }

    public void upp() {
        upgradeSecondMagic(-2);
    }
}