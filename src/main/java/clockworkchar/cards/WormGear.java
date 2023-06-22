package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class WormGear extends AbstractCrankyCard {
    public final static String ID = makeID("WormGear");

    public WormGear() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new WormGearAction(magicNumber));
    }

    public void upp() {
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
                        if (drawn instanceof AbstractCrankyCard && ((AbstractCrankyCard)drawn).part) {
                            att(new WormGearAction(activations));
                            att(((AbstractCrankyCard)drawn).partActivation(activations));
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