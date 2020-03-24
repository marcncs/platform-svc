package com.winsafe.drp.action.yun;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winsafe.drp.dao.AppManufacturer;
import com.winsafe.drp.dao.AppManufacturerContact;
import com.winsafe.drp.dao.AppManufacturerNews;
import com.winsafe.drp.dao.Manufacturer;
import com.winsafe.drp.dao.ManufacturerContact;
import com.winsafe.drp.dao.ManufacturerNews;

@Controller
@RequestMapping("/elabel")
public class ElabelController {

	private AppManufacturer amf = new AppManufacturer();
	private AppManufacturerNews amn = new AppManufacturerNews();
	private AppManufacturerContact amc = new AppManufacturerContact();
	
	/** 企业新闻引导页 
	 * @throws Exception */
	@RequestMapping(value = "/manufacturer/{manufacturer_id}/news", method = RequestMethod.GET)
	public String manufacturerNews(@PathVariable("manufacturer_id") Integer manufacturerId, HttpServletRequest request) throws Exception {
		Manufacturer manufacturer = amf.getManufacturerById(manufacturerId);
		if (manufacturer == null) {
			return "/elabel/error";
		}
		request.setAttribute("manufacturer_id", manufacturerId);
		request.setAttribute("manufacturer_info", manufacturer);
		return "/elabel/manufacturer_news";
	}

	/** 企业新闻详情引导页 
	 * @throws Exception */
	@RequestMapping(value = "/manufacturer/news/{news_id}", method = RequestMethod.GET)
	public String index_news_detail(@PathVariable("news_id") Integer id, HttpServletRequest request) throws Exception {
		ManufacturerNews news = amn.getById(id);
		if (news != null) {
			request.setAttribute("manufacturer_id", news.getManufacturerId());
		}
		Manufacturer manufacturer = amf.getManufacturerById(news.getManufacturerId());
		if (manufacturer == null) {
			return "/elabel/error";
		}
		request.setAttribute("manufacturer_info", manufacturer);
		
		request.setAttribute("news_id", id);
		return "/elabel/manufacturer_news_detail";
	}
	
	/** 企业热门产品引导页
	 * @throws Exception */
	@RequestMapping(value = "/manufacturer/{manufacturer_id}/products", method = RequestMethod.GET)
	public String manufacturerProduct(@PathVariable("manufacturer_id") Integer manufacturerId, HttpServletRequest request) throws Exception {
		Manufacturer manufacturer = amf.getManufacturerById(manufacturerId);
		if (manufacturer == null) {
			return "/elabel/error";
		}
		request.setAttribute("manufacturer_id", manufacturerId);
		request.setAttribute("manufacturer_info", manufacturer);
		return "/elabel/manufacturer_products";
	}
	
	/** 企业联系电话引导页
	 * @throws Exception */
	@RequestMapping(value = "/manufacturer/{manufacturer_id}/contacts", method = RequestMethod.GET)
	public String manufacturerContacts(@PathVariable("manufacturer_id") Integer manufacturerId, HttpServletRequest request) throws Exception {
		List<ManufacturerContact> list = amc.getByManufacturerId(manufacturerId);
		Manufacturer manufacturer = amf.getManufacturerById(manufacturerId);
		if (list == null || list.size() <= 0) { //如果电话列表为空则读取企业注册时的电话号码
			list = new ArrayList<ManufacturerContact>();
			ManufacturerContact contact = new ManufacturerContact();
			contact.setId(0);
			contact.setManufacturerId(manufacturerId);
			contact.setTitle("联系电话");
			contact.setTel(manufacturer.getTel());
			list.add(contact);
		}
		request.setAttribute("manufacturer_id", manufacturerId);
		request.setAttribute("manufacturer_info", manufacturer);
		request.setAttribute("contacts", list);
		return "/elabel/contact_us";
	}
}
