package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import clockworkchar.tools.AbstractTool;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static clockworkchar.util.Wiz.*;

public class EquipToolAction extends AbstractGameAction {
    private AbstractTool tool;

    public EquipToolAction(AbstractTool tool) {
        this.tool = tool;
    }

    public void update() {
        isDone = true;
        System.out.println(tool);
        att(new SetToolAction(tool));
        att(new UseToolAction(AbstractTool.DEQUIP_USE_TIMES));
    }
    
    public static class SetToolAction extends AbstractGameAction {
        private AbstractTool tool;
    
        public SetToolAction(AbstractTool tool) {
            this.tool = tool;
        }
    
        public void update() {
            isDone = true;
            ClockworkChar.toolSlot.tool = tool;
        }
    }
}