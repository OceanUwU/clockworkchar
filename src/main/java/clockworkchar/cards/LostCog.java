package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class LostCog extends AbstractEasyCard {
    public final static String ID = makeID("LostCog");

    public LostCog() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2) {
                    AbstractCard lastCard = ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2));
                    if (lastCard instanceof AbstractEasyCard && ((AbstractEasyCard)lastCard).spinAmount > 0) {
                        dmgTop(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
                    }
                }
            }
        });
    }
  
    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()) {
            AbstractCard lastCard = ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1));
            if (lastCard instanceof AbstractEasyCard && ((AbstractEasyCard)lastCard).spinAmount > 0) {
                glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
                return;
            }
        }
        glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
    }

    public void upp() {
        upgradeDamage(2);
    }
}