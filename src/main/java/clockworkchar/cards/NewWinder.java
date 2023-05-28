package clockworkchar.cards;

import basemod.ReflectionHacks;
import clockworkchar.actions.AttuneAction;
import clockworkchar.actions.EquipToolAction;
import clockworkchar.tools.AllenKey;
import clockworkchar.vfx.AttuneCardEffect;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.DiscardPilePanel;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class NewWinder extends AbstractEasyCard {
    public final static String ID = makeID("NewWinder");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("SelectToAttune")).TEXT;

    public NewWinder() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        showDequipValue = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EquipToolAction(new AllenKey()));
        if (upgraded)
            atb(new SelectCardsAction(p.discardPile.group, TEXT[0], c -> {
                att(new AttuneAction(c.get(0)));
                Hitbox hb = ReflectionHacks.getPrivate(AbstractDungeon.overlayMenu.discardPilePanel, DiscardPilePanel.class, "hb");
                att(new VFXAction(p, new AttuneCardEffect(hb.cX, hb.cY, true), 0.0f, true));
            }));
    }

    public void upp() {
        uDesc();
    }
}