package clockworkchar.tools;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import clockworkchar.ClockworkChar;

import static clockworkchar.util.Wiz.*;

public class Screwdriver extends AbstractTool {
    private static Texture SCREWDRIVER_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/screwdriver.png"));

    private static int BLOCK_GAIN = 2;

    public Screwdriver() {
        super(SCREWDRIVER_TEXTURE);
    }

    public void update() {
        
    }

    public void use() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK_GAIN));
    }
}
