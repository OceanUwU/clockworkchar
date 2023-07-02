package clockworkchar.packs;

import clockworkchar.cards.*;
import clockworkchar.potions.Sawblade;
import java.util.ArrayList;

import static clockworkchar.CrankyMod.makeID;

public class PartsPack extends AbstractCrankyPack {
    public static final String ID = makeID("PartsPack");

    public PartsPack() {
        super(ID, new PackSummary(4, 1, 3, 4, 3, "Strength"));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(UnleashRage.ID);
        cards.add(ScatterNails.ID);
        cards.add(TwistingStrike.ID);

        cards.add(MetalSheet.ID);
        cards.add(Futureproofing.ID);
        cards.add(EmergencyStop.ID);
        cards.add(VexingGlare.ID);
        cards.add(Rewinder.ID);

        cards.add(DangerousDeterrent.ID);
        cards.add(WearAndTear.ID);
        return cards;
    }

    public ArrayList<String> getPackPotions() {
        ArrayList<String> potions = new ArrayList<>();
        potions.add(Sawblade.POTION_ID);
        return potions;
    }
}