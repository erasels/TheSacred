package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.common.CallbackDrawAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.vfx.general.RunAnimationEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class AscensionKick extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "AscensionKick",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 4;

    private static final int MAGIC = 1;

    public AscensionKick() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doAnim(RunAnimationEffect.ANIS.BACKFLIP);
        doDmg(m, damage, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new CallbackDrawAction(magicNumber, c -> {
            if(c.type == CardType.ATTACK) {
                c.setCostForTurn(0);
            }
        }));
    }
}