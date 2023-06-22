package clockworkchar.packs;

import basemod.ReflectionHacks;
import clockworkchar.ClockworkChar;
import clockworkchar.cards.*;
import clockworkchar.characters.TheClockwork;
import clockworkchar.potions.SpareNails;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javassist.CtBehavior;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.hats.Hats;
import thePackmaster.packs.AbstractCardPack;
import thePackmaster.packs.AbstractPackPreviewCard;
import thePackmaster.patches.RenderBaseGameCardPackTopTextPatches;
import thePackmaster.patches.CompendiumPatches.ShowBaseGameCards;
import thePackmaster.summaries.PackSummary;
import thePackmaster.summaries.PackSummaryReader;
import thePackmaster.ui.PackFilterMenu;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.ClockworkChar.makeCardPath;
import static clockworkchar.ClockworkChar.makeImagePath;

public class ToolsPack extends AbstractCardPack {
    public static final String ID = makeID("ToolsPack");
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    public ToolsPack() {
        super(ID, UI_STRINGS.TEXT[0], UI_STRINGS.TEXT[1], UI_STRINGS.TEXT[2]);
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(RearrangeAttack.ID);
        cards.add(UnleashRage.ID);
        cards.add(Mend.ID);
        cards.add(Rearrange.ID);
        cards.add(AsFarAsItGoes.ID);
        cards.add(Mesmerise.ID);
        cards.add(Salvage.ID);
        cards.add(Toolerang.ID);
        cards.add(Scanner.ID);
        cards.add(DiligentWork.ID);
        return cards;
    }

   public ArrayList<String> getPackPotions() {
        ArrayList<String> potions = new ArrayList<>();
        potions.add(SpareNails.POTION_ID);
        return potions;
   }

    @SpirePatch(clz=SpireAnniversary5Mod.class, method="declarePacks", requiredModId="anniv5")
    public static class Declare {
        public static void Postfix() {
            AbstractCardPack pack = new ToolsPack();
            SpireAnniversary5Mod.packsByID.put(ID, pack);
            SpireAnniversary5Mod.unfilteredAllPacks.add(pack);
            if (PackFilterMenu.getFilterConfig(ID))
                SpireAnniversary5Mod.allPacks.add(pack);
            SpireAnniversary5Mod.packExclusivePotions.addAll(pack.getPackPotions());
        }
    }

    @SpirePatch2(clz=RenderBaseGameCardPackTopTextPatches.class, method="shouldShowPackName", requiredModId="anniv5")
    public static class ShowPackName {
        public static SpireReturn<Boolean> Prefix(AbstractCard c) {
            if (c instanceof AbstractEasyCard && !Settings.hideCards && ((boolean)ReflectionHacks.privateStaticMethod(RenderBaseGameCardPackTopTextPatches.class, "isInPackmasterRun").invoke() || RenderBaseGameCardPackTopTextPatches.isInPackmasterCardLibraryScreen()))
                return SpireReturn.Return(true);
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz=ShowBaseGameCards.class, method="showBaseGameCards", requiredModId="anniv5")
    public static class ShowInCompendium {
        @SpireInsertPatch(locator=Locator.class, localvars="group")
        public static void Insert(CardGroup group) {
            group.group.addAll(CardLibrary.getAllCards().stream().filter(c -> c.color == TheClockwork.Enums.CLOCKWORK_BROWN_COLOR && SpireAnniversary5Mod.cardParentMap.containsKey(c.cardID)).collect(Collectors.toList()));
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                return LineFinder.findInOrder(ctMethodToPatch, new Matcher.MethodCallMatcher(ArrayList.class, "addAll"));
            }
        }
    }

    @SpirePatch2(clz=Hats.class, method="getImagePathFromHatID", requiredModId="anniv5")
    public static class GetCorrectHatPath {
        public static SpireReturn<String> Prefix(String hatID) {
            if (hatID == ID)
                return SpireReturn.Return(makeImagePath("hats/"+hatID.replace(ClockworkChar.modID + ":", "")+"Hat.png"));
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz=AbstractPackPreviewCard.class, method="getCardTextureString", requiredModId="anniv5")
    public static class GetCorrectPortraitPath {
        public static SpireReturn<String> Prefix(String cardName) {
            if (cardName == ID)
                return SpireReturn.Return(makeCardPath(cardName.replace(ClockworkChar.modID + ":", "")+".png"));
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz=PackSummaryReader.class, method="getPackSummary", requiredModId="anniv5")
    public static class GetSummaryWithoutTXTFile {
        public static SpireReturn<PackSummary> Prefix(String packID) {
            if (packID == ID) {
                PackSummary summary = new PackSummary();
                summary.offense = 3;
                summary.defense = 3;
                summary.support = 3;
                summary.frontload = 1;
                summary.scaling = 5;
                summary.tags.add(PackSummaryReader.NONE_TAG);
                return SpireReturn.Return(summary);
            }
            return SpireReturn.Continue();
        }
    }
}