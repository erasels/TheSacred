package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.unique.ShuffleIntoDrawAndDrawAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class QuickwittedStrike extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "QuickwittedStrike",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;


    public QuickwittedStrike() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDmg(m, damage, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new ShuffleIntoDrawAndDrawAction());
    }
}