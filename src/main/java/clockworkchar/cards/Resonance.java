package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Resonance extends AbstractEasyCard implements StartupCard {
    public final static String ID = makeID("Resonance");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private int plays = 0;

    public Resonance() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 30;
        baseSpinAmount = spinAmount = 1;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SpinAction(spinAmount, spun -> {}));
        if (++plays >= 3) {
            plays = 0;
            atb(new AbstractGameAction() {
                public void update() {
                    isDone = true;
                    CardCrawlGame.sound.play(makeID("RESONANCE"));
                }
            });
            blck();
        }
        showPlays();
    }

    public void upp() {
        upgradeBlock(10);
    }

    private void showPlays() {
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + plays + cardStrings.EXTENDED_DESCRIPTION[plays == 1 ? 1 : 2];
        initializeDescription();
    }

    @Override
    public boolean atBattleStartPreDraw() {
        showPlays();
        return false;
    }
}