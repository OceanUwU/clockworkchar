package clockworkchar.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import clockworkchar.ClockworkChar;

public class ToolSlotPatches {
    @SpirePatch(clz=AbstractPlayer.class, method="render")
    public static class RenderPatch {
        @SpireInsertPatch(loc=2113)
        public static void Insert(AbstractPlayer __instance, SpriteBatch sb) {
            if (ClockworkChar.toolSlot.shouldRender)
                ClockworkChar.toolSlot.render(sb);
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="combatUpdate")
    public static class UpdatePatch {
        public static void Postfix() {
            if (ClockworkChar.toolSlot.shouldRender)
                ClockworkChar.toolSlot.update();
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="update")
    public static class UpdateAnimationPatch {
        public static void Postfix() {
            if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.EVENT && ClockworkChar.toolSlot.shouldRender)
                ClockworkChar.toolSlot.updateAnimation();
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="preBattlePrep")
    public static class ResetPatch {
        public static void Prefix() {
            ClockworkChar.toolSlot.reset();
        }
    }
}