package clockworkchar.cardmods;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import clockworkchar.actions.WindUpAction;
import clockworkchar.characters.TheClockwork;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static clockworkchar.ClockworkChar.makeID;

public class GearMod extends AbstractAugment {
    public static final String ID = makeID("GearMod");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    public static final int EFFECT = 2;

    @Override
    public boolean validCard(AbstractCard card) {
        return isNormalCard(card) && characterCheck(p -> p instanceof TheClockwork) && card.cost >= 0;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertBeforeText(rawDescription, String.format(CARD_TEXT[0], EFFECT));
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToTop(new WindUpAction(EFFECT));
    }

    @Override public AugmentRarity getModRarity() {return AugmentRarity.RARE;}
    @Override public String getPrefix() {return TEXT[0];}
    @Override public String getSuffix() {return TEXT[1];}
    @Override public String getAugmentDescription() {return TEXT[2];}
    @Override public AbstractCardModifier makeCopy() {return new GearMod();}
    @Override public String identifier(AbstractCard card) {return ID;}
}