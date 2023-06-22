package clockworkchar.tools;

import clockworkchar.CrankyMod;
import clockworkchar.powers.Blinded;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Torch extends AbstractTool {
    private static String TOOL_ID = makeID("Torch");
    private static OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(TOOL_ID);
    private static Texture TORCH_TEXTURE = ImageMaster.loadImage(CrankyMod.makeImagePath("tools/torch.png"));
    private static TextureRegion FLASH_TEXTURE = new TextureRegion(ImageMaster.loadImage(CrankyMod.makeImagePath("tools/flash.png")));
    private static float FLASH_SIZE = 400.0f;
    private static float FLASH_CENTRE = FLASH_SIZE / 2.0f;
    private static float ANGLE_OFFSET = -10.0f;
    private static float OSCILLATE_SPEED = 1.4f;
    private static float OSCILLATE_AMOUNT = 20.0f;

    private boolean lightOn = false;

    public Torch() {
        super(TOOL_ID, orbStrings.NAME, TORCH_TEXTURE, 2, 0, 1);
    }

    public void use() {
        target = getRandomTarget();
        if (target != null) {
            applyToEnemyTopFast(target, new Blinded(target, passiveAmount));
            att(new DamageAction(target, new DamageInfo(adp(), getDamage(target), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true));
            final AbstractCreature hopper = target;
            att(new AbstractGameAction() {
                public void update() {
                    hopper.useHopAnimation();
                    isDone = true;
                }
            });
        }
        blckTop();
        vfxTop(new TorchFlash(this));
    }

    public void updateDescription() {
        description = orbStrings.DESCRIPTION[0] + block + orbStrings.DESCRIPTION[1] + damage + orbStrings.DESCRIPTION[2] + passiveAmount + orbStrings.DESCRIPTION[3];
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