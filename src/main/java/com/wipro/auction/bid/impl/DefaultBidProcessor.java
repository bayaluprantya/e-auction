package com.wipro.auction.bid.impl;

import com.wipro.auction.Bid;
import com.wipro.auction.Item;
import com.wipro.auction.bid.BidProcessor;
import com.wipro.auction.bid.Bidder;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class DefaultBidProcessor implements BidProcessor {

    private static final Logger LOG = Logger.getLogger(DefaultBidProcessor.class.getName());
    private final Item item;

    public DefaultBidProcessor(final Item itemForSale) {
        this.item = itemForSale;
    }

    /**
     * @return The Item that is being offered for sale.
     */
    public Item offeredItem() {
        return this.item;
    }

    /**
     * Determine the winning bid after all bidders have entered their information on the site
     *
     * @param bidders List of bidders in the order of their bid submission.
     * @param baseBid Min bid to start the auction, could be null
     * @return Winning Bid with the winning Bidder and the winning Bid amount
     */
    @Override
    public Bid process(CopyOnWriteArrayList<Bidder> bidders, Bid baseBid) {

        if (bidders == null) throw new IllegalArgumentException("Invalid Bidders");
        if (baseBid == null) baseBid = new Bid(null, 0);

        LOG.info("Starting new round  for " + this.offeredItem());

        boolean validBids = false;

        for (Bidder bidder : bidders) {
            Bid bid = bidder.getBid(baseBid);
            if(bid == null) continue;

            LOG.info("Bid received: " + bid);
            if (bid.compareTo(baseBid) > 0) {
                LOG.info("Bid was higher");
                baseBid = bid;
                validBids = true;
            }

            if (bid.compareTo(baseBid) == 0 && baseBid.getBidder() != null) {
                LOG.info("Bid tie");
                if (bidders.indexOf(bid.getBidder()) < bidders.indexOf(baseBid.getBidder()))
                    baseBid = bid;
            }
        }

        LOG.info("Highest bid of this round: " + baseBid);
        if (validBids) {
            baseBid = process(bidders, baseBid);
        } else if (baseBid.getBidder() != null) {
            LOG.info("We have a winner! " + baseBid + ". Stopping auction for " + this.offeredItem());
        } else if (baseBid.getBidder() == null)
            LOG.info("Nobody is interested. Stopping auction for " + this.offeredItem());

        return baseBid;
    }
}
