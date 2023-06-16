package clockworkchar.cards;

import clockworkchar.actions.EquipToolAction;
import clockworkchar.tools.Torch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class DazzlingDevice extends AbstractEasyCard {
    public final static String ID = makeID("DazzlingDevice");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("SelectToAttune")).TEXT;

    public DazzlingDevice() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        showDequipValue = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EquipToolAction(new Torch()));
    }

    public void upp() {
        isInnate = true;
    }
}