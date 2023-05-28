package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class WormGear extends AbstractEasyCard {
    public final static String ID = makeID("WormGear");

    public WormGear() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber, new AbstractGameAction() {
            public void update() {
                isDone = true;
                ArrayList<AbstractCard> parts = new ArrayList<>(DrawCardAction.drawnCards.stream().filter(c -> c instanceof AbstractEasyCard && ((AbstractEasyCard)c).part).collect(Collectors.toList()));
                if (parts.size() > 0) {
                    parts.stream().forEach(c -> atb(((AbstractEasyCard)c).partActivation()));
                    use(p, m);
                }
            }
        }));
    }

    public void upp() {
        uDesc();
        upgradeSecondMagic(1);
    }
}