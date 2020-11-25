package theSacred.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theSacred.util.UC;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CycleAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("GamblingChipAction");
    public static final String[] TEXT = uiStrings.TEXT;
    protected Consumer<ArrayList<AbstractCard>> callback;

    public CycleAction(int amount, Consumer<ArrayList<AbstractCard>> callback) {
        setValues(UC.p(), source, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.callback = callback;
    }

    public CycleAction(int amount) {
        this(amount, null);
    }

    public void update() {
        if (this.duration == startDuration) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[1], amount, true, true);
            addToBot(new WaitAction(0.25F));
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                UC.att(new DrawCardAction(AbstractDungeon.handCardSelectScreen.selectedCards.size()));
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    UC.att(new DiscardSpecificCardAction(c));
                }
            }
            if(callback != null)
                callback.accept(AbstractDungeon.handCardSelectScreen.selectedCards.group);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
