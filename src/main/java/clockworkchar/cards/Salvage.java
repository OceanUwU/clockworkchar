package clockworkchar.cards;

import clockworkchar.actions.EquipToolAction;
import clockworkchar.helpers.ToolLibrary;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Salvage extends AbstractEasyCard {
    public final static String ID = makeID("Salvage");

    public Salvage() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 10;
        showDequipValue = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new EquipToolAction(ToolLibrary.getRandomTool()));
    }

    public void upp() {
        upgradeDamage(5);
    }
}