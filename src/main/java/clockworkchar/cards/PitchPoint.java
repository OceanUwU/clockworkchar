package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

import clockworkchar.actions.WindUpAction;

public class PitchPoint extends AbstractCrankyCard {
    public final static String ID = makeID("PitchPoint");

    public PitchPoint() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 4;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        atb(new WindUpAction(magicNumber));
    }

    public void triggerInDiscardPileOnSpin() {
        atb(new DiscardToHandAction(this));
    }

    public void upp() {
        upgradeDamage(2);
    }
}