package com.winsafe.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winsafe.drp.service.NotificationService;

@Controller
@RequestMapping(value = "/de")
public class DeliveryController {
	
	private NotificationService service = new NotificationService();
	
	@RequestMapping(value="/{verifyCode}/{deliveryNo}",method=RequestMethod.GET)
	public String toIndex(@PathVariable String verifyCode, @PathVariable String deliveryNo, HttpServletRequest request) throws Exception {
		service.getDeliveryInfo(deliveryNo, verifyCode, request);
		return "delivery/index";
	}
}
