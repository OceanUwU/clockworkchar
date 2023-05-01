package clockworkchar.cards;

import clockworkchar.vfx.BreakEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class UnleashRage extends AbstractEasyCard {
    public final static String ID = makeID("UnleashRage");

    public UnleashRage() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 14;
        baseMagicNumber = magicNumber = 6;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void activate() {
        vfxTop(new BreakEffect());
        att(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
    }

    public void upp() {
        upgradeDamage(4);
    }
}