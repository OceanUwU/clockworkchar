package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;

import static clockworkchar.util.Wiz.*;

public class UseToolAction extends AbstractGameAction {
    private int times;

    public UseToolAction(int times) {
        this.times = times;
    }

    public UseToolAction() {
        this(1);
    }

    public void update() {
        isDone = true;
        ClockworkChar.toolSlot.shouldRender = true;
        ClockworkChar.toolSlot.tool.fontScale *= 2f;
        for (int i = 0; i < times; i++) {
            ClockworkChar.toolSlot.tool.use();
            if (i != times-1)
                att(new WaitAction(0.2f));
        }
    }
}