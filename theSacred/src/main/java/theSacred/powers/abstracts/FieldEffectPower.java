package theSacred.powers.abstracts;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theSacred.TheSacred;
import theSacred.mechanics.field.FieldSystem;
import theSacred.patches.combat.NewPowerTypePatches;
import theSacred.util.UC;

public class FieldEffectPower extends AbstractSacredPower implements InvisiblePower {
    public static final String POWER_ID = TheSacred.makeID("FieldEffectPower");

    public FieldEffectPower(AbstractCreature owner) {
        name = "";
        ID = POWER_ID;
        this.owner = owner;
        type = NewPowerTypePatches.NEUTRAL;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return FieldSystem.atDamageGive(damage, type);
    }

    @Override
    public void onRemove() {
        UC.doPow(owner, this, true);
    }
}