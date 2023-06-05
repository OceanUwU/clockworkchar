package clockworkchar.cards;

import clockworkchar.powers.AbstractEasyPower;
import clockworkchar.powers.BatteryPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class PeepTheHaul extends AbstractEasyCard {
    public final static String ID = makeID("PeepTheHaul");

    public PeepTheHaul() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            applyToSelf(new BatteryPower(p, magicNumber));
        applyToSelf(new PeepTheHaulPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        uDesc();
    }

    public static class PeepTheHaulPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("PeepTheHaul");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public PeepTheHaulPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }

        public void atStartOfTurn() {
            if (pwrAmt(AbstractDungeon.player, BatteryPower.POWER_ID) < amount) {
                flash();
                applyToSelf(new BatteryPower(AbstractDungeon.player, amount - pwrAmt(AbstractDungeon.player, BatteryPower.POWER_ID)));
            }
        }
        
        public void updateDescription() {
            if (amount <= 1)
                description = powerStrings.DESCRIPTIONS[0];
            else
                description = powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2] + amount + powerStrings.DESCRIPTIONS[3];
        }
    }
}