package theSacred.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import theSacred.util.UC;

public class ShuffleIntoDrawAndDrawAction extends AbstractGameAction {
    private float startingDuration;
    private int counter = 0;
    private boolean firstRun = true;

    public ShuffleIntoDrawAndDrawAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            counter = 0;
            for(AbstractCard c : UC.p().hand.group) {
                UC.p().drawPile.moveToDeck(c, true);
                counter++;
            }
        } else if (firstRun) {
            firstRun = false;

            for (int i = 0; i < counter ; i++) {
                //Because onDraworDiscard isn't called otherwise
                UC.p().draw();
            }
            UC.p().hand.refreshHandLayout();
            UC.p().hand.glowCheck();

        }
        tickDuration();
    }
}