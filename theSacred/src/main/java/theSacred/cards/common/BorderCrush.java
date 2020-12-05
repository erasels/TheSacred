package theSacred.cards.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;
import theSacred.vfx.combat.unique.BorderCrushEffect;
import theSacred.vfx.general.RunAnimationEffect;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class BorderCrush extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BorderCrush",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 14;
    private static final int BLOCK = -1;
    private static final int UPG_BLOCK = 5;


    public BorderCrush() {
        super(cardInfo, true);
        p(); //Stupid intellij stuff , 

        setDamage(DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doAnim(RunAnimationEffect.ANIS.THROW);
        doVfx(new BorderCrushEffect(m.hb.cX, m.hb.cY, Color.SKY));
        doDmg(m, this, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if(upgraded) {
            doDef(block);
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int b_blk = baseBlock;
        int blk = block;
        baseBlock = baseDamage;
        super.applyPowers();
        if(baseBlock != block) {
            damage += (block - baseBlock);
            if(damage < 0)
                damage = 0;
            isDamageModified = true;
        }
        baseBlock = b_blk;
        block = blk;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        int b_blk = baseBlock;
        int blk = block;
        baseBlock = baseDamage;
        super.applyPowers();
        if(baseBlock != block) {
            damage += (block - baseBlock);
            isDamageModified = true;
        }
        baseBlock = b_blk;
        block = blk;
    }
}