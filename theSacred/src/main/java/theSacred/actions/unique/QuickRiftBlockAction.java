package theSacred.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theSacred.cards._deprecated.QuickRift;
import theSacred.util.UC;

public class QuickRiftBlockAction extends AbstractGameAction {
    private QuickRift c;
    public QuickRiftBlockAction(QuickRift c) {
        this.c = c;
    }

    @Override
    public void update() {
        c.baseBlock = c.blockHack;
        c.applyPowersToBlock();

        UC.doDef(c.block, true);
        c.baseBlock = 0;
        c.blockHack = 0;
        isDone = true;
    }
}
