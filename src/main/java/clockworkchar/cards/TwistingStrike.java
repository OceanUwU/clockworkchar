package clockworkchar.cards;

import clockworkchar.actions.WindUpAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class TwistingStrike extends AbstractEasyCard {
    public final static String ID = makeID("TwistingStrike");

    public TwistingStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = magicNumber = 3;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new WindUpAction(magicNumber));
    }

    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(2);
    }
}