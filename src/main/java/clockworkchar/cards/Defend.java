package clockworkchar.cards;

import clockworkchar.actions.WindUpAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Defend extends AbstractEasyCard implements BranchingUpgradesCard {
    public final static String ID = makeID("Defend");

    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 5;
        baseMagicNumber = magicNumber = 2;
        tags.add(CardTags.STARTER_DEFEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (upgraded && getUpgradeType() == UpgradeType.BRANCH_UPGRADE)
            atb(new WindUpAction(magicNumber));
    }

    public void upp() {
        if (isBranchUpgrade()) branchUpgrade();
        else baseUpgrade();
    }

    public void baseUpgrade() {
        upgradeBlock(3);
    }

    public void branchUpgrade() {
        uDesc();
    }
}