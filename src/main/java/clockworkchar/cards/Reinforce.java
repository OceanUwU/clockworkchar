package clockworkchar.cards;

import clockworkchar.actions.LetGoAction;
import clockworkchar.CrankyMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Reinforce extends AbstractCrankyCard {
    public final static String ID = makeID("Reinforce");

    public Reinforce() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        baseSecondMagic = secondMagic = 0;
        baseSpinAmount = spinAmount = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new LetGoAction(spent -> {
            if (spent / magicNumber > 0)
                applyToSelfTop(new PlatedArmorPower(p, spent / magicNumber));
        }));
    }

    public void applyPowers() {
        baseSecondMagic = secondMagic = CrankyMod.winder.maxChargeUsedOnCard(this) / magicNumber;
    }

    public void upp() {
        upgradeMagicNumber(-1);
    }
}