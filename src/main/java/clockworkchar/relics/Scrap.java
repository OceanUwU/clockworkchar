package clockworkchar.relics;

import clockworkchar.characters.TheClockwork;
import clockworkchar.powers.CogwheelPower;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;
import static clockworkchar.util.Wiz.applyToSelfTop;

public class Scrap extends AbstractEasyRelic {
    public static final String ID = makeID("Scrap");

    private static final int COGWHEELS = 1;

    public Scrap() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, TheClockwork.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + COGWHEELS + this.DESCRIPTIONS[1];
    }
  
    public void atBattleStartPreDraw() {
        flash();
        atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        applyToSelfTop(new CogwheelPower(AbstractDungeon.player, COGWHEELS));
    }
}