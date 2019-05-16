package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.dao.OrderDAO;

@Service
@Transactional
public class OrderService {

	@SuppressWarnings("unused")
	@Autowired
	private OrderDAO orderDAO;
	
	//Create a Order, Order Details
	
	//Update a Order, Order Details
	
	//Query a Order
	
	//Query Orders, Order Details
	
}
