package clockworkchar.tools;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;

import clockworkchar.ClockworkChar;
import clockworkchar.actions.WindUpAction;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class AllenKey extends AbstractTool {
    private static String TOOL_ID = makeID("AllenKey");
    private static OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(TOOL_ID);
    private static Texture KEY_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/allenkey.png"));

    private static int WINDUP_AMOUNT = 2;

    public AllenKey() {
        super(TOOL_ID, orbStrings.NAME, KEY_TEXTURE);
    }

    public void updateAnimation() {
        super.updateAnimation();
    }

    public void use() {
        att(new WindUpAction(passiveAmount));
    }

    public void applyPowers() {
        passiveAmount = WINDUP_AMOUNT;
        super.applyPowers();
    }

    public void updateDescription() {
        description = orbStrings.DESCRIPTION[0] + passiveAmount + orbStrings.DESCRIPTION[1];
    }
}
