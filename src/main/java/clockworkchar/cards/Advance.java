package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Advance extends AbstractEasyCard {
    public final static String ID = makeID("Advance");

    public Advance() {
        this(true);
    }

    public Advance(boolean real) {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseSpinAmount = spinAmount = 9;
        exhaust = true;
        if (real)
            cardsToPreview = new Advance(false);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(1));
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) {
                att(new GainEnergyAction(1));
                Advance card = new Advance(true);
                if (upgraded) card.upgrade();
                att(new MakeTempCardInHandAction(card));
            }
        }));
    }

    public void upp() {
        uDesc();
        upgradeSpinAmount(3);
        if (cardsToPreview != null)
            cardsToPreview.upgrade();
    }
}