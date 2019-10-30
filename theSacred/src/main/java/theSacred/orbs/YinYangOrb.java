package theSacred.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import org.apache.commons.lang3.math.NumberUtils;
import theSacred.TheSacred;
import theSacred.orbs.interfaces.DamageAndBlockModifyOrb;
import theSacred.orbs.interfaces.OnHPLossOrb;
import theSacred.util.TextureLoader;
import theSacred.util.UC;
import theSacred.vfx.general.ButtonConfirmedEffect;

import static theSacred.TheSacred.makeOrbPath;

public class YinYangOrb extends AbstractOrb implements DamageAndBlockModifyOrb, OnHPLossOrb {
    // Standard ID/Description
    public static final String ORB_ID = TheSacred.makeID("YinYangOrb");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;

    private static final float BLK_MOD = 1.25f;
    private static final float DMG_DEAL_MOD = 1.25f;
    private static final float DMG_TAKE_MOD = 1.25f;

    protected static final float NUM_X_OFFSET = 42.0F * Settings.scale;
    protected static final float NUM_Y_OFFSET = -30.0F * Settings.scale;

    private static final Texture IMG = TextureLoader.getTexture(makeOrbPath("YinYangOrb.png"));
    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;

    public YinYangOrb() {
        ID = ORB_ID;
        name = orbString.NAME;
        img = IMG;

        evokeAmount = baseEvokeAmount = 0;
        passiveAmount = basePassiveAmount = 0;

        updateDescription();

        angle = MathUtils.random(360.0f); // More Animation-related Numbers
        channelAnimTimer = 0.5f;
    }

    public YinYangOrb(int passive) {
        super();
        passiveAmount = basePassiveAmount = passive;
    }

    @Override
    public void updateDescription() {
        applyFocus();
        description = DESC[0] + UC.getPercentageInc(DMG_TAKE_MOD) + DESC[1] + UC.getPercentageInc(BLK_MOD) + DESC[2] + DESC[3];
    }

    @Override
    public void applyFocus() {
        AbstractPower power = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if (power != null) {
            evokeAmount = baseEvokeAmount * power.amount;
        } else {
            evokeAmount = baseEvokeAmount;
        }
        passiveAmount = basePassiveAmount;
    }

    @Override
    public void onEvoke() {
        UC.doAllDmg(evokeAmount, AbstractGameAction.AttackEffect.FIRE, DamageInfo.DamageType.THORNS, false);
        AbstractDungeon.actionManager.addToBottom(new SFXAction("TINGSHA"));
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount * BLK_MOD;
    }

    @Override
    public float atPlayerDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * DMG_TAKE_MOD;
        }
        return damage;
    }

    @Override
    public float atPlayerDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * DMG_DEAL_MOD;
        }
        return damage;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        basePassiveAmount += card.costForTurn;
        int tmp = NumberUtils.max(card.damage, card.block);
        if(card.type == AbstractCard.CardType.POWER) {
            tmp = 25;
        }
        if(tmp > 10 && MathUtils.randomBoolean((tmp/100f)*1.5f)) {
            AbstractDungeon.effectsQueue.add(new ButtonConfirmedEffect(cX, cY, Color.SKY, 0.75f, (tmp/100f)*5f));
        }
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {

    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(cX, cY));
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, c.a));
        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale, scale, angle, 0, 0, 96, 96, false, false);
        renderText(sb);
        hb.render(sb);
    }

    @Override
    protected void renderText(SpriteBatch sb) {
        if (this.showEvokeValue) {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
        } else if (passiveAmount > 0) {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
        }
    }


    @Override
    public void triggerEvokeAnimation() {
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(cX, cY));
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("TINGSHA", 1.5f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new YinYangOrb(basePassiveAmount);
    }
}
