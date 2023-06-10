package clockworkchar.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import clockworkchar.actions.LetGoAction;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Reinforce extends AbstractEasyCard {
    public final static String ID = makeID("Reinforce");

    public Reinforce() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new LetGoAction(spent -> applyToSelfTop(new PlatedArmorPower(p, spent / magicNumber))));
    }

    public void upp() {
        upgradeMagicNumber(-1);
    }
}