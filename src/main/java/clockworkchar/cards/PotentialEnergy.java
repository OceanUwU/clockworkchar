package clockworkchar.cards;

import clockworkchar.CrankyMod;
import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class PotentialEnergy extends AbstractCrankyCard {
    public final static String ID = makeID("PotentialEnergy");

    public PotentialEnergy() {
        super(ID, 3, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 5;
        baseSpinAmount = spinAmount = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int potential = CrankyMod.winder.charge / secondMagic;
        if (potential > 0)
            atb(new SpinAction(potential, spun -> {
                if (spun)
                    att(new GainEnergyAction(potential * magicNumber));
            }));
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}