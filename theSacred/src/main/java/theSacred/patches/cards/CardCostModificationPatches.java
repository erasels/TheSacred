package theSacred.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSacred.powers.RecklessAttackPower;
import theSacred.util.UC;

public class CardCostModificationPatches {
    @SpirePatch(clz = AbstractCard.class, method = "freeToPlay")
    public static class RecklessAttackPatch {
        @SpirePostfixPatch
        public static boolean patch(boolean __result, AbstractCard __instance) {
            return __result || (UC.isInCombat() && AbstractDungeon.player.hasPower(RecklessAttackPower.POWER_ID));
        }
    }
}
