package theSacred.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class SealSpread extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SealSpread",
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 5;
    private static final int MAGIC = 4;
    private static final int MAGIC2 = 2;

    public SealSpread() {
        super(cardInfo, true);

        setDamage(DAMAGE);
        setMagic(MAGIC);
        setMN2(MAGIC2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Danmaku everywhere effect
        for (int i = 0; i < magicNumber ; i++) {
            atb(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
        }

        if(!upgraded) {
            doPow(p, new VulnerablePower(p, magicNumber2, false));
        }
    }
}