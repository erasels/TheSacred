package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theSacred.actions.common.TransformCardInHandAcion;
import theSacred.cards.abstracts.SacredCard;
import theSacred.cards.special.Rot;
import theSacred.util.CardInfo;
import theSacred.util.UC;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class RottenFood extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RottenFood",
            1,
            AbstractCard.CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 3;

    public RottenFood() {
        super(cardInfo, false);
        p(); //Stupid intellij stuff , 

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
        cardsToPreview = new Rot();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDmg(m, this);
        doPow(m, new PoisonPower(m, p, magicNumber));
    }

    @Override
    public void triggerOnBeforeEndOfTurnForPlayingCard() {
        int pos = UC.hand().group.indexOf(this);
        if(pos > -1) {
            TransformCardInHandAcion act;
            if(pos > 0) {
                act = new TransformCardInHandAcion(UC.hand().group.get(pos -1), cardsToPreview.makeStatEquivalentCopy());
                act.update();
            }
            if(pos < UC.hand().size() - 1) {
                act = new TransformCardInHandAcion(UC.hand().group.get(pos +1), cardsToPreview.makeStatEquivalentCopy());
                act.update();
            }
        }
    }
}