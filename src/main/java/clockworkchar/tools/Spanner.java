package clockworkchar.tools;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import clockworkchar.ClockworkChar;

import static clockworkchar.util.Wiz.*;

public class Spanner extends AbstractTool {
    private static Texture SPANNER_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/spanner.png"));

    private static int DAMAGE;

    public Spanner() {
        super(SPANNER_TEXTURE);
    }

    public void use() {
        atb(new ChuckSpannerAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), this, DAMAGE, false));
    }

    public void update() {
        
    }

    private static class ChuckSpannerAction extends AbstractGameAction {
        private static float DURATION = 1.0F;

        private Spanner owner;
        private int damage;
        private boolean dequipping;
        private AbstractMonster target;

        public ChuckSpannerAction(AbstractMonster target, Spanner owner, int damage, boolean dequipping) {
            this.target = target;
            this.owner = owner;
            this.damage = damage;
            this.dequipping = dequipping;
            duration = DURATION;
        }

        public void update() {

        }
    }
}
