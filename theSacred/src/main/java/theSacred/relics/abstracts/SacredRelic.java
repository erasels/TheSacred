package theSacred.relics.abstracts;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSacred.TheSacred;
import theSacred.util.TextureLoader;

public abstract class SacredRelic extends AbstractRelic {
    public SacredRelic(String setId, String imgName, RelicTier tier, LandingSound sfx) {
        super(setId, "", tier, sfx);

        imgUrl = imgName;

        if (img == null || outlineImg == null) {
            img = TextureLoader.getTexture(TheSacred.makeRelicPath(imgName));
            largeImg = TextureLoader.getTexture(TheSacred.makeRelicPath(imgName));
            outlineImg = TextureLoader.getTexture(TheSacred.makeRelicOutlinePath(imgName));
        }
    }
}
