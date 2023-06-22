package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class WearAndTear extends AbstractCrankyCard {
    public final static String ID = makeID("WearAndTear");

    public WearAndTear() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 8;
        baseSecondMagic = secondMagic = 3;
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < secondMagic; i++)
            dmg(m, AbstractGameAction.AttackEffect.SMASH);
    }

    public void activate() {
        applyToSelfTop(new StrengthPower(adp(), magicNumber));
    }

    public void upp() {
        upgradeDamage(2);
    }
}