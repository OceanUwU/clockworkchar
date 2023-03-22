package clockworkchar.tools;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;

import clockworkchar.ClockworkChar;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Screwdriver extends AbstractTool {
    private static String TOOL_ID = makeID("Screwdriver");
    private static OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(TOOL_ID);
    private static Texture SCREWDRIVER_TEXTURE = ImageMaster.loadImage(ClockworkChar.makeImagePath("tools/screwdriver.png"));

    private static int BLOCK_GAIN = 2;

    public Screwdriver() {
        super(TOOL_ID, orbStrings.NAME, SCREWDRIVER_TEXTURE);
    }

    public void updateAnimation() {
        super.updateAnimation();
    }

    public void use() {
        att(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, passiveAmount));
    }

    public void applyPowers() {
        passiveAmount = BLOCK_GAIN;
        super.applyPowers();
    }

    public void updateDescription() {
        description = orbStrings.DESCRIPTION[0] + passiveAmount + orbStrings.DESCRIPTION[1];
    }
}
