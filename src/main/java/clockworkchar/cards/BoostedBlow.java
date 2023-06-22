package clockworkchar.cards;

import clockworkchar.CrankyMod;
import clockworkchar.actions.LetGoAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class BoostedBlow extends AbstractCrankyCard {
    public final static String ID = makeID("BoostedBlow");

    public BoostedBlow() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 0;
        baseMagicNumber = magicNumber = 2;
        baseSpinAmount = spinAmount = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new LetGoAction(spent -> {
            baseDamage = spent * magicNumber;
            calculateCardDamage(m);
            dmgTop(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
            this.initializeDescription();
        }));
    }

    public void applyPowers() {
        baseDamage = CrankyMod.winder.maxChargeUsedOnCard(this) * magicNumber;
        super.applyPowers();
    }
  
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        initializeDescription();
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}