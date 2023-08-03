package clockworkchar.cards;

import basemod.helpers.BaseModCardTags;
import basemod.interfaces.AlternateCardCostModifier;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.AlternateCardCosts;
import clockworkchar.CrankyMod;
import clockworkchar.actions.GainCogwheelsAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import java.util.ArrayList;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class PerpetualForm extends AbstractCrankyCard {
    public final static String ID = makeID("PerpetualForm");

    public PerpetualForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(BaseModCardTags.FORM);
        baseMagicNumber = magicNumber = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ChangeStanceAction(new PerpetualStance()));
        atb(new GainCogwheelsAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(4);
    }

    public static class PerpetualStance extends AbstractStance implements AlternateCardCostModifier {
        public static String STANCE_ID = makeID("PerpetualStance");
        private static final StanceStrings stanceStrings = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
        public final static int ENERGY_LOST = 2;
        public final static int CHARGE_PER_ENERGY = 8;

        public PerpetualStance() {
            ID = STANCE_ID;
            name = stanceStrings.NAME;
            updateDescription();
        }

        public void atStartOfTurn() {
            atb(new LoseEnergyAction(ENERGY_LOST));
        }

        public void updateDescription() {
            description = stanceStrings.DESCRIPTION[0];
        }
  
        public void updateAnimation() {
                if (!Settings.DISABLE_EFFECTS) {
                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0f) {
                    this.particleTimer += 0.4f;
                    AbstractDungeon.effectsQueue.add(new PerpetualParticleEffect());
                }
            } 
            /*particleTimer2 -= Gdx.graphics.getDeltaTime();
            if (particleTimer2 < 0f) {
                particleTimer2 = MathUtils.random(0.45f, 0.55f);
                StanceAuraEffect aura = new StanceAuraEffect(ID);
                Color color = Color.WHITE.cpy();
                color.a = 0f;
                ReflectionHacks.setPrivate(aura, AbstractGameEffect.class, "color", color);
                AbstractDungeon.effectsQueue.add(aura);
            }*/
        }
  
        public void onEnterStance() {
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.WHITE, true));
        }

        public int getAlternateResource(AbstractCard card) {return CrankyMod.winder.charge / CHARGE_PER_ENERGY;}
        public boolean prioritizeAlternateCost(AbstractCard card) {return false;}
        public boolean canSplitCost(AbstractCard card) {return true;}
        public static int amountSpentOnCard(AbstractCard card) {
            if (card.cost == -1)
                return CrankyMod.winder.charge - CrankyMod.winder.charge % CHARGE_PER_ENERGY;
            return (card.costForTurn - EnergyPanel.totalCount) * CHARGE_PER_ENERGY;
        }
        public int spendAlternateCost(AbstractCard card, int costToSpend) {
            int chargeToSpend = 0;
            while (costToSpend > 0 && CrankyMod.winder.charge >= chargeToSpend + CHARGE_PER_ENERGY) {
                chargeToSpend += CHARGE_PER_ENERGY;
                costToSpend--;
            }
            final int spendingCharge = chargeToSpend;
            if (spendingCharge > 0)
                att(new AbstractGameAction() {
                    public void update() {
                        CrankyMod.winder.useCharge(spendingCharge);
                        PerpetualParticleEffect.GLOBAL_SPEED_MULT = 5f;
                        isDone=true;
                    }
                });
            return costToSpend;
        }

        @SpirePatch(clz=AbstractCard.class, method="renderEnergy")
        public static class ChargeCostDisplay {
            private static TextureAtlas.AtlasRegion costDisplayTexture = new TextureAtlas.AtlasRegion(new Texture(CrankyMod.makeImagePath("512/chargeCost.png")), 0, 0, 512, 512);
            private static Color COST_RESTRICTED_COLOR = new Color(1.0F, 0.3F, 0.3F, 1.0F);

            public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
                if (CardCrawlGame.isInARun() && AbstractDungeon.player.stance instanceof PerpetualStance && AbstractDungeon.player.hand.contains(__instance)) {
                    int chargeCost = PerpetualStance.amountSpentOnCard(__instance);
                    if (chargeCost > 0) {
                        sb.setColor(Color.WHITE);
                        sb.draw(costDisplayTexture, __instance.current_x - costDisplayTexture.originalWidth / 2.0F, __instance.current_y - costDisplayTexture.originalHeight / 2.0F, costDisplayTexture.originalWidth / 2.0F, costDisplayTexture.originalHeight / 2.0F, costDisplayTexture.packedWidth, costDisplayTexture.packedHeight, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle);
                        FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(chargeCost), __instance.current_x, __instance.current_y, -132.0F * __instance.drawScale * Settings.scale, 118.0F * __instance.drawScale * Settings.scale, __instance.angle, false, CrankyMod.winder.charge >= chargeCost ? Color.WHITE : COST_RESTRICTED_COLOR);
                    }
                }
            }
        }

        @SpirePatch(clz=AlternateCardCosts.class, method="modifiers")
        public static class AddStanceCostModifier {
            public static ArrayList<AlternateCardCostModifier> Postfix(ArrayList<AlternateCardCostModifier> result) {
                if (CardCrawlGame.isInARun() && AbstractDungeon.player.stance instanceof AlternateCardCostModifier && !result.contains((AlternateCardCostModifier)AbstractDungeon.player.stance))
                    result.add((AlternateCardCostModifier)AbstractDungeon.player.stance);
                return result;
            }
        }

        public static class PerpetualParticleEffect extends AbstractGameEffect {
            private static TextureAtlas.AtlasRegion IMG = ImageMaster.GLOW_SPARK;
            private static int W = IMG.packedWidth;
            private static int H = IMG.packedHeight;
            private static float REVOLUTION_TIME = 0.8f;
            private static float OVAL_Y_SQUASH = 0.25f;
            private static float FULL_STRETCH_LENGTH = 2f;
            public static float GLOBAL_SPEED_MULT = 1f;
            private static long MULT_LAST_FRAME_EDITED = 0;
            private float halfDur;
            private float circleRadius = AbstractDungeon.player.hb.width / 2f;
            private float cX = AbstractDungeon.player.hb.cX + MathUtils.random(-5f, 5f) * Settings.scale - 25f * Settings.scale;
            private float cY = AbstractDungeon.player.hb.cY + MathUtils.random(-110.0F, 110.0F) * Settings.scale - 32.0F;
            private float x, y, stretch;
            private float speed = MathUtils.random(0.75f, 1.25f);
            private float rads = MathUtils.random(0f, (float)Math.PI * 2f);

            public PerpetualParticleEffect() {
                duration = MathUtils.random(2.5f, 3.5f);
                halfDur = duration / 2f;
                scale *= MathUtils.random(0.6f, 1.0f);
                color = Color.WHITE.cpy();
            }

            public void update() {
                if (duration > halfDur) color.a = Interpolation.fade.apply(1f, 0f, (duration - halfDur) / halfDur);
                else color.a = Interpolation.fade.apply(0f, 1f, duration / halfDur);
                color.a *= 0.6f;

                rads -= Gdx.graphics.getDeltaTime() * Math.PI * 2f / REVOLUTION_TIME * speed * GLOBAL_SPEED_MULT;
                if (rads < 0f)
                    rads += (float)Math.PI * 2f;
                renderBehind = rads < (float)Math.PI;

                x = cX + circleRadius * (float)Math.cos(rads);
                y = cY + circleRadius * (float)Math.sin(rads) * OVAL_Y_SQUASH;
                stretch = 0.2f + Math.abs((float)Math.sin(rads)) * 0.8f;
                rotation = (float)Math.toDegrees(rads);

                if (MULT_LAST_FRAME_EDITED != Gdx.graphics.getFrameId()) {
                    GLOBAL_SPEED_MULT = MathUtils.lerp(GLOBAL_SPEED_MULT, 1f, Gdx.graphics.getDeltaTime() * 2f);
                    MULT_LAST_FRAME_EDITED = Gdx.graphics.getFrameId();
                }

                this.duration -= Gdx.graphics.getDeltaTime();
                if (duration < 0f)
                    isDone = true;
            }

            public void render(SpriteBatch sb) {
                sb.setColor(this.color);
                sb.setBlendFunction(770, 1);
                sb.draw((TextureRegion)IMG, x, y, W/2f, H/2f, W, H, scale, scale * stretch * FULL_STRETCH_LENGTH, rotation);
                sb.setBlendFunction(770, 771);
            }

            public void dispose() {}
        }
    }
}