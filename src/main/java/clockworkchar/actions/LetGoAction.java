package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static clockworkchar.ClockworkChar.makeID;
import java.util.function.Consumer;

public class LetGoAction extends AbstractGameAction {
    private boolean sound;
    private Consumer<Integer> cb;

    public LetGoAction(Consumer<Integer> callback) {
        this(true, callback);
    }
    
    public LetGoAction(boolean playSound, Consumer<Integer> callback) {
        sound = playSound;
        cb = callback;
    }

    public void update() {
        int chargeUsed = ClockworkChar.winder.useAllCharge();
        if (chargeUsed > 0) {
            if (sound)
                CardCrawlGame.sound.play(makeID("SPIN"));
            for (AbstractPower p : AbstractDungeon.player.powers)
                p.onGainCharge(-chargeUsed); 
        }
        cb.accept(chargeUsed);
        isDone = true;
    }
}