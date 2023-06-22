package clockworkchar.cards;

import basemod.helpers.BaseModCardTags;
import basemod.interfaces.AlternateCardCostModifier;
import clockworkchar.CrankyMod;
import clockworkchar.actions.GainCogwheelsAction;
import clockworkchar.powers.AbstractEasyPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class PerpetualForm extends AbstractCrankyCard {
    public final static String ID = makeID("PerpetualForm");
    public final static int CHARGE_PER_ENERGY = 8;

    public PerpetualForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(BaseModCardTags.FORM);
        baseMagicNumber = magicNumber = 6;
        baseSecondMagic = secondMagic = 2;
        baseThirdMagic = thirdMagic = CHARGE_PER_ENERGY;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PerpetualFormPower(p, secondMagic));
        atb(new GainCogwheelsAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }

    public static class PerpetualFormPower extends AbstractEasyPower implements AlternateCardCostModifier {
        public static String POWER_ID = makeID("PerpetualFormPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        public static boolean perpetual = false;

        public PerpetualFormPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
            perpetual = true;
        }

        public void onEnergyRecharge() {
            atb(new LoseEnergyAction(amount));
        }

        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0];
            for (int i=0; i<this.amount; i++)
                description += "[E] ";
            description += powerStrings.DESCRIPTIONS[1] + CHARGE_PER_ENERGY + powerStrings.DESCRIPTIONS[2];
        }

        public void onVictory() {
            perpetual = false;
        }

        public void onDeath() {
            perpetual = false;
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
            if (spendingCharge > 0) {
                att(new AbstractGameAction() {public void update() {CrankyMod.winder.useCharge(spendingCharge);isDone=true;}});
                flash();
            }
            return costToSpend;
        }

        @SpirePatch(clz=AbstractCard.class, method="renderEnergy")
        public static class ChargeCostDisplay {
            private static TextureAtlas.AtlasRegion costDisplayTexture = new TextureAtlas.AtlasRegion(new Texture(CrankyMod.makeImagePath("512/chargeCost.png")), 0, 0, 512, 512);
            private static Color COST_RESTRICTED_COLOR = new Color(1.0F, 0.3F, 0.3F, 1.0F);

            public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
                if (perpetual && AbstractDungeon.player.hand.contains(__instance)) {
                    int chargeCost = PerpetualFormPower.amountSpentOnCard(__instance);
                    if (chargeCost > 0) {
                        sb.setColor(Color.WHITE);
                        sb.draw(costDisplayTexture, __instance.current_x - costDisplayTexture.originalWidth / 2.0F, __instance.current_y - costDisplayTexture.originalHeight / 2.0F, costDisplayTexture.originalWidth / 2.0F, costDisplayTexture.originalHeight / 2.0F, costDisplayTexture.packedWidth, costDisplayTexture.packedHeight, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle);
                        FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(chargeCost), __instance.current_x, __instance.current_y, -132.0F * __instance.drawScale * Settings.scale, 118.0F * __instance.drawScale * Settings.scale, __instance.angle, false, CrankyMod.winder.charge >= chargeCost ? Color.WHITE : COST_RESTRICTED_COLOR);
                    }
                }
            }
        }
    }
}