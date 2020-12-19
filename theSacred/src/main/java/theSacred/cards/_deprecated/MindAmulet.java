package theSacred.cards._deprecated;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class MindAmulet extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MindAmulet",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 12;
    private static final int MAGIC = 2;

    public MindAmulet() {
        super(cardInfo, true);
        p(); //Stupid intellij stuff , 

        setDamage(DAMAGE);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Amulet then Shockwave effect on monster?
        doDmg(m, damage);
        doPow(m, new WeakPower(m, magicNumber, false));
        if(upgraded) {
            doPow(m, new VulnerablePower(m, magicNumber, false));
        }
    }
}