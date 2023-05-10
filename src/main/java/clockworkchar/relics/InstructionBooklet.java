package clockworkchar.relics;

import clockworkchar.characters.TheClockwork;
import clockworkchar.powers.ProficiencyPower;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class InstructionBooklet extends AbstractEasyRelic {
    public static final String ID = makeID("InstructionBooklet");

    private static final int PROFICIENCY_AMOUNT = 1;

    public InstructionBooklet() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, TheClockwork.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + PROFICIENCY_AMOUNT + this.DESCRIPTIONS[1];
    }
  
    public void atBattleStart() {
        flash();
        applyToSelf(new ProficiencyPower(AbstractDungeon.player, PROFICIENCY_AMOUNT));
        atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
}
