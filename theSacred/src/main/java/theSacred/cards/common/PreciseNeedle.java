package theSacred.cards.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class PreciseNeedle extends SacredCard implements AlignedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PreciseNeedle",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 1;

    public PreciseNeedle() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDmg(m, damage);
        doPow(m, new VulnerablePower(m, magicNumber, false));
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        if(target != null) {
            doPow(target, new VulnerablePower(target, magicNumber, false));
        }
    }
}