package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.AppPurchaseOrderDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrderDetail;
import com.winsafe.drp.dao.PurchaseOrderDetailForm;
import com.winsafe.drp.dao.PurchaseOrderForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.Internation;

public class PrintPurchaseOrderAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		try {
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/PurchaseOrder.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppPurchaseOrder aps = new AppPurchaseOrder();
			AppDept ad = new AppDept();
			PurchaseOrder po = new PurchaseOrder();
			po = aps.getPurchaseOrderByID(id);
			PurchaseOrderForm pof = new PurchaseOrderForm();
			
			AppUsers au = new AppUsers();
			AppCustomer apv = new AppCustomer();

			pof.setId(id);
			pof.setProvidename(apv.getCustomer(po.getPid()).getCname());
			pof.setPlinkman(po.getPlinkman());
			pof.setTel(po.getTel());
			pof.setPurchasedeptname(ad.getDeptByID(po.getPurchasedept()).getDeptname());
			pof.setPurchaseidname(au.getUsersByid(po.getPurchaseid()).getRealname());
			pof.setPaymentmodename(Internation.getStringByPayPositionDB(po.getPaymentmode()));
			pof.setReceivedate(po.getReceivedate().toString());
			//pbf.setReceiveaddr(pb.getReceiveaddr());
			pof.setTotalsum(po.getTotalsum());
			//pbf.setMakedate(pb.getMakedate());
			pof.setMakeidname(au.getUsersByID(po.getMakeid()).getRealname());

			Map parameters = new HashMap();
			//parameters.put("title", Internation.getStringByKeyPosition("PrinterName",
		    //        request,0, "global.sys.SystemResource"));
			parameters.put("billtype", "物品采购订单");
			parameters.put("id", pof.getId());
			parameters.put("providename", pof.getProvidename());
			parameters.put("plinkman", pof.getPlinkman());
			parameters.put("tel", pof.getTel());
			parameters.put("purchasedeptname", pof.getPurchasedeptname());
			parameters.put("purchaseidname", pof.getPurchaseidname());
			parameters.put("paymentmodename",pof.getPaymentmodename());
			parameters.put("receivedate", pof.getReceivedate().toString().substring(0, 10));
			parameters.put("receiveaddr", po.getReceiveaddr());
			parameters.put("remark", po.getRemark());
			parameters.put("totalsumcapital", DataValidate.arabiaToChinese(String.valueOf(pof
					.getTotalsum())));
			parameters.put("totalsum", po.getTotalsum());
			parameters.put("makeidname", pof.getMakeidname());
			parameters.put("makedate", po.getMakedate().toString());
			if ( po.getPrinttimes() == null || po.getPrinttimes() == 0 ){
				po.setPrinttimes(1);
			}
			parameters.put("printtime", po.getPrinttimes());
			parameters.put("printman", users.getRealname());

			po.setPrinttimes(po.getPrinttimes()+1);
			aps.updPurchaseOrder(po);


			AppPurchaseOrderDetail apbd = new AppPurchaseOrderDetail();
			List pbls = apbd.getPurchaseOrderDetailByPoID(id);
			ArrayList als = new ArrayList();
			AppProduct ap = new AppProduct();
			PurchaseOrderDetail pod = null;
			for (int i = 0; i < pbls.size(); i++) {
				PurchaseOrderDetailForm sldf = new PurchaseOrderDetailForm();
				pod = (PurchaseOrderDetail) pbls.get(i);
				//sldf.setProductid(o[2].toString());
				sldf.setProductname(pod.getProductid()+"  "+pod.getProductname()+"  "+pod.getSpecmode());
				//sldf.setSpecmode(String.valueOf(o[4]));
				//padf.setUnitid(Integer.valueOf(o[3].toString()));
				sldf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit", 
						pod.getUnitid()));
				sldf.setUnitprice(pod.getUnitprice());
				sldf.setQuantity(pod.getQuantity());
				sldf.setSubsum(pod.getSubsum());
				als.add(sldf);
			}

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JRBeanCollectionDataSource(als));

			{
				byte[] bytes = JasperExportManager.exportReportToPdf(print);
				if (bytes != null && bytes.length > 0) {
					response.setContentType("application/pdf");
					response.setContentLength(bytes.length);
					ServletOutputStream ouputStream = response
							.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();
				}
			}
			//return mapping.findForward("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
