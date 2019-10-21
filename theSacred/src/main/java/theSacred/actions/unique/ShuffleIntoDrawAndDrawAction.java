package theSacred.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import theSacred.util.UC;

public class ShuffleIntoDrawAndDrawAction extends AbstractGameAction {
    private float startingDuration;

    public ShuffleIntoDrawAndDrawAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            int cnt = 0;
            for(AbstractCard c : UC.p().hand.group) {
                UC.p().drawPile.moveToDeck(c, true);
                cnt++;
            }
            for (int i = 0; i < cnt ; i++) {
                //Because onDraworDiscard isn't called otherwise
                UC.p().draw();
            }
            this.isDone = true;
        }
    }
}