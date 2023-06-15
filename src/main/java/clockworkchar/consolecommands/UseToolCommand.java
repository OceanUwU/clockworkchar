package clockworkchar.consolecommands;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import clockworkchar.actions.UseToolAction;

import java.util.ArrayList;

import static clockworkchar.util.Wiz.*;

public class UseToolCommand extends ConsoleCommand {
    public static String COMMAND_NAME = "usetool";

    public UseToolCommand() {
        minExtraTokens = 0;
        maxExtraTokens = 1;
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        if(tokens.length > 2 && tokens[depth + 1].matches("\\d+"))
            complete = true;
        return smallNumbers();
    }

    public void execute(String[] tokens, int depth) {
        atb(new UseToolAction(tokens.length > 1 ? ConvertHelper.tryParseInt(tokens[1], 1) : 1));
    }

    public void errorMsg() {
        DevConsole.couldNotParse();
        DevConsole.log("usage: usetool");
    }
}
