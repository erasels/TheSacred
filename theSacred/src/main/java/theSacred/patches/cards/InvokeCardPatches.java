package theSacred.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.UC;

import java.util.ArrayList;

public class InvokeCardPatches {
    //Randomize cost
    @SpirePatch(clz = AbstractPlayer.class, method = "draw", paramtypes = {"int"})
    public static class Draw {
        @SpireInsertPatch(locator = Locator.class, localvars = {"c"})
        public static void Insert(AbstractPlayer __instance, int numCards, AbstractCard c) {
            if (UC.isInvoke(c)) {
                randomizeCost(c);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "triggerWhenDrawn");
                return LineFinder.findAllInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "refreshHandLayout")
    public static class CatchEnteredHand {
        @SpirePostfixPatch
        public static void call(CardGroup __instance) {
            for(AbstractCard c : __instance.group) {
                if(UC.isInvoke(c) && !((SacredCard) c).invokeCostRandomized) {
                    randomizeCost(c);
                }
            }
        }
    }

    private static void randomizeCost(AbstractCard c) {
        c.cost = AbstractDungeon.cardRandomRng.random(((SacredCard)c).invokeMinCost, ((SacredCard) c).invokeMaxCost);
        c.costForTurn = c.cost;
        //c.isCostModified = true;
        ((SacredCard) c).invokeCostRandomized = true;
    }

    //Render cost as ?
    @SpirePatch(clz = AbstractCard.class, method = "getCost")
    public static class GetCost {
        public static String Postfix(String __result, AbstractCard __instance) {
            if (/*!__instance.isCostModified &&*/ UC.isInvoke(__instance)) {
                return "?";
            }
            return __result;
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCost")
    public static class PortraitViewCost {
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(FontHelper.class.getName()) && m.getMethodName().equals("renderFont")) {
                        m.replace("if ("+ UC.class.getName() +".isInvoke(card)) {" +
                                "$3 = \"?\";" +
                                "$4 = 674.0f * com.megacrit.cardcrawl.core.Settings.scale;" +
                                "}" +
                                "$_ = $proceed($$);");
                    }
                }
            };
        }
    }

    //Make the card cost randomizeable if it enters the hand again
    @SpirePatch(clz = CardGroup.class, method = "moveToDiscardPile")
    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    @SpirePatch(clz = CardGroup.class, method = "moveToBottomOfDeck")
    @SpirePatch(clz = CardGroup.class, method = "resetCardBeforeMoving")
    public static class StaysInHand {
        @SpirePostfixPatch
        public static void patch(CardGroup __instance, AbstractCard card) {
            if (UC.isInvoke(card)) {
                ((SacredCard)card).invokeCostRandomized = false;
            }
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "moveToDeck")
    public static class StaysInHand2 {
        @SpirePostfixPatch
        public static void patch(CardGroup __instance, AbstractCard card, boolean randomSpot) {
            if (UC.isInvoke(card) && __instance == AbstractDungeon.player.hand && AbstractDungeon.player.hand.contains(card)) {
                ((SacredCard)card).invokeCostRandomized = false;
            }
        }
    }
}
