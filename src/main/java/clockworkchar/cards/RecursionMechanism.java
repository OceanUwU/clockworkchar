package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class RecursionMechanism extends AbstractEasyCard {
    public final static String ID = makeID("RecursionMechanism");

    public RecursionMechanism() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 12;
        baseMagicNumber = magicNumber = 1;
        baseSpinAmount = spinAmount = 2;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        atb(new SpinAction(spinAmount, spun -> {
            if (spun)
                applyToSelfTop(new ArtifactPower(p, magicNumber));
        }));
    }

    public void upp() {
        upgradeDamage(4);
    }
}