package clockworkchar.relics;

import clockworkchar.characters.TheClockwork;
import clockworkchar.patches.AttunedPatches;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

import static clockworkchar.ClockworkChar.makeID;

public class SwissArmyKnife extends AbstractEasyRelic {
    public static final String ID = makeID("SwissArmyKnife");

    public SwissArmyKnife() {
        super(ID, RelicTier.COMMON, LandingSound.HEAVY, TheClockwork.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public static class Patches {
        public static void attuneFirstCard(RewardItem reward) {
            if (reward.cards.size() > 0)
                for (AbstractRelic r : AbstractDungeon.player.relics)
                    if (r.relicId.equals(ID))
                        AttunedPatches.attune(reward.cards.get(0));
        }

        @SpirePatch(clz=RewardItem.class, method=SpirePatch.CONSTRUCTOR, paramtypez={})
        public static class NoColour {
            public static void Postfix(RewardItem __instance) {
                attuneFirstCard(__instance);
            }
        }

        @SpirePatch(clz=RewardItem.class, method=SpirePatch.CONSTRUCTOR, paramtypez={AbstractCard.CardColor.class})
        public static class Colour {
            public static void Postfix(RewardItem __instance) {
                attuneFirstCard(__instance);
            }
        }
    }
}
