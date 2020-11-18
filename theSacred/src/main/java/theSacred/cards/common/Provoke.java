package theSacred.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.utility.DoActionIfCreatureCheckAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.util.UC;
import theSacred.vfx.general.RunAnimationEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.p;

public class Provoke extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Provoke",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 6;
    private static final int MAGIC = 2;

    public Provoke() {
        super(cardInfo, true);
        p(); //Stupid intellij stuff ,

        setDamage(DAMAGE);
        setMagic(MAGIC);
        cardsToPreview = new Insight();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doAnim(RunAnimationEffect.ANIS.RODSTAB);
        UC.doDmg(m, this, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        UC.atb(new DoActionIfCreatureCheckAction(m, UC::isAttacking, new MakeTempCardInDiscardAction(cardsToPreview, 2)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            cardsToPreview.upgrade();
        }
        super.upgrade();
    }
}