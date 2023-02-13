package clockworkchar.cards;

import clockworkchar.actions.SpinAction;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class MechanicalManoeuvre extends AbstractEasyCard {
    public final static String ID = makeID("MechanicalManoeuvre");

    public MechanicalManoeuvre() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 6;
        baseSpinAmount = spinAmount = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new SpinAction(spinAmount, spun -> {
            if (spun)
                att(new GainEnergyAction(1));
        }));
    }

    public void upp() {
        upgradeBlock(2);
        upgradeSpinAmount(1);
    }
}