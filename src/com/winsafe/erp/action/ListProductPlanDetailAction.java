package com.winsafe.erp.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.pojo.UnitInfo;


public class ListProductPlanDetailAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListProductPlanDetailAction.class);
	
	private AppProductPlan appProductPlan = new AppProductPlan();
	private AppProduct appProduct = new AppProduct();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		String id = request.getParameter("ID");
		String planid = request.getParameter("productPlanId");
		if(StringUtil.isEmpty(planid)){
			planid = (String)request.getSession().getAttribute("productPlanId");
		}
		
		String close = request.getParameter("close");
		String actionType = request.getParameter("actionType");
		
		AppBaseResource appBaseResource = new AppBaseResource();
		//标签打印份数
		int cartonLabelCount = Integer.parseInt(appBaseResource.getBaseResourceValue("CartonLabelCount", 1).getTagsubvalue());

		if(!"1".equals(actionType)) {
			if(!StringUtil.isEmpty(close)){
		    	int pagesize = 20;
		    	AppPrepareCode auv = new AppPrepareCode();      	
		    	String where = " where isuse=1 and tcode != 't' and productPlanId="+planid; 
		    	String KeyWord = request.getParameter("KeyWord");
		    	if(!StringUtil.isEmpty(KeyWord)){
		    		where += " and (code like '%"+KeyWord+"%' or tcode like '%"+KeyWord+"%')";
		    		request.setAttribute("KeyWord", KeyWord);
		    	}
		    	String isRelease = request.getParameter("isRelease");
		    	if(!StringUtil.isEmpty(isRelease)){
		    		if(isRelease.equals("0")){
		    			where += " and (isRelease is null or isRelease=0)  ";
		    		}else{
			    		where += " and isRelease = 1 ";
		    		}
		    		request.setAttribute("isRelease", isRelease);
		    	}
		    	 
//		        Map map=new HashMap(request.getParameterMap());
//		        Map tmpMap = EntityManager.scatterMap(map);
//		        String[] tablename={"PrepareCode"};
//		        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
//		       // String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"CreateDate"); 
//		        String blur = DbUtil.getOrBlur(map, tmpMap, "code"); 
//		        whereSql = whereSql + blur +Condition ; 
//		        whereSql = DbUtil.getWhereSql(whereSql); 

//		        List<PrepareCode> codelist   = auv.getPrepareCode(request, pagesize, where); 
		        List<Map<String,String>> codelist = auv.getCodeListForUpdCovertCode(request, pagesize); 
		        request.setAttribute("codelist", codelist);
		         
		       
		        
		        
		        //Integer realnum = auv.countIsRelease(where);
		        // where isuse=1 and tcode != 't' and productPlanId=323
		        Integer realnum = auv.countReleaseCode(where);
		        request.setAttribute("realnum", realnum);
		        
		        ProductPlan plan = appProductPlan.getProductPlanByID(Integer.parseInt(planid));
		        request.setAttribute("plan", plan);
		        
				Product product = appProduct.getProductByID(plan.getProductId());
				// 产品表中的标签份数等于null或者为0
				if (product.getCopys() == null || product.getCopys() == 0 ) {
					// 标签份数取系统中的值
					product.setCopys(cartonLabelCount);
				}
				request.setAttribute("product", product);
				
		        if(!StringUtil.isEmpty(plan.getTemp())&&plan.getTemp().equals("已生成")){
		        	request.setAttribute("printflag", 1);
		        }else{
		        	request.setAttribute("printflag", 0);
		        }
		        
		        if(plan.getCloseFlag()==null){
		        	plan.setCloseFlag(0);
		        }
		        request.setAttribute("productPlanId", planid);
		        
				return mapping.findForward("close");
			}
			
			if(!StringUtil.isEmpty(planid)){
		    	int pagesize = 20;
		    	AppPrepareCode auv = new AppPrepareCode();      	
		    	String where = " where isuse=1 and tcode != 't' and productPlanId="+planid; 
		    	String KeyWord = request.getParameter("KeyWord");
		    	if(!StringUtil.isEmpty(KeyWord)){
		    		where += " and (code like '%"+KeyWord+"%' or tcode like '%"+KeyWord+"%') ";
		    		request.setAttribute("KeyWord", KeyWord);
		    	}
		    	String isRelease = request.getParameter("isRelease");
		    	if(!StringUtil.isEmpty(isRelease)){
		    		if(isRelease.equals("0")){
		    			where += " and (isRelease is null or isRelease=0)  ";
		    		}else{
			    		where += " and isRelease = 1 ";
		    		}
		    		request.setAttribute("isRelease", isRelease);
		    	}
		    	
//		        Map map=new HashMap(request.getParameterMap());
//		        Map tmpMap = EntityManager.scatterMap(map);
//		        String[] tablename={"PrepareCode"};
//		        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
//		       // String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"CreateDate"); 
//		        String blur = DbUtil.getOrBlur(map, tmpMap, "code"); 
//		        whereSql = whereSql + blur +Condition ; 
//		        whereSql = DbUtil.getWhereSql(whereSql); 

//		        List<PrepareCode> codelist   = auv.getPrepareCode(request, pagesize, where); 
		        List<Map<String,String>> codelist = auv.getCodeListForUpdCovertCode(request, pagesize); 
		        request.setAttribute("codelist", codelist);
		        

		        
		        //Integer realnum = auv.countIsRelease(where);
		        Integer realnum = auv.countReleaseCode(where);
		        request.setAttribute("realnum", realnum);
		        
		        ProductPlan plan = appProductPlan.getProductPlanByID(Integer.parseInt(planid));
		        request.setAttribute("plan", plan);
		        
				Product product = appProduct.getProductByID(plan.getProductId());
				// 产品表中的标签份数等于null或者为0
				if (product.getCopys() == null || product.getCopys() == 0 ) {
					// 标签份数取系统中的值
					product.setCopys(cartonLabelCount);
				}
				request.setAttribute("product", product);
				
		        if(!StringUtil.isEmpty(plan.getTemp())&&plan.getTemp().equals("已生成")){
		        	request.setAttribute("printflag", 1);
		        }else{
		        	request.setAttribute("printflag", 0);
		        }
		        
		        if(plan.getCloseFlag()==null){
		        	plan.setCloseFlag(0);
		        }
		        
				return mapping.findForward("toadd");
			}
		}
