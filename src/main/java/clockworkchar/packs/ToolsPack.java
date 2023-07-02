package clockworkchar.packs;

import clockworkchar.cards.*;
import clockworkchar.potions.SpareNails;
import java.util.ArrayList;

import static clockworkchar.CrankyMod.makeID;

public class ToolsPack extends AbstractCrankyPack {
    public static final String ID = makeID("ToolsPack");

    public ToolsPack() {
        super(ID, new PackSummary(3, 3, 3, 1, 5));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(RearrangeAttack.ID);
        cards.add(Mend.ID);
        cards.add(Rearrange.ID);

        cards.add(AsFarAsItGoes.ID);
        cards.add(Mesmerise.ID);
        cards.add(Salvage.ID);
        cards.add(Toolerang.ID);
        cards.add(ReadInstructions.ID);

        cards.add(Scanner.ID);
        cards.add(DiligentWork.ID);
        return cards;
    }

    public ArrayList<String> getPackPotions() {
        ArrayList<String> potions = new ArrayList<>();
        potions.add(SpareNails.POTION_ID);
        return potions;
    }
}