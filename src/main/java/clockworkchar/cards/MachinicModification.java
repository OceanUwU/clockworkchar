package clockworkchar.cards;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import clockworkchar.ClockworkChar;
import clockworkchar.actions.WindUpAction;
import clockworkchar.patches.AttunedPatches;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

import java.util.ArrayList;

public class MachinicModification extends AbstractEasyCard {
    public final static String ID = makeID("MachinicModification");

    public MachinicModification() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        FleetingField.fleeting.set(this, true);
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new MachinicallyModifyAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    public static class MachinicallyModifyAction extends AbstractGameAction {
        private static String[] TEXT = CardCrawlGame.languagePack.getUIString(ClockworkChar.modID + ":MachinicModificationAction").TEXT;
        private int wind;
        private ArrayList<AbstractCard> cannotModify = new ArrayList<>();

        public MachinicallyModifyAction(int wind) {
            this.wind = wind;
            duration = Settings.ACTION_DUR_FAST;
        }

        public void update() {
            AbstractPlayer p = AbstractDungeon.player;
            if (this.duration == Settings.ACTION_DUR_FAST) {
                for (AbstractCard c : p.hand.group) {
                    boolean modifiable = false;
                    modifiable = AttunedPatches.CardFields.deckCard.get(c) != null;
                    if (!modifiable)
                        cannotModify.add(c);
                }
                for (AbstractCard c : cannotModify)
                    p.hand.group.remove(c);
                if (p.hand.group.size() > 1) 
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, true, true);
                else
                    isDone = true;
            } else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    MachinicCardModification modification = new MachinicCardModification(wind);
                    CardModifierManager.addModifier(AttunedPatches.CardFields.deckCard.get(c), modification);
                    CardModifierManager.addModifier(c, modification.makeCopy());
                    float x = p.drawX;
                    float y = p.drawY + p.hb_h;
                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                    p.hand.addToTop(c);
                }
                for (AbstractCard c : cannotModify)
                    AbstractDungeon.player.hand.addToTop(c);
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                isDone = true;
            }
            tickDuration();
        }

        public static class MachinicCardModification extends AbstractCardModifier {
            private int wind;

            public MachinicCardModification(int wind) {
                this.wind = wind;
            }

            public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
                att(new WindUpAction(wind));
            }

            public String modifyDescription(String rawDescription, AbstractCard card) {
                return TEXT[1] + wind + TEXT[2] + " NL " + rawDescription;
            }

            public MachinicCardModification makeCopy() {
                return new MachinicCardModification(wind);
            };
        }
    }
}