package clockworkchar.cards;

import clockworkchar.powers.AbstractEasyPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Mesmerise extends AbstractEasyCard {
    public final static String ID = makeID("Mesmerise");

    public Mesmerise() {
        super(ID, 3, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        forAllMonstersLiving(q -> {
            applyToEnemy(q, new WeakPower(q, magicNumber, false));
            applyToEnemy(q, new DizzyPower(q, secondMagic));
        });
    }

    public void upp() {
        upgradeBaseCost(2);
    }

    public static class DizzyPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("Dizzy");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public DizzyPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.DEBUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }
  
        public int onAttacked(DamageInfo info, int damageAmount) {
            if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != owner) {
                flash();
                applyToEnemy(owner, new StrengthPower(owner, -amount));
                if (!owner.hasPower("Artifact"))
                    applyToEnemy(owner, new GainStrengthPower(owner, amount));
            }
            return damageAmount;
        }
    }
}