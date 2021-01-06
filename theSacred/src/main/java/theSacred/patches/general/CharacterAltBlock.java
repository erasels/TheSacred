package theSacred.patches.general;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theSacred.TheSacred;
import theSacred.util.TextureLoader;
import theSacred.util.UC;

import static theSacred.characters.SacredCharacter.Enums.THE_SACRED;

public class CharacterAltBlock {
    @SpirePatch(clz = FlashAtkImgEffect.class, method = "loadImage")
    public static class LoadDifferentBlock {
        @SpirePostfixPatch
        public static TextureAtlas.AtlasRegion patch(TextureAtlas.AtlasRegion __result, FlashAtkImgEffect __instance) {
            if(UC.p().chosenClass == THE_SACRED && ReflectionHacks.getPrivate(__instance, FlashAtkImgEffect.class, "effect") == AbstractGameAction.AttackEffect.SHIELD) {
                return TextureLoader.getTextureAsAtlasRegion(TheSacred.makeCharPath("alt_block.png"));
            }

            return __result;
        }
    }
}
