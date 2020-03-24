package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ProductStockpileAll;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
/**
 * 库存检验
* @Title: ProductStockPileVerifyAction.java
* @author: wenping 
* @CreateTime: Jul 4, 2012 3:34:24 PM
* @version:
 */
public class ProductStockPileVerifyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//检验备注
		String remark = request.getParameter("remark");
		//检验状态
		Integer status  = Integer.parseInt(request.getParameter("verifyStatus"));
		//检验时间 
		String verifydate = request.getParameter("verifydate");
		//库存id
		String id =request.getParameter("pid");
		
		String ids =request.getParameter("ids");
		
		AppProductStockpileAll aps = new AppProductStockpileAll();
		AppProductStockpile ap = new AppProductStockpile();
		
//		ProductStockpileAll psa = aps.getProductStockpileAllByID(Long.valueOf(id));
//		ProductStockpile ps = ap.getProductStockpileByID(Long.valueOf(id));
		List<ProductStockpileAll> psaList = aps.getProductStockpileAllByIDs(ids);
		List<ProductStockpile> psList = ap.getProductStockpileByIDs(ids);
		
		if(psaList != null && psaList.size() > 0){
			for(ProductStockpileAll psa : psaList){
				if(psa != null){
					//修改ProductStockpileAll
					psa.setVerifyStatus(status);
					psa.setRemark(remark);
					psa.setVerifydate(Dateutil.StringToDate(verifydate));
					aps.updProductStockpileAll(psa);
				}
			}
		}
		
		if(psList != null && psList.size() > 0){
			for(ProductStockpile ps : psList){
				if(ps != null){
					//修改ProductStockpile
					ps.setVerifyStatus(status);
					ps.setRemark(remark);
					ps.setVerifydate(Dateutil.StringToDate(verifydate));
					ap.updProductStockpile(ps);
				}
			}
		}
		//修改idcode的检验状态及日期
		if(psaList != null && psaList.size() > 0){
			for(ProductStockpileAll psa : psaList){
				if(psa != null){
					String sql = "update Idcode set verifyStatus = " + status + ", verifydate =  to_date('" + verifydate + "','yyyy-mm-dd')"
						+	" where productid='"+ psa.getProductid() +"' and warehouseid='"+ psa.getWarehouseid()
						+"' and  (isuse=1 or quantity>0 and quantity!=fquantity) and batch like '" + psa.getBatch() +"%'";
					EntityManager.updateOrdelete(sql);
				}
			}
		}
		request.setAttribute("result", "databases.upd.success");
		return mapping.findForward("result");
	}
}
