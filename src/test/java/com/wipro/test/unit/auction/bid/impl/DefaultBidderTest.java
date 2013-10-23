package com.wipro.test.unit.auction.bid.impl;

import com.wipro.auction.Bid;
import com.wipro.auction.bid.Bidder;
import com.wipro.auction.bid.impl.DefaultBidder;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultBidderTest {

    @Test
    public void testGetBid() {

        Bidder bidder1 = new DefaultBidder("Carl", 5, 1, 10);
        Bidder bidder2 = new DefaultBidder("Bob", 1, 4, 100);

        Bid bid = bidder2.getBid(new Bid(bidder1, 5));

        assertEquals(bid.getBidder(), bidder2);
        assertEquals(9, bid.getAmount());
    }

    @Test
    public void testGetBidSelfOutBid() {

        Bidder bidder2 = new DefaultBidder("Bob", 1, 4, 100);
        Bid bid = bidder2.getBid(new Bid(bidder2, 5));

        assertEquals(bid.getBidder(), bidder2);
        assertEquals(5, bid.getAmount());
    }

    @Test
    public void testGetBidFirstPost() {

        Bidder bidder2 = new DefaultBidder("Bob", 1, 4, 100);
        Bid bid = bidder2.getBid(new Bid(null, 5));

        assertEquals(bid.getBidder(), bidder2);
        assertEquals(1, bid.getAmount());
    }

    @Test
    public void testGetBidInvalidArgumentException() {
        try {
            Bidder bidder2 = new DefaultBidder("Bob", 1, 4, 100);
            bidder2.getBid(null);
            fail("Exception not thrown");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void testGetBidNullReturn() {
        Bidder bidder1 = new DefaultBidder("Carl", 5, 1, 10);
        Bidder bidder2 = new DefaultBidder("Bob", 1, 1, 4);
        Bid bid = bidder2.getBid(new Bid(bidder1, 5));

        assertNull(bid);
    }
}