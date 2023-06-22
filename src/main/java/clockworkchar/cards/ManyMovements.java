package clockworkchar.cards;

import clockworkchar.actions.SpinAction;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class ManyMovements extends AbstractCrankyCard {
    public final static String ID = makeID("ManyMovements");

    public ManyMovements() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 4;
        baseBlock = 4;
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 1;
        baseSpinAmount = spinAmount = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) {
                dmgTop(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
                atb(new SpinAction(spinAmount, false, spun2 -> {
                    if (spun2) {
                        blckTop();
                        atb(new SpinAction(spinAmount, false, spun3 -> {
                            if (spun3) {
                                applyToEnemyTop(m, new WeakPower(m, magicNumber, false));
                                atb(new SpinAction(spinAmount, false, spun4 -> {
                                    if (spun4) {
                                        applyToEnemyTop(m, new VulnerablePower(m, magicNumber, false));
                                        atb(new SpinAction(spinAmount, false, spun5 -> {
                                            if (spun5) att(new DrawCardAction(secondMagic));
                                        }));
                                    }
                                }));
                            }
                        }));
                    }
                }));
            }
        }));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(3);
    }
}