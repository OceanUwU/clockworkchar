package clockworkchar.consolecommands;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import clockworkchar.ClockworkChar;

import java.util.ArrayList;

public class WinderCommand extends ConsoleCommand {
    public static String COMMAND_NAME = "winder";

    public WinderCommand() {
        minExtraTokens = 2;
        maxExtraTokens = 2;
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        result.add("add");
        result.add("lose");

        if(tokens.length == depth + 1)
            return result;
        else if(tokens[depth].equalsIgnoreCase("add") || tokens[depth].equalsIgnoreCase("lose")) {
            if(tokens[depth + 1].matches("\\d+"))
                complete = true;
            result = smallNumbers();
        }

        return result;
    }

    public void execute(String[] tokens, int depth) {
        if (tokens[1].toLowerCase().equals("add") && tokens.length > 2) {
            ClockworkChar.winder.gainCharge(ConvertHelper.tryParseInt(tokens[2], 0));
        } else if (tokens[1].toLowerCase().equals("lose") && tokens.length > 2) {
            ClockworkChar.winder.useCharge(Math.min(ConvertHelper.tryParseInt(tokens[2], 0), ClockworkChar.winder.charge));
        } else errorMsg();
    }

    public void errorMsg() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* add [amt]");
        DevConsole.log("* lose [amt]");
    }
}
