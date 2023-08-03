package clockworkchar.patches;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import clockworkchar.CrankyMod;
import clockworkchar.actions.UseToolAction;
import clockworkchar.cards.AbstractCrankyCard;
import clockworkchar.cards.PerpetualForm;
import clockworkchar.cards.Teddy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class AttunedPatches {
    public static final int PLAYS_TO_ATTUNE = 0;
    public static final int MAX_TIMES_ATTUNED = 1;
    private static Field colorField;
    private static Method renderHelperMethod;
    private static Method energyFontMethod;

    @SpirePatch(clz=AbstractPlayer.class, method="preBattlePrep")
    public static class SetRelation {
        @SpireInsertPatch(loc=1983)
        public static void Insert(AbstractPlayer __instance) {
            for (AbstractCard c : __instance.drawPile.group)
                for (AbstractCard dc : __instance.masterDeck.group)
                    if (dc.uuid.equals(c.uuid)) {
                        CardFields.deckCard.set(c, dc);
                        CardFields.timesPlayed.set(c, CardFields.timesPlayed.get(dc));
                        break;
                    }
        }
    }

    @SpirePatch(clz=AbstractCard.class, method=SpirePatch.CLASS)
    public static class CardFields {
        public static SpireField<AbstractCard> deckCard = new SpireField<>(() -> null);
        public static SpireField<Integer> timesPlayed = new SpireField<>(() -> 0);
        public static SpireField<Integer> attuned = new SpireField<>(() -> 0);
    }

    @SpirePatch(clz=AbstractCard.class, method="makeStatEquivalentCopy")
    public static class CopyFields {
        @SpireInsertPatch(loc=988, localvars={"card"})
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            if (CardCrawlGame.isInARun() && AbstractDungeon.player.masterDeck.contains(__instance))
                CardFields.deckCard.set(card, __instance);
            CardFields.timesPlayed.set(card, CardFields.timesPlayed.get(__instance));
            CardFields.attuned.set(card, CardFields.attuned.get(__instance));
        }
    }

    public static CustomSavable<ArrayList<Integer>> TimesPlayedSavable = new CustomSavable<ArrayList<Integer>>() {
        public void onLoad(ArrayList<Integer> allTimes) {
            int i = 0;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
                CardFields.timesPlayed.set(c, allTimes.get(i++));
        }

        public ArrayList<Integer> onSave() {
            ArrayList<Integer> allAttunes = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
                allAttunes.add(CardFields.timesPlayed.get(c));
            return allAttunes;
        }
    };

    public static CustomSavable<ArrayList<Integer>> AttunedSavable = new CustomSavable<ArrayList<Integer>>() {
        public void onLoad(ArrayList<Integer> allAttuned) {
            int i = 0;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
                attune(c, allAttuned.get(i++) - (c instanceof AbstractCrankyCard ? ((AbstractCrankyCard)c).extraAttunings : 0));
        }

        public ArrayList<Integer> onSave() {
            ArrayList<Integer> allAttuned = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
                allAttuned.add(CardFields.attuned.get(c));
            return allAttuned;
        }
    };

    @SpirePatch(clz=AbstractPlayer.class, method="useCard")
    public static class CountPlay {
        public static void count(AbstractCard c, boolean top) {
            CardFields.timesPlayed.set(c, CardFields.timesPlayed.get(c)+1);
            if (CardFields.deckCard.get(c) != null)
                CardFields.timesPlayed.set(CardFields.deckCard.get(c), CardFields.timesPlayed.get(c));
            int attunedTimes = CardFields.attuned.get(c);
            if (attunedTimes > 0) {
                UseToolAction action = new UseToolAction(attunedTimes);
                if (top) att(action);
                else atb(action);
            }
        }

        public static void Postfix(AbstractPlayer __instance, AbstractCard c) {
            count(c, false);
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="initializeDescription")
    public static class AttuneKeyword {
        private static String ATTUNED_KEYWORD_ID = makeID("attuned");

        public static void Postfix(AbstractCard __instance) {
            if (CardFields.attuned.get(__instance) > 0 && !__instance.keywords.contains(ATTUNED_KEYWORD_ID))
                __instance.keywords.add(ATTUNED_KEYWORD_ID);
        }
    }

    public static void attune(AbstractCard c) {
        CardFields.attuned.set(c, CardFields.attuned.get(c)+1);
        c.initializeDescription();
    }

    public static void attune(AbstractCard c, int times) {
        for (int i = 0; i < times; i++)
            attune(c);
    }

    public static boolean canAttune(AbstractCard c) {
        return CardFields.timesPlayed.get(c) >= PLAYS_TO_ATTUNE && (CardFields.attuned.get(c) < MAX_TIMES_ATTUNED || c instanceof Teddy);
    }

    static {
        try {
            colorField = AbstractCard.class.getDeclaredField("renderColor");
            colorField.setAccessible(true);
            renderHelperMethod = AbstractCard.class.getDeclaredMethod("renderHelper", new Class[] { SpriteBatch.class, Color.class, Texture.class, float.class, float.class });
            renderHelperMethod.setAccessible(true);
            energyFontMethod = AbstractCard.class.getDeclaredMethod("getEnergyFont", new Class[] {});
            energyFontMethod.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="renderType")
    public static class RenderTimes {
        private static TextureAtlas.AtlasRegion ATTUNED_TEXTURE = new TextureAtlas.AtlasRegion(new Texture(CrankyMod.makeImagePath("512/attuned.png")), 0, 0, 512, 512);
        private static TextureAtlas.AtlasRegion ATTUNED_TEXTURE_DOWN = new TextureAtlas.AtlasRegion(new Texture(CrankyMod.makeImagePath("512/attuneddown.png")), 0, 0, 512, 512);

        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
            try {
                /*if (CardCrawlGame.isInARun() && AbstractDungeon.player instanceof TheClockwork && (CardFields.deckCard.get(__instance) != null || AbstractDungeon.player.masterDeck.group.contains(__instance))) {
                    int times = CardFields.timesPlayed.get(__instance);
                    BitmapFont font = __instance.angle == 0F && __instance.drawScale == 1F ? FontHelper.cardDescFont_N : FontHelper.cardDescFont_L;
                    font.getData().setScale(__instance.drawScale * 0.8F);
                    FontHelper.renderRotatedText(sb, font, Integer.toString(times)+(times < PLAYS_TO_ATTUNE ? "/"+Integer.toString(PLAYS_TO_ATTUNE) : ""), __instance.current_x, __instance.current_y, 0F, -198F * __instance.drawScale * Settings.scale, __instance.angle, false, ((Color)colorField.get(__instance)).cpy().mul(times < PLAYS_TO_ATTUNE ? Settings.CREAM_COLOR : Settings.GOLD_COLOR));
                }*/

                int attunedTimes = CardFields.attuned.get(__instance);
                if (attunedTimes > 0) {
                    sb.setColor((Color)colorField.get(__instance));
                    boolean perpetual = CardCrawlGame.isInARun() && AbstractDungeon.player.stance instanceof PerpetualForm.PerpetualStance;
                    TextureAtlas.AtlasRegion tex = perpetual ? ATTUNED_TEXTURE_DOWN : ATTUNED_TEXTURE;
                    sb.draw(tex, __instance.current_x - tex.originalWidth / 2F, __instance.current_y - tex.originalHeight / 2F, tex.originalWidth / 2F, tex.originalHeight / 2F, tex.packedWidth, tex.packedHeight, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle);
                    FontHelper.renderRotatedText(sb, (BitmapFont)energyFontMethod.invoke(__instance), Integer.toString(attunedTimes), __instance.current_x, __instance.current_y, -132F * __instance.drawScale * Settings.scale, (perpetual ? 44F : 118F) * __instance.drawScale * Settings.scale, __instance.angle, false, (Color)colorField.get(__instance));
                }
                sb.setColor(Color.WHITE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SpirePatch(clz=SingleCardViewPopup.class, method="renderCost")
    public static class RenderAttunedSCV {
        private static TextureAtlas.AtlasRegion ATTUNED_TEXTURE_L = new TextureAtlas.AtlasRegion(new Texture(CrankyMod.makeImagePath("1024/attuned.png")), 0, 0, 164, 164);
        private static float SIZE = 164f;
        private static float HALFSIZE = SIZE / 2f;
        private static Field cardField = ReflectionHacks.getCachedField(SingleCardViewPopup.class, "card");

        public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
            try {
                AbstractCard c = (AbstractCard)cardField.get(__instance);
                int attunedTimes = CardFields.attuned.get(c);
                if (attunedTimes > 0) {
                    sb.draw(ATTUNED_TEXTURE_L, Settings.WIDTH / 2f - 270f * Settings.scale - HALFSIZE, Settings.HEIGHT / 2f + 240f * Settings.scale - HALFSIZE, HALFSIZE, HALFSIZE, SIZE, SIZE, Settings.scale, Settings.scale, 0f);
                    FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(attunedTimes), Settings.WIDTH / 2f - (attunedTimes == 1 ? 284f : 292f) * Settings.scale, Settings.HEIGHT / 2f + 255f * Settings.scale, Settings.CREAM_COLOR);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}