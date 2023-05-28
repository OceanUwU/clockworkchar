package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class GearRatio extends AbstractEasyCard {
    public final static String ID = makeID("GearRatio");

    public GearRatio() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseSpinAmount = spinAmount = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        atb(new SpinAction(baseSpinAmount, spun -> {
            if (spun)
                att(new ModifyDamageAction(this.uuid, this.baseDamage));
        }));
    }

    public void upp() {
        upgradeDamage(2);
    }
}