package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class RecursionMechanism extends AbstractEasyCard {
    public final static String ID = makeID("RecursionMechanism");

    public RecursionMechanism() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 11;
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        applyToSelf(new ArtifactPower(p, magicNumber));
    }

    public void upp() {
        upgradeDamage(4);
    }
}