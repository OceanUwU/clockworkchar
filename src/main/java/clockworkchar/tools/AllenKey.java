package clockworkchar.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import clockworkchar.ClockworkChar;

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

    @Override
    public void use() {
        applyToSelfTop(new VigorPower(adp(), passiveAmount));
        super.use();
        att(new AbstractGameAction() {
            public void update() {
                isDone = true;
                spin_speed = USE_SPIN_SPEED;
            }
        });
    }

    @Override
    protected int getPassiveAmount() {
        return WINDUP_AMOUNT;
    }

    public void updateDescription() {
        description = orbStrings.DESCRIPTION[0] + damage + orbStrings.DESCRIPTION[1] + block + orbStrings.DESCRIPTION[2] + passiveAmount + orbStrings.DESCRIPTION[3];
    }
}
