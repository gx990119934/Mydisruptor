package com.disruptor.event;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

public class OrderEventProducer {
	
	private RingBuffer<OrderEvent> ringBuffer;

	public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
		this.ringBuffer=ringBuffer;
	}
	
	public void sendData(ByteBuffer bb) {
		Long sequence=0L;
		try{
			//1.获取RingBuffer可用序号
			sequence = ringBuffer.next();
			//2.通过序号取出为空属性的对象
			OrderEvent event = ringBuffer.get(sequence);
			//3.属性赋值
			event.setValues(bb.getLong(0));
			
		}finally {
			//4.发布对象
			ringBuffer.publish(sequence);
		}
	}

}
