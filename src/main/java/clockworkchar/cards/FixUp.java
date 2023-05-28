package clockworkchar.cards;

import clockworkchar.actions.AttuneAction;
import clockworkchar.actions.EquipToolAction;
import clockworkchar.tools.Screwdriver;
import clockworkchar.vfx.AttuneCardEffect;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class FixUp extends AbstractEasyCard {
    public final static String ID = makeID("FixUp");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("SelectToAttune")).TEXT;

    public FixUp() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        showDequipValue = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EquipToolAction(new Screwdriver()));
        if (upgraded)
            atb(new SelectCardsInHandAction(TEXT[0], c -> {
                att(new VFXAction(p, new AttuneCardEffect(c.get(0)), 0.0f, true));
                att(new AttuneAction(c.get(0)));
            }));
    }

    public void upp() {
        uDesc();
    }
}