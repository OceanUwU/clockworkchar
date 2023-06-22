package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Advance extends AbstractCrankyCard {
    public final static String ID = makeID("Advance");

    public Advance() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseSpinAmount = spinAmount = 6;
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
        baseThirdMagic = thirdMagic = 2;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
        Advance c = this;
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) {
                att(new GainEnergyAction(secondMagic));
                baseSpinAmount += thirdMagic;
                spinAmount = baseSpinAmount;
                att(new MakeTempCardInHandAction(c));
            }
        }));
    }

    public void upp() {
        upgradeSpinAmount(2);
        if (cardsToPreview != null)
            cardsToPreview.upgrade();
    }
}