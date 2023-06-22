package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.atb;

public class Blurrier extends AbstractCrankyCard {
    public final static String ID = makeID("Blurrier");

    public Blurrier() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
        baseSpinAmount = spinAmount = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) blckTop();
        }));
    }

    public void upp() {
        upgradeBlock(1);
        upgradeSpinAmount(1);
    }
}