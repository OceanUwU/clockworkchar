package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.att;

public class Gyroscope extends AbstractCrankyCard {
    public final static String ID = makeID("Gyroscope");

    public Gyroscope() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseSecondMagic = secondMagic = 6;
        baseSpinAmount = spinAmount = 2;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return canPlayUnplayablePart();
    }

    public void activate() {
        att(new SpinAction(spinAmount, spun -> {
            retain = spun;
            if (spun)
                att(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, secondMagic, DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }));
    }

    public void upp() {
        upgradeSecondMagic(3);
    }
}