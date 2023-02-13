package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class RotationalChaos extends AbstractEasyCard {
    public final static String ID = makeID("RotationalChaos");

    public RotationalChaos() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        baseSpinAmount = spinAmount = 5;
    }

    private void playTopCards(int cardsLeft) {
        if (cardsLeft == 0) return;
        att(new SpinAction(spinAmount, spun -> {
            if (spun) {
                playTopCards(cardsLeft-1);
                att(new PlayTopCardAction((AbstractCreature)(AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false)); 
            }
        }));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                playTopCards(magicNumber);
            }
        });
    }

    public void upp() {
        upgradeSpinAmount(1);
    }
}