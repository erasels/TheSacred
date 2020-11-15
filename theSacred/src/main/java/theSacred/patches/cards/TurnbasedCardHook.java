package theSacred.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;
import theSacred.cards.abstracts.OnTurnChangeCard;
import theSacred.util.UC;

public class TurnbasedCardHook {
    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class Hook {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(GameActionManager __instance) {
            int newTurn = GameActionManager.turn + 1;
            UC.p().drawPile.group.stream().filter(c -> c instanceof OnTurnChangeCard).forEachOrdered(c -> ((OnTurnChangeCard)c).onTurnChange(newTurn));
            UC.p().hand.group.stream().filter(c -> c instanceof OnTurnChangeCard).forEachOrdered(c -> ((OnTurnChangeCard)c).onTurnChange(newTurn));
            UC.p().discardPile.group.stream().filter(c -> c instanceof OnTurnChangeCard).forEachOrdered(c -> ((OnTurnChangeCard)c).onTurnChange(newTurn));
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ModHelper.class, "isModEnabled");
                return LineFinder.findAllInOrder(ctBehavior, finalMatcher);
            }
        }
    }
}
