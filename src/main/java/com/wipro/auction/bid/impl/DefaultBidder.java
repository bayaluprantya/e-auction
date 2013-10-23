package com.wipro.auction.bid.impl;

import com.wipro.auction.Bid;

import java.util.logging.Logger;

/**
 * Default Bidder implementation
 */
public class DefaultBidder implements com.wipro.auction.bid.Bidder {
    /**
     * logger
     */
    private static final Logger LOG = Logger.getLogger(DefaultBidder.class.getName());
    /**
     * The maximum bid this Bidder can make.
     */
    private final int maximumBid;
    /**
     * The maximum increment this Bidder can make in its bid.
     */
    private final int increment;
    /**
     * The starting bid for this Bidder.
     */
    private final int startBid;
    /**
     * Bidder name
     */
    private final String bidderName;

    /**
     * Constructs an Bidder with it's maximum bid set to
     * <code>maximumBid</code> and its increment set to
     * <code>increment</code> and its start bit set to
     * <code>startBid</code>
     * <code>bidderName</code>
     *
     * @param iIncrement  The increment this agent can use.
     * @param iMaximumBid The maximum bid this agent can make.
     * @pararm sName The starting bid amount
     * @pararm iStartBid The starting bid amount
     */
    public DefaultBidder(final String sName, final int iStartBid, final int iIncrement, final int iMaximumBid) {
        // validations of arguments
        if ((iMaximumBid >> 31 != 0) || (iIncrement >> 31 != 0) || (iStartBid >> 31 != 0))
            throw new IllegalArgumentException("Negative bids not allowed");
        if ((iMaximumBid - iStartBid) < 0)
            throw new IllegalArgumentException("Maximum Bid should be greater than Start Bid");
        if ((iStartBid + iIncrement) > iMaximumBid || ((iMaximumBid - iStartBid) > 0 && (iIncrement == 0)))
            throw new IllegalArgumentException("Invalid increment Value");

        this.maximumBid = iMaximumBid;
        this.increment = iIncrement;
        this.startBid = iStartBid;
        this.bidderName = sName;
    }

    /**
     * Asks the Bidder for a bid, taking into account the last highest bid.
     * Returns null for no-bid.
     *
     * @param lastBid The last bid given.
     * @return The new bid of the agent
     */
    @Override
    public Bid getBid(final Bid lastBid) {

        if(lastBid == null) throw new IllegalArgumentException("Invalid Bid parameter");

        Bid nextBid = null;
        int currentBid = this.startBid;

        if (this.equals(lastBid.getBidder())) {
            LOG.info(bidderName + ": I'm not going to outbid myself");
            nextBid = lastBid;
        } else if (lastBid.getBidder() == null) {
            LOG.info(bidderName + ": First post!");
            nextBid = new Bid(this, this.startBid);
        } else if (this.maximumBid >= lastBid.getAmount()) {
            LOG.info(bidderName + ": Attempting to make next bid");
             while (currentBid <= lastBid.getAmount()) {
                    currentBid = currentBid + this.increment;
                    if (currentBid > this.maximumBid) {
                        LOG.info(bidderName + ": Limit reached");
                        currentBid = this.maximumBid;
                        break;
                    }
             }
             nextBid = new Bid(this, currentBid);
        }

        return nextBid;
    }

    public String toString() {
        return "Bidder: " + bidderName;
    }
}
