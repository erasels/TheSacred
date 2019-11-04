package theSacred.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.TrifectaPower;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class Trifecta extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Trifecta",
            0,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public Trifecta() {
        super(cardInfo, true);

        setInvoke(true);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: 3 Planets rotation?
        doPow(p, new TrifectaPower(costForTurn+magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            invokeNonZero = false;
        }
        super.upgrade();
    }
}