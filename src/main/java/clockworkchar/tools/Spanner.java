package clockworkchar.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import clockworkchar.ClockworkChar;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Spanner extends AbstractTool {
    private static String TOOL_ID = makeID("Spanner");
    private static OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(TOOL_ID);
    private static Texture SPANNER_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/spanner.png"));
    private static float SPIN_SPEED = 40.0F;

    private static int DAMAGE = 3;

    public Spanner() {
        super(TOOL_ID, orbStrings.NAME, SPANNER_TEXTURE);
    }

    public void use() {
        att(new ChuckSpannerAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), this, passiveAmount, false));
    }

    public void applyPowers() {
        passiveAmount = DAMAGE;
        super.applyPowers();
    }

    public void updateDescription() {
        description = orbStrings.DESCRIPTION[0] + passiveAmount + orbStrings.DESCRIPTION[1];
    }

    public void updateAnimation() {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * SPIN_SPEED;
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
            isDone = true; //temp
            att(new DamageAction(target, new DamageInfo(source, damage, DamageInfo.DamageType.THORNS)));
        }
    }
}
