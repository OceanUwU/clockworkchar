package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import clockworkchar.powers.ProficiencyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class ReadInstructions extends AbstractCrankyCard {
    public final static String ID = makeID("ReadInstructions");

    public ReadInstructions() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSpinAmount = spinAmount = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) applyToSelfTop(new ProficiencyPower(p, magicNumber));
        }));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}