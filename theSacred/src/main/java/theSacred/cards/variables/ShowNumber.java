package theSacred.cards.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSacred.cards.abstracts.SacredCard;

public class ShowNumber extends DynamicVariable {
    @Override
    public String key() {
        return "theSacred:SN";
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof SacredCard) {
            return ((SacredCard) card).baseShowNumber;
        }
        return -1;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof SacredCard) {
            return ((SacredCard) card).showNumber;
        }
        return -1;
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof SacredCard) {
            return ((SacredCard) card).isShowNumberModified;
        }
        return false;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof SacredCard) {
            ((SacredCard) card).isShowNumberModified = true;
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }
}
