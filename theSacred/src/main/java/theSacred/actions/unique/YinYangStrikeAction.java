package theSacred.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theSacred.orbs.YinYangOrb;
import theSacred.util.UC;

import static theSacred.util.UC.channelYY;

public class YinYangStrikeAction extends AbstractGameAction {

    public YinYangStrikeAction(int num) {
        this.target = UC.p();
        this.amount = num;
    }

    public void update() {
        int counter = 0;
        for(AbstractOrb o : ((AbstractPlayer)target).orbs) {
            if(o instanceof YinYangOrb) {
                counter++;
            }
        }
        if(counter<amount) {
            channelYY(true);
        }
        isDone = true;
    }
}
