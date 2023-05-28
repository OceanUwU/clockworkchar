package clockworkchar.cards;

import clockworkchar.ClockworkChar;
import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class PotentialEnergy extends AbstractEasyCard {
    public final static String ID = makeID("PotentialEnergy");

    public PotentialEnergy() {
        super(ID, 3, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        baseSpinAmount = spinAmount = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int potential = ClockworkChar.winder.charge / 5;
        if (potential > 0)
            atb(new SpinAction(potential, spun -> {
                if (spun)
                    att(new GainEnergyAction(potential));
            }));
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}