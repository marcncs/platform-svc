package com.winsafe.erp.action;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.erp.action.form.DownloadFinaCodeForm;
import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.PrintJob;

public class DownloadFinaCodeAction extends BaseAction {

	private AppProductPlan appProductPlan = new AppProductPlan();
	private static Logger logger = Logger.getLogger(DownloadFinaCodeAction.class);
	private String mcode = "";
	private String pbatcn = "";
	private AppPrintJob appPrintJob = new AppPrintJob();

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);

		try {
			String id = request.getParameter("ID");
			id = id.substring(0, id.length()-1);
//			ProductPlan plan = appProductPlan.getProductPlanByID(id);
	/*		pbatcn = plan.getPbatch();
			
			AppProduct appProduct = new AppProduct();
			Product product = appProduct.getProductByID(plan.getProductId());
			if(null!=product){
				mcode = product.getmCode();
			}
			
			String whereSql = " where  productPlanId = "+plan.getId()+" and ( isrelease is null or isrelease=0) and tcode !='t' order by code ";
			List<PrepareCode>  listcode = apppre.getPrepareCodeByWhere(whereSql);
			*/
			
			AppPrepareCode apppre = new AppPrepareCode();
			List list = apppre.getPrepareCodeByProductPlanId(id);
			List<DownloadFinaCodeForm> dfcflist = new ArrayList<DownloadFinaCodeForm>();
//			PrintJob printJob = appPrintJob.getPrintJobByProcessOrderNumber(id)
			for (int i = 0; i < list.size(); i++) {
				DownloadFinaCodeForm dfcf = new DownloadFinaCodeForm();
				Map map = (Map)list.get(i);
				dfcf.setCode((String)map.get("code"));
				dfcf.setMcode((String)map.get("mcode"));
				dfcf.setPbatch((String)map.get("pbatch"));//packDate
				dfcf.setPackDate((String)map.get("packdate"));
				dfcf.setMbatch((String)map.get("mbatch"));
				dfcf.setOidpid((String)map.get("oidpid"));
				dfcf.setPono((String)map.get("pono"));
				dfcf.setPrintJobId((String)map.get("printjobid"));
				dfcflist.add(dfcf);
			}
			
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition","attachment; filename=CodeOf"+DateUtil.formatDateTime(new Date())+".xls");
			response.setContentType("application/msexcel");
			writeXls(dfcflist, os, request);
			os.flush();
			os.close();
			
		} catch (Exception e) {
			logger.error("Download Product Plan Code  error:", e);
		}
		return null;
	}
	public void writeXls(List<DownloadFinaCodeForm> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		/*OrganService organs = new OrganService();
		UsersService us = new UsersService();
		WarehouseService ws = new WarehouseService();*/
		
		AppUnitInfo appUnitInfo = new AppUnitInfo();
		Map<String,String> needRepackage = appUnitInfo.getNeedRepakageMap();
		Map<String,PrintJob> printJobMap = new HashMap<String,PrintJob>(); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");
		
		int snum = 1; 
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 40000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 40000;
			sheets[j].addCell(new Label(0, start, "BUN_EXIDV", wcfFC));
			sheets[j].addCell(new Label(1, start, "MATNR", wcfFC));
			sheets[j].addCell(new Label(2, start, "CHARG", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 1;
				DownloadFinaCodeForm p = (DownloadFinaCodeForm) list.get(i);
				
				PrintJob printJob = null;
				if(!StringUtil.isEmpty(p.getPrintJobId())) {
					printJob = printJobMap.get(p.getPrintJobId());
					if(printJob == null) {
						printJob = appPrintJob.getPrintJobById(p.getPrintJobId());
						printJobMap.put(p.getPrintJobId(), printJob);
					}
				} else {
					printJob = printJobMap.get(p.getPono());
					if(printJob == null) {
						printJob = appPrintJob.getPrintJobByProcessOrderNumber(p.getPono());
						printJobMap.put(p.getPono(), printJob);
					}
				}
				
				sheets[j].addCell(new Label(0, row, p.getCode()));
				sheets[j].addCell(new Label(1, row, p.getMcode()));
				if("1".equals(needRepackage.get(p.getOidpid()))) {
					sheets[j].addCell(new Label(2, row, printJob.getPackagingDate().concat(StringUtil.removeNull(p.getPbatch()))));
				} else {
					sheets[j].addCell(new Label(2, row, p.getMbatch()));
				}
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
//	private void writeTxt(List olist, OutputStream os){
//		BufferedOutputStream bos = null;
//		AppICode appic = new AppICode();
//		try{
//			bos = new BufferedOutputStream(os);			
//			for (int i=0; i<olist.size(); i++ ){
//				Object[] o = (Object[])olist.get(i);
//				PrepareCode p = (PrepareCode)o[0];
//				
//				bos.write(StringUtil.fillBack(p.getCode(), 20, Constants.TXT_FILL_STRING).getBytes());
//				
//				bos.write(StringUtil.fillBack("", 1, Constants.TXT_FILL_STRING).getBytes());
//				
//				bos.write(StringUtil.fillBack(mcode, 8, Constants.TXT_FILL_STRING).getBytes());
//				
//				bos.write(StringUtil.fillBack("", 1, Constants.TXT_FILL_STRING).getBytes());
//				
//				bos.write(StringUtil.fillBack(pbatcn, 10, Constants.TXT_FILL_STRING).getBytes());
//				
//				bos.write("\r\n".getBytes());
//				
//			}
//			bos.flush();
//			bos.close();
//		}catch ( Exception e ){
//			e.printStackTrace();
//		}finally{
//			if ( bos != null ){
//				try {
//					bos.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

}
