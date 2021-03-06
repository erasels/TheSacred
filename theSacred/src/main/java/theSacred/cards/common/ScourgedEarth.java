package theSacred.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.InvokeCard;
import theSacred.powers.turn.ScourgedEarthPower;
import theSacred.util.CardInfo;
import theSacred.vfx.combat.BetterScreenOnFireEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class ScourgedEarth extends InvokeCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ScourgedEarth",
            0,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 6;
    private static final int MAGIC = 9;
    private static final int UPG_MAGIC = 3;

    public ScourgedEarth() {
        super(cardInfo, false);

        setInvoke(0, 0);
        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        setMultiDamage(false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doVfx(new BetterScreenOnFireEffect(0.5f*getInvokeAmt(), 0.5f*getInvokeAmt(), "ATTACK_FLAME_BARRIER"));
        doAllDmg(this, AbstractGameAction.AttackEffect.FIRE, false);
        if(getInvokeAmt() > 0) {
            doPow(p, new ScourgedEarthPower(getInvokeAmt(), magicNumber));
        }
    }
}