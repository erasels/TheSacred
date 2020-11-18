package theSacred.patches.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import theSacred.cards.abstracts.SacredCard;

@SpirePatch(clz = SingleCardViewPopup.class, method = "renderFrame")
public class RenderTopTextPatches {
    @SpireInsertPatch(rloc = 0, localvars = {"card"})
    public static void patch(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard card) {
        if (card instanceof SacredCard) {
            ((SacredCard)card).renderTopText(sb, true);
        }
    }
}
