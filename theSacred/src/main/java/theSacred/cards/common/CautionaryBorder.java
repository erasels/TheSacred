package theSacred.cards.common;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.utility.DoActionIfMonsterDeadAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.powers.GracePower;
import theSacred.util.CardInfo;
import theSacred.util.UC;
import theSacred.vfx.general.RunAnimationEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class CautionaryBorder extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "CautionaryBorder",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 1;

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;

    public CautionaryBorder() {
        super(cardInfo, false);
        p(); //Stupid intellij stuff , all

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setRetain(false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doAnim(RunAnimationEffect.ANIS.GUARDB);
        doDef(this);
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        doPow(new GracePower(p(), magicNumber));
    }
}