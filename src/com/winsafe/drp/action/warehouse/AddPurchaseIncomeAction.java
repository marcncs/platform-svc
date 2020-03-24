package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncomeDetail;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddPurchaseIncomeAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
   super.initdata(request);
    try {
    	String pid = request.getParameter("pid");
        if(pid==null||pid.equals("null")||pid.equals("")){
        	request.setAttribute("result", "databases.add.fail");
        	return new ActionForward("/sys/lockrecord.jsp");
        }
        
    PurchaseIncome pi = new PurchaseIncome();
    String piid = MakeCode.getExcIDByRandomTableName("purchase_income",2,"PI");
    pi.setId(piid);
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
    pi.setIsaudit(0);
    pi.setAuditid(0);
    pi.setIstally(0);
    pi.setTallyid(0);
    pi.setMakeorganid(users.getMakeorganid());
    pi.setMakedeptid(users.getMakedeptid());
    pi.setMakeid(userid);
    pi.setMakedate(DateUtil.getCurrentDate());
    
    StringBuffer keyscontent = new StringBuffer();
    keyscontent.append(pi.getId()).append(",").append(pi.getProvidename()).append(",")
    .append(pi.getTel()).append(",").append(pi.getPlinkman()).append(",");

    
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

    AppPurchaseIncomeDetail apid = new  AppPurchaseIncomeDetail();
    WarehouseBitDafService wbds = new WarehouseBitDafService("purchase_income_idcode","piid",pi.getWarehouseid());
      for (int i = 0; i < productid.length; i++) {        
        PurchaseIncomeDetail picd = new PurchaseIncomeDetail();
        picd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("purchase_income_detail",0,"")));
        picd.setPiid(piid);
        picd.setProductid(productid[i]);
        picd.setProductname(productname[i]);
        picd.setSpecmode(specmode[i]);
        picd.setUnitid(unitid[i]);
        picd.setUnitprice(unitprice[i]);
        picd.setQuantity(quantity[i]);
        picd.setSubsum(picd.getUnitprice() * picd.getQuantity());        
        totalsum +=picd.getSubsum();        
        apid.addPurchaseIncomeDetail(picd);
        wbds.add(piid, productid[i], unitid[i], quantity[i]);
      }

      pi.setTotalsum(totalsum);
      pi.setKeyscontent(keyscontent.toString());
      AppPurchaseIncome api = new AppPurchaseIncome();
      api.addPurchaseIncome(pi);

      request.setAttribute("result", "databases.add.success");

      DBUserLog.addUserLog(userid,7,"入库>>新增采购入库,编号："+piid); 
      return mapping.findForward("addresult");
    }
    catch (Exception e) {
      e.printStackTrace();
    }



    return new ActionForward(mapping.getInput());
  }
}
