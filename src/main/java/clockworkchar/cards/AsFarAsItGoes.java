package clockworkchar.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class AsFarAsItGoes extends AbstractEasyCard {
    public final static String ID = makeID("AsFarAsItGoes");

    public AsFarAsItGoes() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new SelectCardsAction(p.discardPile.group, p.discardPile.group.size(), cardStrings.EXTENDED_DESCRIPTION[0], true, c -> true, cards -> {
            cards.stream().forEach(c -> {
                p.discardPile.moveToDeck(c, true);
                p.discardPile.removeCard(c);
            });
        }));
    }

    public void upp() {
        upgradeBlock(3);
    }
}