package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class EmergencyStop extends AbstractEasyCard {
    public final static String ID = makeID("EmergencyStop");

    public EmergencyStop() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 28;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        damage = Math.min(damage, (m.currentHealth + m.currentBlock) - magicNumber);
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        atb(new PressEndTurnButtonAction());
    }

    public void upp() {
        upgradeDamage(7);
    }
}