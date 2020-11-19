package theSacred.cards.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.AlchHelper;
import theSacred.util.CardInfo;
import theSacred.util.UC;
import theSacred.vfx.VfxBuilderRepository;
import theSacred.vfx.general.RunAnimationEffect;

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
        UC.doAnim(RunAnimationEffect.ANIS.GRAB);
        for (float angle = 85f ; angle < 95.5f; angle += 5f) {
            UC.doVfx(VfxBuilderRepository.ofudaShot(p.hb, AlchHelper.wrapAroundAngle(angle-90f), angle));
        }

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