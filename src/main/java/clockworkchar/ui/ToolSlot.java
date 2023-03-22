package clockworkchar.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import clockworkchar.characters.TheClockwork;
import clockworkchar.tools.AbstractTool;
import clockworkchar.tools.Spanner;

public class ToolSlot {
    public boolean shouldRender = false;

    public AbstractTool tool = reset();

    public AbstractTool reset() {
        shouldRender = AbstractDungeon.player instanceof TheClockwork;
        tool = new Spanner();
        return tool;
    }

    public void update() {
        tool.update();
    }

    public void updateAnimation() {
        tool.updateAnimation();
    }

    public void render(SpriteBatch sb) {
        tool.render(sb);
        tool.renderText(sb);
    }
}
