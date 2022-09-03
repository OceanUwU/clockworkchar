package clockworkchar.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import clockworkchar.ClockworkChar;

public class WinderPatches {
    private static boolean shouldRenderWinder() {
        return CardCrawlGame.dungeon != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && ClockworkChar.winder.shouldRender;
    }

    @SpirePatch(clz=EnergyPanel.class, method="renderOrb", paramtypes = { "com.badlogic.gdx.graphics.g2d.SpriteBatch"})
    public static class RenderPatch {
        public static void Postfix(EnergyPanel __instance, SpriteBatch sb) {
            if (shouldRenderWinder()) ClockworkChar.winder.render(sb);
        }
    }

    @SpirePatch(clz=EnergyPanel.class, method="update")
    public static class UpdatePatch {
        public static void Postfix() {
            if (shouldRenderWinder()) ClockworkChar.winder.update();
        }
    }

    @SpirePatch(clz=EnergyManager.class, method="prep")
    public static class ResetPatch {
        public static void Postfix() {
            ClockworkChar.winder.reset();
        }
    }
}