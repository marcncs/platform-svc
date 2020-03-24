package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCodeUnit;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.AppStockCheckIdcode;
import com.winsafe.drp.dao.StockCheckDetail;
import com.winsafe.drp.dao.StockCheckIdcode;
import com.winsafe.drp.server.CodeRuleService;
import com.winsafe.hbm.util.DateUtil;

public class AddStockCheckIdcodeiiAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try{
			String billno = request.getParameter("billno");
			String[] wbits = request.getParameterValues("warehousebit");
			String[] idcodes = request.getParameterValues("idcode");
			
			CodeRuleService crs = new CodeRuleService();
			AppICode appicode = new AppICode();
			AppStockCheckDetail appttd = new AppStockCheckDetail();
			
			AppStockCheckIdcode app = new AppStockCheckIdcode();
			AppCodeUnit appcu = new AppCodeUnit();
			AppFUnit af = new AppFUnit();
			if ( idcodes != null ){
				for ( int i=0; i<idcodes.length; i++ ){
					if ( idcodes[i]==null || idcodes[i].equals("") ){
						continue;
					}
					String idcode = idcodes[i];
					idcode = crs.addQuantityCode(idcode);
					String wbit = wbits[i];
					
					String productid = appicode.getICodeByLcode(crs.getLcode(idcode)).getProductid();
					
					List<StockCheckDetail> ttdlist = appttd.getStockCheckDetailBySmID(billno, productid);
					if ( ttdlist == null || ttdlist.isEmpty() ){
						request.setAttribute("result", idcode+"单据产品错误！");
						return new ActionForward("/sys/lockrecord2.jsp");
					}
					
					
								
					if ( app.getStockCheckIdcodeByCidcode(productid, billno, idcode) != null ){
						request.setAttribute("result", idcode+"条码已经存在当前列表中！");
						return new ActionForward("/sys/lockrecord2.jsp");
					}
					
					StockCheckIdcode ic =  app.getStockCheckIdcodeByscid(billno, idcode);
					if ( ic != null && ic.getQuantity().doubleValue() != ic.getFquantity().doubleValue() ) {
						request.setAttribute("result", idcode+"条码已拆过");
						return new ActionForward("/sys/lockrecord2.jsp");
					}	
					
					if ( ic != null ){
						
						ic.setCquantity(ic.getQuantity());	
						ic.setCidcode(idcode);
						app.updStockCheckIdcode(ic);
						appttd.updCheckQuantity(billno, ic.getWarehousebit(), ic.getProductid(),
								ic.getBatch(), ic.getQuantity());
					}else{			
						StockCheckIdcode pi = new StockCheckIdcode();
						pi.setScid(billno);
						pi.setProductid(productid);
						pi.setWarehousebit(wbit);
						pi.setBatch(crs.getBatch(idcode));
						pi.setProducedate(crs.getProduceDate(idcode));
						pi.setValidate("");
						pi.setUnitid(appcu.getCodeUnitByID(crs.getUcode(idcode)).getUnitid());
						pi.setQuantity(0d);
						pi.setCquantity(af.getQuantity(pi.getProductid(), pi.getUnitid(), 1));
						pi.setFquantity(pi.getCquantity());
						pi.setPackquantity(crs.getRealQuantity(idcode));
						pi.setIdcode("");
						pi.setCidcode(idcode);
						pi.setLcode(crs.getLcode(idcode));
						pi.setStartno(crs.getStartNo(idcode));
						pi.setEndno(crs.getEndNo(idcode));		
						pi.setMakedate(DateUtil.getCurrentDate());
						
						StockCheckIdcode parentIdcode = app.getIdcodeByWLM(pi.getStartno(), pi.getEndno(), billno);				
						if ( parentIdcode != null ){					
							
							pi.setQuantity(pi.getCquantity());
							parentIdcode.setCquantity(parentIdcode.getCquantity()+pi.getCquantity());
							app.updStockCheckIdcode(parentIdcode);
						}				
						app.addStockCheckIdcode(pi);
						appttd.updCheckQuantity(billno, pi.getWarehousebit(), pi.getProductid(),
								pi.getBatch(), pi.getCquantity());
					}	
				}
			}
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "条码不符合规则");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
	}

}
