package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.att;

public class Gyroscope extends AbstractEasyCard {
    public final static String ID = makeID("Gyroscope");

    public Gyroscope() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseDamage = 6;
        baseSpinAmount = spinAmount = 2;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public void activate() {
        att(new SpinAction(spinAmount, spun -> {
            retain = spun;
            if (spun)
                att(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, baseDamage, DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }));
    }

    public void upp() {
        upgradeDamage(3);
    }
}