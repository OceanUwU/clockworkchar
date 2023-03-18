package clockworkchar.cards;

import clockworkchar.actions.LetGoAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class RecursionMechanism extends AbstractEasyCard {
    public final static String ID = makeID("RecursionMechanism");
    private final static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public RecursionMechanism() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
        part = true;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new LetGoAction(spent -> {
            baseDamage = GameActionManager.turn * magicNumber;
            calculateCardDamage(m);
            dmg(m, AbstractGameAction.AttackEffect.SMASH);
            this.rawDescription = cardStrings.DESCRIPTION;
            this.initializeDescription();
        }));
    }

    public void activate() {
        att(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, GameActionManager.turn, DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    public void applyPowers() {
        baseDamage = GameActionManager.turn * magicNumber;
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
        upgradeMagicNumber(1);
    }
}