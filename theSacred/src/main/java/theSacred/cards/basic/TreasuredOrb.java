package theSacred.cards.basic;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.channelYY;

public class TreasuredOrb extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TreasuredOrb",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);


    public TreasuredOrb() {
        super(cardInfo, true);
        setExhaust(true);
        setInnate(true, true);
        setRetain(true);
        setEthereal(false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        channelYY();
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            isEthereal = false;
        }
        super.upgrade();
    }
}