package clockworkchar.cards;

import clockworkchar.actions.EasyXCostAction;
import clockworkchar.actions.WindUpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class AsFarAsItGoes extends AbstractEasyCard {
    public final static String ID = makeID("AsFarAsItGoes");

    public AsFarAsItGoes() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 8;
        baseMagicNumber = magicNumber = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount)
            this.energyOnUse = EnergyPanel.totalCount;
        atb(new EasyXCostAction(this, (effect, params) -> {
            if (effect > 1)
                blckTop();
            att(new WindUpAction(magicNumber * effect + pwrAmt(p, "Strength")));
            return true;
        }));
    }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeBlock(1);
    }
}