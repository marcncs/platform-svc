package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.action.finance.UpgradeIntegralService;
import com.winsafe.drp.dao.AppCIntegral;
import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.AppIncomeLogDetail;
import com.winsafe.drp.dao.AppOIntegral;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.CIntegral;
import com.winsafe.drp.dao.CIntegralDeal;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.IdcodeDetail;
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.dao.IncomeLogDetail;
import com.winsafe.drp.dao.OIntegral;
import com.winsafe.drp.dao.OIntegralDeal;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddPeddleOrderAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

	  super.initdata(request);
    try {
    String cid = request.getParameter("cid");
    if(cid==null||cid.equals("null")||cid.equals("")){
    	String result = "databases.add.fail";
    	request.setAttribute("result", result);
    	return new ActionForward("/sys/lockrecord.jsp");
    }
    
    AppCustomer ac = new AppCustomer();
    Customer c = ac.getCustomer(cid);
    String cname=request.getParameter("cname");
    String cmobile = request.getParameter("cmobile");
    String receiveman=request.getParameter("decideman");
    String receivetel = request.getParameter("decidemantel");
    Integer paymentmode = Integer.valueOf(request.getParameter("paymentmode"));
    String strfactsum = request.getParameter("factsum");
    Integer isaccount = Integer.valueOf(request.getParameter("isaccount"));
    Double factsum = 0d;
    if(DataValidate.IsDouble(strfactsum)){
    	factsum = Double.valueOf(strfactsum);
    }
    String remark = request.getParameter("remark");
    String invmsg=request.getParameter("invmsg");
    
    //-----------------------------
    
    double totalsum=0.00;

    
    /*
    String strproductid[] = request.getParameterValues("productid");
    String strproductname[] = request.getParameterValues("productname");
    String strspecmode[] = request.getParameterValues("specmode");
    String strsalesort[] = request.getParameterValues("salesort");
    String strwarehouseid[] = request.getParameterValues("warehouseid");
    String strunitid[] = request.getParameterValues("unitid");
    String strunitprice[] = request.getParameterValues("unitprice");
    String strtaxunitprice[] = request.getParameterValues("taxunitprice");
    String strquantity[] = request.getParameterValues("quantity");
//    String strkickback[] = request.getParameterValues("kickback");
    String strdiscount[] = request.getParameterValues("discount");
    String strtaxrate[] = request.getParameterValues("taxrate");    
    //String strsubsum[] = request.getParameterValues("subsum");
    //String strbatch[] = request.getParameterValues("batch");
    String strcost[] = request.getParameterValues("cost");
    String codes[] = request.getParameterValues("codes");
    */
    
	String productid[] = request.getParameterValues("productid");
	String productname[] = request.getParameterValues("productname");
	String specmode[] = request.getParameterValues("specmode");
	int salesort[] = RequestTool.getInts(request, "salesort");
	int wise[] = RequestTool.getInts(request, "wise");
	String warehouseid[] = request.getParameterValues("warehouseid");
	int unitid[] = RequestTool.getInts(request, "unitid");
	double orgunitprice[] = RequestTool.getDoubles(request, "orgunitprice");
	double unitprice[] = RequestTool.getDoubles(request, "unitprice");
	double taxunitprice[] = RequestTool.getDoubles(request, "taxunitprice");
	double quantity[] = RequestTool.getDoubles(request, "quantity");
	double discount[] = RequestTool.getDoubles(request, "discount");
	double taxrate[] = RequestTool.getDoubles(request, "taxrate");
	double cost[] = RequestTool.getDoubles(request, "cost");
	String codes[] = request.getParameterValues("codes");
    
    Double subsum,kickback;
    
    //------------------检查序号--------------------
    AppIdcode appidcode = new AppIdcode();
    for (int i = 0; i < productid.length; i++) {
    	//System.out.println("=========codes="+codes[i]);
    	if( codes[i] == null || "".equals(codes[i]) ){
    		continue;
    	}
	    String[] idcodes = codes[i].split(",");

	    for ( int j=0; j<idcodes.length; j++ ){
			if ( j+1 > quantity[i] ){
				continue;
			}
//			Idcode ic = appidcode.getIdcodeById(productid[i], idcodes[j]);
//			if ( ic == null || ic.getIsuse() == 0 ){
//			    request.setAttribute("result","databases.record.nouseidcode");
//			    return new ActionForward("/sys/lockrecord.jsp");
//			}
		}
    }
    //----------------------------------------------------------------

    SaleOrder so = new SaleOrder();
	String soid = MakeCode.getExcIDByRandomTableName("peddle_order", 2,"PD");
	so.setId(soid);
	so.setSosort(0);
	so.setCustomerbillid(request.getParameter("customerbillid"));
	so.setCid(cid);
	so.setCname(request.getParameter("cname"));
	so.setCmobile(request.getParameter("cmobile"));
	so.setProvince(c.getProvince());
	so.setCity(c.getCity());
	so.setAreas(c.getAreas());
	so.setDecideman(request.getParameter("decideman"));
	so.setDecidemantel(request.getParameter("decidemantel"));
	so.setReceiveman(request.getParameter("receiveman"));
	so.setReceivemobile(request.getParameter("receivemobile"));
	so.setReceivetel(request.getParameter("receivetel"));
	so.setPaymentmode(RequestTool.getInt(request, "paymentmode"));
	so.setConsignmentdate(DateUtil.getCurrentDate());
	so.setSource(RequestTool.getInt(request, "source"));
	so.setTransportmode(RequestTool.getInt(request, "transportmode"));
	so.setTransit(0);
	so.setTransportaddr(request.getParameter("transportaddr"));
	so.setInvmsg(RequestTool.getInt(request, "invmsg"));
	so.setIsmaketicket(0);
	so.setTickettitle(request.getParameter("tickettitle"));
	so.setMakeorganid(users.getMakeorganid());
	so.setMakedeptid(users.getMakedeptid());
	so.setEquiporganid(users.getMakeorganid());
	so.setRemark( request.getParameter("remark"));
	so.setMakeid(userid);
	so.setMakedate(DateUtil.getCurrentDate());
	so.setIsaudit(0);
	so.setIsendcase(0);
	so.setIsblankout(0);
	so.setTakestatus(0);
	so.setIsdaybalance(0);
	so.setIsaccount(RequestTool.getInt(request, "IsAccount"));
    

      StringBuffer keyscontent = new StringBuffer();
      keyscontent.append(so.getId()).append(",").append(so.getCname()).append(",")
      .append(so.getCmobile()).append(",").append(so.getReceiveman()).append(",")
      .append(so.getReceivetel()).append(",");
      
      AppProductStockpile aps = new AppProductStockpile();
      AppSaleOrder asl = new AppSaleOrder();
      AppSaleOrderDetail asld = new AppSaleOrderDetail();

      IdcodeDetail pii = null;
      AppIdcodeDetail apid = new AppIdcodeDetail();
      String batch="";
      
      for (int i = 0; i < productid.length; i++) {

        subsum = DataFormat.countPrice(quantity[i], taxunitprice[i], discount[i], taxrate[i]);

        SaleOrderDetail sod = new SaleOrderDetail();       
        sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("sale_order_detail",0,"")));
        sod.setSoid(soid);        
        sod.setProductid(productid[i]);
        sod.setProductname(productname[i]);
        sod.setSpecmode(specmode[i]);
        sod.setSalesort(salesort[i]);
        sod.setWarehouseid(warehouseid[i]);
        sod.setUnitid(unitid[i]);
        sod.setUnitprice(unitprice[i]);
        sod.setTaxunitprice(taxunitprice[i]);
        sod.setQuantity(quantity[i]);
