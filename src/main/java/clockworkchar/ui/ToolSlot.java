package clockworkchar.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import clockworkchar.characters.TheClockwork;
import clockworkchar.tools.AbstractTool;
import clockworkchar.tools.Spanner;

public class ToolSlot {
    public boolean shouldRender = false;

    public ArrayList<AbstractTool> dequipped = new ArrayList<>();
    public AbstractTool tool = reset();

    public AbstractTool reset() {
        shouldRender = AbstractDungeon.player instanceof TheClockwork;
        dequipped.clear();
        tool = new Spanner();
        return tool;
    }

    public void update() {
        tool.update();
        for (AbstractTool t : dequipped)
            if (t != tool)
                t.update();
        for (AbstractTool t : dequipped)
            if (t.done) {
                dequipped.remove(t);
                break;
            }
    }

    public void updateAnimation() {
        tool.updateAnimation();
        for (AbstractTool t : dequipped)
            if (t != tool)
                t.updateAnimation();
    }

    public void render(SpriteBatch sb) {
        tool.render(sb);
        for (AbstractTool t : dequipped)
            if (t != tool)
                t.render(sb);
        tool.renderText(sb);
    }
}
