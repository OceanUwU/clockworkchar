package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Tip extends AbstractEasyCard {
    public final static String ID = makeID("Tip");

    public Tip() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 10;
        baseSpinAmount = spinAmount = 6;
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new Spinner();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) applyToEnemyTop(m, new StrengthPower(m, -magicNumber));
        }));
    }

    public void upp() {
        upgradeDamage(5);
    }
}