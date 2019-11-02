package theSacred.powers.turn;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSacred.TheSacred;
import theSacred.powers.abstracts.AbstractSacredPower;
import theSacred.util.UC;

import static theSacred.util.UC.doAllDmg;
import static theSacred.util.UC.p;

public class ScourgedEarthPower extends AbstractSacredPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheSacred.makeID("ScourgedEarth");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ScourgedEarthPower(int amount, int amount2, AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount2;
        type = PowerType.BUFF;
        updateDescription();
        isTurnBased = true;
        loadRegion("skillBurn");
    }

    public ScourgedEarthPower(int amount, int amount2) {
        this(amount, amount2, p());
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flashWithoutSound();
        doAllDmg(amount2, AbstractGameAction.AttackEffect.FIRE, DamageInfo.DamageType.THORNS, false);
        UC.generalPowerLogic(this);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ScourgedEarthPower(amount, amount2, owner);
    }
}