package clockworkchar.cardmods;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import clockworkchar.cards.AbstractCrankyCard;
import clockworkchar.characters.Cranky;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;

public class FamiliarMod extends AbstractAugment {
    public static final String ID = makeID("FamiliarMod");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    @Override
    public boolean validCard(AbstractCard card) {
        return isNormalCard(card) && card instanceof AbstractCrankyCard && characterCheck(p -> p instanceof Cranky) && (card.baseDamage > 1 || card.baseBlock > 1);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        ((AbstractCrankyCard)card).attune(1);
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 1)
            return damage * MINOR_DEBUFF;
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 1)
            return block * MINOR_DEBUFF;
        return block;
    }

    @Override public AugmentRarity getModRarity() {return AugmentRarity.COMMON;}
    @Override public String getPrefix() {return TEXT[0];}
    @Override public String getSuffix() {return TEXT[1];}
    @Override public String getAugmentDescription() {return TEXT[2];}
    @Override public AbstractCardModifier makeCopy() {return new FamiliarMod();}
    @Override public String identifier(AbstractCard card) {return ID;}
}