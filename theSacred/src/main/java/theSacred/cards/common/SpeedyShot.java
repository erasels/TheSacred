package theSacred.cards.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.doDmg;
import static theSacred.util.UC.doDraw;

public class SpeedyShot extends SacredCard implements AlignedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SpeedyShot",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 5;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public SpeedyShot() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Add danmaku vfx
        doDmg(m, damage);
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        doDraw(magicNumber);
    }
}