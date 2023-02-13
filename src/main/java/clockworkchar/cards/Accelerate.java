package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Accelerate extends AbstractEasyCard {
    public final static String ID = makeID("Accelerate");

    public Accelerate() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSpinAmount = spinAmount = 6;
        cardsToPreview = new Spinner();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
        atb(new SpinAction(spinAmount, spun -> {
            if (spun)
                att(new MakeTempCardInHandAction(new Spinner()));
        }));
    }

    public void upp() {
        uDesc();
        upgradeMagicNumber(1);
        upgradeSpinAmount(1);
    }
}