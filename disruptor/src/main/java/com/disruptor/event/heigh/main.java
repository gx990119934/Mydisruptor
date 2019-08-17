package com.disruptor.event.heigh;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class main {

	public static void main(String[] args) throws InterruptedException {
		//构建线程池用于提交任务
				ExecutorService es=Executors.newFixedThreadPool(4);
				
				//1.创建disruptor
				Disruptor<Trade> disruptor=new Disruptor<Trade>(
						new EventFactory<Trade>() {

							@Override
							public Trade newInstance() {
								return new Trade();
							}
						}, 
						1024*1024, 
						Executors.newFixedThreadPool(4), 
						ProducerType.SINGLE, 
						new BusySpinWaitStrategy());
				
				//2.把消费者设置到disruptor中，EventHandler
//				disruptor.handleEventsWith(new Handler1())
//							.handleEventsWith(new Handler2())
//							.handleEventsWith(new Handler3());
				
				
				disruptor.handleEventsWith(new Handler1(),new Handler2(),new Handler3());
				//3.启动disruptor
				RingBuffer<Trade>	ringBuffer = disruptor.start();
				
				CountDownLatch latch=new CountDownLatch(1);
				
				long begin = System.currentTimeMillis();
				es.submit(new TradePublisher(latch,disruptor));
				latch.await();
				es.shutdown();
				disruptor.shutdown();
				
				System.out.println("总耗时："+(System.currentTimeMillis()-begin));
	}
}
