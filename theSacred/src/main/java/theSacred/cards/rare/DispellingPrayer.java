package theSacred.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theSacred.actions.utility.ClearAllDebuffsAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class DispellingPrayer extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "DispellingPrayer",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 2;
    private static final int MAGIC2 = 1;

    public DispellingPrayer() {
        super(cardInfo, true);

        magicNumber2 = baseMagicNumber2 = MAGIC2;
        setMagic(MAGIC);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ClearAllDebuffsAction(p, db -> {
            doPow(p, new StrengthPower(p, magicNumber));
            if(upgraded) {
                doPow(p, new DexterityPower(p, magicNumber2));
            }
        }));
    }
}