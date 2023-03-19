package clockworkchar.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class AbstractTool {
    private static float SIZE = 98.0f;
    private static float CENTRE = SIZE / 2.0f;
    private TextureRegion texture;
    public float cX = 0.0f;
    public float cY = 0.0f;
    public float angle;

    public AbstractTool(Texture texture) {
        this.texture = new TextureRegion(texture);
    }

    public abstract void use();

    public abstract void update();

    public void render(SpriteBatch sb) {
        sb.draw(texture, cX - CENTRE, cY - CENTRE, CENTRE, CENTRE, SIZE, SIZE, 1.0f, 1.0f, angle);
    };
}
