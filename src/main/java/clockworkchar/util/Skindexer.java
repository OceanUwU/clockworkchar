package clockworkchar.util;

import clockworkchar.CrankyMod;
import clockworkchar.characters.Cranky;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Arrays;
import java.util.List;
import skindex.entities.player.SkindexPlayerAtlasEntity;
import skindex.entities.player.SkindexPlayerEntity;
import skindex.itemtypes.CustomizableItem;
import skindex.registering.SkindexPlayerSkinRegistrant;
import skindex.registering.SkindexRegistry;
import skindex.skins.player.PlayerAtlasSkin;
import skindex.skins.player.PlayerAtlasSkinData;
import skindex.skins.player.PlayerSkin;
import skindex.unlockmethods.FreeUnlockMethod;

import static clockworkchar.CrankyMod.makeID;

public class Skindexer implements SkindexPlayerSkinRegistrant {    
    public static void register() {
        SkindexRegistry.subscribe(new Skindexer());
    }
    
    public List<PlayerSkin> getDefaultPlayerSkinsToRegister() {
        return Arrays.asList(new CrankySkin(makeID("skinBase")));
    }

    public List<PlayerSkin> getPlayerSkinsToRegister() {
        return Arrays.asList(
            new CrankySkin(makeID("ruby")),
            new CrankySkin(makeID("emerald")),
            new CrankySkin(makeID("cobalt")),
            new CrankySkin(makeID("copper")),
            new CrankySkin(makeID("chibi"), 0.5f),
            new CrankySkin(makeID("ghost"))
        );
    }

    public static class CrankySkin extends PlayerAtlasSkin {
        public Bone winderBone, handBone, drillBone;

        public CrankySkin(String id) {
            super(new CrankySkinData(id, Cranky.SIZE_SCALE));
        }

        public CrankySkin(String id, float scale) {
            super(new CrankySkinData(id, Cranky.SIZE_SCALE * scale));
        }

        @Override
        public boolean loadOnEntity(SkindexPlayerEntity entity) {
            boolean loaded = super.loadOnEntity(entity);
            if (loaded && entity instanceof SkindexPlayerAtlasEntity) {
                drillBone = ((SkindexPlayerAtlasEntity)entity).getSkeleton().findBone("drill");
                handBone = ((SkindexPlayerAtlasEntity)entity).getSkeleton().findBone("hand");
                winderBone = ((SkindexPlayerAtlasEntity)entity).getSkeleton().findBone("winder");
                drillBone.setScale(0f);
                return false;
            }
            return loaded;
        }

        @Override
        public boolean loadOnPlayer() {
            boolean loaded = super.loadOnPlayer();
            if (loaded && AbstractDungeon.player instanceof Cranky)
                ((Cranky)AbstractDungeon.player).setupAnimation();
            return loaded;
        }

        @Override
        public CustomizableItem makeCopy() {
            return new CrankySkin(id, scale / Cranky.SIZE_SCALE);
        }

        private static class CrankySkinData extends PlayerAtlasSkinData {
            private static String skinResourceFolder = CrankyMod.modID + "Resources/images/char/mainChar/multiplayer/skins/";

            public CrankySkinData(String id, float scale) {
                skeletonUrl = CrankyMod.modID + "Resources/images/char/mainChar/cranky.json";
                if (id.equals(makeID("skinBase"))) {
                    atlasUrl = CrankyMod.modID + "Resources/images/char/mainChar/cranky.atlas";
                    resourceDirectoryUrl = CrankyMod.modID + "Resources/images/char/mainChar/";
                } else {
                    String path = id.replace(CrankyMod.modID + ":", "");
                    if (Gdx.files.internal(skinResourceFolder + path + "/cranky.json").exists())
                        skeletonUrl = skinResourceFolder + path + "/cranky.json";
                    atlasUrl = skinResourceFolder + path + "/cranky.atlas";
                    resourceDirectoryUrl = skinResourceFolder + path + "/";
                }
                defaultAnimName = "Idle";

                this.id = id;
                name = id; //CardCrawlGame.languagePack.getUIString(id).TEXT[0];
                this.scale = 1f / Cranky.SIZE_SCALE * scale;

                unlockMethod = FreeUnlockMethod.methodId;
                playerClass = Cranky.Enums.THE_CLOCKWORK.name();
            }
        }
    }
}