package clockworkchar.potions;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import clockworkchar.ClockworkChar;
import clockworkchar.actions.AttuneAction;
import clockworkchar.actions.EasyModalChoiceAction;
import clockworkchar.actions.EquipToolAction;
import clockworkchar.cards.EasyModalChoiceCard;
import clockworkchar.tools.*;
import clockworkchar.util.TexLoader;
import clockworkchar.vfx.AttuneCardEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

import java.util.ArrayList;

public class SpareNails extends AbstractPotion {
    public static final String POTION_ID = makeID("SpareNails");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final int CHOICES = 2;
    private static AbstractCard[] CHOICE_CARDS = {
        new EasyModalChoiceCard("spanner", potionStrings.DESCRIPTIONS[7], potionStrings.DESCRIPTIONS[4] + potionStrings.DESCRIPTIONS[6] + potionStrings.DESCRIPTIONS[7] + potionStrings.DESCRIPTIONS[11], () -> att(new EquipToolAction(new Spanner()))),
        new EasyModalChoiceCard("screwdriver", potionStrings.DESCRIPTIONS[8], potionStrings.DESCRIPTIONS[4] + potionStrings.DESCRIPTIONS[6] + potionStrings.DESCRIPTIONS[8] + potionStrings.DESCRIPTIONS[11], () -> att(new EquipToolAction(new Screwdriver()))),
        new EasyModalChoiceCard("torch", potionStrings.DESCRIPTIONS[9], potionStrings.DESCRIPTIONS[4] + potionStrings.DESCRIPTIONS[6] + potionStrings.DESCRIPTIONS[9] + potionStrings.DESCRIPTIONS[11], () -> att(new EquipToolAction(new Torch()))),
        new EasyModalChoiceCard("allenkey", potionStrings.DESCRIPTIONS[10].replace("_", " "), potionStrings.DESCRIPTIONS[5] + potionStrings.DESCRIPTIONS[6] + potionStrings.DESCRIPTIONS[10] + potionStrings.DESCRIPTIONS[11], () -> att(new EquipToolAction(new AllenKey()))),
    };

    public SpareNails() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionColor.WHITE);
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "liquidImg", TexLoader.getTexture(ClockworkChar.modID + "Resources/images/potions/NailBottle.png"));
        labOutlineColor = ClockworkChar.characterColor;
    }

    public void initializeData() {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + CHOICES;
        if (potency == 1)
            description += potionStrings.DESCRIPTIONS[1];
        else
            description += potionStrings.DESCRIPTIONS[2] + potency + potionStrings.DESCRIPTIONS[3];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("equip")), BaseMod.getKeywordDescription(makeID("equip"))));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("attuned")), BaseMod.getKeywordDescription(makeID("attuned"))));
    }

    public void use(AbstractCreature abstractCreature) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        while (cards.size() < CHOICES) {
            AbstractCard card = CHOICE_CARDS[AbstractDungeon.cardRandomRng.random(CHOICE_CARDS.length-1)];
            if (cards.contains(card)) continue;
            cards.add(card);
        }
        atb(new EasyModalChoiceAction(cards, 1, potionStrings.DESCRIPTIONS[12]));
        for (AbstractCard c : AbstractDungeon.player.hand.group)
            atb(new AttuneAction(c, potency));
        boolean first = true;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            atb(new VFXAction(AbstractDungeon.player, new AttuneCardEffect(c, first), 0.0f, true));
            first = false;
        }
    }

    public int getPotency(int ascensionlevel) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new SpareNails();
    }
}