package theSacred.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.turn.ScourgedEarthPower;
import theSacred.util.CardInfo;
import theSacred.vfx.combat.BetterScreenOnFireEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class ScourgedEarth extends SacredCard {
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

        setInvoke(false);
        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        setMultiDamage(false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doVfx(new BetterScreenOnFireEffect(0.5f*costForTurn, 0.5f*costForTurn, "ATTACK_FLAME_BARRIER"));
        doAllDmg(damage, AbstractGameAction.AttackEffect.FIRE, DamageInfo.DamageType.NORMAL, false);
        doPow(p, new ScourgedEarthPower(costForTurn, magicNumber));
    }
}