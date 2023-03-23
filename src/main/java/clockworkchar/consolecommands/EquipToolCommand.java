package clockworkchar.consolecommands;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import clockworkchar.actions.EquipToolAction;
import clockworkchar.helpers.ToolLibrary;
import clockworkchar.tools.AbstractTool;

import java.util.ArrayList;

import static clockworkchar.util.Wiz.*;

public class EquipToolCommand extends ConsoleCommand {
    public static String COMMAND_NAME = "equiptool";

    public EquipToolCommand() {
        minExtraTokens = 1;
        maxExtraTokens = 1;
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        for (AbstractTool t : ToolLibrary.tools.values())
            result.add(t.id);
        if (result.contains(tokens[depth]))
            complete = true;
        return result;
    }

    public void execute(String[] tokens, int depth) {
        AbstractTool tool = ToolLibrary.tools.get(tokens[1]);
        if (tool instanceof AbstractTool)
            atb(new EquipToolAction(tool.makeCopy()));
        else
            errorMsg();
    }

    public void errorMsg() {
        DevConsole.couldNotParse();
        DevConsole.log("usage: equiptool [toolID]");
    }
}
