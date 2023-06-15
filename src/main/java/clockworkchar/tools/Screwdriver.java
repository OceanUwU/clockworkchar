package clockworkchar.tools;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;

import clockworkchar.ClockworkChar;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Screwdriver extends AbstractTool {
    private static String TOOL_ID = makeID("Screwdriver");
    private static OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(TOOL_ID);
    private static Texture SCREWDRIVER_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/screwdriver.png"));
    private static float OSCILLATE_SPEED = 2.5f;
    private static float OSCILLATE_AMOUNT = 10.0f;

    private float offsetAngle = 0f;

    public Screwdriver() {
        super(TOOL_ID, orbStrings.NAME, SCREWDRIVER_TEXTURE, 4, 1, 0);
    }

    public void updateAnimation() {
        super.updateAnimation();
        angle = (float)Math.sin(vfxTimer * OSCILLATE_SPEED) * OSCILLATE_AMOUNT + offsetAngle;
        offsetAngle = MathHelper.angleLerpSnap(offsetAngle, 0f);
    }

    public void use() {
        super.use();
        att(new AbstractGameAction() {
            public void update() {
                offsetAngle += 360f;
                isDone = true;
            }
        });
    }

    public void updateDescription() {
        description = orbStrings.DESCRIPTION[0] + block + orbStrings.DESCRIPTION[1] + damage + orbStrings.DESCRIPTION[2];
    }
}
