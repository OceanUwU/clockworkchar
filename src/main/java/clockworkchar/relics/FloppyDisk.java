package clockworkchar.relics;

import clockworkchar.CrankyMod;
import clockworkchar.characters.Cranky;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.atb;

public class FloppyDisk extends AbstractEasyRelic {
    public static final String ID = makeID("FloppyDisk");

    private static final int STARTING_CHARGE = 20;
    public static final int MAX_CHARGE = 50;

    public FloppyDisk() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT, Cranky.Enums.CLOCKWORK_BROWN_COLOR);
    }
  
    public void onEquip() {
        this.counter = STARTING_CHARGE;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + MAX_CHARGE + this.DESCRIPTIONS[1] + STARTING_CHARGE + this.DESCRIPTIONS[2];
    }
  
    public void atBattleStart() {
        flash();
        atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.counter = -1;
    }

    public void onVictory() {
        this.counter = Math.min(CrankyMod.winder.charge, MAX_CHARGE);
    }
}
