package theSacred.cards.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSacred.cards.abstracts.SacredCard;

public class InvokeAddition extends DynamicVariable {
    @Override
    public String key() {
        return "theSacred:IA";
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof SacredCard) {
            return ((SacredCard) card).baseInvokeAddition + (card.upgraded?((SacredCard) card).invokeUpgAddition:0);
        }
        return -1;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof SacredCard) {
            return ((SacredCard) card).invokeAddition + (card.upgraded?((SacredCard) card).invokeUpgAddition:0);
        }
        return -1;
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof SacredCard) {
            return ((SacredCard) card).baseInvokeAddition != ((SacredCard) card).invokeAddition;
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
