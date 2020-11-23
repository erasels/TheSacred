package theSacred.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSacred.actions.common.TransformCardInHandAcion;
import theSacred.util.UC;

public class RottenFoodAction extends AbstractGameAction {
    protected AbstractCard origin;

    public RottenFoodAction(AbstractCard c) {
        this.target = UC.p();
        origin = c;
    }

    public void update() {
        AbstractCard tLeft, tRight;
        int pos = UC.hand().group.indexOf(origin);
        if(pos > -1) {
            if(pos > 0) {
                tLeft = UC.hand().group.get(pos -1);
                UC.att(new TransformCardInHandAcion(tLeft, origin.cardsToPreview.makeStatEquivalentCopy()));
            }
            if(pos < UC.hand().size() - 1) {
                tRight = UC.hand().group.get(pos +1);
                UC.att(new TransformCardInHandAcion(tRight, origin.cardsToPreview.makeStatEquivalentCopy()));
            }
        }
        isDone = true;
    }
}
