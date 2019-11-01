package theSacred.cards._deprecated;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.common.CallbackDrawAction;
import theSacred.actions.unique.QuickRiftBlockAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class QuickRift extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "QuickRift",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 2;
    private static final int MAGIC2 = 3;
    private static final int UPG_MAGIC = 1;

    public int blockHack = 0;

    public QuickRift() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
        setMN2(MAGIC2);
        setBlock(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blockHack = 0;
        atb(new CallbackDrawAction(magicNumber, c -> blockHack+=c.costForTurn*magicNumber2));
        atb(new QuickRiftBlockAction(this));
    }

    @Override
    public void applyPowersToBlock() {
        super.applyPowersToBlock();
    }
}