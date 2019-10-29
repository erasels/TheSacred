package theSacred.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSacred.TheSacred;
import theSacred.patches.cards.CardENUMs;
import theSacred.powers.abstracts.AbstractSacredPower;

import static theSacred.util.UC.atb;
import static theSacred.util.UC.p;

public class ErrantFaithPower extends AbstractSacredPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheSacred.makeID("ErrantFaith");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ErrantFaithPower(int amount, AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        updateDescription();
        isTurnBased = false;
        loadRegion("barricade");
    }

    public ErrantFaithPower(int amount) {
        this(amount, p());
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card.hasTag(CardENUMs.INVOKE)) {
            flash();
            atb(new GainEnergyAction(amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ErrantFaithPower(amount, owner);
    }
}