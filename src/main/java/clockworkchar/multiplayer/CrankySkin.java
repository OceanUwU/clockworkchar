package clockworkchar.multiplayer;

import basemod.ReflectionHacks;
import clockworkchar.characters.TheClockwork;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import spireTogether.Unlockable;
import spireTogether.monsters.CharacterEntity;
import spireTogether.skins.AtlasSkin;

import static clockworkchar.ClockworkChar.makeImagePath;

public class CrankySkin extends AtlasSkin {
    public Bone winderBone, handBone, drillBone;

    public CrankySkin(String ID, Unlockable.UnlockMethod skinType, AbstractPlayer.PlayerClass playerClass) {
        super(ID, makeImagePath("char/mainChar/multiplayer/skins"), skinType, playerClass);
        scale = TheClockwork.SIZE_SCALE;
    }

    public void LoadResources() {
        super.LoadResources();
        shoulderIMG = makeImagePath("char/mainChar/shoulder.png");
        shoulder2IMG = makeImagePath("char/mainChar/shoulder2.png");
        corpseIMG = makeImagePath("char/mainChar/corpse.png");
        jsonLoc = Gdx.files.internal(this.skinResourceFolder + "cranky.json").exists() ? (this.skinResourceFolder + "cranky.json") : makeImagePath("char/mainChar/cranky.json");
        atlasLoc = Gdx.files.internal(this.skinResourceFolder + "cranky.atlas").exists() ? (this.skinResourceFolder + "cranky.atlas") : makeImagePath("char/mainChar/cranky.atlas");
    }

    public boolean LoadSkin(CharacterEntity e, float scaleMult) {
        e.loadAnimation(atlasLoc, jsonLoc, scale * scaleMult);
        return true;
    }

    public void LoadSkinOnPlayer() {
        if (this.playerClass.equals(AbstractDungeon.player.chosenClass)) {
            ReflectionHacks.privateMethod(AbstractCreature.class, "loadAnimation", String.class, String.class, float.class).invoke(AbstractDungeon.player, atlasLoc, jsonLoc, scale);
            ((TheClockwork)AbstractDungeon.player).setupAnimation();
        }
    }
}