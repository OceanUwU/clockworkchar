package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Waddle extends AbstractCrankyCard {
    public final static String ID = makeID("Waddle");

    public Waddle() {
        super(ID, 0, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 7;
        baseBlock = 6;
        baseSpinAmount = spinAmount = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) {
                att(new SpinAction(spinAmount, false, spun2 -> {
                    if (spun2) blckTop();
                }));
                dmgTop(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
            }
        }));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}