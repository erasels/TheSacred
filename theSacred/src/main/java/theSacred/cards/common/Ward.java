package theSacred.cards.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.util.UC;
import theSacred.vfx.combat.BubbleEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.atb;
import static theSacred.util.UC.doPow;

public class Ward extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Ward",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;

    public Ward() {
        super(cardInfo, true);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Color col = Color.SKY.cpy();
        col.a = 0.5f;
        UC.doVfx(new BubbleEffect(col, "TINGSHA", (((AbstractDungeon.player.hb.cY) + (64f* Settings.scale)))));
        atb(new ScryAction(magicNumber));
        doPow(p, new EnergizedPower(p, upgraded?2:1));
    }
}