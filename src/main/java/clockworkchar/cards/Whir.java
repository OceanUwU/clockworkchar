package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Whir extends AbstractCrankyCard {
    public final static String ID = makeID("Whir");

    public Whir() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 1;
        baseThirdMagic = thirdMagic = 2;
        baseSpinAmount = spinAmount = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            atb(new SpinAction(spinAmount, true, spun -> {
                if (spun)
                    att(new DrawCardAction(thirdMagic));
            }));
        for (int i = 0; i < magicNumber; i++) {
            atb(new SpinAction(spinAmount, !upgraded && i == 0, spun -> {
                if (spun)
                    att(new DrawCardAction(secondMagic));
            }));
        }
    }

    public void upp() {
        upgradeMagicNumber(-1);
    }
}