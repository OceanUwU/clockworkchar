package clockworkchar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class RearrangeAttack extends AbstractEasyCard {
    public final static String ID = makeID("RearrangeAttack");

    public RearrangeAttack() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        atb(new SwapDrawAndDiscardAction());
    }

    public void upp() {
        upgradeDamage(3);
    }

    private static class SwapDrawAndDiscardAction extends AbstractGameAction {        
        public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("SwapDrawAndDiscardAction")).TEXT;

        private AbstractCard fromDiscard;
        private AbstractCard fromDraw;
        private boolean gettingDraw = false;

        public SwapDrawAndDiscardAction() {
            duration = Settings.ACTION_DUR_FASTER;
        }

        public void update() {
            if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
                isDone = true;
                return;
            }
            if (duration == Settings.ACTION_DUR_FASTER) {
                if (AbstractDungeon.player.discardPile.isEmpty() || AbstractDungeon.player.drawPile.isEmpty()) {
                    isDone = true;
                    return;
                }
                if (AbstractDungeon.player.discardPile.size() == 1) {
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    AbstractDungeon.gridSelectScreen.selectedCards.add(AbstractDungeon.player.discardPile.getTopCard());
                } else {
                    AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.discardPile, 1, TEXT[0], false, false, false, false);
                    tickDuration();
                    return;
                }
            }
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                if (gettingDraw) {
                    fromDraw = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();

                    int drawPosition = AbstractDungeon.player.drawPile.group.indexOf(fromDraw);
                    int discardPosition = AbstractDungeon.player.discardPile.group.indexOf(fromDiscard);
                    AbstractDungeon.player.drawPile.group.remove(fromDraw);
                    AbstractDungeon.player.discardPile.group.remove(fromDiscard);
                    AbstractDungeon.player.drawPile.group.add(drawPosition, fromDiscard);
                    AbstractDungeon.player.discardPile.group.add(discardPosition, fromDraw);

                    ReflectionHacks.privateMethod(CardGroup.class, "resetCardBeforeMoving", AbstractCard.class).invoke(AbstractDungeon.player.drawPile, fromDraw);
                    fromDraw.shrink();
                    //fromDraw.darken(false);
                    (AbstractDungeon.getCurrRoom()).souls.discard(fromDraw, true);
                    ReflectionHacks.privateMethod(CardGroup.class, "resetCardBeforeMoving", AbstractCard.class).invoke(AbstractDungeon.player.discardPile, fromDiscard);
                    fromDiscard.shrink();
                    (AbstractDungeon.getCurrRoom()).souls.onToDeck(fromDiscard, false, true);
                    AbstractDungeon.player.hand.refreshHandLayout();
                } else {
                    fromDiscard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
    
                    gettingDraw = true;
                    if (AbstractDungeon.player.drawPile.size() == 1) {
                        AbstractDungeon.gridSelectScreen.selectedCards.add(AbstractDungeon.player.drawPile.getTopCard());
                    } else {
                        CardGroup unsortedDraw = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
                            unsortedDraw.addToRandomSpot(c);
                        AbstractDungeon.gridSelectScreen.open(unsortedDraw, 1, TEXT[1], false, false, false, false);
                        tickDuration();
                        return;
                    }
                }
            }
            tickDuration();
        }
    }
}