//		int actionType = Integer.parseInt(request.getParameter("actionType"));
		
		ProductPlan detail = appProductPlan.getProductPlanByID(Integer.parseInt(id));
		request.setAttribute("productPlanDetail", detail);
		
		Product product = appProduct.getProductByID(detail.getProductId());
		// 产品表中的标签份数等于null或者为0
		if (product.getCopys() == null || product.getCopys() == 0 ) {
			// 标签份数取系统中的值
			product.setCopys(cartonLabelCount);
		}
		request.setAttribute("product", product);
		
		if(detail.getCopys() != null && detail.getBoxnum() != null) {
			Integer totalnum = detail.getCopys()*detail.getBoxnum();
			request.setAttribute("totalnum", totalnum);
		}
		
		if(detail.getBoxnum() != null) {
			//总量 zongliang
			String zongliang = "";
			if((product.getBoxquantity() != null && product.getSunit()>0 && product.getBoxquantity() != 0d) ){
				AppFUnit appFUnit = new AppFUnit();
//				FUnit fUnit = appFUnit.getFUnit(product.getId(), product.getCountunit());
				Double xquantity = appFUnit.getQuantityByProductId(product.getId());
				if (xquantity!= null) {
					//计算
					Double getmu = product.getBoxquantity()*xquantity*detail.getBoxnum();
					BaseResource brByUnit = new AppBaseResource().getBaseResourceValue("CountUnit", product.getCountunit());
					zongliang = getmu+brByUnit.getTagsubvalue();
				} 
			}
			request.setAttribute("zongliang", zongliang);
		}
		
		//托数
		Integer tnum = 0;
		AppUnitInfo appu = new AppUnitInfo();
		UnitInfo unitinfo = appu.getUnitInfoByOidAndPid(detail.getOrganId(),detail.getProductId());
		if(null!= unitinfo){
			tnum = (int) Math.ceil(detail.getBoxnum().doubleValue()/unitinfo.getUnitCount().doubleValue());
			request.setAttribute("tnum", tnum);
		}else{
			request.setAttribute("tnum", "未配置托盘");
		}
		//已释放箱数
		AppPrepareCode auv = new AppPrepareCode(); 
		String where = " where productPlanId ="+Integer.parseInt(id);
		//Integer realnum = auv.countIsRelease(where);
		Integer realnum = auv.countReleaseCode(where);
        request.setAttribute("realnum", realnum);
        
        if(detail.getBoxnum() != null) {
        	//实际生产箱
            Integer sjnum  = detail.getBoxnum() - realnum;
            request.setAttribute("sjnum", sjnum);
        }
		
		DBUserLog.addUserLog(request, "详细");
		return mapping.findForward("detail");
	}
}
