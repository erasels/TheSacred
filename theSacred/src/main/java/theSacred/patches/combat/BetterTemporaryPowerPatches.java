package theSacred.patches.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import javassist.*;
import theSacred.util.UC;

public class BetterTemporaryPowerPatches {
    @SpirePatch(clz = AbstractPower.class, method = SpirePatch.CLASS)
    public static class Fields {
        public static SpireField<Integer> temporaryAmount = new SpireField<>(() -> 0);

        public static int getTA(AbstractPower pow) {
            return temporaryAmount.get(pow);
        }

        public static void resetTA(AbstractPower pow) {
            UC.reducePower(pow, getTA(pow));
            temporaryAmount.set(pow, 0);
        }
    }

    //Function
    @SpirePatch(clz = StrengthPower.class, method = SpirePatch.CONSTRUCTOR)
    public static class DecreaseAtEoTStr {
        public static void Raw(CtBehavior ctMethodToPatch) throws NotFoundException, CannotCompileException {
            CtClass ctClass = ctMethodToPatch.getDeclaringClass();
            ClassPool pool = ctClass.getClassPool();

            CtMethod method = CtNewMethod.make(
                    CtClass.voidType, // Return
                    "atEndOfTurn", // Method name
                    new CtClass[]{CtClass.booleanType}, //Paramters
                    null, // Exceptions
                    "{" +
                            "if(" + Fields.class.getName() + ".getTA(this) > 0) { " +
                            Fields.class.getName() + ".resetTA(this);" +
                            "}" +
                            "}",
                    ctClass
            );
            ctClass.addMethod(method);

            CtClass ctColor = pool.get(Color.class.getName());
            CtClass ctSB = pool.get(SpriteBatch.class.getName());
            CtMethod method2 = CtNewMethod.make(
                    CtClass.voidType, // Return
                    "renderAmount", // Method name
                    new CtClass[]{ctSB, CtClass.floatType, CtClass.floatType, ctColor}, //Paramters
                    null, // Exceptions
                    "{" +
                            "super.renderAmount($1, $2, $3, $4);" +
                            BetterTemporaryPowerPatches.class.getName() + ".renderDecrease($1, $2, $3, $4, fontScale, " + Fields.class.getName() + ".getTA(this));" +
                            "}",
                    ctClass
            );
            ctClass.addMethod(method2);
        }
    }

    @SpirePatch(clz = DexterityPower.class, method = SpirePatch.CONSTRUCTOR)
    public static class DecreaseAtEoTDex {
        public static void Raw(CtBehavior ctMethodToPatch) throws NotFoundException, CannotCompileException {
            CtClass ctClass = ctMethodToPatch.getDeclaringClass();
            ClassPool pool = ctClass.getClassPool();

            CtMethod method = CtNewMethod.make(
                    CtClass.voidType, // Return
                    "atEndOfTurn", // Method name
                    new CtClass[]{CtClass.booleanType}, //Paramters
                    null, // Exceptions
                    "{" +
                            "if(" + Fields.class.getName() + ".getTA(this) > 0) { " +
                            Fields.class.getName() + ".resetTA(this);" +
                            "}" +
                            "}",
                    ctClass
            );
            ctClass.addMethod(method);

            CtClass ctColor = pool.get(Color.class.getName());
            CtClass ctSB = pool.get(SpriteBatch.class.getName());
            CtMethod method2 = CtNewMethod.make(
                    CtClass.voidType, // Return
                    "renderAmount", // Method name
                    new CtClass[]{ctSB, CtClass.floatType, CtClass.floatType, ctColor}, //Paramters
                    null, // Exceptions
                    "{" +
                            "super.renderAmount($1, $2, $3, $4);" +
                            BetterTemporaryPowerPatches.class.getName() + ".renderDecrease($1, $2, $3, $4, fontScale, " + Fields.class.getName() + ".getTA(this));" +
                            "}",
                    ctClass
            );
            ctClass.addMethod(method2);
        }
    }

    public static void renderDecrease(SpriteBatch sb, float x, float y, Color c, float fontScale, int amount) {
        if(amount > 0) {
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount), x, y + 15 * Settings.scale, fontScale, Color.RED);
        }
    }

    //Description
    private static final PowerStrings strStrings = CardCrawlGame.languagePack.getPowerStrings("Flex");
    private static final PowerStrings dexStrings = CardCrawlGame.languagePack.getPowerStrings("DexLoss");
    @SpirePatch(clz = StrengthPower.class, method = "updateDescription")
    public static class UpdateDescWithTempDecrease {
        @SpirePostfixPatch
        public static void patch(AbstractPower __instance) {
            PowerStrings strings;
            if(__instance instanceof StrengthPower) {
                strings = strStrings;
            } else {
                strings = dexStrings;
            }
            int i = __instance.description.indexOf(strings.DESCRIPTIONS[0]);
            if(i < 0) {
                __instance.description += " NL " + strings.DESCRIPTIONS[0] + Fields.getTA(__instance) + strings.DESCRIPTIONS[1];
            } else {
                int j = __instance.description.indexOf(strings.DESCRIPTIONS[1]);
                __instance.description = __instance.description.substring(0, i) + Fields.getTA(__instance) + __instance.description.substring(j);
            }
        }
    }

    //Application is part of TemporaryPowerApplicationAction
}
