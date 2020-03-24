package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.PurchaseOrderDetailForm;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.metadata.PlantType;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.FUnitUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager2.PageQuery;

public class PrintTakeTicketAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context
					.getRealPath("/WEB-INF/reports/TakeTicket.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppTakeTicket aps = new AppTakeTicket();
			WarehouseService aw = new WarehouseService();
			AppUsers au = new AppUsers();
			OrganService os = new OrganService();

			AppStockAlterMove asam=new AppStockAlterMove();
			
			TakeTicket tt = aps.getTakeTicketById(id);
			StockAlterMove sam=asam.getStockAlterMoveByID(tt.getBillno());
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			//Organ o = os.getOrganByID(tt.getMakeorganid());
			Organ o = os.getOrganByID(tt.getOid());  //取发货机构ID
			//parameters.put("organtitle", o.getOrganname());
			parameters.put("organtitle", "");  //不要显示
			parameters.put("title", "发货单");
			String path = "";
			if (null!=o.getLogo()) {
			if (o.getLogo().equals("")) {
				path = context.getRealPath("images\\logo\\logo.jpg");
			} else {
				path = context.getRealPath(o.getLogo());
			}
			} else {
				path = context.getRealPath("images\\logo.png");
			}
			parameters.put("img", path);

			parameters.put("billtype", "Inspection Voucher 检货小票");
			//parameters.put("id", tt.getId());
			parameters.put("id", tt.getNccode());
			parameters.put("makedate", DateUtil
					.formatDateTime(tt.getMakedate()));
			parameters.put("billno", tt.getBillno());	
			
			UsersService us = new UsersService();
			Users  u = us.getUsersByid(tt.getMakeid());
			if (u.getRealname()==null) parameters.put("name", ""); else parameters.put("name", u.getRealname());
			parameters.put("tel",u.getOfficetel()+"/"+u.getMobile());
			parameters.put("email", u.getEmail());
			parameters.put("fax", "");

			parameters.put("pid", tt.getOid());
			
			parameters.put("pname", tt.getOname());
			if (sam.getTransportaddr()==null) parameters.put("paddr","" ); else parameters.put("paddr",sam.getTransportaddr() ); //订单中的发货地址
			if (tt.getRlinkman()==null) parameters.put("plinkman", ""); else parameters.put("plinkman", tt.getRlinkman());
			if (tt.getTel()==null) parameters.put("ptel", "");else parameters.put("ptel", tt.getTel());

			AppOlinkMan appO = new AppOlinkMan();
			List<Olinkman> list = appO.getOlinkmanByCid(o.getId());
			Olinkman olinkman = list.get(0);
			
			if ( o.getOrganname()==null) parameters.put("oname",""); else parameters.put("oname", o.getOrganname());
			if ( o.getOaddr()==null) parameters.put("oaddr","");else parameters.put("oaddr", o.getOaddr());
			if (olinkman.getName()==null) parameters.put("olinkman", "");else parameters.put("olinkman", olinkman.getName());
			if (olinkman.getOfficetel()==null) parameters.put("otel", "");else parameters.put("otel", olinkman.getOfficetel());
			if (olinkman.getMobile()==null) parameters.put("omobile", "");else parameters.put("omobile", olinkman.getMobile());
			//parameters.put("warehousename", aw.getWarehouseName(tt.getWarehouseid()));
			parameters.put("warehousename", aw.getWarehouseName(tt.getInwarehouseid()));

			parameters.put("printb", "");
			parameters.put("printc", "");
			parameters.put("remark", tt.getRemark());
			if (!(null==tt.getRemark())) parameters.put("remark", tt.getRemark()); else  parameters.put("remark","");  
			
			
			parameters.put("makeidname", au.getUsersByID(tt.getMakeid())
					.getRealname());
			String takeidname = "";
			if (tt.getTakeid() != null && tt.getTakeid() > 0) {
				takeidname = au.getUsersByID(tt.getTakeid()).getRealname();
			}

			parameters.put("takeidname", takeidname);

			//设置的打印次数
			if (tt.getPrinttimes() == null || tt.getPrinttimes() == 0) {
				tt.setPrinttimes(1);
			}
			tt.setPrinttimes(tt.getPrinttimes() + 1);
			aps.updTakeTicket(tt);

			AppTakeTicketDetail apbd = new AppTakeTicketDetail();
			AppProduct aproduct=new AppProduct();
			Product prd=new Product();
			
			AppFUnit appFUnit = new AppFUnit();
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			
			List<TakeTicketDetail> pbls = apbd.getTakeTicketDetailByTtid(id);
			List<PurchaseOrderDetailForm> als = new ArrayList<PurchaseOrderDetailForm>();
			
			List<Map> listTTidcode =  new ArrayList<Map>();
			AppTakeTicketIdcode appttidcode = new AppTakeTicketIdcode();
			if (OrganType.Plant.getValue().equals(o.getOrganType()) && PlantType.Toller.getValue().equals(o.getOrganModel())) {
				listTTidcode = appttidcode.getTtdGroupTTIFromPrintJob(id);
			} else {
				listTTidcode = appttidcode.getTtdGroupTTI(id);
			}
			
			if(listTTidcode != null && listTTidcode.size()>0){
				for(Map map : listTTidcode){
					String productid = (String)map.get("productid");
					Double receiveQuantity = Double.valueOf((String)map.get("total"));
					String productBatch = (String)map.get("batch");
					
					prd=aproduct.getProductByID(productid);//获取产品信息
					
					PurchaseOrderDetailForm sldf = new PurchaseOrderDetailForm();

					sldf.setProductid(prd.getmCode());  //产品代码需要修改为SAP的代码
					//sldf.setProductname(prd.getProductname() + " | "+ prd.getPackSizeName());
					sldf.setProductname( prd.getPackSizeName());
					sldf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit", prd.getCountunit()));
					
					sldf.setQuantity(receiveQuantity);//箱数
					Double countValue=FUnitUtil.changeUnit(productid, Constants.DEFAULT_UNIT_ID, receiveQuantity, prd.getSunit(),funitMap);
					countValue=countValue*prd.getBoxquantity();
					sldf.setUnitprice(countValue);//uintprice 设置为计量单位的数量
					sldf.setPoid(productBatch);				//批次号用POID进行。
					als.add(sldf);
				}
			} else { //如果未上传码
				
				for (TakeTicketDetail ttd : pbls) {
					prd=aproduct.getProductByID(ttd.getProductid());//获取产品信息
					
					PurchaseOrderDetailForm sldf = new PurchaseOrderDetailForm();
					//sldf.setProductid(ttd.getProductid());  
					sldf.setProductid(prd.getmCode());  //产品代码需要修改为SAP的代码
					//sldf.setProductname(ttd.getProductname() + " | "+ prd.getPackSizeName());
					sldf.setProductname( prd.getPackSizeName());
					sldf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit",  prd.getCountunit()));
					
					sldf.setQuantity(ttd.getQuantity());//箱数
					//sldf.setUnitprice(ttd.getUnitprice());
					Double countValue=FUnitUtil.changeUnit(ttd.getProductid(), ttd.getUnitid(), ttd.getQuantity(), prd.getSunit(),funitMap);
					countValue=countValue*prd.getBoxquantity();
					sldf.setUnitprice(countValue);//uintprice 设置为计量单位的数量
					sldf.setPoid("");				//批次号用POID进行。
					als.add(sldf);
				}
			}

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JRBeanCollectionDataSource(als));
			byte[] bytes = JasperExportManager.exportReportToPdf(print);
			if (bytes != null && bytes.length > 0) {
				response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();
			}

			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 4, "打印检货小票");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
