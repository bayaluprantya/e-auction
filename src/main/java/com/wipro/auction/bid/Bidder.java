package com.wipro.auction.bid;

import com.wipro.auction.Bid;

/**
 * Represents a Bidder
 */
public interface Bidder {
    /**
     * Asks the Bidder for a bid, taking into account the last highest bid.
     * Returns null for no-bid.
     *
     * @param lastBid The last bid given.
     * @return The new bid of the agent
     */
    public Bid getBid(final Bid lastBid);
}