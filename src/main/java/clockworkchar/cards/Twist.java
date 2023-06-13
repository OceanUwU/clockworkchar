package clockworkchar.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import clockworkchar.actions.UseToolAction;
import clockworkchar.actions.WindUpAction;
import clockworkchar.helpers.ToolLibrary;

import java.util.Arrays;
import java.util.List;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

public class Twist extends AbstractEasyCard {
    public final static String ID = makeID("Twist");

    public Twist() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = 8;
        baseSecondMagic = secondMagic = 3;
        part = true;
    }

    public List<TooltipInfo> getCustomTooltips() {
        return Arrays.asList(new TooltipInfo(ToolLibrary.defaultTool.name, GameDictionary.keywords.get(ToolLibrary.defaultTool.id.toLowerCase())));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new WindUpAction(magicNumber));
        atb(new UseToolAction());
    }

    public void activate() {
        att(new WindUpAction(secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(4);
        upgradeSecondMagic(3);
    }
}