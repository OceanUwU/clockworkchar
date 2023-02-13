package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Whirl extends AbstractEasyCard {
    public final static String ID = makeID("Whirl");

    public Whirl() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 7;
        baseSpinAmount = spinAmount = 1;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("ATTACK_WHIRLWIND"));
        atb(new VFXAction(p, new WhirlwindEffect(), 0.1F));
        allDmg(AbstractGameAction.AttackEffect.NONE);
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) {
                att(new DrawCardAction(magicNumber));
            }
        }));
    }

    public void upp() {
        upgradeDamage(3);
    }
}