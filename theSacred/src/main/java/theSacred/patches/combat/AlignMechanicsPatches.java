package theSacred.patches.combat;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import theSacred.actions.utility.ActivateAlignEffectAction;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.util.UC;

public class AlignMechanicsPatches {
    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class UseCardHook {
        @SpireInsertPatch(locator = Locator.class)
        public static void callHook(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            if(c instanceof AlignedCard) {
                UC.atb(new ActivateAlignEffectAction((AlignedCard) c, monster));
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "removeCard");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }
}
