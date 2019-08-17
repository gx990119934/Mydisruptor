package com.disruptor.event.heigh;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

public class TradePublisher implements Runnable {

	private CountDownLatch latch;
	
	private Disruptor<Trade> disruptor;
	
	private static int PUBLISH_COUNT = 1;
	
	public TradePublisher(CountDownLatch latch, Disruptor<Trade> disruptor) {
		this.disruptor=disruptor;
		this.latch=latch;
	
	}

	@Override
	public void run() {
		
		TradeEventTranslator	eventTranslator=new TradeEventTranslator();
		for (int i = 0; i < PUBLISH_COUNT; i++) {
			disruptor.publishEvent(eventTranslator);
		}
		
		latch.countDown();
	}

}

class TradeEventTranslator implements EventTranslator<Trade>{

	private Random random=new Random();
	
	@Override
	public void translateTo(Trade event, long sequence) {
		this.generaterTrade(event);
	}

	private void generaterTrade(Trade event) {
		event.setPrice(random.nextDouble()*9999);
	}
	
	
}