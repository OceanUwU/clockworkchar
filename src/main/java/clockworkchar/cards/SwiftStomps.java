package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class SwiftStomps extends AbstractEasyCard {
    public final static String ID = makeID("SwiftStomps");

    public SwiftStomps() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 4;
        baseSpinAmount = spinAmount = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) {
                att(new SpinAction(spinAmount, false, spun2 -> {
                    if (spun2)
                        dmgTop(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
                }));
                dmgTop(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
            }
        }));
    }

    public void upp() {
        upgradeDamage(1);
    }
}