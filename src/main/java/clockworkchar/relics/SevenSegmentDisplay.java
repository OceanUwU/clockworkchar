package clockworkchar.relics;

import clockworkchar.CrankyMod;
import clockworkchar.characters.Cranky;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.ReflectionHacks;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.CrankyMod.makeRelicPath;
import static clockworkchar.util.Wiz.atb;

import java.lang.reflect.Field;
import java.util.Arrays;

public class SevenSegmentDisplay extends AbstractEasyRelic {
    public static final String ID = makeID("SevenSegmentDisplay");
    private static Texture[] displays = Arrays.stream(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "off"}).map(i -> new Texture(makeRelicPath("ss/"+i+".png"))).toArray(Texture[]::new);
    private static final int CHARGE_PER_DAMAGE = 5;
    private static Field offsetField = ReflectionHacks.getCachedField(AbstractRelic.class, "offsetX");
    private static Field rotationField = ReflectionHacks.getCachedField(AbstractRelic.class, "rotation");
    private int damage = 0;

    public SevenSegmentDisplay() {
        super(ID, RelicTier.UNCOMMON, LandingSound.SOLID, Cranky.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CHARGE_PER_DAMAGE + this.DESCRIPTIONS[1];
    }
  
    public void onPlayerEndTurn() {
        if (damage > 0) {
            flash();
            atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            atb(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    public void updateDisplay() {
        int newDamage = CrankyMod.winder.charge / CHARGE_PER_DAMAGE;
        if (newDamage > damage)
            flash();
        damage = newDamage;
    }

    @Override
    public void renderCounter(SpriteBatch sb, boolean inTopPanel) {
        try {
            String displayDamage = String.format("%02d", Math.min(99, damage));
            for (int i = 0; i < 2; i++) {
                sb.draw(displays[Integer.parseInt(displayDamage.substring(i, i+1))], currentX - 27f + (30f * i) + (inTopPanel ? (float)offsetField.get(this) : 0), currentY - 17f, 27f - (30f * i), 17f, 23f, 35f, scale, scale, (float)rotationField.get(this), 0, 0, 23, 35, false, false);
            }
        } catch (Exception e) {}
        super.renderCounter(sb, inTopPanel);
    }
}