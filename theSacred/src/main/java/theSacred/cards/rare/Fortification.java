package theSacred.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.FortificationPower;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class Fortification extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Fortification",
            2,
            CardType.POWER,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 1;

    public Fortification() {
        super(cardInfo, true);
        setMagic(MAGIC);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(p, new FortificationPower(magicNumber));
    }
}