package clockworkchar.relics;

import clockworkchar.characters.TheClockwork;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class BeltDrive extends AbstractEasyRelic {
    public static final String ID = makeID("BeltDrive");

    private static final int SKILLS_NEEDED = 3;
    private static final int CARDS_DRAWN = 2;

    public BeltDrive() {
        super(ID, RelicTier.UNCOMMON, LandingSound.HEAVY, TheClockwork.Enums.CLOCKWORK_BROWN_COLOR);
        counter = 0;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + SKILLS_NEEDED + this.DESCRIPTIONS[1] + CARDS_DRAWN + this.DESCRIPTIONS[2];
    }
  
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            if (++counter >= SKILLS_NEEDED) {
                counter = 0;
                flash();
                pulse = false;
                atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                atb(new DrawCardAction(CARDS_DRAWN));
            } else if (counter == SKILLS_NEEDED - 1) {
                beginPulse();
                pulse = true;
            }
        } else {
            counter = 0;
            pulse = false;
        }
    }
}
