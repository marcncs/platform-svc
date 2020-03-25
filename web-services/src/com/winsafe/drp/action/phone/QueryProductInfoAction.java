package com.winsafe.drp.action.phone;

import java.util.ArrayList;
import java.util.List; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.ResponseUtil2;

public class QueryProductInfoAction extends Action {
	private Logger logger = Logger.getLogger(QueryProductInfoAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		String imeinumber = request.getParameter("IMEI_number"); // 手机IMEI号
		try {
			// 获取所有发布的公告
			List<ProductInfoJSON> proList = getProList();
			ResponseUtil2.writeJsonMsg(response, "200", Constants.CODE_SUCCESS_MSG, proList, null, "手机",
					"IMEI:[" + imeinumber + "]", false);
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtil2.writeJsonMsg(response, "500", Constants.CODE_ERROR_MSG, "", null, "手机",
					"IMEI:[" + imeinumber + "]", false);
		}
		return null;
	}

	private List<ProductInfoJSON> getProList() {
		List<ProductInfoJSON> proList = new ArrayList<ProductInfoJSON>();
		proList.add(new ProductInfoJSON("001", "来福禄100ml", "100ml", "50", "0", "PD20120215", "001", "0000001"));
		proList.add(new ProductInfoJSON("002", "来福禄200ml", "100ml", "50", "0", "PD20120215", "002", "0000001"));
		proList.add(new ProductInfoJSON("003", "来福禄300ml", "100ml", "50", "0", "PD20120215", "003", "0000001"));
		return proList;
	}
	
	public class ProductInfoJSON {
		private String id;
		private String product_name;
		private String spec_mode;
		private String carton_quantity;
		private String case_quantity;
		private String pesticide_num;
		private String lcode;
		private String init_logistics_sequence;
		public ProductInfoJSON(String id, String productName, String specMode,
				String cartonQuantity, String caseQuantity,
				String pesticideNum, String lcode, String initLogisticsSequence) {
			super();
			this.id = id;
			product_name = productName;
			spec_mode = specMode;
			carton_quantity = cartonQuantity;
			case_quantity = caseQuantity;
			pesticide_num = pesticideNum;
			this.lcode = lcode;
			init_logistics_sequence = initLogisticsSequence;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getProduct_name() {
			return product_name;
		}
		public void setProduct_name(String productName) {
			product_name = productName;
		}
		public String getSpec_mode() {
			return spec_mode;
		}
		public void setSpec_mode(String specMode) {
			spec_mode = specMode;
		}
		public String getCarton_quantity() {
			return carton_quantity;
		}
		public void setCarton_quantity(String cartonQuantity) {
			carton_quantity = cartonQuantity;
		}
		public String getCase_quantity() {
			return case_quantity;
		}
		public void setCase_quantity(String caseQuantity) {
			case_quantity = caseQuantity;
		}
		public String getPesticide_num() {
			return pesticide_num;
		}
		public void setPesticide_num(String pesticideNum) {
			pesticide_num = pesticideNum;
		}
		public String getLcode() {
			return lcode;
		}
		public void setLcode(String lcode) {
			this.lcode = lcode;
		}
		public String getInit_logistics_sequence() {
			return init_logistics_sequence;
		}
		public void setInit_logistics_sequence(String initLogisticsSequence) {
			init_logistics_sequence = initLogisticsSequence;
		}
	}
}
