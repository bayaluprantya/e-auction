package com.wipro.auction.bid;

import com.wipro.auction.Bid;

import java.util.concurrent.CopyOnWriteArrayList;

public interface BidProcessor {

    /**
     * Determine the winning bid after all bidders have entered their information on the site
     *
     * @param bidders List of bidders in the order of their bid submission
     * @param baseBid Min bid to start the auction, could be null
     * @return Winning Bid
     */
    public Bid process(CopyOnWriteArrayList<Bidder> bidders, Bid baseBid);

}
