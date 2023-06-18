package clockworkchar.util;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.AutoAdd;
import clockworkchar.ClockworkChar;

public class CardAugmentsLoader {
    public static void load() {
        new AutoAdd(ClockworkChar.modID)
            .packageFilter("clockworkchar.cardmods")
            .any(AbstractAugment.class, (info, abstractAugment) -> CardAugmentsMod.registerAugment(abstractAugment, ClockworkChar.modID));
    }
}