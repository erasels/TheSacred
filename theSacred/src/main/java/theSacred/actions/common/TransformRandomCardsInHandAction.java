package theSacred.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.commons.lang3.math.NumberUtils;
import theSacred.util.UC;

public class TransformRandomCardsInHandAction extends AbstractGameAction {
    public TransformRandomCardsInHandAction(int num) {
        target = UC.p();
        amount = num;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (UC.p().hand.group.isEmpty()) {
                this.isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            AbstractCard c;
            amount = NumberUtils.min(amount, UC.p().hand.size());
            for (int i = 0; i < amount ; i++) {
                c = UC.p().hand.getRandomCard(true);
                tmp.group.add(c);
                UC.p().hand.removeCard(c);
            }

            for (AbstractCard card : tmp.group) {
                AbstractCard transformedCard = AbstractDungeon.returnTrulyRandomCard();
                transformedCard.current_x = transformedCard.target_x = card.current_x;
                transformedCard.current_y = transformedCard.target_y = card.current_y;
                transformedCard.drawScale = transformedCard.targetDrawScale = card.drawScale;
                transformedCard.angle = transformedCard.targetAngle = card.angle;
                transformedCard.superFlash();
                AbstractDungeon.player.hand.addToTop(transformedCard);
                AbstractDungeon.player.hand.refreshHandLayout();
            }
            AbstractDungeon.player.hand.glowCheck();
            tickDuration();
            return;
        }
        UC.p().hand.refreshHandLayout();
        this.isDone = true;
    }
}
