package clockworkchar.relics;

import clockworkchar.characters.Cranky;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.atb;

public class Gearbox extends AbstractEasyRelic {
    public static final String ID = makeID("Gearbox");

    public Gearbox() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT, Cranky.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atTurnStart() {
        grayscale = false;
    }
  
    public void onWind(int amount) {
        if (!grayscale) {
            grayscale = true;
            flash();
            atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            atb(new GainEnergyAction(1));
        }
    }

    public void onVictory() {
        grayscale = false;
    }
}
