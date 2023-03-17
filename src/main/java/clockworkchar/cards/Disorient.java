package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Disorient extends AbstractEasyCard {
    public final static String ID = makeID("Disorient");

    public Disorient() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
        baseSpinAmount = spinAmount = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) {
                att(new SpinAction(spinAmount, spun2 -> {
                    if (spun2) applyToEnemyTop(m, new VulnerablePower(m, magicNumber, false));
                }));
                applyToEnemyTop(m, new WeakPower(m, magicNumber, false));
            }
        }));
    }

    public void upp() {
        upgradeSpinAmount(1);
    }
}