package theSacred.cards.abstracts;

public interface OnTurnChangeCard {
    /**
     * @param newTurn   The number of the turn that is about to start
     */
    void onTurnChange(int newTurn);
}
