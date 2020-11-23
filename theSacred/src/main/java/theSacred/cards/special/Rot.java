package theSacred.cards.special;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class Rot extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Rot",
            0,
            CardType.SKILL,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 4;
    private static final int DRAW = 1;

    public Rot() {
        super(CardColor.COLORLESS, cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, false);
        p(); //Stupid intellij stuff s, 

        setMagic(MAGIC);
        baseMagicNumber2 = magicNumber2 = DRAW;
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(m, new PoisonPower(m, p, magicNumber));
        doDraw(magicNumber2);
    }
}