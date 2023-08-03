package clockworkchar.actions;

import clockworkchar.patches.AttunedPatches;
import clockworkchar.vfx.AttuneCardEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AttuneAction extends AbstractGameAction {
    private AbstractCard card;
    private int times;
    private boolean vfx, sfx;

    public AttuneAction(AbstractCard c, int times, boolean vfx, boolean sfx) {
        this.card = c;
        this.times = times;
        this.vfx = vfx;
        this.sfx = sfx;
    }

    public AttuneAction(AbstractCard c, int times, boolean vfx) {
        this(c, times, vfx, true);
    }

    public AttuneAction(AbstractCard c, int times) {
        this(c, times, false);
    }

    public AttuneAction(AbstractCard c, boolean vfx) {
        this(c, 1, vfx);
    }

    public AttuneAction(AbstractCard c) {
        this(c, 1);
    }

    public void update() {
        isDone = true;
        if (vfx)
            AbstractDungeon.topLevelEffects.add(new AttuneCardEffect(card, sfx));
        AttunedPatches.attune(card, times);
    }
}