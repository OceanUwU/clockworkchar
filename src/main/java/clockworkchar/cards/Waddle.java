package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import clockworkchar.actions.SpinAction;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class Waddle extends AbstractEasyCard {
    public final static String ID = makeID("Waddle");

    public Waddle() {
        super(ID, 0, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 5;
        baseBlock = 4;
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SpinAction(magicNumber, spun -> {
            if (spun) {
                dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
                atb(new SpinAction(magicNumber, false, spun2 -> {
                    if (spun2) blck();
                }));
            }
        }));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}