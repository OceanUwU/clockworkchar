package clockworkchar.cards;

import clockworkchar.ClockworkChar;
import clockworkchar.actions.LetGoAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class BoostedBlow extends AbstractEasyCard {
    public final static String ID = makeID("BoostedBlow");
    private final static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public BoostedBlow() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new LetGoAction(spent -> {
            baseDamage = spent * magicNumber;
            calculateCardDamage(m);
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
            this.rawDescription = cardStrings.DESCRIPTION;
            this.initializeDescription();
        }));
    }

    public void applyPowers() {
        baseDamage = ClockworkChar.winder.charge * magicNumber;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }
  
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (this.baseMagicNumber > 0)
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}