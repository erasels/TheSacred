package theSacred.cards.abstracts;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import theSacred.TheSacred;
import theSacred.characters.SacredCharacter;
import theSacred.mechanics.field.FieldSystem;
import theSacred.util.CardInfo;
import theSacred.util.TextureLoader;
import theSacred.util.UC;
import theSacred.vfx.combat.BarrierVfx;
import theSacred.vfx.general.RunAnimationEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class FieldCard extends SacredCard {
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(TheSacred.makeID("FieldCards"));
    private static final Texture DURATION_BG = TextureLoader.getTexture(TheSacred.makeImagePath("512/durationOrb.png"));
    private static final Texture DURATION_BIG_BG = TextureLoader.getTexture(TheSacred.makeImagePath("1024/durationOrbBig.png"));
    private static final Color DURATION_REDUCED_COLOR = Color.SKY;
    protected int duration, defaultDuration;

    public FieldCard(CardInfo cardInfo, boolean upgradesDescription, int defaultDuration) {
        this(SacredCharacter.Enums.COLOR_SACRED, cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription, defaultDuration);
    }

    public FieldCard(CardColor color, String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription, int defaultDuration) {
        super(color, cardName, cost, cardType, target, rarity, upgradesDescription);
        duration = this.defaultDuration = defaultDuration;
        //setTopText(uiStrings.TEXT[0], Color.SKY);
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        ArrayList<TooltipInfo> tps = (ArrayList<TooltipInfo>) super.getCustomTooltipsTop();
        if (!FieldSystem.fields.contains(this)) {
            tps.add(0, new TooltipInfo(TheSacred.fieldKeywords[0], TheSacred.fieldKeywords[1]));
        }
        return tps;
    }

    @Override
    public List<String> getCardDescriptors() {
        return Collections.singletonList(uiStrings.TEXT[0]);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        UC.doAnim(RunAnimationEffect.ANIS.SPELLA);
        UC.doVfx(new BarrierVfx(abstractPlayer.hb, getBarrierColor()));
        //Adds itself to the field
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                FieldSystem.acceptCard(FieldCard.this);
                isDone = true;
            }
        });
    }

    public Color getBarrierColor() {
        return Color.WHITE;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (CardCrawlGame.isInARun()) {
            if (FieldSystem.fields.contains(this)) {
                glowColor = GOLD_BORDER_GLOW_COLOR;
            } else {
                glowColor = BLUE_BORDER_GLOW_COLOR;
            }
        }
    }

    @SpireOverride
    protected void renderEnergy(SpriteBatch sb) {
        SpireSuper.call(sb);
        renderDuration(sb);
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        resetDuration();
    }

    protected void renderDuration(SpriteBatch sb) {
        float drawX = current_x - 256.0F;
        float drawY = current_y - 256.0F;

        if (!isLocked && isSeen) {
            sb.draw(DURATION_BG, drawX, drawY, 256.0F, 256.0F, 512.0F, 512.0F, drawScale * Settings.scale, drawScale * Settings.scale, angle, 0, 0, 512, 512, false, false);

            String msg = Integer.toString(duration);
            Color costColor = Color.WHITE.cpy();
            if (duration != defaultDuration) {
                costColor = DURATION_REDUCED_COLOR;
            }
            costColor.a = transparency;

            FontHelper.cardEnergyFont_L.getData().setScale(drawScale * 0.75f);
            FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, msg, current_x, current_y, -132.0F * drawScale * Settings.scale, 129.0F * drawScale * Settings.scale, angle, true, costColor);
        }
    }

    public void decrementDuration() {
        duration--;
    }

    public void resetDuration() {
        duration = defaultDuration;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isHovered() {
        return hb.hovered;
    }

    public void updateParticles() { }

    public void onLeaveField() { }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage;
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) { }

    public void atEndOfTurn() { }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCost")
    private static class ShowDurationOnSCP {
        @SpirePostfixPatch
        public static void patch(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            if (___card instanceof FieldCard) {
                sb.draw(DURATION_BIG_BG, Settings.WIDTH / 2.0F - (270.0F * Settings.scale) - (DURATION_BIG_BG.getWidth() / 2f), Settings.HEIGHT / 2.0F + (248F * Settings.scale) - (DURATION_BIG_BG.getHeight() / 2f), DURATION_BIG_BG.getWidth(), DURATION_BIG_BG.getHeight());
                FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(((FieldCard) ___card).defaultDuration), Settings.WIDTH / 2.0F - 292.0F * Settings.scale, Settings.HEIGHT / 2.0F + 268F * Settings.scale, Color.WHITE.cpy());
            }
        }
    }
}
