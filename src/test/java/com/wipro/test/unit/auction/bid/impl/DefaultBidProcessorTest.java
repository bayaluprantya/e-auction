package com.wipro.test.unit.auction.bid.impl;

import com.wipro.auction.Bid;
import com.wipro.auction.Item;
import com.wipro.auction.bid.BidProcessor;
import com.wipro.auction.bid.Bidder;
import com.wipro.auction.bid.impl.DefaultBidProcessor;
import com.wipro.auction.bid.impl.DefaultBidder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DefaultBidProcessorTest {

    private BidProcessor bidProcessor;
    private Item item;
    private Bidder[] bidders = new Bidder[4];
    private Bid winningBid;

    public DefaultBidProcessorTest(Item item, Bidder[] bidders, Bid winningBid) {
        this.item = item;
        this.bidders = bidders;
        this.winningBid = winningBid;
    }

    @Parameterized.Parameters
    public static Collection data() {
        DefaultBidder alice1 = new DefaultBidder("Alice", 50, 3, 80);
        DefaultBidder aaron1 = new DefaultBidder("Aaron", 60, 2, 82);
        DefaultBidder amanda1 = new DefaultBidder("Amanda", 55, 5, 85);

        DefaultBidder alice2 = new DefaultBidder("Alice", 700, 2, 725);
        DefaultBidder aaron2 = new DefaultBidder("Aaron", 599, 15, 725);
        DefaultBidder amanda2 = new DefaultBidder("Amanda", 625, 8, 725);

//        DefaultBidder alice3 = new DefaultBidder("Alice", 2500, 500, 3000);
//        DefaultBidder aaron3 = new DefaultBidder("Aaron", 2800, 201, 3100);
//        DefaultBidder amanda3 = new DefaultBidder("Amanda", 2501, 247, 3200);

        DefaultBidder alice3 = new DefaultBidder("Alice", 1, 1, 200);
        DefaultBidder aaron3 = new DefaultBidder("Aaron", 1, 3, 201);
        DefaultBidder amanda3 = new DefaultBidder("Amanda", 1, 4, 201);

        return Arrays.asList(new Object[][]{
                {new Item("1", "Bicycle"),
                        new Bidder[]{alice1, aaron1, amanda1},
                        new Bid(amanda1, 85)},
                {new Item("2", "Scooter"),
                        new Bidder[]{alice2, aaron2, amanda2},
                        new Bid(alice2, 725)},
                {new Item("3", "Boat"),
                        new Bidder[]{alice3, aaron3, amanda3},
                        new Bid(amanda3, 3200)}
        });
    }

    @Before
    public void setUp() {
        bidProcessor = new DefaultBidProcessor(item);
    }

    @Test
    public void testProcess() {
        CopyOnWriteArrayList<Bidder> bidders = new CopyOnWriteArrayList<>(Arrays.asList(this.bidders));
        Bid winningBid = bidProcessor.process(bidders, null);

        assertEquals(this.winningBid.getBidder(), winningBid.getBidder());
        assertEquals(this.winningBid.getAmount(), winningBid.getAmount());
    }
}