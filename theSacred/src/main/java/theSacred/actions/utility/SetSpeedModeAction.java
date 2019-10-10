package theSacred.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theSacred.TheSacred;
import theSacred.mechanics.speed.AbstractSpeedTime;

public class SetSpeedModeAction extends AbstractGameAction {
    AbstractSpeedTime instance;

    public SetSpeedModeAction(AbstractSpeedTime instance) {
        this.instance = instance;
    }

    public void update() {
        TheSacred.speedScreen = instance;
        isDone = true;
    }
}
