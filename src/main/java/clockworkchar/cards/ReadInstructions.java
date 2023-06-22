package clockworkchar.cards;

import clockworkchar.powers.ProficiencyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.applyToSelf;

public class ReadInstructions extends AbstractCrankyCard {
    public final static String ID = makeID("ReadInstructions");

    public ReadInstructions() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ProficiencyPower(p, magicNumber));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}