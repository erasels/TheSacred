package theSacred.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.turn.DuplexBoundaryPower;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class DuplexBoundary extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "DuplexBoundary",
            2,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 2;

    public DuplexBoundary() {
        super(cardInfo, true);

        setMagic(MAGIC);
        setRetain(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(p, new DuplexBoundaryPower(magicNumber));
    }
}