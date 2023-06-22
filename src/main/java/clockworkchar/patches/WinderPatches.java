package clockworkchar.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.GainEnergyAndEnableControlsAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import clockworkchar.CrankyMod;
import clockworkchar.cards.PerpetualForm;

public class WinderPatches {
    private static boolean shouldRenderWinder() {
        return CardCrawlGame.dungeon != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && CrankyMod.winder.shouldRender;
    }

    @SpirePatch(clz=EnergyPanel.class, method="renderOrb", paramtypes = { "com.badlogic.gdx.graphics.g2d.SpriteBatch"})
    public static class RenderPatch {
        public static void Postfix(EnergyPanel __instance, SpriteBatch sb) {
            if (shouldRenderWinder()) CrankyMod.winder.render(sb);
        }
    }

    @SpirePatch(clz=GainEnergyAndEnableControlsAction.class, method="update")
    public static class ApplyCogwheelsStartCombatPatch {
        @SpireInsertPatch(rloc=12)
        public static void Insert() {
            CrankyMod.winder.triggerCogwheels();
        }
    }

    @SpirePatch(clz=EnergyManager.class, method="recharge")
    public static class ApplyCogwheelsPatch {
        public static void Postfix() {
            CrankyMod.winder.triggerCogwheels();
        }
    }

    @SpirePatch(clz=EnergyPanel.class, method="update")
    public static class UpdatePatch {
        public static void Postfix() {
            if (shouldRenderWinder()) CrankyMod.winder.update();
        }
    }

    @SpirePatch(clz=EnergyManager.class, method="prep")
    public static class ResetPatch {
        public static void Postfix() {
            CrankyMod.winder.reset();
            PerpetualForm.PerpetualFormPower.perpetual = false;
        }
    }
}