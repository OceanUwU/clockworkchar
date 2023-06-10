package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import clockworkchar.cards.GreasedCogs;
import clockworkchar.cards.Inertia;
import clockworkchar.powers.AbstractEasyPower;
import clockworkchar.relics.Gearbox;
import clockworkchar.relics.OilCanister;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.pwrAmt;

public class WindUpAction extends AbstractGameAction {
    private boolean sound;

    public WindUpAction(int amount) {
        this(amount, true);
    }

    public WindUpAction(int amount, boolean playSound) {
        this.amount = amount;
        sound = playSound;
    }

    public void update() {
        isDone = true;
        if (sound)
            CardCrawlGame.sound.play(makeID("WIND_UP"));
        /*if (AbstractDungeon.player.hasPower(GreasedCogs.TwistyPower.POWER_ID)) {
            AbstractDungeon.player.getPower(GreasedCogs.TwistyPower.POWER_ID).flash();
            amount *= pwrAmt(AbstractDungeon.player, GreasedCogs.TwistyPower.POWER_ID) + 1;
        }*/
        if (AbstractDungeon.player.hasRelic(OilCanister.ID)) {
            AbstractDungeon.player.getRelic(OilCanister.ID).flash();
            amount += OilCanister.EXTRA_WIND_AMOUNT;
        }
        if (AbstractDungeon.player.hasRelic(Gearbox.ID))
            ((Gearbox)AbstractDungeon.player.getRelic(Gearbox.ID)).onWind(amount);
        ClockworkChar.winder.gainCharge(amount, true);
        
        for (AbstractPower power: AbstractDungeon.player.powers)
            if (power instanceof AbstractEasyPower)
                ((AbstractEasyPower)power).onWindUp(amount);
        AbstractDungeon.player.hand.applyPowers();
    }
}