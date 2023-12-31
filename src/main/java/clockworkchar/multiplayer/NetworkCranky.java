package clockworkchar.multiplayer;

import basemod.ReflectionHacks;
import clockworkchar.characters.Cranky;
import clockworkchar.relics.Drill;
import clockworkchar.relics.LeftHand;
import clockworkchar.CrankyMod;
import clockworkchar.util.TexLoader;
import clockworkchar.util.Skindexer.CrankySkin;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import skindex.registering.SkindexRegistry;
import skindex.skins.player.PlayerSkin;
import spireTogether.SpireTogetherMod;
import spireTogether.UnlockableItem.UnlockMethod;
import spireTogether.monsters.CharacterEntity;
import spireTogether.monsters.playerChars.NetworkCharPreset;
import spireTogether.network.P2P.P2PCallbacks;
import spireTogether.network.P2P.P2PManager;
import spireTogether.network.P2P.P2PMessageAnalyzer;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.ui.elements.presets.Nameplate;
import spireTogether.util.NetworkMessage;
import spireTogether.util.UIElements;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.CrankyMod.makeImagePath;

public class NetworkCranky extends NetworkCharPreset {
    public static final String REQUEST_CHANGE_CHARGE = "cranky_charge";
    public static final String REQUEST_CHANGE_COGWHEELS = "cranky_cogwheels";
    public static Nameplate nameplate = new Nameplate("reward_cranky", Color.valueOf("2B2B2B"), Color.valueOf("2B2B2B"), UnlockMethod.ACHIEVEMENT);

    public NetworkCranky() {
        super(new Cranky(Cranky.characterStrings.NAMES[1], Cranky.Enums.THE_CLOCKWORK));
        energyOrb = new CrankyEnergyOrb();
        loadAnimation(makeImagePath("char/mainChar/cranky.atlas"), makeImagePath("char/mainChar/cranky.json"), 1f);
        lobbyScale = 1.7f;
    }

    public String GetThreeLetterID() {
        return "CRA";
    }

    public CharacterEntity CreateNew() {
        return new NetworkCranky();
    }

    public PlayerSkin GetGhostSkin() {
        return SkindexRegistry.getPlayerSkinByClassAndId(playerClass, makeID("ghost"));
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
        return CrankyMod.characterColor.cpy();
    }
  
    public Nameplate GetNameplateUnlock() {
        return nameplate;
    }

    /*@Override
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
        track.setTimeScale(Cranky.ANIMATION_SPEED);
        setStateDataMix("Hit", "Idle", 0.5f);

        ((Cranky)source).winderBone = skeleton.findBone("winder");
        ((Cranky)source).handBone = skeleton.findBone("hand");
        ((Cranky)source).drillBone = skeleton.findBone("drill");
        ((Cranky)source).drillBone.setScale(0.0F);
    }*/
  
    @SpirePatch(clz=SpireTogetherMod.class, method="RegisterModdedChars", requiredModId="spireTogether")
    public static class Register {
        public static void Postfix() {
            SpireTogetherMod.allCharacterEntities.put(Cranky.Enums.THE_CLOCKWORK, new NetworkCranky());
        }
    }
  
    @SpirePatch(clz=P2PMessageAnalyzer.class, method="AnalyzeMessage", requiredModId="spireTogether")
    public static class MessageAnalyzer {
        public static void Postfix(NetworkMessage data) {
            P2PPlayer p = P2PManager.GetPlayer(data.senderID);
            if (p != null && p.GetEntity() instanceof NetworkCranky) {
                MultiplayerWinder winder = MultiplayerWinder.InfoBoxWinderField.winder.get(p.GetInfobox());
                if (winder != null) {
                    winder.source = (CrankySkin)ReflectionHacks.getPrivate(p, P2PPlayer.class, "skinInstance");
                    switch (data.request) {
                        case REQUEST_CHANGE_CHARGE:
                            winder.setNonPlayerCharge((int)data.object, true);
                            break;

                        case REQUEST_CHANGE_COGWHEELS:
                            winder.setNonPlayerCogwheels((int)data.object, true);
                            break;
                    }
                }
            }
        }
    }
  
    @SpirePatch(clz=P2PCallbacks.class, method="OnPlayerChangedRelics", requiredModId="spireTogether")
    public static class SwitchHand {
        public static void Postfix(P2PPlayer player) {
            if (player.GetEntity() instanceof NetworkCranky) {
                CrankySkin skin = (CrankySkin)ReflectionHacks.getPrivate(player, P2PPlayer.class, "skinInstance");
                skin.handBone.setScale(player.hasRelic(LeftHand.ID) ? 1.0F : 0.0F);
                skin.drillBone.setScale(player.hasRelic(Drill.ID) ? 1.0F : 0.0F);
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