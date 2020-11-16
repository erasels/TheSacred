package theSacred.cards.basic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.common.BetterDiscardPileToDeckAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.util.UC;
import theSacred.vfx.general.RunAnimationEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class Twirl extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Twirl",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 1;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Twirl() {
        super(cardInfo, true);
        p(); //Stupid intellij stuff , , 

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doAnim(RunAnimationEffect.ANIS.GRAB);
        doDmg(m, this, AbstractGameAction.AttackEffect.BLUNT_LIGHT);

        atb(new BetterDiscardPileToDeckAction(magicNumber, BetterDiscardPileToDeckAction.Spot.BOTTOM));
    }
}