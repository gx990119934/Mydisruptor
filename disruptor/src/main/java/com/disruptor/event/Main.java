package com.disruptor.event;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class Main {
	public static void main(String[] args) {
		
		//参数准备
			OrderEventFactory eventFactory = new OrderEventFactory();
			int ringBufferSize = 1024*1024;
			Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		//1.实例化disruptor
		Disruptor<OrderEvent> disruptor=new Disruptor<OrderEvent>(eventFactory, 
																	ringBufferSize, 
																	executor, 
																	ProducerType.SINGLE, 
																	new BlockingWaitStrategy());
		
		//2.绑定EventHandlder
		disruptor.handleEventsWith(new OrderEventHandler());
		
		//3.启动disruptor
		disruptor.start();
		
		//4.获取实际存储数据的容器 Ringbuffer
		RingBuffer<OrderEvent> ringBuffer =	disruptor.getRingBuffer();
	
		//5.创建生产者
		OrderEventProducer producer=new OrderEventProducer(ringBuffer);
		
		ByteBuffer bb=ByteBuffer.allocate(8);
		for (Long i = 0L; i < 100; i++) {
			bb.putLong(0,i);
			producer.sendData(bb);
		}
		
	}
}
