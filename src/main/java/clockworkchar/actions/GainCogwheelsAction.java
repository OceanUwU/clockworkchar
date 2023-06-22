package clockworkchar.actions;

import clockworkchar.CrankyMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class GainCogwheelsAction extends AbstractGameAction {
    private int amount;

    public GainCogwheelsAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        this.isDone = true;
        CrankyMod.winder.gainCogwheels(amount);
    }
}