package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.common.DoActionForAllCardsInHandAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class Invocation extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Invocation",
            2,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 1;
    private static final int UPG_COST = 1;

    public Invocation() {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DoActionForAllCardsInHandAction(0, c -> {
            if(isInvoke(c)) {
                ((SacredCard)c).incrementInvokeForCombat(magicNumber);
            }
        }));
    }
}