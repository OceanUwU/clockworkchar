package clockworkchar.patches;

import clockworkchar.CrankyMod;
import clockworkchar.powers.ProficiencyPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;

@SpirePatch(clz=SpireShield.class, method="takeTurn")
public class SpearTakeProficiencyPatch {
    @SpireInsertPatch(rloc=8)
    public static SpireReturn<Void> Insert(SpireShield __instance) {
        if (CrankyMod.toolSlot.shouldRender && AbstractDungeon.aiRng.randomBoolean()) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new ProficiencyPower(AbstractDungeon.player, -1), -1));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}