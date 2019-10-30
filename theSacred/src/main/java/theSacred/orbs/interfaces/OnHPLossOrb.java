package theSacred.orbs.interfaces;

import com.megacrit.cardcrawl.cards.DamageInfo;

public interface OnHPLossOrb {
    void wasHPLost(DamageInfo info, int damageAmount);
}
