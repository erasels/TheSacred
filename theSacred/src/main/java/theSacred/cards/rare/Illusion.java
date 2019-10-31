package theSacred.cards.rare;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.common.CallbackScryAction;
import theSacred.actions.unique.IllusionAction;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class Illusion extends SacredCard implements AlignedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Illusion",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 4;
    private static final int MAGIC = -1;
    private static final int UPG_MAGIC = 3;

    public Illusion() {
        super(cardInfo, true);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDmg(m, damage);
        if (!upgraded) {
            atb(new ScryAction(damage));
        } else {
            atb(new CallbackScryAction(damage, l -> this.baseMagicNumber2 = l.size()));
        }
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        if (upgraded) {
            atb(new IllusionAction(this, (AbstractMonster) target));
        }
    }

    /*@Override
    public void applyPowers() {
        magicNumber = baseMagicNumber;

        int tmp = baseDamage;
        baseDamage = baseMagicNumber;

        super.applyPowers();

        magicNumber = damage;
        baseDamage = tmp;

        super.applyPowers();

        isMagicNumberModified = (magicNumber != baseMagicNumber);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        magicNumber = baseMagicNumber;

        int tmp = baseDamage;
        baseDamage = baseMagicNumber;

        super.calculateCardDamage(mo);

        magicNumber = damage;
        baseDamage = tmp;

        super.calculateCardDamage(mo);

        isMagicNumberModified = (magicNumber != baseMagicNumber);
    }*/
}