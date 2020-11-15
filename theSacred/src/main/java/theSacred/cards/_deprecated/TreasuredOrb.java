package theSacred.cards._deprecated;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theSacred.actions.common.OrbCheckAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.orbs.YinYangOrb;
import theSacred.util.CardInfo;
import theSacred.vfx.general.ButtonConfirmedEffect;
import theSacred.vfx.general.RunAnimationEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class TreasuredOrb extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TreasuredOrb",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DMG = 9;

    public TreasuredOrb() {
        super(cardInfo, true);
        setDamage(DMG);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doAnim(RunAnimationEffect.ANIS.SPELLA);
        doVfx(new ButtonConfirmedEffect(m.hb.cX, m.hb.cY, Color.SALMON, 2f, 3f));
        //doVfx(new FlashImageEffect(TextureLoader.getTexture(makeOrbPath("YinYangOrb.png")), m.hb.cX, m.hb.cY, Color.WHITE, 1f, 1.5f));
        doDmg(m, damage, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        channelYY();
        if(upgraded) {
            atb(new OrbCheckAction(o -> o instanceof YinYangOrb, AbstractOrb::onStartOfTurn));
        }
    }

}