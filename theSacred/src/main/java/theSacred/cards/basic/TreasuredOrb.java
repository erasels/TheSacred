package theSacred.cards.basic;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.orbs.YinYangOrb;
import theSacred.util.CardInfo;
import theSacred.util.UC;

import static theSacred.TheSacred.makeID;

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
        UC.atb(new ChannelAction(new YinYangOrb()));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if(!upgraded) {
            isEthereal = false;
        }
    }
}