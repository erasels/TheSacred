package theSacred.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.CardInfo;

import static theSacred.TheSacred.makeID;
import static theSacred.util.UC.*;

public class Vengeance extends SacredCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Vengeance",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 0;

    public Vengeance() {
        super(cardInfo, true);

        setDamage(DAMAGE);
        setMultiDamage(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doVfx(new VerticalAuraEffect(Color.FIREBRICK, p.hb.cX, p.hb.cY));
        if (!upgraded) {
            doDmg(m, damage, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        } else {
            doAllDmg(this, AbstractGameAction.AttackEffect.BLUNT_HEAVY, false);
        }
    }

    @Override
    public void applyPowers() {
        System.out.println(target);
        baseDamage = p().maxHealth - p().currentHealth;
        super.applyPowers();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.target = CardTarget.ALL_ENEMY;
        }
        super.upgrade();
    }
}