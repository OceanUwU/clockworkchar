package clockworkchar.relics;

import clockworkchar.actions.WindUpAction;
import clockworkchar.characters.TheClockwork;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class LeftHand extends AbstractEasyRelic {
    public static final String ID = makeID("LeftHand");

    private static final int WIND_AMOUNT = 8;

    public LeftHand() {
        super(ID, RelicTier.STARTER, LandingSound.HEAVY, TheClockwork.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + WIND_AMOUNT + this.DESCRIPTIONS[1];
    }
  
    public void atBattleStart() {
        flash();
        atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        atb(new WindUpAction(WIND_AMOUNT));
    }
}