//        sod.setTakequantity(0d);
        sod.setDiscount(discount[i]);
        sod.setTaxrate(taxrate[i]);
        sod.setBatch("");
        sod.setCost(cost[i]);
        sod.setSubsum(subsum);      
        asld.addSaleOrderDetail(sod);
        totalsum += sod.getSubsum(); 
        keyscontent.append(sod.getProductid()).append(",")
        .append(sod.getProductname()).append(",")
        .append(sod.getSpecmode()).append(",");
        
        
//		aps.returninProductStockpile(productid[i], batch, quantity[i], warehouseid[i],soid,"零售单-出货");
		
		//====================序号管理 =====================	    	
		String[] idcodes = codes[i].split(",");
		for ( int j=0; j<idcodes.length; j++ ){
			if ( j+1 > sod.getQuantity().intValue() ){
				continue;
			}
			pii = new IdcodeDetail();
			pii.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"idcode_detail", 0, "")));
			pii.setBillid(so.getId());
			pii.setProductid(sod.getProductid());
			pii.setProductname(sod.getProductname());
			pii.setSpecmode(sod.getSpecmode());
			pii.setIdcode(idcodes[j]);
			pii.setMakedate(DateUtil.getCurrentDate());
//			pii.setMakeid(userid);
			pii.setMakeuser(users.getRealname());
			pii.setIdbilltype(9);
			apid.addIdcodeDetail(pii);
