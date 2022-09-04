package clockworkchar.relics;

import clockworkchar.actions.WindUpAction;
import clockworkchar.characters.TheClockwork;
import clockworkchar.patches.HandPatches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.commons.lang3.StringUtils;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;


public class Drill extends AbstractEasyRelic {
    public static final String ID = makeID("Drill");

    private static final int WIND_AMOUNT = 1;

    public Drill() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheClockwork.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + WIND_AMOUNT + this.DESCRIPTIONS[1];
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        flash();
        atb(new WindUpAction(WIND_AMOUNT, false));
    }
    
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(LeftHand.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, LeftHand.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    HandPatches.switchHand();
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }
  
    public boolean canSpawn() {
      return AbstractDungeon.player.hasRelic(LeftHand.ID);
    }
}
