package clockworkchar.relics;

import clockworkchar.cards.PeepTheHaul;
import clockworkchar.characters.Cranky;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class OilCanister extends AbstractEasyRelic {
    public static final String ID = makeID("OilCanister");

    public static final int TRINKETS_SHUFFLED = 2;

    public OilCanister() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, Cranky.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public void atBattleStart() {
        flash();
        atb(new RelicAboveCreatureAction(adp(), this));
        PeepTheHaul.PeepTheHaulPower.shuffleRandomTrinket(TRINKETS_SHUFFLED);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + TRINKETS_SHUFFLED + this.DESCRIPTIONS[1];
    }
}