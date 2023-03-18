package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Tip extends AbstractEasyCard {
    public final static String ID = makeID("Tip");

    public Tip() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 1;
        baseSpinAmount = spinAmount = 15;
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        atb(new SpinAction(magicNumber, spun -> {
            if (spun) {
                Spinner s = new Spinner();
                if (upgraded) s.upgrade();
                makeInHand(s, 4);
            }
        }));
    }

    public void upp() {
        uDesc();
    }
}