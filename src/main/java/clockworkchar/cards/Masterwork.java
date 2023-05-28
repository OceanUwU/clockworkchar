package clockworkchar.cards;

import basemod.ReflectionHacks;
import clockworkchar.actions.AttuneAction;
import clockworkchar.actions.SpinAction;
import clockworkchar.vfx.AttuneCardEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.DiscardPilePanel;
import com.megacrit.cardcrawl.ui.panels.DrawPilePanel;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Masterwork extends AbstractEasyCard {
    public final static String ID = makeID("Masterwork");

    public Masterwork() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseSpinAmount = spinAmount = 30;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SpinAction(spinAmount, spun -> {
            if (spun) {
                Hitbox hb = ReflectionHacks.getPrivate(AbstractDungeon.overlayMenu.combatDeckPanel, DrawPilePanel.class, "hb");
                    att(new VFXAction(p, new AttuneCardEffect(hb.cX, hb.cY, true), 0.0f, true));
                hb = ReflectionHacks.getPrivate(AbstractDungeon.overlayMenu.discardPilePanel, DiscardPilePanel.class, "hb");
                    att(new VFXAction(p, new AttuneCardEffect(hb.cX, hb.cY, false), 0.0f, true));
                for (AbstractCard c : p.hand.group)
                    att(new VFXAction(p, new AttuneCardEffect(c, false), 0.0f, true));
                
                for (AbstractCard c : p.hand.group)
                    att(new AttuneAction(c));
                for (AbstractCard c : p.drawPile.group)
                    att(new AttuneAction(c));
                for (AbstractCard c : p.discardPile.group)
                    att(new AttuneAction(c));
            }
        }));
    }

    public void upp() {
        upgradeSpinAmount(5);
    }
}