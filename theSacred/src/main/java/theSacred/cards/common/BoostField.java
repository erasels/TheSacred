package theSacred.cards.common;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSacred.cards.abstracts.FieldCard;
import theSacred.util.CardInfo;
import theSacred.vfx.combat.BetterWrathParticleEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.p;

public class BoostField extends FieldCard {
    private float particleTimer = 0;

    private final static CardInfo cardInfo = new CardInfo(
            "BoostField",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public BoostField() {
        super(cardInfo, false);
        p(); //Stupid intellij stuff s, s

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL) {
            damage += magicNumber;
        }
        return damage;
    }

    @Override
    public void updateParticles() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new BetterWrathParticleEffect(hb));
            }
        }
    }
}