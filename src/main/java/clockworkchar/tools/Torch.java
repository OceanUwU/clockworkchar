package clockworkchar.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import clockworkchar.ClockworkChar;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Torch extends AbstractTool {
    private static String TOOL_ID = makeID("Torch");
    private static OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(TOOL_ID);
    private static Texture TORCH_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/torch.png"));
    private static TextureRegion FLASH_TEXTURE = new TextureRegion(ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/flash.png")));
    private static float FLASH_SIZE = 400.0f;
    private static float FLASH_CENTRE = FLASH_SIZE / 2.0f;
    private static float ANGLE_OFFSET = 20.0f;
    private static float OSCILLATE_TIME = 5.0f;
    private static float OSCILLATE_AMOUNT = 30.0f;

    private static int STRENGTH_LOSS = 1;

    private boolean lightOn = false;

    public Torch() {
        super(TOOL_ID, orbStrings.NAME, TORCH_TEXTURE);
    }

    public void use() {
        forAllMonstersLiving(mo -> {
            if (!mo.hasPower("Artifact"))
                applyToEnemyTop(mo, new GainStrengthPower(mo, passiveAmount));
            applyToEnemyTop(mo, new StrengthPower(mo, -passiveAmount));
        });
    }

    public void updateDescription() {
        description = orbStrings.DESCRIPTION[0] + passiveAmount + orbStrings.DESCRIPTION[1];
    }

    public void applyPowers() {
        passiveAmount = STRENGTH_LOSS;
        super.applyPowers();
    }

    public void updateAnimation() {
        super.updateAnimation();
        angle = ANGLE_OFFSET + (float)Math.sin(vfxTimer / OSCILLATE_TIME) * OSCILLATE_AMOUNT;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (lightOn)
            sb.draw(FLASH_TEXTURE, cX - FLASH_CENTRE, cY - FLASH_CENTRE, FLASH_CENTRE, FLASH_CENTRE, FLASH_SIZE, FLASH_SIZE, 1.0f, 1.0f, angle);
        super.render(sb);
    }
}