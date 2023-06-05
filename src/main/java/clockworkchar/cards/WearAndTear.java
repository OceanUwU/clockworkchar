package clockworkchar.cards;

import clockworkchar.ClockworkChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class WearAndTear extends AbstractEasyCard {
    public final static String ID = makeID("WearAndTear");
    private final static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public WearAndTear() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        exhaust = true;
    }

    private String desc() {
        if (upgraded) return cardStrings.UPGRADE_DESCRIPTION;
        return cardStrings.DESCRIPTION;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            public void update() {
                this.isDone = true;
                baseDamage = ClockworkChar.winder.chargeGained;
                calculateCardDamage(m);
                dmgTop(m, AbstractGameAction.AttackEffect.SMASH);
                rawDescription = desc();
                initializeDescription();
            }
        });
    }

    public void applyPowers() {
        baseDamage = ClockworkChar.winder.chargeGained;
        super.applyPowers();
        rawDescription = desc() + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }
  
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (baseMagicNumber > 0)
            rawDescription = desc() + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void upp() {
        uDesc();
        selfRetain = true;
    }
}