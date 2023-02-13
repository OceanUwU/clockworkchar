package clockworkchar.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

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
                isDone = true;
                ArrayList<AbstractCard> cardsToPut = new ArrayList<>();
                if (upgraded) {
                    for (AbstractCard c : p.drawPile.group)
                        if (c instanceof AbstractEasyCard && ((AbstractEasyCard)c).part && p.hand.size() < BaseMod.MAX_HAND_SIZE) {
                            p.hand.addToHand(c);
                            cardsToPut.add(c);
                        }
                    for (AbstractCard c : cardsToPut)
                        p.drawPile.removeCard(c);
                    cardsToPut.clear();
                }
                for (AbstractCard c : p.discardPile.group)
                    if (c instanceof AbstractEasyCard && ((AbstractEasyCard)c).part && p.hand.size() < BaseMod.MAX_HAND_SIZE) {
                        p.hand.addToHand(c);
                        cardsToPut.add(c);
                    }
                for (AbstractCard c : cardsToPut)
                    p.discardPile.removeCard(c);
            }
        });
    }

    public void upp() {}
}