package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.att;

public class Spinner extends AbstractEasyCard {
    public final static String ID = makeID("Spinner");

    public Spinner() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ALL_ENEMY, CardColor.COLORLESS);
        baseSecondMagic = secondMagic = 6;
        baseMagicNumber = magicNumber = 3;
        damageType = DamageType.THORNS;
        damageTypeForTurn = DamageType.THORNS;
        isMultiDamage = true;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return canPlayUnplayablePart();
    }

    public void activate() {
        att(new AbstractGameAction() {
            public void update() {
                isDone = true;
                baseSecondMagic += magicNumber;
                secondMagic = baseSecondMagic;
            }
        });
        att(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(secondMagic, true), damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}