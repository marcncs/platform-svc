package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncomeDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdPurchaseIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {


		AppPurchaseIncome api = new AppPurchaseIncome();
		super.initdata(request);try{
			String id = request.getParameter("id");
			PurchaseIncome pi = api.getPurchaseIncomeByID(id);
			PurchaseIncome oidpi = (PurchaseIncome)BeanUtils.cloneBean(pi);
			String pid = request.getParameter("pid");
			if (pid == null || pid.equals("null") || pid.equals("")) {
				request.setAttribute("result", "databases.upd.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			pi.setWarehouseid(request.getParameter("warehouseid"));
		    pi.setPoid(request.getParameter("poid"));
		    pi.setProvideid(pid);
		    pi.setProvidename(request.getParameter("providename"));    
		    pi.setPlinkman(request.getParameter("plinkman"));
		    pi.setTel(request.getParameter("tel"));
		    pi.setPaymode(RequestTool.getInt(request, "paymode"));
		    pi.setPrompt(RequestTool.getInt(request, "prompt"));
		    pi.setIncomedate(RequestTool.getDate(request, "incomedate"));
		    pi.setRemark(request.getParameter("remark"));

			
		    String productid[] = request.getParameterValues("productid");
		    String productname[] = request.getParameterValues("productname");
		    String specmode[] = request.getParameterValues("specmode");
		    int unitid[] = RequestTool.getInts(request, "unitid");
		    double unitprice[] = RequestTool.getDoubles(request, "unitprice");
		    double quantity[] = RequestTool.getDoubles(request, "quantity");
		    Double totalsum=0.00;
		    if(productid==null){
				request.setAttribute("result", "databases.makeshipment.nostockpile");
				return new ActionForward("/sys/lockrecord.jsp");
		    }
			
		    StringBuffer keyscontent = new StringBuffer();
		    keyscontent.append(pi.getId()).append(",").append(pi.getProvidename()).append(",")
		    .append(pi.getTel()).append(",").append(pi.getPlinkman()).append(",").append(pi.getPoid());

			AppPurchaseIncomeDetail apid = new AppPurchaseIncomeDetail();
			apid.delPurchaseIncomeDetailByPiID(id);
			WarehouseBitDafService wbds = new WarehouseBitDafService("purchase_income_idcode","piid",pi.getWarehouseid());
			wbds.del(pi.getId(), productid);
			
			 for (int i = 0; i < productid.length; i++) {        
		        PurchaseIncomeDetail picd = new PurchaseIncomeDetail();
		        picd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("purchase_income_detail",0,"")));
		        picd.setPiid(id);
		        picd.setProductid(productid[i]);
		        picd.setProductname(productname[i]);
		        picd.setSpecmode(specmode[i]);
		        picd.setUnitid(unitid[i]);
		        picd.setUnitprice(unitprice[i]);
		        picd.setQuantity(quantity[i]);
		        picd.setSubsum(picd.getUnitprice() * picd.getQuantity());        
		        totalsum +=picd.getSubsum();        
		        apid.addPurchaseIncomeDetail(picd);
		        wbds.add(pi.getId(), productid[i], unitid[i], quantity[i]);
		      }

			pi.setTotalsum(totalsum);
			pi.setKeyscontent(keyscontent.toString());
			api.updPurchaseIncome(pi);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(users.getUserid(), 7, "入库>>修改采购入库,编号："+oidpi,pi,pi);
			return mapping.findForward("updresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
