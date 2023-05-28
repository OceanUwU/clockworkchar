package clockworkchar.cards;

import clockworkchar.actions.SpinAction;
import clockworkchar.characters.TheClockwork;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.att;

import java.util.ArrayList;

public class Rust extends AbstractEasyCard {
    public final static String ID = makeID("Rust");

    public Rust() {
        super(ID, -2, CardType.CURSE, CardRarity.CURSE, CardTarget.NONE, CardColor.CURSE);
        baseSpinAmount = spinAmount = 4;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return canPlayUnplayablePart();
    }

    public void activate() {
        att(new SpinAction(spinAmount, spun -> {}));
    }

    public void upp() {}

    public static class RustClockworkOnlyPatches {
        @SpirePatch(clz=CardLibrary.class,method="getCurse",paramtypez={})
        public static class WithoutRandom {
            @SpireInsertPatch(rloc=8,localvars={"tmp"})
            public static void Insert(ArrayList<String> tmp) {removeRustFromPool(tmp);}
        }
        @SpirePatch(clz=CardLibrary.class,method="getCurse",paramtypez={AbstractCard.class, Random.class})
        public static class WithRandom {
            @SpireInsertPatch(rloc=12,localvars={"tmp"})
            public static void Insert(ArrayList<String> tmp) {removeRustFromPool(tmp);}
        }

        private static void removeRustFromPool(ArrayList<String> tmp) {
            if (AbstractDungeon.player == null || !(AbstractDungeon.player instanceof TheClockwork))
                tmp.remove(Rust.ID);
        }
    }
}