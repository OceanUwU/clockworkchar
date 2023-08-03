package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Resonance extends AbstractCrankyCard {
    public final static String ID = makeID("Resonance");

    public Resonance() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 5;
        baseSpinAmount = spinAmount = 4;
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) baseBlock += magicNumber;
        }));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}