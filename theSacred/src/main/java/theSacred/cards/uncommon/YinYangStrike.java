package theSacred.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.actions.unique.YinYangStrikeAction;
import theSacred.cards.abstracts.SacredCard;
import theSacred.orbs.YinYangOrb;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class YinYangStrike extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "YinYangStrike",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public YinYangStrike() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDmg(m, damage);
        atb(new YinYangStrikeAction(magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if(AbstractDungeon.player != null && AbstractDungeon.player.orbs.stream().filter(o -> o instanceof YinYangOrb).count()<magicNumber) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        }
    }
}