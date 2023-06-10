package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class WormGear extends AbstractEasyCard {
    public final static String ID = makeID("WormGear");

    public WormGear() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new WormGearAction(magicNumber));
    }

    public void upp() {
        uDesc();
        upgradeMagicNumber(1);
    }

    public static class WormGearAction extends AbstractGameAction {
        private int activations;

        public WormGearAction(int activations) {
            this.activations = activations;
        }

        public void update() {
            att(new DrawCardAction(1, new AbstractGameAction() {
                public void update() {
                    if (DrawCardAction.drawnCards.size() > 0) {
                        AbstractCard drawn = DrawCardAction.drawnCards.get(0);
                        if (drawn instanceof AbstractEasyCard && ((AbstractEasyCard)drawn).part) {
                            att(new WormGearAction(activations));
                            for (int i = 0; i < activations; i++)
                                att(((AbstractEasyCard)drawn).partActivation());
                        }
                        else
                            att(new DiscardSpecificCardAction(drawn));
                    }
                    isDone = true;
                }
            }));
            isDone = true;
        }
    }
}