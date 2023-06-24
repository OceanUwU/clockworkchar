package clockworkchar.multiplayer;

import basemod.ReflectionHacks;
import clockworkchar.characters.Cranky;
import clockworkchar.ui.Winder;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import javassist.CtBehavior;
import spireTogether.modcompat.downfall.characters.energyorbs.CustomizableEnergyOrbCustom;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.ui.elements.presets.PlayerInfoBox;
import spireTogether.ui.elements.useable.Clickable;
import spireTogether.util.SpireVariables;

public class MultiplayerWinder extends Winder {
    public Cranky source;

    public MultiplayerWinder() {
        super((float)ReflectionHacks.getPrivateStatic(CustomizableEnergyOrbCustom.class, "ORB_IMG_SCALE") / Settings.scale, false, false, false, false, FontHelper.energyNumFontRed);
    }

    public void render(SpriteBatch sb, float orb_x, float orb_y) {
        x = orb_x - offset - 128f * Settings.scale * 1.25f;
        y = orb_y + offset;
        super.render(sb);
        if (source != null)
            spinSkinWinder(source.winderBone);
    }

    @SpirePatch(clz=PlayerInfoBox.class, method=SpirePatch.CLASS)
    public static class InfoBoxWinderField {
        public static SpireField<MultiplayerWinder> winder = new SpireField<>(() -> null);
    }

    @SpirePatch2(clz=PlayerInfoBox.class, method="LoadData")
    public static class CreateWinderForInfoBox {
        @SpireInsertPatch(locator=Locator.class, localvars="sb")
        public static void Insert(PlayerInfoBox __instance, P2PPlayer p) {
            if (p.playerClass == Cranky.Enums.THE_CLOCKWORK && InfoBoxWinderField.winder.get(__instance) == null)
                InfoBoxWinderField.winder.set(__instance, new MultiplayerWinder());
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                return LineFinder.findInOrder(ctMethodToPatch, new Matcher.FieldAccessMatcher(PlayerInfoBox.class, "energyOrbEnabled"));
            }
        }
    }

    @SpirePatch2(clz=PlayerInfoBox.class, method="update")
    public static class UpdateWinderOnInfoBox {
        @SpireInsertPatch(locator=Locator.class, localvars="sb")
        public static void Insert(PlayerInfoBox __instance) {
            MultiplayerWinder winder = InfoBoxWinderField.winder.get(__instance);
            if (winder != null)
                winder.update();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                return LineFinder.findInOrder(ctMethodToPatch, new Matcher.MethodCallMatcher(EnergyOrbInterface.class, "updateOrb"));
            }
        }
    }

    @SpirePatch2(clz=PlayerInfoBox.class, method="renderForeground")
    public static class RenderWinderOnInfoBox {
        @SpireInsertPatch(locator=Locator.class, localvars="sb")
        public static void Insert(PlayerInfoBox __instance, SpriteBatch sb) {
            MultiplayerWinder winder = InfoBoxWinderField.winder.get(__instance);
            if (winder != null) {
                Clickable background = (Clickable)ReflectionHacks.getPrivate(__instance, PlayerInfoBox.class, "background");
                winder.render(sb, (__instance.xPos + background.width - 15) * SpireVariables.scale.x, (__instance.yPos + background.height - 10) * SpireVariables.scale.y);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                return LineFinder.findInOrder(ctMethodToPatch, new Matcher.MethodCallMatcher(EnergyOrbInterface.class, "renderOrb"));
            }
        }
    }
}