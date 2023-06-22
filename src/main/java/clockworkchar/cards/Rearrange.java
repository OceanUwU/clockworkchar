package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import clockworkchar.actions.AttuneAction;
import clockworkchar.vfx.AttuneCardEffect;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Rearrange extends AbstractCrankyCard {
    public final static String ID = makeID("Rearrange");

    public Rearrange() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new AbstractGameAction() {
            public void update() {
                for (AbstractCard c : p.hand.group)
                    att(new AttuneAction(c));
                boolean first = true;
                for (AbstractCard c : p.hand.group) {
                    att(new VFXAction(p, new AttuneCardEffect(c, first), 0.0f, true));
                    first = false;
                }
                isDone = true;
            }
        });
    }

    public void upp() {
        upgradeBlock(4);
    }
}