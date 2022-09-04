package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static clockworkchar.ClockworkChar.makeID;

public class WindUpAction extends AbstractGameAction {
    public WindUpAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        CardCrawlGame.sound.play(makeID("WIND_UP"));
        ClockworkChar.winder.gainCharge(amount);
        isDone = true;
    }
}