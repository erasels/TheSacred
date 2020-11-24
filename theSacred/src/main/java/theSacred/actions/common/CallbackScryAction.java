package theSacred.actions.common;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CallbackScryAction extends ScryAction {
    protected Consumer<ArrayList<AbstractCard>> callback;
    protected boolean callCallback = false;
    protected ArrayList<AbstractCard> cards;

    public CallbackScryAction(int numCards, Consumer<ArrayList<AbstractCard>> callback) {
        super(numCards);
        this.callback = callback;
    }

    @Override
    public void update() {
        if(startDuration != duration && !callCallback) {
            callCallback = true;
            cards = new ArrayList<>(AbstractDungeon.gridSelectScreen.selectedCards);
        }
        super.update();
        if(callCallback && isDone) {
            callback.accept(cards);
        }
    }
}