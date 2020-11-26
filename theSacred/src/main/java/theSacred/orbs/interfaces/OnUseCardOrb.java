package theSacred.orbs.interfaces;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnUseCardOrb {
    default void onUseCard(AbstractCard card, UseCardAction action) {}
}
