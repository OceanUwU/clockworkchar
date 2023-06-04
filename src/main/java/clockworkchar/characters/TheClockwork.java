package clockworkchar.characters;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import clockworkchar.cards.Defend;
import clockworkchar.cards.Strike;
import clockworkchar.cards.Twist;
import clockworkchar.cards.Waddle;
import clockworkchar.relics.LeftHand;
import clockworkchar.util.TexLoader;
import clockworkchar.vfx.TiltingSpannerVictoryEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Bone;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static clockworkchar.ClockworkChar.*;
import static clockworkchar.characters.TheClockwork.Enums.CLOCKWORK_BROWN_COLOR;

import java.util.ArrayList;
import java.util.List;

public class TheClockwork extends CustomPlayer {
    public static final String[] orbTextures = {
            modID + "Resources/images/char/mainChar/orb/l1.png",
            modID + "Resources/images/char/mainChar/orb/l2.png",
            modID + "Resources/images/char/mainChar/orb/l3.png",
            modID + "Resources/images/char/mainChar/orb/empty.png",
            modID + "Resources/images/char/mainChar/orb/empty.png",
            modID + "Resources/images/char/mainChar/orb/l4.png",
            modID + "Resources/images/char/mainChar/orb/l1d.png",
            modID + "Resources/images/char/mainChar/orb/l2d.png",
            modID + "Resources/images/char/mainChar/orb/l3d.png",
            modID + "Resources/images/char/mainChar/orb/empty.png",
            modID + "Resources/images/char/mainChar/orb/empty.png",};
    public static final float[] orbRotationValues = new float[]{0.0F, 0.0F, 32.0F, 0.0F, 0.0F};
    private static final Float SIZE_SCALE = 1.2F;
    public static final Float ANIMATION_SPEED = 0.8F;
    static final String ID = makeID("TheClockwork");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    public static boolean endEffectStarted = false;
    static final String[] NAMES = characterStrings.NAMES;
    static final String[] TEXT = characterStrings.TEXT;
    
    public Bone winderBone;
    public Bone handBone;
    public Bone drillBone;

    public TheClockwork(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, modID + "Resources/images/char/mainChar/orb/vfx.png", orbRotationValues), new SpineAnimation(modID + "Resources/images/char/mainChar/cranky.atlas", modID + "Resources/images/char/mainChar/cranky.json", SIZE_SCALE));
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), -5.0F, -10.0F, 172.0F, 268.0F, new EnergyManager(3));
        
        winderBone = this.skeleton.findBone("winder");
        handBone = this.skeleton.findBone("hand");
        drillBone = this.skeleton.findBone("drill");
        drillBone.setScale(0.0F);


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);

        
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.5F);
        e.setTimeScale(ANIMATION_SPEED);
    }

    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            AnimationState.TrackEntry e = this.state.setAnimation(0, "Hit", false);
            AnimationState.TrackEntry e2 = this.state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(ANIMATION_SPEED);
            e2.setTimeScale(ANIMATION_SPEED);
        }

        super.damage(info);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                75, 75, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++) retVal.add(Strike.ID);
        //retVal.add(StrikeAttuned.ID);
        for (int i = 0; i < 4; i++) retVal.add(Defend.ID);
        retVal.add(Twist.ID);
        retVal.add(Waddle.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(LeftHand.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA(makeID("WIND_UP"), MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return makeID("WIND_UP");
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 8;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CLOCKWORK_BROWN_COLOR;
    }

    @Override
    public Color getCardTrailColor() {
        return characterColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Twist();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheClockwork(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return characterColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return characterColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SMASH,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public String getSensoryStoneText() {
        return TEXT[3];
    }

    @Override
    public Texture getCutsceneBg() {
        return TexLoader.getTexture(makeImagePath("ending/brownBg.png"));
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        endEffectStarted = false;
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(makeImagePath("ending/1.png"), makeID("WIND_UP")));
        panels.add(new CutscenePanel(makeImagePath("ending/2.png"), makeID("SPIN")));
        panels.add(new CutscenePanel(makeImagePath("ending/3.png")));
        return panels;
    }

    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
        if (!endEffectStarted) {
            effects.add(new TiltingSpannerVictoryEffect());
            endEffectStarted = true;
        }
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_CLOCKWORK;
        @SpireEnum(name = "CLOCKWORK_BROWN_COLOR")
        public static AbstractCard.CardColor CLOCKWORK_BROWN_COLOR;
        @SpireEnum(name = "CLOCKWORK_BROWN_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
