package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Rewinder extends AbstractEasyCard {
    public final static String ID = makeID("Rewinder");

    public Rewinder() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseSpinAmount = spinAmount = 2;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public void atTurnStart() {
        selfRetain = false;
    }

    public void activate() {
        att(new SpinAction(spinAmount, spun -> {
            if (spun) {
                selfRetain = true;
                att(new RetainCardsAction(AbstractDungeon.player, 1));
            }
        }));
    }

    public void upp() {
        upgradeSpinAmount(1);
    }
}