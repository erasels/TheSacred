package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.unique.TargetingTalismanAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.util.UC;

import java.util.ArrayList;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.atb;

public class TargetingTalisman extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TargetingTalisman",
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 3;


    public TargetingTalisman() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractMonster> aM = UC.getAliveMonsters();
        AbstractCreature t = null;
        for(AbstractMonster mon : aM) {
            if(t == null || t.currentHealth > mon.currentHealth) {
                t = mon;
            }
        }
        atb(new TargetingTalismanAction(t, new DamageInfo(t, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE, false));
    }
}