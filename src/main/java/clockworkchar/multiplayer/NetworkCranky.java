package clockworkchar.multiplayer;

import basemod.ReflectionHacks;
import clockworkchar.characters.TheClockwork;
import clockworkchar.relics.Drill;
import clockworkchar.relics.LeftHand;
import clockworkchar.ClockworkChar;
import clockworkchar.util.TexLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import spireTogether.SpireTogetherMod;
import spireTogether.Unlockable;
import spireTogether.monsters.CharacterEntity;
import spireTogether.monsters.playerChars.NetworkCharPreset;
import spireTogether.network.P2P.P2PCallbacks;
import spireTogether.network.P2P.P2PManager;
import spireTogether.network.P2P.P2PMessageAnalyzer;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.skins.PlayerSkin;
import spireTogether.ui.elements.presets.Nameplate;
import spireTogether.ui.elements.presets.PlayerInfoBox;
import spireTogether.util.BundleManager;
import spireTogether.util.NetworkMessage;
import spireTogether.util.UIElements;

import static clockworkchar.ClockworkChar.makeImagePath;

public class NetworkCranky extends NetworkCharPreset {
    public static final String REQUEST_CHANGE_CHARGE = "cranky_charge";
    public static final String REQUEST_CHANGE_COGWHEELS = "cranky_cogwheels";
    public static Nameplate nameplate = new Nameplate("reward_cranky", Color.valueOf("2B2B2B"), Color.valueOf("2B2B2B"), Unlockable.UnlockMethod.ACHIEVEMENT);

    public NetworkCranky() {
        super(new TheClockwork(TheClockwork.characterStrings.NAMES[1], TheClockwork.Enums.THE_CLOCKWORK));
        energyOrb = new CrankyEnergyOrb();
        loadAnimation(makeImagePath("char/mainChar/cranky.atlas"), makeImagePath("char/mainChar/cranky.json"), 1f);
        lobbyScale = 0.6f;
    }

    public String GetThreeLetterID() {
        return "CRA";
    }

    public void GetSkins() {
        skins.add(GetDefaultSkin());
        skins.add(new CrankySkin("RUBY", Unlockable.UnlockMethod.FREE, playerClass));
        skins.add(new CrankySkin("EMERALD", Unlockable.UnlockMethod.FREE, playerClass));
        skins.add(new CrankySkin("COBALT", Unlockable.UnlockMethod.FREE, playerClass));
        skins.add(new CrankySkin("COPPER", Unlockable.UnlockMethod.FREE, playerClass));
        skins.add(new CrankySkin("CHIBI", Unlockable.UnlockMethod.FREE, playerClass).SetScaleModifier(TheClockwork.SIZE_SCALE * 2f));
        skins.add(new CrankySkin("TWITCH", Unlockable.UnlockMethod.PROMOTION, playerClass).SetBundles(new String[] { BundleManager.STREAMER }));
        skins.add(GetGhostSkin());
        skins.add(new CrankySkin("HEARTSLAYER", Unlockable.UnlockMethod.ACHIEVEMENT, playerClass));
    }
  
    public PlayerSkin GetDefaultSkin() {
        return new CrankySkin("BASE", Unlockable.UnlockMethod.FREE, playerClass);
    }

    public PlayerSkin GetGhostSkin() {
        return new CrankySkin("GHOST", Unlockable.UnlockMethod.ACHIEVEMENT, playerClass).SetBundles(new String[] { BundleManager.GHOST });
    }

    public CharacterEntity CreateNew() {
        return new NetworkCranky();
    }

    public Texture GetNameplateIcon(String s) {
        return TexLoader.getTexture(makeImagePath("char/mainChar/multiplayer/icons/"+s+".png"));
    }

    public Texture GetDefaultIcon() {
        return GetNameplateIcon("basic");
    }

    public Texture GetWhiteSpecialIcon() {
        return GetNameplateIcon("whiteSpecial");
    }

    public Color GetCharColor() {
        return ClockworkChar.characterColor.cpy();
    }
  
    public Nameplate GetNameplateUnlock() {
        return nameplate;
    }

