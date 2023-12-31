package clockworkchar;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import clockworkchar.cards.AbstractCrankyCard;
import clockworkchar.cards.Bottlecap;
import clockworkchar.cards.BouncyBall;
import clockworkchar.cards.LittleCranky;
import clockworkchar.cards.RareCoin;
import clockworkchar.cards.cardvars.*;
import clockworkchar.characters.Cranky;
import clockworkchar.consolecommands.*;
import clockworkchar.helpers.ToolLibrary;
import clockworkchar.patches.AttunedPatches;
import clockworkchar.potions.*;
import clockworkchar.relics.AbstractEasyRelic;
import clockworkchar.tools.AbstractTool;
import clockworkchar.ui.ToolSlot;
import clockworkchar.ui.Winder;
import clockworkchar.util.CardAugmentsLoader;
import clockworkchar.util.PackLoader;
import clockworkchar.util.Skindexer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.KeywordStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thePackmaster.SpireAnniversary5Mod;
import java.nio.charset.StandardCharsets;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class CrankyMod implements
        AddAudioSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {

    public static final String modID = "clockworkchar";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Color characterColor = new Color(0.43F, 0.28F, 0.19F, 1);

    public static final String SHOULDER1 = makeImagePath("char/mainChar/shoulder.png");
    public static final String SHOULDER2 = makeImagePath("char/mainChar/shoulder2.png");
    public static final String CORPSE = makeImagePath("char/mainChar/corpse.png");
    private static final String ATTACK_S_ART = makeImagePath("512/attack.png");
    private static final String SKILL_S_ART = makeImagePath("512/skill.png");
    private static final String POWER_S_ART = makeImagePath("512/power.png");
    private static final String CARD_ENERGY_S = makeImagePath("512/energy.png");
    private static final String TEXT_ENERGY = makeImagePath("512/text_energy.png");
    private static final String ATTACK_L_ART = makeImagePath("1024/attack.png");
    private static final String SKILL_L_ART = makeImagePath("1024/skill.png");
    private static final String POWER_L_ART = makeImagePath("1024/power.png");
    private static final String CARD_ENERGY_L = makeImagePath("1024/energy.png");
    private static final String CHARSELECT_BUTTON = makeImagePath("charSelect/charButton.png");
    private static final String CHARSELECT_PORTRAIT = makeImagePath("charSelect/charBG.png");

    public static Winder winder;
    public static ToolSlot toolSlot;

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    public static CardGroup trinkets;

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    public CrankyMod() {
        BaseMod.subscribe(this);
        System.out.println(Loader.isModLoaded("skindex") || Loader.isModLoaded("spireTogether"));
        if (Loader.isModLoaded("anniv5"))
            SpireAnniversary5Mod.subscribe(new PackLoader());
        if (Loader.isModLoaded("skindex") || Loader.isModLoaded("spireTogether"))
            Skindexer.register();

        BaseMod.addColor(Cranky.Enums.CLOCKWORK_BROWN_COLOR, characterColor, characterColor, characterColor,
            characterColor, characterColor, characterColor, characterColor,
            ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
            ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
            CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return makePath("images/" + resourcePath);
    }

    public static String makeRelicPath(String resourcePath) {
        return makeImagePath("relics/" + resourcePath);
    }

    public static String makePowerPath(String resourcePath) {
        return makeImagePath("powers/" + resourcePath);
    }

    public static String makeCardPath(String resourcePath) {
        return makeImagePath("cards/" + resourcePath);
    }

    public static void initialize() {
        CrankyMod thismod = new CrankyMod();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Cranky(Cranky.characterStrings.NAMES[1], Cranky.Enums.THE_CLOCKWORK),
            CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, Cranky.Enums.THE_CLOCKWORK);
        
        BaseMod.addPotion(HandInAJar.class, Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), HandInAJar.POTION_ID, Cranky.Enums.THE_CLOCKWORK);
        BaseMod.addPotion(SpareNails.class, Color.WHITE.cpy(), Color.WHITE.cpy().set(0,0,0,0), Color.WHITE.cpy().set(0,0,0,0), SpareNails.POTION_ID, Cranky.Enums.THE_CLOCKWORK);
        BaseMod.addPotion(Sawblade.class, Color.WHITE.cpy().set(0,0,0,0), Color.WHITE.cpy().set(0,0,0,0), Color.WHITE.cpy().set(0,0,0,0), Sawblade.POTION_ID, Cranky.Enums.THE_CLOCKWORK);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
            .packageFilter(AbstractEasyRelic.class)
            .any(AbstractEasyRelic.class, (info, relic) -> {
                if (relic.color == null) {
                    BaseMod.addRelic(relic, RelicType.SHARED);
                } else {
                    BaseMod.addRelicToCustomPool(relic, relic.color);
                }
                if (!info.seen) {
                    UnlockTracker.markRelicAsSeen(relic.relicId);
                }
            });
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new ThirdMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        BaseMod.addDynamicVariable(new SecondBlock());
        BaseMod.addDynamicVariable(new SpinAmount());
        trinkets = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        trinkets.group.add(new BouncyBall());
        trinkets.group.add(new Bottlecap());
        trinkets.group.add(new LittleCranky());
        trinkets.group.add(new RareCoin());
        new AutoAdd(modID)
            .packageFilter(AbstractCrankyCard.class)
            .setDefaultSeen(true)
            .cards();
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/Cardstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + getLangString() + "/Relicstrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + getLangString() + "/Charstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + getLangString() + "/Powerstrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/" + getLangString() + "/Potionstrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + getLangString() + "/UIstrings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, modID + "Resources/localization/" + getLangString() + "/Orbstrings.json");
        BaseMod.loadCustomStringsFile(StanceStrings.class, modID + "Resources/localization/" + getLangString() + "/Stancestrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        ToolLibrary.initialize();

        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                if (keyword.DESCRIPTION.contains("!D!")) {
                    AbstractTool tool = ToolLibrary.getTool(makeID(keyword.PROPER_NAME.replaceAll(" ", "")));
                    keyword.DESCRIPTION = keyword.DESCRIPTION.replace("!D!", Integer.toString(tool.damage));
                    keyword.DESCRIPTION = keyword.DESCRIPTION.replace("!B!", Integer.toString(tool.block));
                    keyword.DESCRIPTION = keyword.DESCRIPTION.replace("!M!", Integer.toString(tool.passiveAmount));
                }
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(makeID("WIND_UP"), modID + "Resources/audio/windup.ogg");
        BaseMod.addAudio(makeID("SPIN"), modID + "Resources/audio/spin.ogg");
        BaseMod.addAudio(makeID("BREAK"), modID + "Resources/audio/break.ogg");
        BaseMod.addAudio(makeID("TORCH"), modID + "Resources/audio/torch.ogg");
        BaseMod.addAudio(makeID("ATTUNE"), modID + "Resources/audio/attune.ogg");
        BaseMod.addAudio(makeID("RESONANCE"), modID + "Resources/audio/resonance.ogg");
        BaseMod.addAudio(makeID("BOIL"), modID + "Resources/audio/boil.ogg");
    }

    @Override
    public void receivePostInitialize() {
        ConsoleCommand.addCommand(WinderCommand.COMMAND_NAME, WinderCommand.class);
        ConsoleCommand.addCommand(EquipToolCommand.COMMAND_NAME, EquipToolCommand.class);
        ConsoleCommand.addCommand(UseToolCommand.COMMAND_NAME, UseToolCommand.class);
        BaseMod.addSaveField(makeID("times_played_cards"), AttunedPatches.TimesPlayedSavable);
        BaseMod.addSaveField(makeID("attuned"), AttunedPatches.AttunedSavable);
        if (Loader.isModLoaded("CardAugments"))
            CardAugmentsLoader.load();
        winder = new Winder();
        toolSlot = new ToolSlot();
    }
}