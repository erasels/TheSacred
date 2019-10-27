package theSacred.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.SlaveSealsPower;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class SlaveSeals extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SlaveSeals",
            3,
            CardType.POWER,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int UPG_COST = 2;

    public SlaveSeals() {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(p, new SlaveSealsPower(1));
    }
}