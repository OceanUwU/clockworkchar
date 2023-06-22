package clockworkchar.cards;

import basemod.AutoAdd;

import static clockworkchar.CrankyMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class EasyModalChoiceCard extends AbstractCrankyCard {
    private Runnable onUseOrChosen;
    private String passedId;
    private String passedName;
    private String passedDesc;

    public EasyModalChoiceCard(String id, String name, String description, Runnable onUseOrChosen) {
        super(makeID(id), -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        this.passedId = id;
        this.name = this.originalName = passedName = name;
        this.rawDescription = passedDesc = description;
        this.onUseOrChosen = onUseOrChosen;
        initializeTitle();
        initializeDescription();
    }

    @Override
    public void onChoseThisOption() {
        onUseOrChosen.run();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onUseOrChosen.run();
    }

    @Override
    public void upp() {

    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new EasyModalChoiceCard(passedId, passedName, passedDesc, onUseOrChosen);
    }
}
