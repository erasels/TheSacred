package theSacred.cards._deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class PersuasionNeedles extends SacredCard implements AlignedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PersuasionNeedles",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 1;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public PersuasionNeedles() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO:Add Vfx
        for (int i = 0; i < magicNumber ; i++) {
            doDmg(m, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE, true);
        }
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        atb(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(), 1));
    }

    @Override
    public float getTitleFontSize() {
        return 18f;
    }
}