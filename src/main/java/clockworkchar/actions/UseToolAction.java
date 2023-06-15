package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static clockworkchar.util.Wiz.*;

public class UseToolAction extends AbstractGameAction {
    private int times;
    private boolean first;

    public UseToolAction(int times, boolean first) {
        this.times = times;
        this.first = first;
    }

    public UseToolAction(int times) {
        this(times, true);
    }

    public UseToolAction() {
        this(1);
    }

    public void update() {
        isDone = true;
        ClockworkChar.toolSlot.shouldRender = true;
        if (first)
            ClockworkChar.toolSlot.tool.fontScale *= 2f;
        if (times > 1)
            att(new UseToolAction(times-1, false));
        ClockworkChar.toolSlot.tool.use();
    }
}