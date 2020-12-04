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
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import theSacred.TheSacred;
import theSacred.actions.common.RemoveSpecificOrbAction;
import theSacred.actions.unique.TemporaryPowerApplicationAction;
import theSacred.orbs.interfaces.OnHPLossOrb;
import theSacred.orbs.interfaces.OnUseCardOrb;
import theSacred.util.TextureLoader;
import theSacred.util.UC;

import static theSacred.TheSacred.makeOrbPath;

public class YinYangOrb extends AbstractOrb implements OnUseCardOrb, OnHPLossOrb {
    // Standard ID/Description
    public static final String ORB_ID = TheSacred.makeID("YinYangOrb");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;

    private static final float BLK_MOD = 1.25f;
    private static final float DMG_DEAL_MOD = 1.25f;
    private static final float DMG_TAKE_MOD = 1.25f;
    private static final int CHARGE_INC = 1;
    private static final int BREAK_AMT = 10;
    private static final int INC_AMT = 1;

    protected static final float NUM_X_OFFSET = 42.0F * Settings.scale;
    protected static final float NUM_Y_OFFSET = -30.0F * Settings.scale;

    public static final Texture IMG = TextureLoader.getTexture(makeOrbPath("YinYangOrb.png"));
    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;

    public static int incAmt;

    public int breakAmount, baseBreakAmount;
    public State curState;

    public YinYangOrb() {
        ID = ORB_ID;
        name = orbString.NAME;
        img = IMG;

        evokeAmount = baseEvokeAmount = 0;
        passiveAmount = basePassiveAmount = 0;
        breakAmount = baseBreakAmount = BREAK_AMT;

        angle = MathUtils.random(360.0f); // More Animation-related Numbers
        channelAnimTimer = 0.5f;
        curState = State.YANG;

        updateDescription();
    }

    public YinYangOrb(int passive) {
        super();
        passiveAmount = basePassiveAmount = passive;
    }

    @Override
    public void updateDescription() {
        applyFocus();
        if(curState == State.YANG) {
            description = DESC[0] + DESC[2] + DESC[3] + DESC[5] + DESC[6] + breakAmount + DESC[7];
        } else if (curState == State.YIN) {
            description = DESC[1] +  DESC[2] + DESC[4] + DESC[5] + DESC[6] + breakAmount + DESC[7];
        }
    }

    @Override
    public void applyFocus() {
    }

    @Override
    public void onEvoke() {
        UC.doAllDmg(evokeAmount, AbstractGameAction.AttackEffect.FIRE, DamageInfo.DamageType.THORNS, false);
        AbstractDungeon.actionManager.addToBottom(new SFXAction("TINGSHA"));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(curState == State.YANG && card.type == AbstractCard.CardType.SKILL) {
            incAmt += INC_AMT;
            if(getLast() == this) {
                UC.atb(new TemporaryPowerApplicationAction(new StrengthPower(UC.p(), incAmt)));
                incAmt = 0;
            }
        } else if (curState == State.YIN && card.type == AbstractCard.CardType.ATTACK) {
            incAmt += INC_AMT;
            if(getLast() == this) {
                UC.atb(new TemporaryPowerApplicationAction(new DexterityPower(UC.p(), incAmt)));
                incAmt = 0;
            }
        }
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        breakAmount -= damageAmount;
        if(breakAmount < 0) {
            breakAmount = 0;
        }
        if(breakAmount == 0) {
            UC.att(new RemoveSpecificOrbAction(this));
        }
    }

    @Override
    public void onStartOfTurn() {
        passiveAmount += getCharge();

        if(curState == State.YIN) {
            curState = State.YANG;
        } else if (curState == State.YANG) {
            curState = State.YIN;
        }
        updateDescription();
    }

    public void onChannel() {
        //basePassiveAmount = (int)UC.p().orbs.stream().filter(o -> o instanceof YinYangOrb).count();
        //basePassiveAmount = CHARGE_INC;
        basePassiveAmount = 0;
        passiveAmount = basePassiveAmount;
    }

    private int getCharge() {
        int mod = 0;
        FocusPower p = (FocusPower) UC.p().getPower(FocusPower.POWER_ID);
        if(p != null) {
            mod += p.amount;
        }
        return CHARGE_INC+mod;
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f) {
            //AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(cX, cY));
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);

            if(curState == State.YIN) {
                for (int i = 0; i < 2; i++) {
                    AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(cX, cY, Color.LIGHT_GRAY));
                }
            } else if (curState == State.YANG) {
                for (int i = 0; i < 2; i++) {
                    AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(cX, cY, Color.RED));
                }
            }
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
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.breakAmount), this.cX - NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F - NUM_Y_OFFSET, new Color(1F, 0.4F, 0.4F, this.c.a), this.fontScale);
    }


    @Override
    public void triggerEvokeAnimation() {
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(cX, cY));
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("TINGSHA", 1.5f);
    }

    public YinYangOrb getLast() {
        for(int i = UC.p().orbs.size() - 1; i > -1; i--) {
            AbstractOrb o = UC.p().orbs.get(i);
            if(o instanceof YinYangOrb) {
                return (YinYangOrb) o;
            }
        }
        return null;
    }

    public YinYangOrb getFirst() {
        for(AbstractOrb o : UC.p().orbs) {
            if(o instanceof YinYangOrb) {
                return (YinYangOrb) o;
            }
        }
        return null;
    }

    @Override
    public AbstractOrb makeCopy() {
        return new YinYangOrb(basePassiveAmount);
    }

    public enum State {
        YIN, YANG
    }
}
