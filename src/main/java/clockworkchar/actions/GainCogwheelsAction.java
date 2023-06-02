package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class GainCogwheelsAction extends AbstractGameAction {
    private int amount;

    public GainCogwheelsAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        this.isDone = true;
        ClockworkChar.winder.gainCogwheels(amount);
    }
}