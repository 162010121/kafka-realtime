package net.javaguides.orderservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.basedomains.dto.Order;
import net.javaguides.basedomains.dto.OrderEvent;
import net.javaguides.orderservice.kafka.OrderProducer;

@RestController
@RequestMapping("api/v1")
public class OrderController {

	@Autowired
	private OrderProducer orderProducer;

	public OrderController(OrderProducer orderProducer) {
		this.orderProducer = orderProducer;
	}


	@PostMapping("/order")
	public String placeOrder(@RequestBody Order order)
	{
		
		order.setOrderId(UUID.randomUUID().toString());
		
		OrderEvent orderEvent=new OrderEvent();
		orderEvent.setStatus("PENDING");
		orderEvent.setMessage("Order Is Pending State");
		orderEvent.setOrder(order);
		
		
		orderProducer.sendMessage(orderEvent);
		
		
		return "Order Placed SuccessFully....";
	}
	
	
	
}
