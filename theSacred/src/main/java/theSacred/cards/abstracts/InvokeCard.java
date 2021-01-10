package theSacred.cards.abstracts;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import theSacred.TheSacred;
import theSacred.characters.SacredCharacter;
import theSacred.patches.cards.CardENUMs;
import theSacred.util.CardInfo;
import theSacred.util.UC;

import java.util.ArrayList;
import java.util.List;

public abstract class InvokeCard extends SacredCard{
    public static final int INVOKE_MAX_COST = 3;

    public boolean invoke;
    public boolean invokeCostRandomized;
    public int invokeMinCost, invokeMaxCost;
    public int baseInvokeAddition, invokeAddition, invokeUpgAddition;

    public InvokeCard(CardColor color, String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription) {
        super(color, cardName, cost, cardType, target, rarity, upgradesDescription);

        invoke = false;
        invokeCostRandomized = false;
        invokeMinCost = 0;
        invokeMaxCost = INVOKE_MAX_COST;
        baseInvokeAddition = 0;
        invokeAddition = 0;
        invokeUpgAddition = 0;

        tags.add(CardENUMs.INVOKE);
    }

    public InvokeCard(CardInfo cardInfo, boolean upgradesDescription) {
        this(SacredCharacter.Enums.COLOR_SACRED, cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription);
    }

    public void setInvoke(int invkAdd, int invkUpgAdd) {
        setInvoke(invkAdd, invkUpgAdd, 0, INVOKE_MAX_COST);
    }

    public void setInvoke(int invkAdd, int invkUpgAdd, int invkMin, int invkMax) {
        this.invoke = true;
        baseInvokeAddition = invokeAddition = invkAdd;
        invokeUpgAddition = invkUpgAdd;
        invokeMinCost = invkMin;
        invokeMaxCost = invkMax;
        this.tags.add(CardENUMs.INVOKE);
        initializeDescription();
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        ArrayList<TooltipInfo> var = new ArrayList<>(super.getCustomTooltipsTop());
        if(var != null) {
            var.add(new TooltipInfo(TheSacred.invokeKeywords[0], TheSacred.invokeKeywords[1]));
        }
        return var;
    }

    private int invokeChangeIndex;
    private boolean invokeStringChanged = false;
    private static final String INVOKE_DYNAMIC_MARKER = "_?_";
    @Override
    public void initializeDescription() {
        if(invoke) {
            int marker = rawDescription.indexOf(INVOKE_DYNAMIC_MARKER);
            if (marker > -1) {
                invokeChangeIndex = marker;
                rawDescription = rawDescription.replace(INVOKE_DYNAMIC_MARKER, "?");
            }

            if (invokeAddition != baseInvokeAddition && !invokeStringChanged && !rawDescription.contains("? + !theSacred:IA!")) {
                invokeStringChanged = true;
                rawDescription = rawDescription.substring(0, invokeChangeIndex + 1)
                        + " + !theSacred:IA!"
                        + rawDescription.substring(invokeChangeIndex + 1);
            }
        }
        super.initializeDescription();
    }

    public int getInvokeAmt() {
        return costForTurn + invokeAddition + (upgraded?invokeUpgAddition:0);
    }

    public void incrementInvokeForCombat(int invkAdd) {
        invokeAddition+=invkAdd;
        if(!invokeStringChanged) {
            initializeDescription();
        }
    }

    public void randomizeCost() {
        cost = AbstractDungeon.cardRandomRng.random(invokeMinCost, invokeMaxCost);
        costForTurn = cost;
        //isCostModified = true;
        invokeCostRandomized = true;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();

        if (card instanceof InvokeCard) {
            ((InvokeCard) card).baseInvokeAddition = baseInvokeAddition;
            ((InvokeCard) card).invokeAddition = invokeAddition;
            ((InvokeCard) card).invokeUpgAddition = invokeUpgAddition;
            ((InvokeCard) card).invokeMinCost = invokeMinCost;
            ((InvokeCard) card).invokeMaxCost = invokeMaxCost;
        }
        return card;
    }

    //PATCHES
    @SpireOverride
    protected String getCost() {
        return "?";
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "draw", paramtypes = {"int"})
    public static class Draw {
        @SpireInsertPatch(locator = Locator.class, localvars = {"c"})
        public static void Insert(AbstractPlayer __instance, int numCards, AbstractCard c) {
            if (UC.isInvoke(c)) {
                ((InvokeCard) c).randomizeCost();
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
                if(UC.isInvoke(c) && !((InvokeCard) c).invokeCostRandomized) {
                    ((InvokeCard) c).randomizeCost();
                }
            }
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
                                "$4 = 674.0f * " + Settings.class.getName() + ".scale;" +
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
                ((InvokeCard)card).invokeCostRandomized = false;
            }
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "moveToDeck")
    public static class StaysInHand2 {
        @SpirePostfixPatch
        public static void patch(CardGroup __instance, AbstractCard card, boolean randomSpot) {
            if (UC.isInvoke(card) && __instance == AbstractDungeon.player.hand && AbstractDungeon.player.hand.contains(card)) {
                ((InvokeCard)card).invokeCostRandomized = false;
            }
        }
    }
}
