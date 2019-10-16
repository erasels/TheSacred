package theSacred.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.orbs.YinYangOrb;
import theSacred.util.UC;

public class ActivateAlignEffectAction extends AbstractGameAction {
    private AlignedCard c;

    public ActivateAlignEffectAction(AlignedCard c, AbstractCreature target) {
        this.c = c;
        this.target = target;
    }

    @Override
    public void update() {
        for(AbstractOrb o : UC.p().orbs) {
            if(o instanceof YinYangOrb) {
                c.alignEffect(target);
            }
        }
        isDone = true;
    }
}
