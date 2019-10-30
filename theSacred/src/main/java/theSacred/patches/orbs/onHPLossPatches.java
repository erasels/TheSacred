package theSacred.patches.orbs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CtBehavior;
import theSacred.orbs.interfaces.OnHPLossOrb;

public class onHPLossPatches {
    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class PlayerHook {
        @SpireInsertPatch(locator = Locator.class, localvars = {"damageAmount"})
        public static void call(AbstractPlayer __instance, DamageInfo info, int damageAmount) {
            for(AbstractOrb o : __instance.orbs) {
                if(o instanceof OnHPLossOrb) {
                    ((OnHPLossOrb)o).wasHPLost(info, damageAmount);
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
                return new int[]{LineFinder.findAllInOrder(ctBehavior, finalMatcher)[3]};
            }
        }
    }
}
