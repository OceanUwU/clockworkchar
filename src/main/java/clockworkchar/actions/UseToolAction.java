package clockworkchar.actions;

import clockworkchar.CrankyMod;
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
        CrankyMod.toolSlot.shouldRender = true;
        if (first)
            CrankyMod.toolSlot.tool.fontScale *= 2f;
        if (times > 1)
            att(new UseToolAction(times-1, false));
        CrankyMod.toolSlot.tool.use();
    }
}