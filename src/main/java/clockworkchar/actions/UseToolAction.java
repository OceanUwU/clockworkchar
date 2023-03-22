package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class UseToolAction extends AbstractGameAction {
    private int times;

    public UseToolAction(int times) {
        this.times = times;
    }

    public void update() {
        isDone = true;
        ClockworkChar.toolSlot.shouldRender = true;
        ClockworkChar.toolSlot.tool.fontScale = 1.0F;
        for (int i = 0; i < times; i++)
            ClockworkChar.toolSlot.tool.use();
    }
}