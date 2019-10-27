package theSacred.cards.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class HomingAmulet extends SacredCard implements AlignedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "HomingAmulet",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;


    public HomingAmulet() {
        super(cardInfo, false);
        p(); //Stupid intellij stuff , 

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Kinda like Bouncing flask?
        doDmg(m, damage);
        AbstractMonster secondaryTarget = AbstractDungeon.getRandomMonster(m);
        doDmg(secondaryTarget, damage);
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        this.use(p(), (AbstractMonster)target);
    }
}