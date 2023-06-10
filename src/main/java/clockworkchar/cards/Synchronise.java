package clockworkchar.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import clockworkchar.actions.AttuneAction;
import clockworkchar.actions.SpinAction;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Synchronise extends AbstractEasyCard {
    public final static String ID = makeID("Synchronise");

    public Synchronise() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseSpinAmount = spinAmount = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], c -> c.type == CardType.SKILL || c.type == CardType.ATTACK, cards -> {
            AbstractCard c = cards.get(0);
            att(new SpinAction(baseSpinAmount, spun -> {
                if (spun) makeInHand(c.makeStatEquivalentCopy());
            }));
            att(new AttuneAction(c, true));
        }));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}