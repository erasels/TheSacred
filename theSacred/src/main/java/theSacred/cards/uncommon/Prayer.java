package theSacred.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.common.ChooseCardsFromPilesAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.patches.cards.CardENUMs;
import theSacred.util.CardInfo;

import java.util.ArrayList;
import java.util.Arrays;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class Prayer extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Prayer",
            0,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int EXH_AMT = 2;

    public Prayer() {
        super(cardInfo, true);

        setMagic(MAGIC, UPG_MAGIC);
        ExhaustiveVariable.setBaseValue(this, EXH_AMT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ChooseCardsFromPilesAction((ArrayList<CardGroup>)Arrays.asList(p.drawPile, p.discardPile), c -> c.hasTag(CardENUMs.INVOKE), magicNumber));
    }
}