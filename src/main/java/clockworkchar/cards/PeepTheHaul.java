package clockworkchar.cards;

import clockworkchar.ClockworkChar;
import clockworkchar.powers.AbstractEasyPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;

public class PeepTheHaul extends AbstractEasyCard {
    public final static String ID = makeID("PeepTheHaul");

    public PeepTheHaul() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        for (AbstractCard trinket : ClockworkChar.trinkets.group)
            MultiCardPreview.add(this, trinket.makeCopy());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            PeepTheHaulPower.shuffleRandomTrinket(magicNumber);
        applyToSelf(new PeepTheHaulPower(p, magicNumber));
    }

    public void upp() {}

    public static class PeepTheHaulPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("PeepTheHaul");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public PeepTheHaulPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }

        public static void shuffleRandomTrinket(int num) {
            for (int i = 0; i < num; i++)
                shuffleIn(ClockworkChar.trinkets.getRandomCard(AbstractDungeon.cardRandomRng).makeCopy());
        }

        public void atEndOfTurn(boolean playerTurn) {
            shuffleRandomTrinket(amount);
        }
        
        public void updateDescription() {
            if (amount <= 1)
                description = powerStrings.DESCRIPTIONS[0];
            else
                description = powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
        }
    }
}