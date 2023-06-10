package clockworkchar.consolecommands;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import clockworkchar.actions.UseToolAction;

import java.util.ArrayList;

import static clockworkchar.util.Wiz.*;

public class UseToolCommand extends ConsoleCommand {
    public static String COMMAND_NAME = "usetool";

    public UseToolCommand() {
        minExtraTokens = 0;
        maxExtraTokens = 0;
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        complete = true;
        return result;
    }

    public void execute(String[] tokens, int depth) {
        atb(new UseToolAction());
    }

    public void errorMsg() {
        DevConsole.couldNotParse();
        DevConsole.log("usage: usetool");
    }
}
