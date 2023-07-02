package clockworkchar.packs;

import clockworkchar.cards.*;
import java.util.ArrayList;

import static clockworkchar.CrankyMod.makeID;

public class ClockworkPack extends AbstractCrankyPack {
    public static final String ID = makeID("ClockworkPack");

    public ClockworkPack() {
        super(ID, new PackSummary(1, 4, 3, 4, 2));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(Solder.ID);
        cards.add(Blurrier.ID);
        cards.add(Whirl.ID);

        cards.add(Whir.ID);
        cards.add(MechanicalManoeuvre.ID);
        cards.add(PotentialEnergy.ID);
        cards.add(Cogwheels.ID);

        cards.add(Tip.ID);
        cards.add(Advance.ID);
        cards.add(MachinicModification.ID);
        return cards;
    }
}