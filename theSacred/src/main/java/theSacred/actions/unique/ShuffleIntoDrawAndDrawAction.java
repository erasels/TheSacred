package theSacred.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import theSacred.actions.common.MoveCardFromHandToDeckAction;
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
            UC.att(new DrawCardAction(UC.p(), UC.p().hand.size()));
            for(AbstractCard c : UC.p().hand.group) {
                UC.att(new MoveCardFromHandToDeckAction(c));
            }
        }
        isDone = true;
    }
}