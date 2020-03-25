package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.esapi.ESAPI;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.service.ProductService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.PYCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdProductAction extends Action { 
	private static Logger logger = Logger.getLogger(UpdProductAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		AppProduct ap = new AppProduct();
		AppFUnit afu = new AppFUnit();
		FUnit fu = new FUnit();
		AppProductStockpile aps = new AppProductStockpile();
		
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		
		try {
			String pid = request.getParameter("id");
			Product p = ap.getProductByID(pid);
			ProductService ps = new ProductService();
			//检查是否有权限修改
			if(!ps.canModify(p.getProductType(), users.getUserid())) {
				request.setAttribute("result", "您当前无权限修改此类型的产品!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			if(!p.getmCode().equals(request.getParameter("mCode"))) {
				if(ap.isMcodeAlreadyExists(request.getParameter("mCode"))) {
					request.setAttribute("result", request.getParameter("mCode") + "此商品物料号已经存在，请重新录入!");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
			}
			String regCertCodeFixed = request.getParameter("regCertCodeFixed");
			String specCode = request.getParameter("specCode");
			if(ap.isRegCertAndSpecCodeAlreadyExists(regCertCodeFixed, specCode, pid)) {
				request.setAttribute("result", "系统中已存在登记证后六位为"+regCertCodeFixed+"规格码为"+specCode+"的产品!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			Product oldp = (Product)BeanUtils.cloneBean(p);
			int sunit = RequestTool.getInt(request, "sunit");
			if( p.getCountunit() != sunit ){
				afu.delFUnitByPIDNoIsMain(pid, 1);
				fu.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("f_unit",0,"")));
				fu.setFunitid(sunit);
				fu.setProductid(pid);
				fu.setXquantity(1d);
				fu.setIsmain(1);
				afu.addFUnit(fu);
				aps.updProductStockpileUnitidByProductID(pid, sunit);
			}

			p.setPsid(request.getParameter("psid"));
			p.setNccode(request.getParameter("nccode"));
			p.setProductname(ESAPI.encoder().decodeForHTML(request.getParameter("productname")));
			p.setPycode(PYCode.getSampCode(p.getProductname()));
			p.setProductnameen(ESAPI.encoder().decodeForHTML(request.getParameter("productnameen")));
			//规格统一大写
//			p.setSpecmode(request.getParameter("specmode").toUpperCase());
			//规格值设置成包装大小的值
			//#start modified by ryan.xi at 20150602
//			p.setSpecmode(request.getParameter("packSizeName"));
			p.setSpecmode(ESAPI.encoder().decodeForHTML(request.getParameter("specmode")));
			//#end modified by ryan.xi at 20150602
			p.setMemo(request.getParameter("memo"));
			//设置计量单位
			p.setCountunit(RequestTool.getInt(request, "countunit"));
			//设置最小包装单位
			p.setSunit(sunit);
			//最小包装到计量单位转化率：
			p.setBoxquantity(RequestTool.getDouble(request, "boxquantity"));
			//是否可用
			p.setUseflag(RequestTool.getInt(request, "useflag"));
			//物料号
			p.setmCode(request.getParameter("mCode")); 
		    //物料中文描述
			p.setMatericalChDes(ESAPI.encoder().decodeForHTML(request.getParameter("matericalChDes")));
			//物料中文描述
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
		    System.out.println(request.getParameter("wise"));
		    
		    System.out.println(RequestTool.getInt(request, "wise"));
		    //是否License-in  tommy add 2016/12/24
		    if (request.getParameter("wise")!=null) {
		    	p.setWise(RequestTool.getInt(request, "wise"));
		    } else {
		    	p.setWise(null);
		    }
		    
		    p.setCodeType(RequestTool.getInt(request, "codeType"));
		    p.setLinkMode(RequestTool.getInt(request, "linkMode"));
		    p.setRegCertCode(ESAPI.encoder().decodeForHTML(request.getParameter("regCertCode")));
		    p.setRegCertType(RequestTool.getInt(request, "regCertType"));
		    p.setRegCertUser(request.getParameter("regCertUser"));
			p.setStandardName(ESAPI.encoder().decodeForHTML(request.getParameter("standardName")));
			p.setSpecCode(request.getParameter("specCode"));
			p.setProduceType(RequestTool.getInt(request, "produceType"));
			p.setRegCertCodeFixed(request.getParameter("regCertCodeFixed"));
			p.setInnerProduceType(RequestTool.getInt(request, "innerProduceType"));
			p.setModificationTime(Dateutil.getCurrentDate());
			
			p.setProductType(Integer.valueOf(request.getParameter("productType")));
			p.setCopys(Integer.valueOf(request.getParameter("copys")));
			p.setInspectionInstitution(request.getParameter("inspectionInstitution"));
//			p.setValidResult(request.getParameter("validResult"));
			p.setIsidcode(RequestTool.getInt(request, "isidcode"));
			ap.updProduct(p); 
			
			afu.delFUnitByPIDNoIsMain(pid,0);			
			int strfunitid[] = RequestTool.getInts(request, "funitid");
		    double strxquantity[] = RequestTool.getDoubles(request, "xquantity");
		    if(strfunitid !=null){
		    	for(int f=0;f<strfunitid.length;f++){
		    		fu = new FUnit();
		    		fu.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("f_unit",0,"")));
					fu.setFunitid(strfunitid[f]);		
					fu.setProductid(pid);
					fu.setXquantity(strxquantity[f]);
					fu.setIsmain(0);
					afu.addFUnit(fu);
		    	}
		    }
			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, "产品资料", "修改产品,编号:"+pid, oldp, p);
			return mapping.findForward("updresult");
		} catch (Exception e) { 
			logger.error("", e);
			throw e;
		} 
	}
}