    @Override
    public void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.renderScale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        skeleton = new Skeleton(skeletonData);
        skeleton.setColor(Color.WHITE);
        stateData = new AnimationStateData(skeletonData);
        state = new AnimationState(this.stateData);

        ReflectionHacks.setPrivate(source, AbstractCreature.class, "skeleton", skeleton);
        ReflectionHacks.setPrivate(source, AbstractCreature.class, "stateData", stateData);
        source.state = new AnimationState(ReflectionHacks.getPrivate(source, AbstractCreature.class, "stateData"));
        //ReflectionHacks.privateMethod(AbstractCreature.class, "loadAnimation", String.class, String.class, float.class).invoke(source, atlasUrl, skeletonUrl, scale);

        AnimationState.TrackEntry track = setStateAnimation(0, "Idle", true);
        track.setTimeScale(TheClockwork.ANIMATION_SPEED);
        setStateDataMix("Hit", "Idle", 0.5f);

        ((TheClockwork)source).winderBone = skeleton.findBone("winder");
        ((TheClockwork)source).handBone = skeleton.findBone("hand");
        ((TheClockwork)source).drillBone = skeleton.findBone("drill");
        ((TheClockwork)source).drillBone.setScale(0.0F);
    }
  
    @SpirePatch(clz=SpireTogetherMod.class, method="RegisterModdedChars", requiredModId="spireTogether")
    public static class Register {
        public static void Postfix() {
            SpireTogetherMod.allCharacterEntities.put(TheClockwork.Enums.THE_CLOCKWORK, new NetworkCranky());
        }
    }
  
    @SpirePatch(clz=P2PMessageAnalyzer.class, method="AnalyzeMessage", requiredModId="spireTogether")
    public static class MessageAnalyzer {
        public static void Postfix(NetworkMessage data) {
            String request = data.request;
            Object object = data.object;
            Integer senderID = data.senderID;
            P2PPlayer p = P2PManager.GetPlayer(senderID);
            if (p != null && p.GetEntity() instanceof NetworkCranky) {
                PlayerInfoBox infoBox = p.GetInfobox();
                EnergyOrbInterface energyOrb = ReflectionHacks.getPrivate(infoBox, PlayerInfoBox.class, "energyOrb");
                if (energyOrb instanceof CrankyEnergyOrb)
                    ((CrankyEnergyOrb)energyOrb).source = (TheClockwork)((NetworkCranky)p.GetEntity()).source;
                switch (request) {
                    case REQUEST_CHANGE_CHARGE:
                        if (energyOrb instanceof CrankyEnergyOrb)
                            ((CrankyEnergyOrb)energyOrb).winder.setNonPlayerCharge((int)object, true);
                        break;

                    case REQUEST_CHANGE_COGWHEELS:
                        if (energyOrb instanceof CrankyEnergyOrb)
                            ((CrankyEnergyOrb)energyOrb).winder.setNonPlayerCogwheels((int)object, true);
                        break;
                }
            }
        }
    }
  
    @SpirePatch(clz=P2PCallbacks.class, method="OnPlayerChangedRelics", requiredModId="spireTogether")
    public static class SwitchHand {
        public static void Postfix(P2PPlayer player) {
            if (player.GetEntity() instanceof NetworkCranky) {
                TheClockwork source = (TheClockwork)((NetworkCranky)player.GetEntity()).source;
                boolean hadBefore = source.handBone.getScaleX() > 0f;
                if (!player.hasRelic(LeftHand.ID) && hadBefore)
                    AbstractDungeon.effectsQueue.add(new SmokePuffEffect(source.getSkeleton().getX() + source.handBone.getWorldX(), source.getSkeleton().getY() + source.handBone.getWorldY()));
                source.handBone.setScale(player.hasRelic(LeftHand.ID) ? 1.0F : 0.0F);
                source.drillBone.setScale(player.hasRelic(Drill.ID) ? 1.0F : 0.0F);
            }
        }
    }
  
    @SpirePatch(clz=UIElements.Nameplates.class, method="Init", requiredModId="spireTogether")
    public static class AddNameplate {
        public static void Postfix() {
            UIElements.Nameplates.nameplates.add(nameplate);
        }
    }
}