package theSacred.cards._deprecated;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.turn.barriers.AnachronicBarrierPower;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class AnachronicBarrier extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "AnachronicBarrier",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 10;

    public AnachronicBarrier() {
        super(cardInfo, true);

        setBlock(BLOCK);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(p, new AnachronicBarrierPower(0));
        if(upgraded) {
            doPow(p, new NextTurnBlockPower(p, block));
        }
    }
}