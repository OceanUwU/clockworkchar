package clockworkchar.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import clockworkchar.ClockworkChar;

import java.util.ArrayList;
import java.util.Collections;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Torch extends AbstractTool {
    private static String TOOL_ID = makeID("Torch");
    private static OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(TOOL_ID);
    private static Texture TORCH_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/torch.png"));
    private static TextureRegion FLASH_TEXTURE = new TextureRegion(ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/flash.png")));
    private static float FLASH_SIZE = 400.0f;
    private static float FLASH_CENTRE = FLASH_SIZE / 2.0f;
    private static float ANGLE_OFFSET = -10.0f;
    private static float OSCILLATE_SPEED = 1.4f;
    private static float OSCILLATE_AMOUNT = 20.0f;

    private static int STRENGTH_LOSS = 1;

    private boolean lightOn = false;

    public Torch() {
        super(TOOL_ID, orbStrings.NAME, TORCH_TEXTURE);
    }

    public void use() {
        ArrayList<AbstractMonster> monsters = getEnemies();
        Collections.reverse(monsters);
        target = getRandomTarget();
        for (AbstractMonster mo : monsters)
            if (!mo.hasPower("Artifact"))
                att(new ApplyPowerAction(mo, AbstractDungeon.player, new GainStrengthPower(mo, passiveAmount), passiveAmount, true, AbstractGameAction.AttackEffect.NONE));
        for (AbstractMonster mo : monsters) {
            att(new ApplyPowerAction(mo, AbstractDungeon.player, new StrengthPower(mo, -passiveAmount), -passiveAmount, true, AbstractGameAction.AttackEffect.NONE));
            if (mo == target)
                dmgTop();
            att(new AbstractGameAction() {
                public void update() {
                    mo.useHopAnimation();
                    isDone = true;
                }
            });
        };
        blckTop();
        vfxTop(new TorchFlash(this));
    }

    public void updateDescription() {
        description = orbStrings.DESCRIPTION[0] + block + orbStrings.DESCRIPTION[1] + damage + orbStrings.DESCRIPTION[2] + passiveAmount + orbStrings.DESCRIPTION[3];
    }

    @Override
    protected int getPassiveAmount() {
        return STRENGTH_LOSS;
    }

    public void updateAnimation() {
        super.updateAnimation();
        if (!dequipped)
            angle = ANGLE_OFFSET + (float)Math.sin(vfxTimer * OSCILLATE_SPEED) * OSCILLATE_AMOUNT;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        if (lightOn)
            sb.draw(FLASH_TEXTURE, cX - FLASH_CENTRE, cY - FLASH_CENTRE, FLASH_CENTRE, FLASH_CENTRE, FLASH_SIZE, FLASH_SIZE, 1.0f, 1.0f, angle);
        super.render(sb);
    }

    public static class TorchFlash extends AbstractGameEffect {
        private static float duration = 0.255F;
        private Torch owner;
        private float timer = 0.0F;

        public TorchFlash(Torch owner) {
            this.owner = owner;
        }
    
        public void update() {
            if (timer == 0.0F)
                CardCrawlGame.sound.play(makeID("TORCH"));
            owner.lightOn = true;
            if (timer >= duration) {
                owner.lightOn = false;
                this.isDone = true;
            }
            timer += Gdx.graphics.getDeltaTime();
        }
    
        public void render(SpriteBatch sb) {}
        public void dispose() {}
    }
}