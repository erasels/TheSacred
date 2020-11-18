package theSacred.cards.common;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.utility.DoActionIfMonsterDeadAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.util.UC;
import theSacred.vfx.general.RunAnimationEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.p;

public class Punishment extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Punishment",
            1,
            CardType.ATTACK,
            CardTarget.ALL);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC = 2;

    public Punishment() {
        super(cardInfo, false);
        p(); //Stupid intellij stuff , all

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
        GraveField.grave.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doAnim(RunAnimationEffect.ANIS.RODSLASH);
        UC.doAllDmg(this, AbstractGameAction.AttackEffect.SLASH_HEAVY, false);
        for(AbstractMonster mon : UC.getAliveMonsters()) {
            UC.atb(new DoActionIfMonsterDeadAction(mon, new GainEnergyAction(magicNumber)));
        }
    }
}