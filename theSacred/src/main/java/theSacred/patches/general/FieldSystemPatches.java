package theSacred.patches.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import theSacred.cards.abstracts.FieldCard;
import theSacred.mechanics.field.FieldSystem;

public class FieldSystemPatches {
    //FieldSystem
    @SpirePatch(clz = AbstractPlayer.class, method = "render")
    public static class RenderSystem {
        @SpirePostfixPatch
        public static void render(AbstractPlayer __instance, SpriteBatch sb) {
            FieldSystem.render(sb);
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "combatUpdate")
    public static class UpdateSystem {
        @SpirePostfixPatch
        public static void patch(AbstractPlayer __instance) {
            FieldSystem.update();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "updateInput")
    public static class UpdateHoverSystem {
        @SpirePostfixPatch
        public static void patch(AbstractPlayer __instance) {
            FieldSystem.updateHover();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "preBattlePrep")
    public static class PreCombatSystem {
        @SpirePostfixPatch
        public static void patch(AbstractPlayer __instance) {
            FieldSystem.preBattlePrep();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "onVictory")
    public static class PostCombatSystem {
        @SpirePostfixPatch
        public static void patch(AbstractPlayer __instance) {
            FieldSystem.onVictory();
        }
    }

    //Field Cards
    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class NoMoveFields {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<?> patch(UseCardAction __instance, AbstractCard ___targetCard, @ByRef float[] ___duration) {
            if (___targetCard instanceof FieldCard) {
                if(!___targetCard.dontTriggerOnUseCard) {
                    if (!FieldSystem.locked) {
                        ___duration[0] = ___duration[0] - Gdx.graphics.getDeltaTime();

                        AbstractDungeon.player.hand.removeCard(___targetCard);

                        AbstractDungeon.player.cardInUse = null;
                        ___targetCard.exhaustOnUseOnce = false;
                        ___targetCard.dontTriggerOnUseCard = false;
                        AbstractDungeon.actionManager.addToBottom(new HandCheckAction());

                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }


        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "purgeOnUse");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
