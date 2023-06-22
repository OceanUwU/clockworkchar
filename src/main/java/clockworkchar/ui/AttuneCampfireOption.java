package clockworkchar.ui;

import basemod.ReflectionHacks;
import clockworkchar.characters.Cranky;
import clockworkchar.patches.AttunedPatches;
import clockworkchar.ui.AttuneCampfireEffect.AttuningField;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.FusionHammer;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.CrankyMod.makeImagePath;

public class AttuneCampfireOption extends AbstractCampfireOption {
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getUIString(makeID("AttuneCampfireOption")).TEXT;
    private static final Texture TEXTURE = new Texture(makeImagePath("ui/campfire/attune.png"));
    private static final Texture DISABLED_TEXTURE = new Texture(makeImagePath("ui/campfire/attuneDisabled.png"));


    public AttuneCampfireOption() {
        label = DESCRIPTIONS[0];
        setUsable();
    }

    private void setUsable() {
        usable = !AbstractDungeon.player.hasRelic(FusionHammer.ID) && getAttunableCards().group.size() > 0;
        description = usable ? DESCRIPTIONS[1] : DESCRIPTIONS[2];
        img = usable ? TEXTURE : DISABLED_TEXTURE;
    }

    public static CardGroup getAttunableCards() {
        CardGroup validCards = new CardGroup(CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (AttunedPatches.canAttune(c))
                validCards.group.add(c);
        return validCards;
    }

    public void useOption() {
        setUsable();
        if (usable)
            AbstractDungeon.effectList.add(0, new AttuneCampfireEffect());
    }

    @SpirePatch(clz=CampfireUI.class, method="initializeButtons")
    public static class AddOption {
        @SpireInsertPatch(loc=97)
        public static void Insert(CampfireUI __instance) {
            AttuningField.attuning.set(AbstractDungeon.gridSelectScreen, false);
            if (AbstractDungeon.player instanceof Cranky)
                ((ArrayList<AbstractCampfireOption>)ReflectionHacks.getPrivate(__instance, CampfireUI.class, "buttons")).add(new AttuneCampfireOption());
        }
    }
}