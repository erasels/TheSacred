package theSacred.patches.orbs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CtBehavior;
import theSacred.orbs.interfaces.OnUseCardOrb;
import theSacred.util.UC;

public class OrbHookPatches {
    @SpirePatch(clz = UseCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, AbstractCreature.class})
    public static class UseCardHook {
        @SpireInsertPatch(locator = Locator.class)
        public static void callHook(UseCardAction __instance, AbstractCard c, AbstractCreature target) {
            for(AbstractOrb o : UC.p().orbs) {
                if(o instanceof  OnUseCardOrb) {
                    ((OnUseCardOrb) o).onUseCard(c, __instance);
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }
}