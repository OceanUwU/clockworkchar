package clockworkchar.tools;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import clockworkchar.ClockworkChar;
import clockworkchar.actions.WindUpAction;

import static clockworkchar.util.Wiz.*;

public class AllenKey extends AbstractTool {
    private static Texture KEY_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/allenkey.png"));

    private static int WINDUP_AMOUNT = 2;

    public AllenKey() {
        super(KEY_TEXTURE);
    }

    public void update() {
        
    }

    public void use() {
        atb(new WindUpAction(WINDUP_AMOUNT));
    }
}
