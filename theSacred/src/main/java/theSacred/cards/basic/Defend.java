package theSacred.cards.basic;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.util.UC;

import static basemod.helpers.BaseModCardTags.BASIC_DEFEND;
import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.channelYY;

public class Defend extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Defend",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public Defend() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        tags.add(BASIC_DEFEND);
        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(Settings.isDebug) {
            channelYY();
        }
        UC.doDef(block);
    }
}