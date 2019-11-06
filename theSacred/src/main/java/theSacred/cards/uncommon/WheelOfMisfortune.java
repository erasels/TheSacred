package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.WheelOfMisfortunePower;
import theSacred.util.CardInfo;
import theSacred.vfx.general.RunAnimationEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class WheelOfMisfortune extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "WheelOfMisfortune",
            0,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);


    public WheelOfMisfortune() {
        super(cardInfo, true);

        setExhaust(true);
        setInvoke(0, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(getInvokeAmt() > 0) {
            doPow(p, new WheelOfMisfortunePower(getInvokeAmt()));
        } else {
            doVfx(new RunAnimationEffect(RunAnimationEffect.ANIS.FAILURE));
        }
    }
}