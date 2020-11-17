package theSacred.cards.abstracts;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSacred.mechanics.field.FieldSystem;
import theSacred.util.CardInfo;

public abstract class FieldCard extends SacredCard {

    public FieldCard(CardInfo cardInfo, boolean upgradesDescription) {
        super(cardInfo, upgradesDescription);
    }

    public FieldCard(CardColor color, String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription) {
        super(color, cardName, cost, cardType, target, rarity, upgradesDescription);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        //Adds itself to the field
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                FieldSystem.acceptCard(FieldCard.this);
                isDone = true;
            }
        });
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

    public void onLeaveField() { }
}
