package clockworkchar.cards;

import clockworkchar.actions.AttuneAction;
import clockworkchar.actions.EquipToolAction;
import clockworkchar.tools.Torch;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
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
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EquipToolAction(new Torch()));
        if (upgraded)
            atb(new SelectCardsAction(p.drawPile.group, TEXT[0], c -> att(new AttuneAction(c.get(0)))));
    }

    public void upp() {
        uDesc();
    }
}