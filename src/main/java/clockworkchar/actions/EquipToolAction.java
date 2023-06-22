package clockworkchar.actions;

import clockworkchar.CrankyMod;
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
        CrankyMod.toolSlot.tool.dequipping = true;
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
            CrankyMod.toolSlot.dequipped.add(CrankyMod.toolSlot.tool);
            CrankyMod.toolSlot.tool.dequip();
            CrankyMod.toolSlot.tool = tool;
        }
    }
}