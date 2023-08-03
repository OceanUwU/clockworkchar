package clockworkchar.cards;

import clockworkchar.powers.AbstractEasyPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Reassemble extends AbstractCrankyCard {
    public final static String ID = makeID("Reassemble");

    public Reassemble() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 5;
        baseThirdMagic = thirdMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ReassemblePower(p, magicNumber, secondMagic, thirdMagic));
    }

    public void upp() {
        upgradeSecondMagic(2);
    }

    public static class ReassemblePower extends AbstractEasyPower {
        public static String POWER_ID = makeID("ReassemblePower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        private static int idOffset = 0;

        private int draw;

        public ReassemblePower(AbstractCreature owner, int amount, int block, int draw) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
            ID += idOffset++;
            isTwoAmount = true;
            amount2 = block;
            this.draw = draw;
            updateDescription();
        }
        
        public void updateDescription() {
            description = (amount == 1 ? powerStrings.DESCRIPTIONS[0] : powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2]) + amount2 + powerStrings.DESCRIPTIONS[3] + draw + powerStrings.DESCRIPTIONS[draw == 1 ? 4 : 5];
        }

        public void onCardDraw(AbstractCard card) {
            if (card instanceof AbstractCrankyCard && ((AbstractCrankyCard)card).part) {
                flash();
                atb(new GainBlockAction(owner, amount2));
                atb(new DrawCardAction(draw));
                atb(new ReducePowerAction(this.owner, this.owner, this, 1));
            }
        }
    }
}