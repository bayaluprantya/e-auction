package com.wipro.auction;

import com.wipro.auction.bid.Bidder;

/**
 * Represents a bid.
 */
public class Bid implements Comparable<Bid> {
    /**
     * The Bidder that made the bid.
     */
    private final Bidder bidder;
    /**
     * The amount that was bid.
     */
    private final int amount;

    /**
     * Creates a bid with a specified Bidder and a specified amount.
     *
     * @param bidder The Bidder the made the bid.
     * @param amount The amount the has bid.
     */
    public Bid(final Bidder bidder, final int amount) {
        if (amount >> 31 != 0) throw new IllegalArgumentException("Negative bids not allowed");
        this.bidder = bidder;
        this.amount = amount;
    }

    /**
     * @return The Bidder that made the bid.
     */
    public Bidder getBidder() {
        return this.bidder;
    }

    /**
     * @return The amount of the bid.
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Compares two bids by their amounts.
     *
     * @param bid The bid to compare ourselves with
     * @return Whether the bid was smaller, equal, or larger.
     */
    public int compareTo(final Bid bid) {
        final Integer bidA = this.getAmount();
        final Integer bidB = bid.getAmount();
        return bidA.compareTo(bidB);
    }

    /**
     * Give a string representation of the bid.
     *
     * @return A string representation.
     */
    public String toString() {
        return "$" + this.getAmount() + " by " + this.getBidder();
    }
}
