package theSacred.patches.combat;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class InvisiblePowerPatches {
    @SpirePatch(clz = ApplyPowerAction.class, method = "update")
    public static class NoApplicationEffect {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(ApplyPowerAction __instance, AbstractPower ___powerToApply) {
            if (___powerToApply instanceof InvisiblePower) {
                for (int i = AbstractDungeon.effectList.size() - 1; i > -1; i--) {
                    if (___powerToApply.type == AbstractPower.PowerType.DEBUFF) {
                        if (AbstractDungeon.effectList.get(i) instanceof PowerDebuffEffect) {
                            AbstractDungeon.effectList.remove(i);
                        }
                    } else if (___powerToApply.type == AbstractPower.PowerType.BUFF) {
                        if (AbstractDungeon.effectList.get(i) instanceof PowerBuffEffect) {
                            AbstractDungeon.effectList.remove(i);
                        }
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "onModifyPower");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }

        @SpireInstrumentPatch
        public static ExprEditor DontFlashInvisiblePowers() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractPower.class.getName()) && m.getMethodName().equals("flash")) {
                        m.replace("if (!(powerToApply instanceof " + InvisiblePower.class.getName() + ")) {" +
                                "$_ = $proceed($$);" +
                                "}");
                    }
                }
            };
        }
    }
}
