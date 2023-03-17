package clockworkchar.cards;

import clockworkchar.actions.WindUpAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Strike extends AbstractEasyCard implements BranchingUpgradesCard {
    public final static String ID = makeID("Strike");

    public Strike() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 2;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (upgraded && getUpgradeType() == UpgradeType.BRANCH_UPGRADE)
            atb(new WindUpAction(magicNumber));
    }

    public void upp() {
        if (isBranchUpgrade()) branchUpgrade();
        else baseUpgrade();
    }

    public void baseUpgrade() {
        upgradeDamage(3);
    }

    public void branchUpgrade() {
        uDesc();
    }
}