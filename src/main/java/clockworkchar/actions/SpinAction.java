package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import clockworkchar.powers.BatteryPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.att;
import java.util.function.Consumer;

public class SpinAction extends AbstractGameAction {
    private boolean sound;
    private Consumer<Boolean> cb;

    private boolean spinning = false;

    public SpinAction(int amount, Consumer<Boolean> callback) {
        this(amount, true, callback);
    }
    
    public SpinAction(int amount, boolean playSound, Consumer<Boolean> callback) {
        this.amount = amount;
        sound = playSound;
        cb = callback;
    }

    public void update() {
        if (spinning) return;
        spinning = true;
        if (ClockworkChar.winder.useCharge(amount)) {
            if (sound)
                CardCrawlGame.sound.play(makeID("SPIN"));
            cb.accept(true);
        } else if (AbstractDungeon.player.hasPower(BatteryPower.POWER_ID)) {
            att(new AbstractGameAction() {
                public void update() {
                    this.isDone = true;
                    cb.accept(true);
                }
            });
            att(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, BatteryPower.POWER_ID, 1));
        } else
            cb.accept(false);
        for (AbstractPower p : AbstractDungeon.player.powers)
            p.onGainCharge(-amount);
        isDone = true;
    }
}