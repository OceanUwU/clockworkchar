package clockworkchar.potions;

import basemod.ReflectionHacks;
import basemod.helpers.CardPowerTip;
import clockworkchar.ClockworkChar;
import clockworkchar.cards.Spinner;
import clockworkchar.powers.AbstractEasyPower;
import clockworkchar.util.TexLoader;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Sawblade extends AbstractPotion {
    public static final String POTION_ID = makeID("Sawblade");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public Sawblade() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.RARE, PotionSize.S, PotionColor.WHITE);
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "containerImg", TexLoader.getTexture(ClockworkChar.modID + "Resources/images/potions/Sawblade.png"));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "outlineImg", TexLoader.getTexture(ClockworkChar.modID + "Resources/images/potions/SawbladeOutline.png"));
        labOutlineColor = ClockworkChar.characterColor;
    }

    public void initializeData() {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new CardPowerTip(new Spinner()));
    }

    public void use(AbstractCreature abstractCreature) {
        shuffleIn(new Spinner(), potency);
        applyToSelf(new SawbladePower(AbstractDungeon.player, 1));
    }

    public int getPotency(int ascensionlevel) {
        return 2;
    }

    public AbstractPotion makeCopy() {
        return new Sawblade();
    }

    public static class SawbladePower extends AbstractEasyPower {
        public static String POWER_ID = makeID("Sawblade");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public SawbladePower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }

        public void onCardDraw(AbstractCard card) {
            if (card instanceof Spinner) {
                flash();
                atb(new DrawCardAction(amount));
            }
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[amount == 1 ? 1 : 2];
        }
    }
}