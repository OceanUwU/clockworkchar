package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Whir extends AbstractEasyCard {
    public final static String ID = makeID("Whir");
    
    private String originalDescription;

    public Whir() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        baseSpinAmount = spinAmount = 2;

        originalDescription = rawDescription;
        adjustDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            atb(new SpinAction(spinAmount, i == 0, spun -> {
                if (spun)
                    att(new DrawCardAction(1));
            }));
        }
    }

    public void adjustDescription() {
        rawDescription = originalDescription;
        for (int i = 1; i < magicNumber; i++)
            rawDescription += " NL " + originalDescription;
        super.initializeDescription();
    }

    public void upp() {
        upgradeMagicNumber(1);
        adjustDescription();
    }
}