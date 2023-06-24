package clockworkchar.multiplayer;

import clockworkchar.characters.Cranky;
import spireTogether.modcompat.downfall.characters.energyorbs.CustomizableEnergyOrbCustom;

import static clockworkchar.CrankyMod.makeImagePath;

public class CrankyEnergyOrb extends CustomizableEnergyOrbCustom {
    public CrankyEnergyOrb() {
        super(Cranky.orbTextures, makeImagePath("char/mainChar/orb/vfx.png"), Cranky.orbRotationValues);
    }
}