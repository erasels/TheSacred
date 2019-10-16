package theSacred.orbs.interfaces;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

public interface DamageAndBlockModifyOrb {
    default float modifyBlock(float blockAmount) { return blockAmount;}
    default float atPlayerDamageReceive(float damage, DamageInfo.DamageType type) {return damage;}
    default float atPlayerDamageGive(float damage, DamageInfo.DamageType type) { return damage; }
    default void onUseCard(AbstractCard card, UseCardAction action) {}
}
