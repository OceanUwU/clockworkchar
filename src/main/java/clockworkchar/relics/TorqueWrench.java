package clockworkchar.relics;

import clockworkchar.cards.AbstractCrankyCard;
import clockworkchar.characters.Cranky;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class TorqueWrench extends AbstractEasyRelic {
    public static final String ID = makeID("TorqueWrench");

    public TorqueWrench() {
        super(ID, RelicTier.SHOP, LandingSound.SOLID, Cranky.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
  
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof AbstractCrankyCard && ((AbstractCrankyCard)card).part) {
            flash();
            atb(((AbstractCrankyCard)card).partActivation());
        }
    }
}