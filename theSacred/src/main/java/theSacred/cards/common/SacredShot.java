package theSacred.cards.common;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.AlignedCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.orbs.YinYangOrb;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class SacredShot extends SacredCard implements AlignedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SacredShot",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 9;
    private static final int EXH_AMT = 2;

    public SacredShot() {
        super(cardInfo, true);
        p(); //Stupid intellij stuff , 

        setDamage(DAMAGE);
        setExhaust(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ChannelAction(new YinYangOrb()));
    }

    @Override
    public void upgrade() {
        super.upgrade();

        if(!upgraded) {
            ExhaustiveVariable.setBaseValue(this, EXH_AMT);
        }
    }

    @Override
    public void alignEffect(AbstractCreature target) {
        //TODO: Add Orb smack Vfx
        doDmg(target, damage);
    }
}