//			appidcode.updIsUse(pii.getProductid(), pii.getIdcode(), 0);
		}
		//==================================================
      }
      
      so.setTotalsum(totalsum);
      so.setKeyscontent(keyscontent.toString());
      asl.addSaleOrder(so);
      
      
      
//      List pils = asld.getPeddleOrderDetailObjectByPOID(soid);	

		AppReceivableObject appro = new AppReceivableObject();
		ReceivableObject ro = new ReceivableObject();
		ro.setOid(so.getCid());
		ro.setObjectsort(1);
		ro.setPayer(so.getCname());
		ro.setMakeorganid(users.getMakeorganid());
		ro.setMakedeptid(users.getMakedeptid());
		ro.setMakeid(userid);
		ro.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
		ro.setKeyscontent(so.getCid()+","+so.getCname()+","+so.getCmobile());
		appro.addReceivableObjectIsNoExist(ro);
		//System.out.println("=============so.getPaymentmode()="+so.getPaymentmode());
		
		if ( so.getIsaccount() == 0 ){
			
//		    PeddleIntegralDealService ids = new PeddleIntegralDealService(so, pils);
//		    ids.dealIntegral();
			
		    
			AppReceivable ar = new AppReceivable();
			Receivable r = new Receivable();
			r.setId(MakeCode.getExcIDByRandomTableName("receivable",2,""));
			r.setRoid(so.getCid());
			r.setReceivablesum(so.getTotalsum());
			r.setPaymentmode(so.getPaymentmode());
			r.setBillno(so.getId());
			r.setReceivabledescribe(soid+"零售单生成应收款结算单");
			r.setAlreadysum(so.getTotalsum());
			r.setIsclose(1);
			r.setMakeorganid(users.getMakeorganid());
			r.setMakedeptid(users.getMakedeptid());
			r.setMakeid(so.getMakeid());
			r.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));		
			ar.addReceivable(r);
	
			
			IncomeLog il = new IncomeLog();
			il.setId(MakeCode.getExcIDByRandomTableName("income_log",2,"IL"));
			il.setRoid(so.getCid());
			il.setDrawee(so.getCname());
