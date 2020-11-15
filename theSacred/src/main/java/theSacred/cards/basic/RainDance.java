package theSacred.cards.basic;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.OnTurnChangeCard;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.util.UC;
import theSacred.vfx.general.RunAnimationEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.p;

public class RainDance extends SacredCard implements OnTurnChangeCard, StartupCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RainDance",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 4;

    private static final int MAGIC = 6;

    private boolean modifiedShuffle = false;
    public RainDance() {
        this(false);
    }
    public RainDance(boolean recursionPrevention) {
        super(cardInfo, false);
        p(); //Stupid intellij stuff a, e, 

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
        if(!recursionPrevention) {
            cardsToPreview = new RainDance(true);
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(isEvenTurn()) {
            UC.doAnim(RunAnimationEffect.ANIS.RODSLASH);
            UC.doDmg(m, this, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(!isEvenTurn()) {
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[2];
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void onTurnChange(int newTurn) {
        int i = newTurn % 2;
        rawDescription = cardStrings.EXTENDED_DESCRIPTION[i];
        initializeDescription();
        setTopText(cardStrings.EXTENDED_DESCRIPTION[3 + i]);
        //Odd turn
        if(i == 1) {
            selfRetain = true;
            shuffleBackIntoDrawPile = modifiedShuffle;
        } else {//even turn
            //will overwrite selfRetain that is applied mid combat
            selfRetain = false;
            shuffleBackIntoDrawPile = true;
        }
        if(cardsToPreview != null)
            ((RainDance)cardsToPreview).onTurnChange(newTurn + 1);
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        if(!isEvenTurn()) {
            UC.atb(new AbstractGameAction() {
                @Override
                public void update() {
                    RainDance.this.baseDamage += magicNumber;
                    RainDance.this.isDamageModified = true;
                    isDone = true;
                }
            });
        }
    }

    private boolean isEvenTurn() {
        return GameActionManager.turn%2 == 0;
    }

    @Override
    public boolean atBattleStartPreDraw() {
        //To check if this modifier has been added to the card
        modifiedShuffle = shuffleBackIntoDrawPile;
        onTurnChange(1);
        //Return false prevents it from being played
        return false;
    }
}