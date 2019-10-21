package theSacred.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.unique.ImperishableShootingAction;
import theSacred.actions.utility.UpgradeMagicNumberAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.util.UC;

import static theSacred.TheSacred.makeID;

public class SealErode extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SealErode",
            1,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardTarget.ALL_ENEMY
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 2;
    private static final int MAGIC = 2;
    private static final int HIT_UPG = 3;

    public SealErode() {
        super(cardInfo, true);

        baseMagicNumber2 = magicNumber2 = HIT_UPG;
        setDamage(DAMAGE);
        setMagic(MAGIC);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(UC.p(), damage, DamageInfo.DamageType.NORMAL);
        UC.atb(new ImperishableShootingAction(UC.p().hb.cX, UC.p().hb.cY, magicNumber, info));
        UC.atb(new UpgradeMagicNumberAction(this.uuid, magicNumber2));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(baseMagicNumber > MAGIC) {
            isMagicNumberModified = true;
        }
    }
}