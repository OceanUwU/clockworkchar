package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static clockworkchar.ClockworkChar.makeID;
import java.util.function.Consumer;

public class SpinAction extends AbstractGameAction {
    boolean sound;
    Consumer<Boolean> cb;

    public SpinAction(int amount, Consumer<Boolean> callback) {
        this(amount, true, callback);
    }
    
    public SpinAction(int amount, boolean playSound, Consumer<Boolean> callback) {
        this.amount = amount;
        sound = playSound;
        cb = callback;
    }

    public void update() {
        if (ClockworkChar.winder.useCharge(amount)) {
            if (sound)
                CardCrawlGame.sound.play(makeID("SPIN"));
            cb.accept(true);
        } else
            cb.accept(false);
        isDone = true;
    }
}