package theSacred.cards.basic;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theSacred.actions.utility.BeginSpeedModeAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.mechanics.speed.ButtonGenerators.BasicButtonGenerator;
import theSacred.mechanics.speed.SpeedClickButtonTime;
import theSacred.util.CardInfo;
import theSacred.util.UC;

import static basemod.helpers.BaseModCardTags.BASIC_STRIKE;
import static theSacred.TheSacred.makeID;

public class Strike extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Strike",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public Strike() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);

        tags.add(BASIC_STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!Settings.isDebug) {
            UC.doDmg(m, this.damage, MathUtils.randomBoolean() ? AbstractGameAction.AttackEffect.SLASH_VERTICAL : AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        } else {
            //UC.atb(new BeginSpeedModeAction(new SpeedClickEnemyTime(3.0f, mon -> UC.doDmg(mon, damage, DamageInfo.DamageType.NORMAL, UC.getSpeedyAttackEffect(), true))));
            Runnable myRunnable = () -> {
                System.out.println("Ran");
                UC.doVfx(new CleaveEffect());
                UC.doAllDmg(damage, AbstractGameAction.AttackEffect.NONE, false);
            };
            UC.atb(new BeginSpeedModeAction(new SpeedClickButtonTime(10.0f, myRunnable, new BasicButtonGenerator(1f, true))));
            //UC.atb(new BeginSpeedModeAction(new SpeedHoverZoneTime(10.0f, myRunnable, true, 10)));
        }
    }
}