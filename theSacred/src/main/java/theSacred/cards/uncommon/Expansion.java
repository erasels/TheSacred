package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.common.DealDamageEqualToBlockAction;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class Expansion extends SacredCard implements AlignedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Expansion",
            1,
            CardType.SKILL,
            CardTarget.ALL_ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;


    public Expansion() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDef(block);
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        atb(new DealDamageEqualToBlockAction(null, DamageInfo.DamageType.THORNS, block>10? AbstractGameAction.AttackEffect.BLUNT_HEAVY: AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}