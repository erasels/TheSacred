package theSacred.cards.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.turn.barriers.BrittleBarrierPower;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class BrittleBarrier extends SacredCard implements AlignedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BrittleBarrier",
            0,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 3;

    private static final int MAGIC = 1;

    public BrittleBarrier() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDef(block);
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        doPow(p(), new BrittleBarrierPower(magicNumber));
    }
}