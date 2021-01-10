package theSacred.cards.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSacred.cards.abstracts.InvokeCard;
import theSacred.util.UC;

public class InvokeAddition extends DynamicVariable {
    @Override
    public String key() {
        return "theSacred:IA";
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (UC.isInvoke(card)) {
            return ((InvokeCard) card).baseInvokeAddition + (card.upgraded?((InvokeCard) card).invokeUpgAddition:0);
        }
        return -1;
    }

    @Override
    public int value(AbstractCard card) {
        if (UC.isInvoke(card)) {
            return ((InvokeCard) card).invokeAddition + (card.upgraded?((InvokeCard) card).invokeUpgAddition:0);
        }
        return -1;
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (UC.isInvoke(card)) {
            return ((InvokeCard) card).baseInvokeAddition != ((InvokeCard) card).invokeAddition;
        }
        return false;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }
}
