package theSacred.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.patches.cards.MakeTempCardInDrawCallbackPatches;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class InfiniteNeedles extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "InfiniteNeedles",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 2;

    private static final int EXH_AMT = 3;
    private static final int MAGIC = 1;

    public InfiniteNeedles() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
        ExhaustiveVariable.setBaseValue(this, EXH_AMT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Simple needle vfx should be alright
        doDmg(m, damage);
        doDraw(magicNumber);
        if(ExhaustiveField.ExhaustiveFields.exhaustive.get(this) > 1) {
            atb(MakeTempCardInDrawCallbackPatches.getAction(new MakeTempCardInDrawPileAction(this, 1, true, true),
                    c -> {
                        ExhaustiveField.ExhaustiveFields.exhaustive.set(c, ExhaustiveField.ExhaustiveFields.exhaustive.get(this));
                    }));
        }
    }
}