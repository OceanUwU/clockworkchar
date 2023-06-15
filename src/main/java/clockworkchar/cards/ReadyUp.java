package clockworkchar.cards;

import clockworkchar.actions.WindUpAction;
import clockworkchar.powers.AbstractEasyPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class ReadyUp extends AbstractEasyCard {
    public final static String ID = makeID("ReadyUp");

    public ReadyUp() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 10;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new WindUpAction(magicNumber));
        applyToSelf(new EnergizedCrankyPower(p, secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(4);
    }

    public static class EnergizedCrankyPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("EnergizedCranky");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public EnergizedCrankyPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }
  
        public void onEnergyRecharge() {
            flash();
            AbstractDungeon.player.gainEnergy(this.amount);
            atb(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }
}