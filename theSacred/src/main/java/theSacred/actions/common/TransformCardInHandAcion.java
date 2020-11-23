package theSacred.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSacred.TheSacred;
import theSacred.util.UC;

public class TransformCardInHandAcion extends AbstractGameAction {
    protected AbstractCard transformCard, originCard;
    protected int position = -1;
    protected boolean isRandom;

    public TransformCardInHandAcion(AbstractCard originCard, AbstractCard newCard) {
        this.target = UC.p();
        transformCard = newCard;
        this.originCard = originCard;
    }

    public TransformCardInHandAcion(AbstractCard originCard) {
        this.target = UC.p();
        this.originCard = originCard;
        isRandom = true;
    }

    public TransformCardInHandAcion(int position) {
        this.target = UC.p();
        this.position = position;
        isRandom = true;
    }

    public void update() {
        if(isRandom) {
            transformCard = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
        }

        if(position > -1) {
            UC.hand().group.set(position, transformCard);
            UC.copyCardPosition(UC.hand().group.get(position), transformCard);
            transformCard.flash();
        } else if (originCard != null) {
            int pos = UC.hand().group.indexOf(originCard);
            if(pos > -1) {
                UC.hand().group.set(pos, transformCard);
                UC.copyCardPosition(originCard, transformCard);
                transformCard.flash();
            }
        } else {
            TheSacred.logger.info("Something went wrong in TransformCardInHandAction, yell at author.");
        }
        isDone = true;
    }
}
