package clockworkchar.cards;

import clockworkchar.actions.SpinAction;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class MechanicalManoeuvre extends AbstractCrankyCard {
    public final static String ID = makeID("MechanicalManoeuvre");

    public MechanicalManoeuvre() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 8;
        baseSpinAmount = spinAmount = 4;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new SpinAction(spinAmount, spun -> {
            if (spun)
                att(new GainEnergyAction(magicNumber));
        }));
    }

    public void upp() {
        upgradeBlock(3);
        upgradeSpinAmount(1);
    }
}