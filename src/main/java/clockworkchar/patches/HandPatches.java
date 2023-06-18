package clockworkchar.patches;

import clockworkchar.characters.TheClockwork;
import clockworkchar.relics.Drill;
import clockworkchar.relics.LeftHand;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;

public class HandPatches {
    public static void switchHand() {
        if (AbstractDungeon.player instanceof TheClockwork) {
            TheClockwork p = ((TheClockwork)AbstractDungeon.player);
            boolean hadBefore = p.handBone.getScaleX() > 0f;
            if (!AbstractDungeon.player.hasRelic(LeftHand.ID)) {
                if (hadBefore)
                    AbstractDungeon.effectsQueue.add(new SmokePuffEffect(p.getSkeleton().getX() + p.handBone.getWorldX(), p.getSkeleton().getY() + p.handBone.getWorldY()));
                p.handBone.setScale(0.0F);
            }
            p.drillBone.setScale(AbstractDungeon.player.hasRelic(Drill.ID) ? 1.0F : 0.0F);
        } 
    }

    @SpirePatch(clz=AbstractPlayer.class, method="loseRelic")
    public static class RelicLossPatch {
        public static void Postfix() {
            switchHand();
        }
    }

    @SpirePatch(clz=AbstractRelic.class, method="relicTip")
    public static class RelicTipPatch {
        public static void Postfix() {
            switchHand();
        }
    }

    @SpirePatch(clz=CardCrawlGame.class, method="loadPlayerSave")
    public static class LoadPatch {
        public static void Postfix() {
            switchHand();
        }
    }
}