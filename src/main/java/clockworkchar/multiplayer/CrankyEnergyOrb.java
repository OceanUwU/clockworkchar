package clockworkchar.multiplayer;

import basemod.ReflectionHacks;
import clockworkchar.characters.TheClockwork;
import clockworkchar.ui.Winder;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import spireTogether.modcompat.downfall.characters.energyorbs.CustomizableEnergyOrbCustom;

import static clockworkchar.ClockworkChar.makeImagePath;

public class CrankyEnergyOrb extends CustomizableEnergyOrbCustom {
    public Winder winder;
    public TheClockwork source;

    public CrankyEnergyOrb() {
        super(TheClockwork.orbTextures, makeImagePath("char/mainChar/orb/vfx.png"), TheClockwork.orbRotationValues);
        winder = new Winder((float)ReflectionHacks.getPrivateStatic(CustomizableEnergyOrbCustom.class, "ORB_IMG_SCALE") / Settings.scale, false, false, false, FontHelper.energyNumFontRed);
    }

    @Override
    public void updateOrb(int energyCount) {
        super.updateOrb(energyCount);
        winder.update();
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        super.renderOrb(sb, enabled, current_x, current_y);
        winder.x = current_x - winder.offset - 128f * Settings.scale * 1.25f;
        winder.y = current_y + winder.offset;
        winder.render(sb);
        if (source != null)
            winder.spinSkinWinder(source.winderBone);
    }
}