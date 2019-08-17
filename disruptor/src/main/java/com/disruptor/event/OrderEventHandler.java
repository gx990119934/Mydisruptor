package com.disruptor.event;

import com.lmax.disruptor.EventHandler;

public class OrderEventHandler implements EventHandler<OrderEvent>{

	@Override
	public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {

		System.out.println("消费者：" + event.getValues());
	}

}
