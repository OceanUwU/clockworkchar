package clockworkchar.cardmods;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import clockworkchar.cards.AbstractCrankyCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static clockworkchar.CrankyMod.makeID;

public class ElectricMod extends AbstractAugment {
    public static final String ID = makeID("ElectricMod");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    @Override
    public boolean validCard(AbstractCard card) {
        return isNormalCard(card) && card instanceof AbstractCrankyCard && card.cost >= 0 && ((AbstractCrankyCard)card).baseSpinAmount > 1 && cardCheck(card, c -> doesntUpgradeCost());
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        ((AbstractCrankyCard)card).baseSpinAmount = 0;
        ((AbstractCrankyCard)card).spinAmount = 0;
        card.cost++;
        card.costForTurn = card.cost;
    }

    @Override public AugmentRarity getModRarity() {return AugmentRarity.UNCOMMON;}
    @Override public String getPrefix() {return TEXT[0];}
    @Override public String getSuffix() {return TEXT[1];}
    @Override public String getAugmentDescription() {return TEXT[2];}
    @Override public AbstractCardModifier makeCopy() {return new ElectricMod();}
    @Override public String identifier(AbstractCard card) {return ID;}
}