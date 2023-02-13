package clockworkchar.cards;

import basemod.helpers.VfxBuilder;
import clockworkchar.ClockworkChar;
import clockworkchar.util.TexLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class DangerousDeterrent extends AbstractEasyCard {
    public final static String ID = makeID("DangerousDeterrent");

    public DangerousDeterrent() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        baseSecondMagic = secondMagic = 4;
        exhaust = true;
        cardsToPreview = new Spinner();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        vfx(new BreakEffect());
        atb(new LoseHPAction(p, p, secondMagic));
        makeInHand(new Spinner(), magicNumber);
    }

    public void upp() {
        upgradeSecondMagic(-2);
    }

    private static class BreakEffect extends AbstractGameEffect {
        private static Texture IMAGE = TexLoader.getTexture(ClockworkChar.modID + "Resources/images/vfx/part.png");

        private int count = 0;
        private float timer = 0.0F;
        
        public void update() {
            timer -= Gdx.graphics.getDeltaTime();
            if (timer < 0.0F) {
                timer += 0.3F;
                switch(count) {
                    case 0:
                        CardCrawlGame.sound.play(makeID("BREAK"));
                        AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(new Color(1.0F, 0.1F, 0.1F, 1.0F)));
                        dropPart(0.0F, 30.0F);
                        break;
                    case 1:
                        dropPart(16.0F, -10.0F);
                        break;
                    case 2:
                        dropPart(-30.0F, 10.0F);
                        break;
                    case 3:
                        dropPart(30.0F, 10.0F);
                        break;
                    case 4:
                        dropPart(-16.0F, -20.0F);
                        break;
                }
                if (++count == 6)
                    isDone = true;
            }
        }

        private void dropPart(float xOffset, float yOffset) {
            float x = AbstractDungeon.player.hb.cX + xOffset * scale;
            float y = AbstractDungeon.player.hb.cY + yOffset * scale;
            AbstractDungeon.effectsQueue.add(new VfxBuilder(IMAGE, x, y, 2.0F)
                .moveY(y, y - 150.0F * scale, VfxBuilder.Interpolations.BOUNCEOUT)
                .fadeIn(0.5F)
                .fadeOut(1.5F)
                .setAngle(MathUtils.random(0, 360))
                .rotate(MathUtils.random(-30, 30))
                .build());
        }
  
        public void render(SpriteBatch sb) {}
        public void dispose() {}
    }
}