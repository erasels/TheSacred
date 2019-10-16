package theSacred.orbs.interfaces;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

public interface DamageAndBlockModifyOrb {
    default float modifyBlock(float blockAmount) { return blockAmount;}
    default float atDamageReceive(float damage, DamageInfo.DamageType type, boolean isPlayer) {return damage;}
    default float atDamageGive(float damage, DamageInfo.DamageType type) { return damage; }
    default void onUseCard(AbstractCard card, UseCardAction action) {}
}
