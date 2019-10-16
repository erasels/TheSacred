package theSacred.patches.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import theSacred.characters.SacredCharacter;

import java.util.ArrayList;

@SpirePatch(
        clz = TipHelper.class,
        method = "renderKeywords"
)
public class SpecificChannelKeyword {
    public static final String CHANNEL_KEYWORD = GameDictionary.CHANNEL.NAMES[0].toLowerCase();
    public static final String ALT_CHANNEL_KEYWORD = "thesacred:altchannel";

    @SpireInsertPatch(
            rloc = 0,
            localvars = {"card", "y"}
    )
    public static void ModifyKeywords(float x, float stupidDumbUselessYThatIsntByRef, SpriteBatch sb, ArrayList<String> keywords,
                                      AbstractCard card, @ByRef float[] y) {
        if (keywords.contains(CHANNEL_KEYWORD) && card.color == SacredCharacter.Enums.COLOR_SACRED) {
            keywords.remove(CHANNEL_KEYWORD);
            keywords.add(ALT_CHANNEL_KEYWORD);
        }
    }
}
