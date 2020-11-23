package theSacred.cards.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.common.DoActionForAllCardsInHandAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.util.UC;
import theSacred.vfx.VfxBuilderRepository;
import theSacred.vfx.general.RunAnimationEffect;

import java.util.ArrayList;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.atb;

public class LashOut extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "LashOut",
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 3;

    public LashOut() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setRetain(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractMonster> mons = UC.getAliveMonsters();
        UC.atb(new DoActionForAllCardsInHandAction(0, c-> {
            UC.doVfx(VfxBuilderRepository.curvedDanmakuShot(p.hb, UC.getRandomItem(mons).hb, Color.FIREBRICK));
            atb(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.NONE));
        }));
        UC.doAnim(RunAnimationEffect.ANIS.SPELLA);
        UC.atb(new DiscardAction(p, p, p.hand.size(), false));
    }
}