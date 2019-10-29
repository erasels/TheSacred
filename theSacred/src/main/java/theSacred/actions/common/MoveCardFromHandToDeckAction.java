package theSacred.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSacred.util.UC;

public class MoveCardFromHandToDeckAction extends AbstractGameAction {
    private AbstractCard c;
    public MoveCardFromHandToDeckAction(AbstractCard c) {
        this.c = c;
    }

    @Override
    public void update() {
        UC.p().hand.moveToDeck(c, true);
        isDone = true;
    }
}
