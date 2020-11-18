package theSacred.cards.abstracts;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.TheSacred;
import theSacred.characters.SacredCharacter;
import theSacred.mechanics.field.FieldSystem;
import theSacred.util.CardInfo;
import theSacred.util.UC;
import theSacred.vfx.combat.BarrierVfx;
import theSacred.vfx.general.RunAnimationEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class FieldCard extends SacredCard {
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(TheSacred.makeID("FieldCards"));

    public FieldCard(CardInfo cardInfo, boolean upgradesDescription) {
        this(SacredCharacter.Enums.COLOR_SACRED, cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription);
    }

    public FieldCard(CardColor color, String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription) {
        super(color, cardName, cost, cardType, target, rarity, upgradesDescription);
        //setTopText(uiStrings.TEXT[0], Color.SKY);
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        ArrayList<TooltipInfo> tps = (ArrayList<TooltipInfo>) super.getCustomTooltipsTop();
        if(tps == null) {
            tps = new ArrayList<>();
        }
        if(!FieldSystem.fields.contains(this)) {
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
        if(CardCrawlGame.isInARun()) {
            if (FieldSystem.fields.contains(this)) {
                glowColor = GOLD_BORDER_GLOW_COLOR;
            } else {
                glowColor = BLUE_BORDER_GLOW_COLOR;
            }
        }
    }

    public boolean isHovered() {
        return hb.hovered;
    }

    public void updateParticles() { }

    public void onLeaveField() { }

    public float atDamageGive(float damage, DamageInfo.DamageType type) { return damage; }
}
