package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
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
        baseSecondMagic = secondMagic = 2;
        baseSpinAmount = spinAmount = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new WormGearAction(magicNumber, spinAmount, secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    public static class WormGearAction extends AbstractGameAction {
        private int activations, spinAmount, scry;

        public WormGearAction(int activations, int spinAmount, int scry) {
            this.activations = activations;
            this.spinAmount = spinAmount;
            this.scry = scry;
        }

        public void update() {
            att(new DrawCardAction(1, new AbstractGameAction() {
                public void update() {
                    if (DrawCardAction.drawnCards.size() > 0) {
                        AbstractCard drawn = DrawCardAction.drawnCards.get(0);
                        if (drawn instanceof AbstractCrankyCard && ((AbstractCrankyCard)drawn).part) {
                            att(new WormGearAction(activations, spinAmount, scry));
                            att(((AbstractCrankyCard)drawn).partActivation(activations));
                        }
                        else
                            att(new DiscardSpecificCardAction(drawn));
                    }
                    isDone = true;
                }
            }));
            att(new SpinAction(spinAmount, spun -> {
                if (spun) att(new ScryAction(scry));
            }));
            isDone = true;
        }
    }
}