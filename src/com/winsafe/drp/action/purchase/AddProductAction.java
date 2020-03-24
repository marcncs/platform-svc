package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.esapi.ESAPI;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.service.ProductService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.PYCode;
import com.winsafe.hbm.util.RequestTool;

public class AddProductAction extends Action { 

	private AppFUnit afu = new AppFUnit();
	private AppProduct ap = new AppProduct();
	private AppOrganProduct appOrganProduct = new AppOrganProduct();
	private AppMakeConf appmc = new AppMakeConf();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductService ps = new ProductService();
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		String id = request.getParameter("id");
		int productType = Integer.valueOf(request.getParameter("productType"));
		//检查是否有权限修改
		if(!ps.canModify(productType, users.getUserid())) {
			request.setAttribute("result", "您当前无权限新增此类型的产品!");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		Product checkp = ap.getProductByID(id);
		if (checkp != null) {
			request.setAttribute("result", id + "此商品编号已经存在，请重新录入!");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		
		if(ap.isMcodeAlreadyExists(request.getParameter("mCode"))) {
			request.setAttribute("result", request.getParameter("mCode") + "此商品物料号已经存在，请重新录入!");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		
		String regCertCodeFixed = request.getParameter("regCertCodeFixed");
		String specCode = request.getParameter("specCode");
		if(ap.isRegCertAndSpecCodeAlreadyExists(regCertCodeFixed, specCode, null)) {
			request.setAttribute("result", "系统中已存在登记证后六位为"+regCertCodeFixed+"规格码为"+specCode+"的产品!");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		
		Product p = new Product();
		p.setPsid(request.getParameter("psid"));
		p.setNccode(request.getParameter("nccode"));
		p.setId(id);
		p.setProductname(ESAPI.encoder().decodeForHTML(request.getParameter("productname")));
		p.setPycode(PYCode.getSampCode(p.getProductname()));
		p.setProductnameen(ESAPI.encoder().decodeForHTML(request.getParameter("productnameen")));
		//规格统一大写
//		p.setSpecmode(request.getParameter("specmode").toUpperCase());
		//规格值设置成包装大小的值
		//#start modified by ryan.xi at 20150602
//		p.setSpecmode(request.getParameter("packSizeName"));
		p.setSpecmode(ESAPI.encoder().decodeForHTML(request.getParameter("specmode")));
		//#end modified by ryan.xi at 20150602
		p.setMemo(request.getParameter("memo"));
		//计量单位
		p.setCountunit(RequestTool.getInt(request, "countunit"));
		//最小包装单位
		p.setSunit(RequestTool.getInt(request, "sunit"));
		//最小包装到计量单位的转化率
		p.setBoxquantity(RequestTool.getDouble(request, "boxquantity"));
		//是否可用
		p.setUseflag(RequestTool.getInt(request, "useflag"));
	    //物料号
		p.setmCode(request.getParameter("mCode"));
	    //物料中文描述
		p.setMatericalChDes(ESAPI.encoder().decodeForHTML(request.getParameter("matericalChDes")));
		//物料英文描述
		p.setMatericalEnDes(ESAPI.encoder().decodeForHTML(request.getParameter("matericalEnDes")));
	    //规格明细
		p.setPackSizeName(ESAPI.encoder().decodeForHTML(request.getParameter("packSizeName")));
	    //包装大小英文
	    p.setPackSizeNameEn(ESAPI.encoder().decodeForHTML(request.getParameter("packSizeNameEn")));
	    //保质期
	    p.setExpiryDays(RequestTool.getInt(request, "expiryDays"));
	    //箱码是否打印
	    p.setCartonPrintFlag(RequestTool.getInt(request, "cartonPrintFlag"));
	    //小包装是否打印
	    p.setPrimaryPrintFlag(RequestTool.getInt(request, "primaryPrintFlag"));
	    //箱码是否扫描
	    p.setCartonScanning(RequestTool.getInt(request, "cartonScanning"));
	    //是否可分包
	    p.setIsunify(RequestTool.getInt(request, "isunify"));
	    
	    //是否License-in  tommy add 2016/12/24
	    p.setWise(RequestTool.getInt(request, "wise"));
	    
	    p.setCodeType(RequestTool.getInt(request, "codeType"));
	    p.setLinkMode(RequestTool.getInt(request, "linkMode"));
	    p.setRegCertCode(request.getParameter(ESAPI.encoder().decodeForHTML("regCertCode")));
	    p.setRegCertType(RequestTool.getInt(request, "regCertType"));
	    p.setRegCertUser(request.getParameter("regCertUser"));
		p.setStandardName(ESAPI.encoder().decodeForHTML(request.getParameter("standardName")));
		p.setSpecCode(request.getParameter("specCode"));
		p.setProduceType(RequestTool.getInt(request, "produceType"));
		p.setRegCertCodeFixed(request.getParameter("regCertCodeFixed")); 
		p.setInnerProduceType(RequestTool.getInt(request, "innerProduceType"));
		
		p.setMakeorganid(users.getMakeorganid());
		p.setMakedeptid(users.getMakedeptid());
		p.setMakeid(userid);
		p.setMakedate(DateUtil.getCurrentDate());
		p.setProductType(productType);
		p.setCopys(Integer.valueOf(request.getParameter("copys")));
		p.setInspectionInstitution(request.getParameter("inspectionInstitution"));
//		p.setValidResult(request.getParameter("validResult"));
		
		p.setIsidcode(RequestTool.getInt(request, "isidcode"));
		ap.addProduct(p); 

		FUnit fu = new FUnit();
		fu.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("f_unit",0, "")));
		fu.setFunitid(p.getSunit());
		fu.setProductid(p.getId());
		fu.setXquantity(1d);
		fu.setIsmain(1);
		afu.addFUnit(fu);

		int strfunitid[] = RequestTool.getInts(request, "funitid");
		double strxquantity[] = RequestTool.getDoubles(request, "xquantity");
		if (strfunitid != null) {
			for (int f = 0; f < strfunitid.length; f++) {
				fu = new FUnit();
				fu.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"f_unit", 0, "")));
				fu.setFunitid(strfunitid[f]);
				fu.setProductid(p.getId());
				fu.setXquantity(strxquantity[f]);
				fu.setIsmain(0);
				afu.addFUnit(fu);
			}
		}
		//所有机构上架产品
		appOrganProduct.addAllOrgan(p.getId());
		//更新make_conf
		appmc.updMakeConf("organ_product","organ_product");
		
		
		request.setAttribute("result", "databases.add.success");
		DBUserLog.addUserLog(userid, 11, "新增商品,编号:" + p.getId());
		return mapping.findForward("addresult");

		// return mapping.getInputForward();
	}
}
