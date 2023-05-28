package clockworkchar.actions;

import clockworkchar.ClockworkChar;
import clockworkchar.cards.AbstractEasyCard;
import clockworkchar.cards.Inertia;
import clockworkchar.powers.BatteryPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.att;
import java.util.function.Consumer;

public class SpinAction extends AbstractGameAction {
    private boolean sound;
    private Consumer<Boolean> cb;

    private boolean spun;

    public SpinAction(int amount, Consumer<Boolean> callback) {
        this(amount, true, callback);
    }
    
    public SpinAction(int amount, boolean playSound, Consumer<Boolean> callback) {
        this.amount = amount;
        sound = playSound;
        cb = callback;
    }

    public void update() {
        spun = true;
        if (ClockworkChar.winder.useCharge(amount)) {
            if (sound)
                CardCrawlGame.sound.play(makeID("SPIN"));
            cb.accept(true);
            spun = true;
        } else if (AbstractDungeon.player.hasPower(BatteryPower.POWER_ID)) {
            att(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, BatteryPower.POWER_ID, 1));
            att(new AbstractGameAction() {
                public void update() {
                    this.isDone = true;
                    AbstractDungeon.player.getPower(BatteryPower.POWER_ID).flash();
                    cb.accept(true);
                }
            });
            spun = true;
        } else {
            cb.accept(false);
            spun = false;
        }
        if (spun) {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group)
                if (c instanceof AbstractEasyCard)
                    ((AbstractEasyCard)c).triggerInDiscardPileOnSpin(); 
            if (AbstractDungeon.player.hasPower(Inertia.InertiaPower.POWER_ID))
                ((Inertia.InertiaPower)AbstractDungeon.player.getPower(Inertia.InertiaPower.POWER_ID)).onGainCCharge(-amount);
        }
        isDone = true;
    }
}