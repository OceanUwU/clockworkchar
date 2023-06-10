package clockworkchar.helpers;

import clockworkchar.tools.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.HashMap;

public class ToolLibrary {
    public static HashMap<String, AbstractTool> tools = new HashMap<>();

    public static void initialize() {
        add(new AllenKey());
        add(new Screwdriver());
        add(new Spanner());
        add(new Torch());
    };

    public static AbstractTool getTool(String id) {
        return tools.get(id);
    }

    public static AbstractTool getRandomTool(AbstractTool exclude) {
        while (true) {
            AbstractTool tool = ((AbstractTool)tools.values().toArray()[AbstractDungeon.cardRandomRng.random(tools.size() - 1)]);
            if (exclude == null || tool.getClass() != exclude.getClass())
                return tool.makeCopy();
        }
    }

    public static AbstractTool getRandomTool() {
        return getRandomTool(null);
    }

    public static void add(AbstractTool tool) {
        tools.put(tool.id, tool);
    }
}
