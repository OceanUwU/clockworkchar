package clockworkchar.relics;

import clockworkchar.characters.TheClockwork;

import static clockworkchar.ClockworkChar.makeID;

public class OilCanister extends AbstractEasyRelic {
    public static final String ID = makeID("OilCanister");

    public static final int EXTRA_WIND_AMOUNT = 1;

    public OilCanister() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, TheClockwork.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + EXTRA_WIND_AMOUNT + this.DESCRIPTIONS[1];
    }
}