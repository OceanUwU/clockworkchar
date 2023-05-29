package clockworkchar.vfx;

import clockworkchar.util.TexLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

import static clockworkchar.ClockworkChar.makeImagePath;

//DO NOT TOUCH THIS UNLESS YOU WANT TO GO INSANE!!!!!!!!
public class TiltingSpannerVictoryEffect extends AbstractGameEffect {
    private static float SCALE = 0.5f;
    private ArrayList<ArrayList<Nut>> nuts = new ArrayList<>();
    private ArrayList<Spanner> spanners = new ArrayList<>();
    private float timer = 0f;

    public TiltingSpannerVictoryEffect() {
        for (float i = -3.5f; i <= 3.5; i++) {
            ArrayList<Nut> row = new ArrayList<>();
            float mid = Settings.WIDTH / 2f - i * 100f * Settings.scale;
            for (float x = 0; x < Settings.WIDTH / 2f + 500f * Settings.scale; x += 200f * Settings.scale) {
                float y = Settings.HEIGHT / 2f + i * 174f * Settings.scale;
                row.add(0, new Nut(mid - x, y));
                if (x != 0)
                    row.add(new Nut(mid + x, y));
            }
            nuts.add(row);
        }
        for (float i = 0f; i <= 6; i++)
            spanners.add(new Spanner(Settings.HEIGHT / 2f + 174f * (i-3.5f), new ArrayList<>(nuts.subList((int)i, ((int)i)+2)), (int)i));
    }

    public void update() {
        timer += Gdx.graphics.getDeltaTime();
        for (Spanner spanner : spanners)
            spanner.update();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1, 1, 1, Math.min(timer, 1f)));
        for (ArrayList<Nut> row : nuts)
            for (Nut nut : row)
                nut.render(sb);
        for (Spanner spanner : spanners)
            spanner.render(sb);
    }

    public void dispose() {}

    public static class Spanner {
        private static TextureRegion SPANNER_TEXTURE = new TextureRegion(TexLoader.getTexture(makeImagePath("vfx/longspanner.png")));
        private static float W = SPANNER_TEXTURE.getTexture().getWidth();
        private static float H = SPANNER_TEXTURE.getTexture().getHeight();
        private static float TURN_DURATION = 0.9f;
        private static float ANGLE_OFFSET = 30.0f;
        private static float ONE_ROTATION = -60.0f;

        private float timer = 0f;
        private float x, y, progress, angle;
        private ArrayList<ArrayList<Nut>> nuts;
        private Nut attached;
        private boolean top;
        private int nutIndex;

        public Spanner(float y, ArrayList<ArrayList<Nut>> nuts, int offset) {
            this.y = y;
            this.nuts = nuts;
            nutIndex = offset / 2;
            nutIndex *= 8;
            top = offset % 2 == 1;
            if (top) nutIndex += 3;
            getNut();
        }

        public void getNut() {
            ArrayList<Nut> row = nuts.get(top ? 0 : 1);
            nutIndex %= row.size();
            attached = row.get(nutIndex);
        }

        public void update() {
            timer += Gdx.graphics.getDeltaTime();
            if (timer / TURN_DURATION > 1) {
                timer -= TURN_DURATION;
                if (top) nutIndex++;
                top = !top;
                attached.progress = 0;
                getNut();
            }
            progress = -((float)Math.cos(Math.PI * (double)(timer / TURN_DURATION)) - 1) / 2;
            attached.progress = top ? 1 - progress : progress;
            attached.update();
            x = attached.x + 100f * (progress - 0.5f) * Settings.scale;
            angle = ANGLE_OFFSET + ONE_ROTATION * (top ? progress : 1 - progress);
        }

        public void render(SpriteBatch sb) {
            float originX = (float)W / 2f;
            float originY = (float)(top ? Nut.H / 2f : H - Nut.H / 2f);
            sb.draw(SPANNER_TEXTURE, attached.x - originX, attached.y - originY, originX, originY, W, H, SCALE * Settings.scale, SCALE * Settings.scale, angle);
        }
    }

    public static class Nut {
        private static TextureRegion NUT_TEXTURE = new TextureRegion(TexLoader.getTexture(makeImagePath("vfx/nut.png")));
        private static int W = NUT_TEXTURE.getTexture().getWidth();
        private static int H = NUT_TEXTURE.getTexture().getHeight();
        private static float ANGLE_OFFSET = 30.0f;
        private static float ONE_ROTATION = 60.0f;

        public float x, y, angle;
        public float progress = 0f;

        public Nut(float x, float y) {
            this.x = x;
            this.y = y;
            update();
        }

        public void update() {
            angle = ANGLE_OFFSET + ONE_ROTATION * (progress);
        }

        public void render(SpriteBatch sb) {
            sb.draw(NUT_TEXTURE, x - W / 2f, y - H /2f, (float)W / 2f, (float)H / 2f, W, H, SCALE * Settings.scale, SCALE * Settings.scale, angle);
        }
    }
}