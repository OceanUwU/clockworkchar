package clockworkchar.cards;

import clockworkchar.actions.AttuneAction;
import clockworkchar.actions.UseToolAction;
import clockworkchar.vfx.AttuneCardEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Mend extends AbstractEasyCard {
    public final static String ID = makeID("Mend");

    public Mend() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new UseToolAction(magicNumber));
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                CardGroup attunable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : p.hand.group)
                    attunable.addToTop(c);
                if (attunable.size() > 0) {
                    attunable.shuffle();
                    boolean first = true;
                    int toAttune = secondMagic;
                    for (AbstractCard c : attunable.group) {
                        att(new VFXAction(p, new AttuneCardEffect(c, first), 0.0f, true));
                        att(new AttuneAction(c));
                        if (--toAttune <= 0) break;
                        first = false;
                    }
                }
            }
        });
    }

    public void upp() {
        uDesc();
        upgradeSecondMagic(1);
    }
}