package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

import clockworkchar.patches.AttunedPatches;

public class PreciseTuning extends AbstractEasyCard {
    public final static String ID = makeID("PreciseTuning");

    public PreciseTuning() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        atb(new DrawCardAction(magicNumber, new AbstractGameAction() {
            public void update() {
                tickDuration();
                if (isDone)
                    for (AbstractCard c : DrawCardAction.drawnCards)
                        if (AttunedPatches.CardFields.attuned.get(c) <= 0) {
                            p.hand.moveToDiscardPile(c);
                            c.triggerOnManualDiscard();
                            GameActionManager.incrementDiscard(false);
                        }

            }
        }));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}