//			il.setFundattach(0l);			
			il.setIncomesum(so.getTotalsum());
			il.setBillnum(so.getId());		
			il.setPaymentmode(paymentmode);
			il.setRemark(soid+"零售单收款");
			il.setMakeorganid(users.getMakeorganid());
			il.setMakedeptid(users.getMakedeptid());
			il.setMakeid(userid);
			il.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			il.setIsaudit(1);
			il.setAuditid(userid);
			il.setAuditdate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			il.setIsreceive(1);
			il.setReceiveid(userid);
			il.setReceivedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			StringBuffer ilkeysc = new StringBuffer();
			ilkeysc.append(il.getId()).append(",").append(il.getRoid()).append(",")
			.append(il.getDrawee()).append(",").append(so.getCmobile()).append(",")
			.append(r.getBillno()).append(",");
			il.setKeyscontent(ilkeysc.toString());
			AppIncomeLog ail = new AppIncomeLog();
			ail.addIncomeLog(il);
			

			IncomeLogDetail ild = new IncomeLogDetail();
//			ild.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("income_log_detail",0,"")));
			ild.setIlid(il.getId());
			ild.setRid(r.getId());
			ild.setReceivablesum(r.getReceivablesum());
			ild.setPaymentmode(r.getPaymentmode());
			ild.setBillno(r.getBillno());
			ild.setThisreceivablesum(r.getReceivablesum());
			AppIncomeLogDetail appild = new AppIncomeLogDetail();
			appild.addIncomeLogDetail(ild);
			
			
			AppCIntegralDeal acid = new AppCIntegralDeal();
			AppOIntegralDeal aoid = new AppOIntegralDeal();
			AppCIntegral aci = new AppCIntegral();
			AppOIntegral aoi = new AppOIntegral();
			
			
			List cidls = acid.getCIntegralDealByBillno(so.getId());
			for(int cd=0;cd<cidls.size();cd++){
				CIntegral ci = new CIntegral();
				CIntegralDeal o=(CIntegralDeal)cidls.get(cd);
				ci.setOrganid(o.getOrganid());
				ci.setCid(o.getCid());
				ci.setIsort(o.getIsort());
				ci.setCintegral(o.getDealintegral());
				ci.setIyear(DateUtil.getCurrentYear());
				//System.out.println("---CIntegral=="+ci.getCintegral());
				aci.addCIntegralIsNoExist(ci);
				aci.updCIntegralByIntegral(ci);
			}
			
			
			List oidls = aoid.getOIntegralDealByBillno(so.getId());
			for(int od=0;od<oidls.size();od++){
				OIntegral oi = new OIntegral();
				OIntegralDeal obj = (OIntegralDeal)oidls.get(od);
				oi.setPowerorganid("1");
				oi.setEquiporganid(obj.getOid());
				oi.setIsort(obj.getIsort());
				oi.setOintegral(obj.getDealintegral());
				oi.setIyear(DateUtil.getCurrentYear());
				aoi.addOIntegralIsNoExist(oi);
				aoi.updOIntegralByIntegral(oi);
	
			}
			
			
			UpgradeIntegralService uis = new UpgradeIntegralService();
			uis.upgrade(so.getCid(), so.getTotalsum());
		}else{
			
			//System.out.println("=========生成应收款结算===");
			AppReceivable ar = new AppReceivable();
			Receivable r = new Receivable();
			r.setId(MakeCode.getExcIDByRandomTableName("receivable",2,""));
			r.setRoid(so.getCid());
			r.setReceivablesum(so.getTotalsum());
			r.setPaymentmode(so.getPaymentmode());
			r.setBillno(so.getId());
			r.setReceivabledescribe(soid+"零售单生成应收款结算单");
			r.setAlreadysum(0d);
			r.setIsclose(0);
			r.setMakeorganid(users.getMakeorganid());
			r.setMakedeptid(users.getMakedeptid());
			r.setMakeid(so.getMakeid());
			r.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));		
			ar.addReceivable(r);
		}


      request.setAttribute("result", "databases.add.success");
      request.setAttribute("gourl", "../sales/toAddPeddleOrderAction.do");
      DBUserLog.addUserLog(userid,"新增零售单"); 

      return mapping.findForward("addresult");
    }catch (Exception e) {
      e.printStackTrace();
    }


    return new ActionForward(mapping.getInput());
  }
}
