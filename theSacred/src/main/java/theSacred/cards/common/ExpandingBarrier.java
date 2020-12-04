package theSacred.cards.common;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ChokePower;
import theSacred.cards.abstracts.FieldCard;
import theSacred.util.CardInfo;
import theSacred.util.UC;
import theSacred.vfx.combat.BetterWrathParticleEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.doPow;
import static theSacred.util.UC.p;

public class ExpandingBarrier extends FieldCard {
    private float particleTimer = 0;

    private final static CardInfo cardInfo = new CardInfo(
            "ExpandingBarrier",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public ExpandingBarrier() {
        super(cardInfo, false);
        p(); //Stupid intellij stuff s, s

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        return new ArrayList<>(Arrays.asList(new TooltipInfo(cardStrings.EXTENDED_DESCRIPTION[0], cardStrings.EXTENDED_DESCRIPTION[1])));
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(target != p() && info.owner == p()) {
            doPow(target, new ChokePower(target, magicNumber));
        }
    }

    @Override
    public void updateParticles() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new BetterWrathParticleEffect(hb));
            }
        }
    }

    @Override
    public Color getBarrierColor() {
        return Color.ORANGE;
    }
}