package clockworkchar.potions;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import clockworkchar.ClockworkChar;
import clockworkchar.actions.WindUpAction;
import clockworkchar.relics.FloppyDisk;
import clockworkchar.util.TexLoader;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class HandInAJar extends AbstractPotion {
    public static final String POTION_ID = makeID("HandInAJar");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public HandInAJar() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.COMMON, PotionSize.GHOST, PotionColor.WHITE);
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "liquidImg", TexLoader.getTexture(ClockworkChar.modID + "Resources/images/potions/HandInAJar.png"));
        labOutlineColor = ClockworkChar.characterColor;
    }

    public void initializeData() {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("wind_up")), BaseMod.getKeywordDescription(makeID("wind_up"))));
    }

    public void use(AbstractCreature abstractCreature) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            atb(new WindUpAction(potency));
        else {
            FloppyDisk disk = ((FloppyDisk)AbstractDungeon.player.getRelic(FloppyDisk.ID));
            CardCrawlGame.sound.play(makeID("WIND_UP"));
            disk.flash();
            disk.counter = Math.min(disk.counter + potency, FloppyDisk.MAX_CHARGE);
        }
    }
  
    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            return false; 
        if ((AbstractDungeon.getCurrRoom()).event != null && (AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain)
            return false;
        if ((AbstractDungeon.getCurrRoom()).monsters == null || (AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead() || AbstractDungeon.actionManager.turnHasEnded || (AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT)
            return AbstractDungeon.player.hasRelic(FloppyDisk.ID) && ((FloppyDisk)AbstractDungeon.player.getRelic(FloppyDisk.ID)).counter < FloppyDisk.MAX_CHARGE;
        return true;
    }

    public int getPotency(int ascensionlevel) {
        return 25;
    }

    public AbstractPotion makeCopy() {
        return new HandInAJar();
    }
}