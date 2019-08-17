package com.disruptor.event.heigh;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class Handler1 implements EventHandler<Trade>,WorkHandler<Trade>{

	@Override
	public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
		this.onEvent(event);
	}

	@Override
	public void onEvent(Trade event) throws Exception {
		System.err.println("handler 1 : SET NAME");
		 event.setName("H1");
		Thread.sleep(1000);
	}

}
