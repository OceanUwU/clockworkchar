package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static clockworkchar.ClockworkChar.makeID;

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
        if (sound)
            CardCrawlGame.sound.play(makeID("WIND_UP"));
        ClockworkChar.winder.gainCharge(amount);
        isDone = true;
    }
}