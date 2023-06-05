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
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
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
        lobbyScale *= 2f;
    }

    public String GetThreeLetterID() {
        return "CRA";
    }

    public void GetSkins() {}
  
    public PlayerSkin GetDefaultSkin() {
        return null;
    }

    public PlayerSkin GetGhostSkin() {
        return null;
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
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.renderScale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);

        AnimationState.TrackEntry state = setStateAnimation(0, "Idle", true);
        state.setTimeScale(TheClockwork.ANIMATION_SPEED);
        setStateDataMix("Hit", "Idle", 0.5f);
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
                source.handBone.setScale(player.hasRelic(LeftHand.ID) ? 1.0F : 0.0F);
                source.drillBone.setScale(player.hasRelic(Drill.ID) ? 1.0F : 0.0F);
            }
        }
    }
  
    @SpirePatch(clz=UIElements.Nameplates.class, method="Init", requiredModId="spireTogether")
    public static class AddNameplate {
        public static void Postfix() {
            UIElements.Nameplates.nameplates.add(nameplate);
            System.out.println(nameplate.GetUUID());
        }
    }
}