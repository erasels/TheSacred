package theSacred.cards._deprecated;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class YinYangShield extends SacredCard implements AlignedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "YinYangShield",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 9;

    public YinYangShield() {
        super(cardInfo, true);

        setBlock(BLOCK);
        setRetain(true);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        channelYY();
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        doDef(block);
    }
}