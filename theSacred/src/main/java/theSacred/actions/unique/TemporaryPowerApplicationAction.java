package theSacred.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSacred.patches.combat.BetterTemporaryPowerPatches;
import theSacred.util.UC;

public class TemporaryPowerApplicationAction extends AbstractGameAction {
    protected AbstractPower p;

    public TemporaryPowerApplicationAction(AbstractCreature target, AbstractPower pow) {
        this.target = target;
        p = pow;
        this.amount = pow.amount;
    }

    public void update() {
        UC.att(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCreature t = TemporaryPowerApplicationAction.this.target;
                int amt = TemporaryPowerApplicationAction.this.amount;
                AbstractPower pow = t.getPower(p.ID);
                if(pow != null) {
                    BetterTemporaryPowerPatches.Fields.temporaryAmount.set(pow, amt + BetterTemporaryPowerPatches.Fields.getTA(pow));
                    pow.updateDescription();
                }
                isDone = true;
            }
        });
        UC.doPow(target, p, true);
        isDone = true;
    }
}
