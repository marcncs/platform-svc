package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductIncomeIdcode;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.dao.AppViewWlmIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductIncomeIdcode;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.UploadProduceReport;
import com.winsafe.drp.dao.ViewIdcodeTrendForm;
import com.winsafe.drp.dao.ViewWlmIdcode;
/**
 * 物流码流向查询
* @Title: ListViewIdcodeTrendAction.java
* @Description: TODO
* @author: wenping 
* @CreateTime: Apr 23, 2013 3:47:27 PM
* @version:
 */
public class ListViewIdcodeTrendAction extends BaseAction {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		
		AppProduct ap = new AppProduct();
		AppUploadProduceReport ar = new AppUploadProduceReport();
		AppICode ai = new AppICode();
		AppProductIncomeIdcode apii = new AppProductIncomeIdcode();
		AppViewWlmIdcode asb = new AppViewWlmIdcode();
//		AppWarehouse aw = new AppWarehouse();
		AppOrgan ao  =new AppOrgan();
		AppRuleUserWH aru = new AppRuleUserWH();
		
		ViewIdcodeTrendForm viewform = new ViewIdcodeTrendForm();
		
		String type = request.getParameter("type");
		
		//得到产品编码
		String pncccode = request.getParameter("pnccode");
		//得到分装日期
		String packdate = request.getParameter("packdate");
		//得到基础码
		String code = request.getParameter("code");
		//得到箱码
		String boxcode = request.getParameter("boxcode");
		
		if(!StringUtil.isEmpty(pncccode) && !StringUtil.isEmpty(packdate) ){
				//通过pnccode查找到产品物流码前缀
				Product p = ap.findProductByNccode(pncccode);
				if(p!=null){
					String lcode = ai.getLCodeByPid(p.getId());
					
					//通过生产报表查找
					UploadProduceReport upr = ar.getUploadProduceReport(lcode, packdate);
					
					//因为生产报表中只有国内条码，故不用判断国外条码
					if(null == upr){
						upr = ar.getUploadProduceReport(lcode.substring(lcode.length() - 2, lcode.length()), packdate);
					}
					
					if(upr!=null){
						code = upr.getProCode();
					    boxcode=upr.getBoxCode();
					    
					    
//					    viewform.setCode(code);
//					    viewform.setBoxcode(boxcode);
//					    viewform.setProduceDate(upr.getPdate());
//					    viewform.setPname(p.getProductname());
//					    viewform.setSpecmode(p.getSpecmode());
//					    request.setAttribute("code", code);
						//request.setAttribute("boxcode", boxcode);
					}
				}
			    
			   request.setAttribute("pncccode", pncccode);
			   request.setAttribute("packdate", packdate);
		}
		
		ViewWlmIdcode viewIdcode  =null;
		
		if(StringUtil.isEmpty(code)){
			//如果基础码为空，箱码不为空的情况下，则由箱码决定查询
			if(!StringUtil.isEmpty(boxcode)){
				
				/*//通过生产报表查找
				List<UploadProduceReport> list = ar.getUploadProduceReportByUnitnocode(boxcode);
				StringBuilder sb = new StringBuilder();
				StringBuilder sql = new StringBuilder();
				 for(UploadProduceReport u : list){
					 sql.append("'" + u.getUnitno() + "' between startno and endno or ");
					 sb.append(u.getUnitno());
					 sb.append("\n");
				 }*/
				 
				 viewIdcode  = asb.getLastViewWlmIdcode(boxcode,"");
				 
//				 //显示多个基础码
//				 viewform.setCode(sb.toString());
				 
				 viewform.setCode("hidden");
				 viewform.setBoxcode(boxcode);
				
				request.setAttribute("boxcode", boxcode);
			}else{
				
				//do nothing
			}
			
		}else{
			//如果基础码不空，则由基础码决定查询
			
			//通过生产报表查找
			UploadProduceReport upr = ar.getUploadProduceReportByUnitNo(code);
			if(upr!=null){
				//得到箱码
				boxcode = upr.getBoxCode();
			}
			
			//首先通过基础码查询最后流向，如果存在的话，则一定为最后流向
			viewIdcode  = asb.getLastViewWlmIdcode(code);
			if(viewIdcode==null){
				//否则，通过箱码来查询最后流向
				viewIdcode  = asb.getLastViewWlmIdcode(boxcode);
			}
			
			 viewform.setCode(code);
			 viewform.setBoxcode(boxcode);
			
			request.setAttribute("code", code);
//			request.setAttribute("boxcode", boxcode);
			
		}
		
		
		
		if(viewIdcode!=null){
			
			//通过箱码得到入库单号
			ProductIncomeIdcode pii = apii.getProductIncomeIdcodeByIdcode(boxcode);
			if(pii!=null){
				viewform.setBillNo(pii.getPiid());
			}
			
			String wid = viewIdcode.getWarehouseid();
			
			RuleUserWh ruw  = aru.getRuleByWH(wid, userid);
			//用户对机构有管辖权限
			if(ruw!=null){
				Organ  organ  = ao.getOrganByWarehouseid(wid);
				if(organ!=null){
					viewform.setOrganNo(organ.getOecode());
					viewform.setOrganName(organ.getOrganname());
				}
			}else{
				viewform.setOrganName("其他");
			}
			
			viewform.setBatch(viewIdcode.getBatch());
			viewform.setProduceDate(viewIdcode.getProducedate());
		    viewform.setPname(viewIdcode.getProductname());
		    viewform.setSpecmode(viewIdcode.getSpecmode());
			
			request.setAttribute("viewform", viewform);
		}else{
			//无结果
			if(type!=null){
				request.setAttribute("noResult", "noResult");
			}
			request.setAttribute("viewform", new ViewIdcodeTrendForm());
		}
		
		return mapping.findForward("list");
	}

}
