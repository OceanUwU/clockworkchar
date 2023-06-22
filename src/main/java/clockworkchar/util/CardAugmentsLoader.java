package clockworkchar.util;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.AutoAdd;
import clockworkchar.CrankyMod;

public class CardAugmentsLoader {
    public static void load() {
        new AutoAdd(CrankyMod.modID)
            .packageFilter("clockworkchar.cardmods")
            .any(AbstractAugment.class, (info, abstractAugment) -> CardAugmentsMod.registerAugment(abstractAugment, CrankyMod.modID));
    }
}