package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class FlyingCogs extends AbstractCrankyCard {
    public final static String ID = makeID("FlyingCogs");

    public FlyingCogs() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c instanceof AbstractCrankyCard && ((AbstractCrankyCard)c).part) {
                        att(new DrawCardAction(1));
                        dmgTop(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
                    }
                }
                for (int i = AbstractDungeon.player.hand.group.size() - 1; i >= 0; i--) {
                    AbstractCard c = AbstractDungeon.player.hand.group.get(i);
                    if (c instanceof AbstractCrankyCard && ((AbstractCrankyCard)c).part) {
                        AbstractGameAction partAction = ((AbstractCrankyCard)c).partActivation();
                        if (partAction != null) {
                            att(new DiscardSpecificCardAction(c));
                            att(partAction);
                        }
                    }
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(2);
    }
}