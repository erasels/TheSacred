package theSacred.cards.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class BlightBuster extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BlightBuster",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;

    private static final int MAGIC = 1;


    public BlightBuster() {
        super(cardInfo, false);

        setMagic(MAGIC);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Bunch of ofuda shotgun
        doDmg(m, damage);
        doPow(p, new EnergizedPower(p, magicNumber));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(checkRemnant()) {
           setCostForTurn(0);
        }
    }
}