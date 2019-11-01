package theSacred.cards.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.turn.barriers.UnstableBarrierPower;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class UnstableBarrier extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "UnstableBarrier",
            2,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 14;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 15;
    private static final int UPG_MAGIC = 3;

    public UnstableBarrier() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDef(block);
        doPow(p, new UnstableBarrierPower(magicNumber));
    }
}