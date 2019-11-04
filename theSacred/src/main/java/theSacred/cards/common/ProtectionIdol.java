package theSacred.cards.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.doDef;

public class ProtectionIdol extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ProtectionIdol",
            0,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 12;

    public ProtectionIdol() {
        super(cardInfo, true);

        setInvoke(0, 0);
        setBlock(BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(costForTurn>0) {
            //TODO: Add Stone Idol Vfx
            doDef(block * getInvokeAmt());
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            invokeMinCost = 1;
        }
        super.upgrade();
    }
}