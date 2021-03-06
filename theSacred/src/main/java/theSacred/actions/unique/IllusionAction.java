package theSacred.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.cards.abstracts.SacredCard;
import theSacred.util.UC;

public class IllusionAction extends AbstractGameAction {
    private SacredCard c;

    public IllusionAction(SacredCard c, AbstractMonster target) {
        this.c = c;
        this.target = target;
    }

    @Override
    public void update() {
        for (int i = 0; i < c.baseMagicNumber2; i++) {
            UC.doDmg(target, c.magicNumber, DamageInfo.DamageType.THORNS, AttackEffect.BLUNT_LIGHT, true, true);
        }
        isDone = true;
    }
}
