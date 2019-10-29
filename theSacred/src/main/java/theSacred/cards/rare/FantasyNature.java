package theSacred.cards.rare;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import theSacred.actions.utility.DelayActionAction;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.LimitPower;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class FantasyNature extends SacredCard implements AlignedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FantasyNature",
            2,
            CardType.POWER,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 1;
    private static final int UPG_COST = 1;

    public FantasyNature() {
        super(cardInfo, false);

        setMagic(MAGIC);
        setCostUpgrade(UPG_COST);
        tags.add(BaseModCardTags.FORM);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(p, new IntangiblePlayerPower(p, magicNumber));
        atb(new DelayActionAction(new ApplyPowerAction(p, p, new LimitPower(), 0, true)));
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        doPow(p(), new IntangiblePlayerPower(p(), magicNumber));
    }
}