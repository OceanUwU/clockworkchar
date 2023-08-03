package clockworkchar.cards;

import clockworkchar.CrankyMod;
import clockworkchar.actions.EquipToolAction;
import clockworkchar.helpers.ToolLibrary;
import clockworkchar.potions.SpareNails;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Salvage extends AbstractCrankyCard {
    public final static String ID = makeID("Salvage");

    public Salvage() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 14;
        showDequipValue = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (upgraded)
            SpareNails.chooseToolToEquip(2);
        else
            atb(new EquipToolAction(ToolLibrary.getRandomTool(CrankyMod.toolSlot.tool)));
    }

    public void upp() {
        upgradeDamage(1);
    }
}