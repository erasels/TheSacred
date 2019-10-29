package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.common.BetterDiscardPileToTopOfDeckAction;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class AscensionKick extends SacredCard implements AlignedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "AscensionKick",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    private static final int MAGIC2 = 1;

    public AscensionKick() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        setMN2(MAGIC2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDmg(m, damage, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new BetterDiscardPileToTopOfDeckAction(magicNumber));
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        doDraw(magicNumber2);
    }
}