package theSacred.patches.orbs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CtBehavior;
import theSacred.orbs.interfaces.DamageAndBlockModifyOrb;
import theSacred.util.UC;

public class OrbHookPatches {
    @SpirePatch(clz = UseCardAction.class, method = SpirePatch.CONSTRUCTOR)
    public static class UseCardHook {
        @SpireInsertPatch(locator = Locator.class)
        public static void callHook(UseCardAction __instance, AbstractCard c, AbstractCreature target) {
            UC.p().orbs.stream().filter(o -> o instanceof DamageAndBlockModifyOrb).forEach(o -> ((DamageAndBlockModifyOrb) o).onUseCard(c, __instance));
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "applyPowers")
    public static class AbstractCardDamageGiveHook {
        @SpireInsertPatch(locator = LocatorSingle.class, localvars = {"tmp"})
        public static void callSingleHook(AbstractCard __instance, @ByRef float[] tmp) {
            for (AbstractOrb o : UC.p().orbs) {
                if (o instanceof DamageAndBlockModifyOrb) {
                    tmp[0] = ((DamageAndBlockModifyOrb) o).atPlayerDamageGive(tmp[0], __instance.damageTypeForTurn);
                }
            }
        }

        @SpireInsertPatch(locator = LocatorMulti.class, localvars = {"tmp", "i"})
        public static void callMultiHook(AbstractCard __instance, float[] tmp, int i) {
            for (AbstractOrb o : UC.p().orbs) {
                if (o instanceof DamageAndBlockModifyOrb) {
                    tmp[i] = ((DamageAndBlockModifyOrb) o).atPlayerDamageGive(tmp[i], __instance.damageTypeForTurn);
                }
            }
        }

        private static class LocatorSingle extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }

        private static class LocatorMulti extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
                return new int[]{LineFinder.findAllInOrder(ctBehavior, finalMatcher)[2]};
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "calculateCardDamage")
    public static class AbstractCardDamageGiveHook2 {
        @SpireInsertPatch(locator = LocatorSingle.class, localvars = {"tmp"})
        public static void callHook(AbstractCard __instance, AbstractMonster mo, @ByRef float[] tmp) {
            for (AbstractOrb o : UC.p().orbs) {
                if (o instanceof DamageAndBlockModifyOrb) {
                    tmp[0] = ((DamageAndBlockModifyOrb) o).atPlayerDamageGive(tmp[0], __instance.damageTypeForTurn);
                }
            }
        }

        @SpireInsertPatch(locator = LocatorMulti.class, localvars = {"tmp", "i"})
        public static void callMultiHook(AbstractCard __instance, AbstractMonster mo, float[] tmp, int i) {
            for (AbstractOrb o : UC.p().orbs) {
                if (o instanceof DamageAndBlockModifyOrb) {
                    tmp[i] = ((DamageAndBlockModifyOrb) o).atPlayerDamageGive(tmp[i], __instance.damageTypeForTurn);
                }
            }
        }

        private static class LocatorSingle extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }

        private static class LocatorMulti extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
                return new int[]{LineFinder.findAllInOrder(ctBehavior, finalMatcher)[2]};
            }
        }
    }

    @SpirePatch(clz = DamageInfo.class, method = "applyPowers")
    public static class DamageInfoDamageGiveHook {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void callHook(DamageInfo __instance, AbstractCreature owner, AbstractCreature target, @ByRef float[] tmp) {
            for (AbstractOrb o : UC.p().orbs) {
                if (o instanceof DamageAndBlockModifyOrb) {
                    tmp[0] = ((DamageAndBlockModifyOrb) o).atPlayerDamageGive(tmp[0], __instance.type);
                }
            }
        }

        //There's another damageGive in here but orbs only make the attacks fo the player stronger
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "powers");
                return new int[]{LineFinder.findAllInOrder(ctBehavior, finalMatcher)[4]};
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "applyPowersToBlock")
    public static class BlockModifyHook {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void callHook(AbstractCard __instance, @ByRef float[] tmp) {
            for (AbstractOrb o : UC.p().orbs) {
                if (o instanceof DamageAndBlockModifyOrb) {
                    tmp[0] = ((DamageAndBlockModifyOrb) o).modifyBlock(tmp[0]);
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

    @SpirePatch(clz = DamageInfo.class, method = "applyPowers")
    public static class DamageInfoDamageReceiveHook {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void callHook(DamageInfo __instance, AbstractCreature owner, AbstractCreature target, @ByRef float[] tmp) {
            for (AbstractOrb o : UC.p().orbs) {
                if (o instanceof DamageAndBlockModifyOrb) {
                    tmp[0] = ((DamageAndBlockModifyOrb) o).atPlayerDamageReceive(tmp[0], __instance.type);
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "powers");
                return new int[]{LineFinder.findAllInOrder(ctBehavior, finalMatcher)[1]};
            }
        }
    }

    @SpirePatch(clz = AbstractMonster.class, method = "calculateDamage")
    public static class AbstractMonsterDamageReceiveHook {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void callHook(AbstractMonster __instance, int dmg, @ByRef float[] tmp) {
            for (AbstractOrb o : UC.p().orbs) {
                if (o instanceof DamageAndBlockModifyOrb) {
                    tmp[0] = ((DamageAndBlockModifyOrb) o).atPlayerDamageReceive(tmp[0], DamageInfo.DamageType.NORMAL);
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
