package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
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
        baseMagicNumber = magicNumber = 2;
        baseSpinAmount = spinAmount = 8;
        cardsToPreview = new Spinner();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        makeInHand(new Spinner());
        atb(new SpinAction(spinAmount, spun -> {
            if (spun)
                for (int i = 0; i < magicNumber; i++)
                    att(new PlayTopCardAction((AbstractCreature)(AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false)); 
        }));
    }

    public void upp() {
        upgradeSpinAmount(4);
    }
}