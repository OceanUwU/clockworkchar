package clockworkchar.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import clockworkchar.CrankyMod;
import clockworkchar.cards.AbstractCrankyCard;

public class ToolSlotPatches {
    @SpirePatch(clz=AbstractPlayer.class, method="renderPlayerBattleUi")
    public static class RenderPatch {
        public static void Prefix(AbstractPlayer __instance, SpriteBatch sb) {
            if (CrankyMod.toolSlot.shouldRender)
                CrankyMod.toolSlot.render(sb);
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="combatUpdate")
    public static class UpdatePatch {
        public static void Postfix() {
            if (CrankyMod.toolSlot.shouldRender)
                CrankyMod.toolSlot.update();
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="update")
    public static class UpdateAnimationPatch {
        public static void Postfix() {
            if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.EVENT && CrankyMod.toolSlot.shouldRender)
                CrankyMod.toolSlot.updateAnimation();
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="preBattlePrep")
    public static class ResetPatch {
        public static void Prefix() {
            CrankyMod.toolSlot.reset();
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="updateInput")
    public static class HoverPatch {
        @SpireInsertPatch(loc=924)
        public static void Insert(AbstractPlayer p) {
            if (p.hoveredCard instanceof AbstractCrankyCard && ((AbstractCrankyCard)p.hoveredCard).showDequipValue)
                CrankyMod.toolSlot.tool.showDequipValue();
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="manuallySelectCard")
    public static class SelectPatch {
        @SpireInsertPatch(loc=1570)
        public static void Insert(AbstractPlayer p) {
            if (p.hoveredCard instanceof AbstractCrankyCard && ((AbstractCrankyCard)p.hoveredCard).showDequipValue)
                CrankyMod.toolSlot.tool.showDequipValue();
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="releaseCard")
    public static class ReleasePatch {
        public static void Prefix(AbstractPlayer p) {
            CrankyMod.toolSlot.tool.hideDequipValue();
        }
    }
}