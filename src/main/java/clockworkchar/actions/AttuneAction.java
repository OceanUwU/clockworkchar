package clockworkchar.actions;

import clockworkchar.patches.AttunedPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AttuneAction extends AbstractGameAction {
    private AbstractCard card;
    private int times;

    public AttuneAction(AbstractCard c, int times) {
        this.card = c;
        this.times = times;
    }

    public void update() {
        isDone = true;
        AttunedPatches.attune(card, times);
    }
}