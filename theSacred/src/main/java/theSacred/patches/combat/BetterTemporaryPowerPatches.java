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
                            DecreaseAtEoTStr.class.getName() + ".render($1, $2, $3, $4, fontScale, " + Fields.class.getName() + ".getTA(this));" +
                            "}",
                    ctClass
            );
            ctClass.addMethod(method2);
        }

        public static void render(SpriteBatch sb, float x, float y, Color c, float fontScale, int amount) {
            if(amount > 0) {
                FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount), x, y + 15 * Settings.scale, fontScale, Color.RED);
            }
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
                            DecreaseAtEoTDex.class.getName() + ".render($1, $2, $3, $4, fontScale, " + Fields.class.getName() + ".getTA(this));" +
                            "}",
                    ctClass
            );
            ctClass.addMethod(method2);
        }

        public static void render(SpriteBatch sb, float x, float y, Color c, float fontScale, int amount) {
            if(amount > 0) {
                FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount), x, y + 15 * Settings.scale, fontScale, Color.RED);
            }
        }
    }

    //Description
    private static final PowerStrings strStrings = CardCrawlGame.languagePack.getPowerStrings("Flex");
    @SpirePatch(clz = StrengthPower.class, method = "updateDescription")
    public static class UpdateDescWithTempDecreaseStr {
        @SpirePostfixPatch
        public static void patch(StrengthPower __instance) {
            int i = __instance.description.indexOf(strStrings.DESCRIPTIONS[0]);
            if(i < 0) {
                __instance.description += " NL " + strStrings.DESCRIPTIONS[0] + Fields.getTA(__instance) + strStrings.DESCRIPTIONS[1];
            } else {
                int j = __instance.description.indexOf(strStrings.DESCRIPTIONS[1]);
                __instance.description = __instance.description.substring(0, i) + Fields.getTA(__instance) + __instance.description.substring(j);
            }
        }
    }

    private static final PowerStrings dexStrings = CardCrawlGame.languagePack.getPowerStrings("DexLoss");
    @SpirePatch(clz = DexterityPower.class, method = "updateDescription")
    public static class UpdateDescWithTempDecreaseDex {
        @SpirePostfixPatch
        public static void patch(DexterityPower __instance) {
            int i = __instance.description.indexOf(dexStrings.DESCRIPTIONS[0]);
            if(i < 0) {
                __instance.description += " NL " + dexStrings.DESCRIPTIONS[0] + Fields.getTA(__instance) + dexStrings.DESCRIPTIONS[1];
            } else {
                int j = __instance.description.indexOf(dexStrings.DESCRIPTIONS[1]);
                __instance.description = __instance.description.substring(0, i) + Fields.getTA(__instance) + __instance.description.substring(j);
            }
        }
    }

    //Application is part of TemporaryPowerApplicationAction
}
