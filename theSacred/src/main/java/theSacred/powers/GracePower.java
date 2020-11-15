package theSacred.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSacred.TheSacred;
import theSacred.powers.abstracts.AbstractSacredPower;
import theSacred.util.UC;

import static theSacred.util.UC.p;

public class GracePower extends AbstractSacredPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheSacred.makeID("Grace");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GracePower(AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = AbstractPower.PowerType.BUFF;
        updateDescription();
        loadRegion("pressure_points");
    }

    public GracePower(int amount) {
        this(p(), amount);
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount + amount;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.baseBlock > -1 && GraceField.useGrace.get(card)) {
            flash();
            UC.atb(new ReducePowerAction(UC.p(), UC.p(), this, amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GracePower(owner, amount);
    }

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class GraceField {
        public static SpireField<Boolean> useGrace = new SpireField<>(() -> true);
    }
}
