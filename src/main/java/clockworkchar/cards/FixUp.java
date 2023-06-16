package clockworkchar.cards;

import clockworkchar.actions.EquipToolAction;
import clockworkchar.tools.Screwdriver;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class FixUp extends AbstractEasyCard {
    public final static String ID = makeID("FixUp");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("SelectToAttune")).TEXT;

    public FixUp() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        showDequipValue = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EquipToolAction(new Screwdriver()));
    }

    public void upp() {
        isInnate = true;
    }
}