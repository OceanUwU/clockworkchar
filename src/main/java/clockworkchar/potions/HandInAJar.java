package clockworkchar.potions;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import clockworkchar.ClockworkChar;
import clockworkchar.actions.WindUpAction;
import clockworkchar.util.TexLoader;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class HandInAJar extends AbstractPotion {
    public static final String POTION_ID = makeID("HandInAJar");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public HandInAJar() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.COMMON, PotionSize.GHOST, PotionColor.WHITE);
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "liquidImg", TexLoader.getTexture(ClockworkChar.modID + "Resources/images/potions/HandInAJar.png"));
        labOutlineColor = ClockworkChar.characterColor;
    }

    public void initializeData() {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("wind_up")), BaseMod.getKeywordDescription(makeID("wind_up"))));
    }

    public void use(AbstractCreature abstractCreature) {
        atb(new WindUpAction(potency));
    }

    public int getPotency(int ascensionlevel) {
        return 25;
    }

    public AbstractPotion makeCopy() {
        return new HandInAJar();
    }
}