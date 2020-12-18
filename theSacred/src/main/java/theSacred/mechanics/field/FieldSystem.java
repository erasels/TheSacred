package theSacred.mechanics.field;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import theSacred.cards.abstracts.FieldCard;
import theSacred.powers.abstracts.FieldEffectPower;
import theSacred.util.UC;

import java.util.ArrayList;
import java.util.Iterator;

public class FieldSystem {
    public static int fieldAmount = 1;
    public static boolean locked = false;
    public static CardGroup fields = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public static FieldEffectPower power;
    public static boolean addedThisCombat = false;

    public static final float BASE_SCALE = 0.5f;
    public static final float HOVER_SCALE = 0.7f;

    public static void acceptCard(FieldCard c) {
        if(locked)
                return;

        if(!addedThisCombat) {
            UC.doPow(power.owner, power, true);
            addedThisCombat = true;
        }
        if(fields.size() >= fieldAmount) {
            removeCard((FieldCard) fields.group.get(0));
        }
        addCard(c);
    }

    public static void addCard(FieldCard c) {
        fields.group.add(c);

        updatePositions();
        fields.glowCheck();
    }

    public static void removeCard(FieldCard c) {
        fields.group.remove(c);
        c.onLeaveField();
        c.dontTriggerOnUseCard = true;
        UC.att(new UseCardAction(c));
    }

    public static void reset() {
        fieldAmount = 1;
        locked = false;
        fields.clear();
        power = null;
        addedThisCombat = false;
    }

    public static void preBattlePrep() {
        power = new FieldEffectPower(UC.p());
    }

    public static void onVictory() {
        reset();
    }

    public static void render(SpriteBatch sb) {
        for (FieldCard c : getFields()) {
            c.render(sb);
            c.renderCardTip(sb);
        }
    }

    protected static void updatePositions() {
        float targetX = UC.p().hb.cX + (400f * Settings.scale);
        float targetY = Settings.HEIGHT * 0.7f;
        for (FieldCard card : getFields()) {
            card.targetDrawScale = BASE_SCALE;
            card.target_x = targetX;
            card.target_y = targetY;
            targetX += (AbstractCard.RAW_W * card.drawScale * Settings.scale) + (50f * Settings.scale);
        }
    }

    public static void update() {
        for(FieldCard c : getFields()) {
            c.update();
            c.updateParticles();
        }
    }

    public static void updateHover() {
        for (FieldCard c : getFields()) {
            float curScale = c.drawScale;
            c.updateHoverLogic();
            c.drawScale = curScale;
            if(c.isHovered()) {
                c.targetDrawScale = HOVER_SCALE;
            } else {
                c.targetDrawScale = BASE_SCALE;
            }
        }
    }

    public static float atDamageGive(float damage, DamageInfo.DamageType type) {
        for(FieldCard c : getFields()) {
            damage = c.atDamageGive(damage, type);
        }
        return damage;
    }

    public static void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        getFields().forEach(c -> c.onAttack(info, damageAmount, target));
    }

    public static void atEndOfTurn() {
        Iterator<AbstractCard> iter = fields.group.iterator();
        while(iter.hasNext()) {
            FieldCard c = (FieldCard) iter.next();
            c.atEndOfTurn();
            c.decrementDuration();
            if(c.getDuration() <= 0) {
                iter.remove();
                removeCard(c);
            }
        }
    }

    private static ArrayList<FieldCard> getFields() {
        return (ArrayList<FieldCard>)(ArrayList<?>)fields.group;
    }
}
