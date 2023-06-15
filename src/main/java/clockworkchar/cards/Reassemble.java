package clockworkchar.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Reassemble extends AbstractEasyCard {
    public final static String ID = makeID("Reassemble");

    public Reassemble() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            public void update() {
                if (upgraded)
                    for (AbstractCard c : p.drawPile.group)
                        if (c instanceof AbstractEasyCard && ((AbstractEasyCard)c).part)
                            atb(new FetchAction(p.drawPile, card -> card == c));
                for (AbstractCard c : p.discardPile.group)
                    if (c instanceof AbstractEasyCard && ((AbstractEasyCard)c).part)
                        atb(new DiscardToHandAction(c));
                isDone = true;
            }
        });
    }

    public void upp() {}
}