package clockworkchar.packs;

import clockworkchar.CrankyMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import thePackmaster.packs.AbstractCardPack;
import thePackmaster.packs.PackPreviewCard;

import static clockworkchar.CrankyMod.makeCardPath;
import static clockworkchar.CrankyMod.makeImagePath;

public abstract class AbstractCrankyPack extends AbstractCardPack {
    public AbstractCrankyPack(String id, AbstractCardPack.PackSummary summary) {
        super(id, CardCrawlGame.languagePack.getUIString(id).TEXT[0], CardCrawlGame.languagePack.getUIString(id).TEXT[1], CardCrawlGame.languagePack.getUIString(id).TEXT[2], summary);
    }

    public PackPreviewCard makePreviewCard() {
        return new PackPreviewCard(packID, makeCardPath(packID.replace(CrankyMod.modID + ":", "")+".png"), this);
    }

    public String getHatPath() {
        return makeImagePath("hats/"+packID.replace(CrankyMod.modID + ":", "")+"Hat.png");
    }
}
