package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.ErrantFaithPower;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class ErrantFaith extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ErrantFaith",
            2,
            CardType.POWER,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 1;

    public ErrantFaith() {
        super(cardInfo, true);

        setMagic(MAGIC);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(p, new ErrantFaithPower(magicNumber));
    }
}