package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import clockworkchar.powers.AbstractEasyPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Rewinder extends AbstractCrankyCard {
    public final static String ID = makeID("Rewinder");

    public Rewinder() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseSpinAmount = spinAmount = 3;
        baseMagicNumber = magicNumber = 1;
        part = true;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return canPlayUnplayablePart();
    }

    public void activate() {
        att(new SpinAction(spinAmount, spun -> {
            retain = true;
            if (spun)
                applyToSelfTop(new RetainCardsThisTurnPower(adp(), magicNumber));
        }));
    }

    public void upp() {
        upgradeSpinAmount(1);
    }

    public static class RetainCardsThisTurnPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("RetainCardsThisTurn");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

        public RetainCardsThisTurnPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, true, owner, amount);
            loadRegion("retain");
        }

        public void atEndOfTurn(boolean isPlayer) {
            if (isPlayer && !adp().hand.isEmpty() && !adp().hasRelic(RunicPyramid.ID) && !adp().hasPower(EquilibriumPower.POWER_ID)) {
                atb(new RetainCardsAction(owner, amount));
                atb(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[amount == 1 ? 1 : 2];
        }
    }
}