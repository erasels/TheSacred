package theSacred.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.UC;

public class BeforeEndTurnForPlayingCardPatches {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class InterceptEoT {
        public static SpireField<Boolean> intercept = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = GameActionManager.class, method = "callEndOfTurnActions")
    public static class HookAndSinker {
        @SpireInsertPatch(locator = Locator.class)
        public static void hook(GameActionManager __instance) {
            for (AbstractCard c : UC.hand().group) {
                if (c instanceof SacredCard) {
                    ((SacredCard) c).triggerOnBeforeEndOfTurnForPlayingCard();
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(CardGroup.class, "group");
                return LineFinder.findAllInOrder(ctBehavior, finalMatcher);
            }
        }


        @SpireInstrumentPatch
        public static ExprEditor interceptor() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractCard.class.getName()) && m.getMethodName().equals("triggerOnEndOfTurnForPlayingCard")) {
                        m.replace("if (!"+ BeforeEndTurnForPlayingCardPatches.class.getName() +".shouldIntercept(c)) {" +
                                "$proceed($$);" +
                                "}");
                    }
                }
            };
        }
    }

    public static boolean shouldIntercept(AbstractCard c) {
        return InterceptEoT.intercept.get(c);
    }
}
