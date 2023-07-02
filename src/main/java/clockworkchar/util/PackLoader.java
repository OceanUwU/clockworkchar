package clockworkchar.util;

import basemod.AutoAdd;
import clockworkchar.CrankyMod;
import clockworkchar.cards.AbstractCrankyCard;
import clockworkchar.characters.Cranky;
import clockworkchar.packs.AbstractCrankyPack;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.interfaces.EditPacksSubscriber;

public class PackLoader implements EditPacksSubscriber {
    @Override
    public void receiveEditPacks() {
        SpireAnniversary5Mod.allowCardClass(AbstractCrankyCard.class);
        SpireAnniversary5Mod.allowCardColor(Cranky.Enums.CLOCKWORK_BROWN_COLOR);
        new AutoAdd(CrankyMod.modID)
            .packageFilter("clockworkchar.packs")
            .any(AbstractCrankyPack.class, (info, pack) -> SpireAnniversary5Mod.declarePack(pack));
    }
}