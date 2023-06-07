package clockworkchar.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;

public class MetalSheet extends AbstractEasyCard {
    public final static String ID = makeID("MetalSheet");

    public MetalSheet() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 8;
        baseSecondBlock = secondBlock = 4;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void activate() {
        altBlckTop();
    }

    public void upp() {
        upgradeBlock(3);
        upgradeSecondBlock(1);
    }
}