package theSacred.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.InvokeCard;
import theSacred.powers.TrifectaPower;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.doPow;

public class Trifecta extends InvokeCard {
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

        setInvoke(0, 1, 1, INVOKE_MAX_COST);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: 3 Planets rotation?
        if(getInvokeAmt() > 0) {
            doPow(p, new TrifectaPower(getInvokeAmt()));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            invokeMinCost = 0;
        }
        super.upgrade();
    }
}