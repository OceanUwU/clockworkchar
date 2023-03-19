package clockworkchar.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import clockworkchar.ClockworkChar;

import static clockworkchar.util.Wiz.*;

public class Torch extends AbstractTool {
    private static Texture TORCH_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/torch.png"));
    private static TextureRegion FLASH_TEXTURE = new TextureRegion(ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/flash.png")));
    private static float FLASH_SIZE = 400.0f;
    private static float FLASH_CENTRE = FLASH_SIZE / 2.0f;

    private static int STRENGTH_LOSS = 1;

    private boolean lightOn = false;

    public Torch() {
        super(TORCH_TEXTURE);
    }

    public void use() {
        forAllMonstersLiving(mo -> {
            applyToEnemy(mo, new StrengthPower(mo, -STRENGTH_LOSS));
            if (!mo.hasPower("Artifact"))
                applyToEnemy(mo, new GainStrengthPower(mo, STRENGTH_LOSS));
        });
    }

    public void update() {
        
    }

    @Override
    public void render(SpriteBatch sb) {
        if (lightOn)
            sb.draw(FLASH_TEXTURE, cX - FLASH_CENTRE, cY - FLASH_CENTRE, FLASH_CENTRE, FLASH_CENTRE, FLASH_SIZE, FLASH_SIZE, 1.0f, 1.0f, angle);
        super.render(sb);
    }
}