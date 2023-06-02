package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class HastySweep extends AbstractEasyCard {
    public final static String ID = makeID("HastySweep");

    public HastySweep() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 30;
        baseSpinAmount = spinAmount = 10;
        baseMagicNumber = magicNumber = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new SpinAction(spinAmount, spun -> {
            if (!spun)
                applyToSelfTop(new WeakPower(p, magicNumber, false));
        }));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeSpinAmount(3);
    }
}