package clockworkchar.cards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.ClockworkChar.makeCardPath;

public class JackInABox extends AbstractEasyCard {
    public final static String ID = makeID("JackInABox");

    public JackInABox() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 11;
    }

    @Override
    protected Texture getPortraitImage() {
        if (upgraded) {
            if (UnlockTracker.betaCardPref.getBoolean(ID, false) || Settings.PLAYTESTER_ART_MODE)
                return ImageMaster.loadImage(makeCardPath("JackInABoxPlus_b_p.png"));
            return ImageMaster.loadImage(makeCardPath("JackInABoxPlus_p.png"));
        }
        return super.getPortraitImage();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        randomDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void upp() {
        upgradeDamage(4);
        loadCardImage(makeCardPath("JackInABoxPlus.png"));
    }
}