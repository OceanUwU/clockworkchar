package clockworkchar.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import basemod.ReflectionHacks;
import clockworkchar.patches.AttunedPatches;
import clockworkchar.vfx.AttuneCardEffect;

import static clockworkchar.ClockworkChar.makeID;

public class AttuneCampfireEffect extends AbstractGameEffect {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("AttuneCampfireEffect")).TEXT;
    private static final float DUR = 1.5f;

    private boolean openedScreen = false;
    private Color color = AbstractDungeon.fadeColor.cpy();

    public AttuneCampfireEffect() {
        duration = DUR;
        color.a = 0f;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    @SpirePatch(clz=GridCardSelectScreen.class, method=SpirePatch.CLASS)
    public static class AttuningField {
        public static SpireField<Boolean> attuning = new SpireField<>(() -> false);
    }
    
    @SpirePatch(clz=GridCardSelectScreen.class, method="update")
    public static class AttunePreview {
        @SpireInsertPatch(loc=172)
        public static void Insert(GridCardSelectScreen __instance) {
            if (AttuningField.attuning.get(__instance)) {
                __instance.upgradePreviewCard = ((AbstractCard)ReflectionHacks.getPrivate(__instance, GridCardSelectScreen.class, "hoveredCard")).makeStatEquivalentCopy();
                AttunedPatches.attune(__instance.upgradePreviewCard);
                __instance.upgradePreviewCard.drawScale = 0.875f;
            }
        }
    }
    
    @SpirePatch(clz=GridCardSelectScreen.class, method="open", paramtypez={CardGroup.class, int.class, String.class, boolean.class, boolean.class, boolean.class, boolean.class})
    public static class NoLongerAttuning {
        public static void Prefix(GridCardSelectScreen __instance) {
            AttuningField.attuning.set(__instance, false);
        }
    }
    
    @SpirePatch(clz=GridCardSelectScreen.class, method="callOnOpen")
    public static class NoLongerAttuning2 {
        public static void Prefix(GridCardSelectScreen __instance) {
            AttuningField.attuning.set(__instance, false);
        }
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp || !AttuningField.attuning.get(AbstractDungeon.gridSelectScreen)) {
            duration -= Gdx.graphics.getDeltaTime();
            if (duration > 1.0f)
                color.a = Interpolation.fade.apply(1f, 0f, (duration - 1.0f) * 2f);
            else
                color.a = Interpolation.fade.apply(0f, 1f, duration / DUR);
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.forUpgrade) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    CardCrawlGame.metricData.addCampfireChoiceData(makeID("ATTUNE"), c.getMetricID());
                    AttunedPatches.attune(c);
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(copy));
                    AbstractDungeon.effectsQueue.add(new AttuneCardEffect(copy));
                    AttuningField.attuning.set(AbstractDungeon.gridSelectScreen, false);
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
            }
        }
        if (duration < 1.0f && !openedScreen) {
            openedScreen = true;
            AbstractDungeon.gridSelectScreen.open(AttuneCampfireOption.getAttunableCards(), 1, TEXT[0], true);
            AttuningField.attuning.set(AbstractDungeon.gridSelectScreen, true);
        }
        if (duration < 0f) {
            isDone = true;
            if (CampfireUI.hidden) {
                AbstractRoom.waitTimer = 0f;
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0f, 0f, Settings.WIDTH, Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID)
            AbstractDungeon.gridSelectScreen.render(sb);
    }

    public void dispose() {}
}