package theSacred.cards.special;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.p;

public class TestCard extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TestCard",
            0,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);


    public TestCard() {
        super(cardInfo, false);
        p(); //Stupid intellij stuff s, s
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.overlayMenu.showBlackScreen(0.5f);
    }
}