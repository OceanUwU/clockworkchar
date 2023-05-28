package clockworkchar.helpers;

import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import clockworkchar.tools.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.HashMap;

@SpireInitializer
public class ToolLibrary implements PostInitializeSubscriber {
    public static HashMap<String, AbstractTool> tools = new HashMap<>();

    public static void initialize() {
        BaseMod.subscribe(new ToolLibrary());
    };

    public void receivePostInitialize() {
        add(new AllenKey());
        add(new Screwdriver());
        add(new Spanner());
        add(new Torch());
    }

    public static AbstractTool getRandomTool() {
        return ((AbstractTool)tools.values().toArray()[AbstractDungeon.cardRandomRng.random(tools.size() - 1)]).makeCopy();
    }

    public static void add(AbstractTool tool) {
        tools.put(tool.id, tool);
    }
}
