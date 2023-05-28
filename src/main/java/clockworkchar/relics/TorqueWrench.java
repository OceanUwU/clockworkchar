package clockworkchar.relics;

import clockworkchar.cards.AbstractEasyCard;
import clockworkchar.characters.TheClockwork;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class TorqueWrench extends AbstractEasyRelic {
    public static final String ID = makeID("TorqueWrench");

    public TorqueWrench() {
        super(ID, RelicTier.SHOP, LandingSound.SOLID, TheClockwork.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
  
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof AbstractEasyCard && ((AbstractEasyCard)card).part) {
            flash();
            atb(((AbstractEasyCard)card).partActivation());
        }
    }
}