package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class ExorcismDance extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ExorcismDance",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int MAGIC2 = 4;

    public ExorcismDance() {
        super(cardInfo, true);

        setBlock(BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setMN2(MAGIC2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber2; i++) {
            doDef(block);
        }

        for (int i = 0; i < magicNumber; i++) {
            atb(new MakeTempCardInHandAction(getRandomNeedle(), 1));
        }
    }
}