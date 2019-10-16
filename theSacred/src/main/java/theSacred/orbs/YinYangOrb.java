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
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;
import theSacred.TheSacred;
import theSacred.orbs.interfaces.DamageAndBlockModifyOrb;
import theSacred.util.TextureLoader;
import theSacred.util.UC;

import static theSacred.TheSacred.makeOrbPath;

public class YinYangOrb extends AbstractOrb implements DamageAndBlockModifyOrb {
    // Standard ID/Description
    public static final String ORB_ID = TheSacred.makeID("YinYangOrb");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;

    private static final float BLK_MOD = 1.25f;
    private static final float DMG_DEAL_MOD = 1.25f;
    private static final float DMG_TAKE_MOD = 1.5f;

    private static final Texture IMG = TextureLoader.getTexture(makeOrbPath("default_orb.png"));
    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.1f;
    private static final float PI_4 = 12.566371f;

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

    @Override
    public void updateDescription() {
        applyFocus();
        description = DESC[0] + convert(DMG_TAKE_MOD) + DESC[1] + convert(BLK_MOD) + DESC[2];
    }

    @Override
    public void applyFocus() {
        AbstractPower power = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if(power != null) {
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
        return blockAmount*BLK_MOD;
    }

    @Override
    public float atPlayerDamageReceive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL) {
            return damage*DMG_TAKE_MOD;
        }
        return damage;
    }

    @Override
    public float atPlayerDamageGive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL) {
            return damage*DMG_DEAL_MOD;
        }
        return damage;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        basePassiveAmount += card.costForTurn;
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(cX, cY));
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    // Render the orb.
    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, c.a / 2.0f));
        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, scale, angle, 0, 0, 96, 96, false, false);
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
        sb.setBlendFunction(770, 1);
        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, -angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        renderText(sb);
        hb.render(sb);
    }


    @Override
    public void triggerEvokeAnimation() { // The evoke animation of this orb is the dark-orb activation effect.
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(cX, cY));
    }

    @Override
    public void playChannelSFX() { // When you channel this orb, the ATTACK_FIRE effect plays ("Fwoom").
        CardCrawlGame.sound.play("TINGSHA", 0.5f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new YinYangOrb();
    }

    private int convert(float val) {
        return MathUtils.floor((val-1f)*100f);
    }
}
