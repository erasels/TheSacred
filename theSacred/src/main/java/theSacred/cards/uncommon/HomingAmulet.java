package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.cards.DamageInfo;
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

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 2;


    private static final int MAGIC = 7;
    private static final int UPG_MAGIC = 2;


    public HomingAmulet() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Kinda like Bouncing flask?
        doDmg(m, damage);
        AbstractMonster secondaryTarget = AbstractDungeon.getRandomMonster(m);
        if(secondaryTarget != m) {
            doDmg(secondaryTarget, magicNumber, DamageInfo.DamageType.THORNS);
        }
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        this.use(p(), (AbstractMonster)target);
    }
}