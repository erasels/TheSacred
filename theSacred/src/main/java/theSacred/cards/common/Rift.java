package theSacred.cards.common;

import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.vfx.combat.unique.PlayerFadeEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class Rift extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Rift",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = -3;

    private static final int MAGIC = 1;

    public Rift() {
        super(cardInfo, true);
        p(); //Stupid intellij stuff s, s

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doVfx(new PlayerFadeEffect(1.5f));
        doDef(this);
        if(upgraded) {
            doDef(this);
        }
        atb(new PutOnDeckAction(p, p, magicNumber, false));
    }
}