package clockworkchar.cards;

import clockworkchar.actions.WindUpAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.atb;

public class ExtendoGrip extends AbstractEasyCard {
    public final static String ID = makeID("ExtendoGrip");

    public ExtendoGrip() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 12;
        baseMagicNumber = magicNumber = 7;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("ATTACK_HEAVY"));
        atb(new VFXAction(p, new CleaveEffect(), 0.1F));
        allDmg(AbstractGameAction.AttackEffect.NONE);
        atb(new WindUpAction(magicNumber));
    }

    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(2);
    }
}