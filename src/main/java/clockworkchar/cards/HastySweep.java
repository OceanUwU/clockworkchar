package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class HastySweep extends AbstractCrankyCard {
    public final static String ID = makeID("HastySweep");

    public HastySweep() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 26;
        baseSpinAmount = spinAmount = 10;
        baseMagicNumber = magicNumber = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SpinAction(spinAmount, spun -> {
            if (spun)
                dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        }));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeSpinAmount(2);
    }
} 