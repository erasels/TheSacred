package theSacred.cards._deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.utility.ForEachBarrierOnCreatureAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class TripUp extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TripUp",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 1;
    private static final int UPG_COST = 0;

    public TripUp() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDmg(m, damage, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        atb(new ForEachBarrierOnCreatureAction(p, pow -> pow.retaliate(m)));
    }
}