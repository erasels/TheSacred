package theSacred.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theSacred.TheSacred;

public class BetterDiscardPileToDeckAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(TheSacred.makeID("ChooseCardsFromPilesAction"));

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    public Spot position;

    public BetterDiscardPileToDeckAction(int amount, Spot spot) {
        this.p = AbstractDungeon.player;
        setValues(null, p, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        position = spot;
    }

    public BetterDiscardPileToDeckAction(int amount) {
        this(amount, Spot.TOP);
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            isDone = true;
            return;
        }
        if (duration == Settings.ACTION_DUR_FASTER) {
            if (p.discardPile.isEmpty()) {
                isDone = true;
                return;
            }
            if (p.discardPile.size() <= amount) {
                while (!p.discardPile.isEmpty()) {
                    AbstractCard c = p.discardPile.getBottomCard();
                    p.discardPile.removeCard(c);
                    putInSpot(c);
                }
                isDone = true;
            } else {
                AbstractDungeon.gridSelectScreen.open(p.discardPile, amount, TEXT[0] + amount + TEXT[1], false, false, false, false);
                tickDuration();
            }
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                p.discardPile.removeCard(c);
                putInSpot(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }

    private void putInSpot(AbstractCard c) {
        switch (position) {
            case TOP:
                p.hand.moveToDeck(c, false);
            case BOTTOM:
                p.hand.moveToBottomOfDeck(c);
                break;
            case RANDOM:
            default:
                p.hand.moveToDeck(c, true);
        }
    }

    public enum Spot {
        TOP,
        BOTTOM,
        RANDOM
    }
}
