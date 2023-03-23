package clockworkchar.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import clockworkchar.ClockworkChar;
import clockworkchar.actions.WindUpAction;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class AllenKey extends AbstractTool {
    private static String TOOL_ID = makeID("AllenKey");
    private static OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(TOOL_ID);
    private static Texture KEY_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/allenkey.png"));
    private static float SPIN_SPEED = 30.0F;
    private static float USE_SPIN_SPEED = 960.0F;

    private static int WINDUP_AMOUNT = 2;

    private float spin_speed = SPIN_SPEED;

    public AllenKey() {
        super(TOOL_ID, orbStrings.NAME, KEY_TEXTURE);
    }

    public void updateAnimation() {
        super.updateAnimation();
        if (!dequipped) {
            angle += Gdx.graphics.getDeltaTime() * spin_speed;
            spin_speed = MathHelper.slowColorLerpSnap(spin_speed, SPIN_SPEED);
        }
    }

    public void use() {
        att(new WindUpAction(passiveAmount));
        att(new AbstractGameAction() {
            public void update() {
                isDone = true;
                spin_speed = USE_SPIN_SPEED;
            }
        });
    }

    public void applyPowers() {
        passiveAmount = WINDUP_AMOUNT;
        super.applyPowers();
    }

    public void updateDescription() {
        description = orbStrings.DESCRIPTION[0] + passiveAmount + orbStrings.DESCRIPTION[1];
    }
}
