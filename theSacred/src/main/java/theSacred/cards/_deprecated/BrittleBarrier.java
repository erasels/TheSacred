package theSacred.cards._deprecated;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.turn.barriers.BrittleBarrierPower;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.doDef;
import static theSacred.util.UC.doPow;

public class BrittleBarrier extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BrittleBarrier",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;

    private static final int MAGIC = 2;

    public BrittleBarrier() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDef(block);
        doPow(p, new BrittleBarrierPower(MAGIC));
    }
}