package theSacred.mechanics.field;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import theSacred.cards.abstracts.FieldCard;
import theSacred.util.UC;

public class FieldSystem {
    public static int fieldAmount = 1;
    public static boolean locked = false;
    public static CardGroup fields = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public static final float BASE_SCALE = 0.5f;
    public static final float HOVER_SCALE = 0.7f;

    public static void acceptCard(FieldCard c) {
        if(locked)
                return;

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
    }

    public static void preBattlePrep() {

    }

    public static void onVictory() {
        reset();
    }

    public static void render(SpriteBatch sb) {
        for(AbstractCard c : fields.group) {
            c.render(sb);
            c.renderCardTip(sb);
        }
    }

    protected static void updatePositions() {
        float targetX = UC.p().hb.cX + (400f * Settings.scale);
        float targetY = Settings.HEIGHT * 0.7f;
        for(AbstractCard card : fields.group) {
            card.targetDrawScale = BASE_SCALE;
            card.target_x = targetX;
            card.target_y = targetY;
            targetX += (AbstractCard.RAW_W * card.drawScale * Settings.scale) + (50f * Settings.scale);
        }
    }

    public static void update() {
        fields.group.forEach(AbstractCard::update);
    }

    public static void updateHover() {
        for (AbstractCard c : fields.group) {
            float curScale = c.drawScale;
            c.updateHoverLogic();
            c.drawScale = curScale;
            if(((FieldCard)c).isHovered()) {
                c.targetDrawScale = HOVER_SCALE;
            } else {
                c.targetDrawScale = BASE_SCALE;
            }
        }
    }
}
