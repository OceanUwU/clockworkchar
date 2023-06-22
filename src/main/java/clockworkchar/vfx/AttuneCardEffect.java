package clockworkchar.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.CrankyMod.makeImagePath;

public class AttuneCardEffect extends AbstractGameEffect {
    private static final String AUDIO_KEY = makeID("ATTUNE");
    private static final float DURATION = 1.6f;

    private AbstractCard card;
    private float x;
    private float y;
    private boolean playSound = true;
    private ArrayList<TiltingSpanner> spanners = new ArrayList<>();

    public AttuneCardEffect(float x, float y, boolean playSound) {
        this.x = x;
        this.y = y;
        this.playSound = playSound;
        duration = DURATION;
        initSpanners();
    }

    public AttuneCardEffect(AbstractCard c) {
        this.card = c;
        duration = DURATION;
        initSpanners();
    }

    private void initSpanners() {
        spanners.add(new TiltingSpanner(-70f, -40f, 150f, 1f, 0.0f));
        spanners.add(new TiltingSpanner(90f, 20f, 0f, -1f, 0.42f));
        spanners.add(new TiltingSpanner(-50f, 120f, 280f, 1f, 0.84f));
    }

    public AttuneCardEffect(AbstractCard c, boolean playSound) {
        this(c);
        this.playSound = playSound;
    }

    public void update() {
        if (duration == DURATION && playSound)
            CardCrawlGame.sound.play(AUDIO_KEY);
        for (TiltingSpanner s : spanners)
            s.update();
        if (spanners.size() > 0 && spanners.get(0).isDone)
            spanners.remove(0);
        duration -= Gdx.graphics.getDeltaTime();
        if (duration <= 0.0f)
            isDone = true;
    }

    public void render(SpriteBatch sb) {
        for (TiltingSpanner s : spanners)
            if (card == null)
                s.render(sb, x, y, 1f, 0);
            else
                s.render(sb, card);
    }

    public void dispose() {}



    public static class TiltingSpanner {
        private static final Texture TEXTURE = new Texture(makeImagePath("vfx/spanner.png"));
        private static final float SIZE = 256f;
        private static final TextureRegion TEXTURE_REGION = new TextureRegion(TEXTURE);
        private static final float DURATION = 0.42f;
        private static final float TILT_AMOUNT = 90f;

        public boolean isDone = false;
        private float x;
        private float y;
        private float startingAngle;
        private float angle;
        private float direction;
        private float delay;
        private float timer = 0.0f;
        private Color color = Color.WHITE.cpy();

        public TiltingSpanner(float x, float y, float angle, float direction, float delay) {
            this.x = x;
            this.y = y;
            this.startingAngle = angle;
            this.angle = startingAngle;
            this.direction = direction;
            this.delay = delay;
            color.a = 0;
        }

        public void update() {
            if (delay > 0f)
                delay -= Gdx.graphics.getDeltaTime();
            else {
                if (timer <= DURATION) {
                    float t = timer / DURATION;
                    float progress = (t < 0.5f) ? 2f * t * t : 1 - (float)Math.pow(-2 * t + 2, 2) / 2f;
                    color.a = Math.min(((t < 0.5f ? t : 1f - t) * 2f) * 2f, 1f);
                    angle = startingAngle + TILT_AMOUNT * progress * direction;
                    timer += Gdx.graphics.getDeltaTime();
                } else
                    isDone = true;
            }
        }

        public void render(SpriteBatch sb, AbstractCard c) {
            render(sb, c.current_x, c.current_y, c.drawScale, c.angle);
        }

        public void render(SpriteBatch sb, float tX, float tY, float scale, float offsetAngle) {
            float drawX = tX + x * Settings.scale;
            float drawY = tY + y * Settings.scale;
            sb.setColor(color);
            sb.draw(TEXTURE_REGION, drawX - SIZE / 2f, drawY - SIZE / 2F, SIZE / 2f, SIZE / 2f, SIZE, SIZE, scale * Settings.scale, scale * Settings.scale, offsetAngle+angle);
        }
    }
}