package clockworkchar.relics;

import static clockworkchar.ClockworkChar.makeID;

import clockworkchar.characters.TheClockwork;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheClockwork.Enums.CLOCKWORK_BROWN_COLOR);
    }
}
