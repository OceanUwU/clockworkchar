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
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return canPlayUnplayablePart();
    }

    public void activate() {
        att(new SpinAction(spinAmount, spun -> {
            if (spun)
                atb(new RetainCardsAction(AbstractDungeon.player, 1)); //atb so that it triggers after other parts have decided whether to retain or not
        }));
    }

    public void upp() {
        upgradeSpinAmount(1);
    }
}