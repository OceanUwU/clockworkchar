package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Futureproofing extends AbstractEasyCard {
    public final static String ID = makeID("Futureproofing");

    public Futureproofing() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 2;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
    }

    public void activate() {
        att(new DrawCardAction(secondMagic, new AbstractGameAction() {
            public void update() {
                isDone = true;
                DrawCardAction.drawnCards.stream().forEach(c -> c.retain = true);
            }
        }));
    }

    public void upp() {
        upgradeSecondMagic(1);
    }
}