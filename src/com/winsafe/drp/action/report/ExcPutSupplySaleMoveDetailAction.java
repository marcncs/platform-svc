package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.SSMDetailReportForm;
import com.winsafe.drp.dao.SupplySaleMoveDetail;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutSupplySaleMoveDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = "pw.id=pwd.ssmid and (pw.iscomplete=1 and pw.isblankout=0 " + visitorgan+ " )  ";
			String[] tablename = { "SupplySaleMove", "SupplySaleMoveDetail"  };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSupplySaleMove asod=new AppSupplySaleMove(); 
	        List sodls = asod.getDetailReport(whereSql);
	        ArrayList alsod=new ArrayList();
	        Double totalsum=0.00;
	        for(int d = 0;d<sodls.size();d++){
	        	SSMDetailReportForm sodf = new SSMDetailReportForm();
	        	Object[] o = (Object[])sodls.get(d);
	        	String morganname = (String)o[0];
	        	String rorganname = (String)o[1];
	        	String sorganname = (String)o[2];
	        	String makedate = DateUtil.formatDateTime((Date)o[3]);
	        	SupplySaleMoveDetail pbd = (SupplySaleMoveDetail)o[4];
	        	sodf.setOname(morganname);
	        	sodf.setRname(rorganname);
	        	sodf.setSname(sorganname);
	        	sodf.setMakedate(makedate);
	        	sodf.setBillid(pbd.getSsmid());
	        	sodf.setProductid(pbd.getProductid());
	        	sodf.setProductname(pbd.getProductname());
	        	sodf.setSpecmode(pbd.getSpecmode());
	        	sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", pbd.getUnitid()));
	        	sodf.setPunitprice(pbd.getPunitprice());
	        	sodf.setSunitprice(pbd.getSunitprice());
	        	sodf.setQuantity(pbd.getQuantity());
	        	sodf.setPsubsum(pbd.getPsubsum());
	        	sodf.setSsubsum(pbd.getSsubsum());
	        	totalsum += sodf.getSsubsum();
	        	alsod.add(sodf);
	        }
		       
	        if (alsod.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=SupplySaleMoveDetail.xls");
			response.setContentType("application/msexcel");
			writeXls(alsod, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出渠道代销明细");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List<SSMDetailReportForm> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organ = new OrganService();
		int snum = 1;
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 50000;

			sheets[j].mergeCells(0, start, 13, start);
			sheets[j].addCell(new Label(0, start, "渠道代销明细",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "申请机构:",seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("SupplyOrganIDName")));
			sheets[j].addCell(new Label(4, start+1, "调入机构:",seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("ReceiveOrganIDName")));
			
			sheets[j].addCell(new Label(0, start+2, "订购日期:",seachT));
			sheets[j].addCell(new Label(1, start+2, map.get("BeginDate").toString()+"-"+map.get("EndDate").toString()));
			sheets[j].addCell(new Label(2, start+2, "产品:",seachT));
			sheets[j].addCell(new Label(3, start+2, map.get("ProductName").toString()));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			
			sheets[j].addCell(new Label(0, start+4, "调入机构",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "制单机构",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "制单时间",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "申请机构",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "单据号",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "产品编号",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "产品名称",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "规格",wcfFC));
			sheets[j].addCell(new Label(8, start+4, "单位",wcfFC));
			sheets[j].addCell(new Label(9, start+4, "订购单价",wcfFC));
			sheets[j].addCell(new Label(10, start+4, "销售单价",wcfFC));
			sheets[j].addCell(new Label(11, start+4, "数量",wcfFC));
			sheets[j].addCell(new Label(12, start+4, "订购金额",wcfFC));
			sheets[j].addCell(new Label(13, start+4, "销售金额",wcfFC));
			
			
			int row = 0;
			Double totalqt = 0.00;
			Double stotalsum = 0.00;
			Double ptotalsum = 0.00;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				SSMDetailReportForm p = (SSMDetailReportForm) list.get(i);
				sheets[j].addCell(new Label(0, row, organ.getOrganName(p.getRname())));
				sheets[j].addCell(new Label(1, row, organ.getOrganName(p.getOname())));
				sheets[j].addCell(new Label(2, row, p.getMakedate()));
				sheets[j].addCell(new Label(3, row , organ.getOrganName(p.getSname())));
				sheets[j].addCell(new Label(4, row, p.getBillid()));
				sheets[j].addCell(new Label(5, row, p.getProductid()));
				sheets[j].addCell(new Label(6, row, p.getProductname()));
				sheets[j].addCell(new Label(7, row, p.getSpecmode()));
				sheets[j].addCell(new Label(8, row, p.getUnitidname()));
				sheets[j].addCell(new Number(9, row, p.getPunitprice(),wcfN));
				sheets[j].addCell(new Number(10, row, p.getSunitprice(),wcfN));
				sheets[j].addCell(new Number(11, row, p.getQuantity(),QWCF));
				sheets[j].addCell(new Number(12, row, p.getPsubsum(),wcfN));
				sheets[j].addCell(new Number(13, row, p.getSsubsum(),wcfN));
				totalqt += p.getQuantity();
				stotalsum += p.getSsubsum();
				ptotalsum += p.getPsubsum();
			}
			sheets[j].addCell(new Label(0, row + 1, "合计："));
			sheets[j].addCell(new Label(1, row + 1, ""));
			sheets[j].addCell(new Label(2, row + 1, ""));
			sheets[j].addCell(new Label(3, row + 1, ""));
			sheets[j].addCell(new Label(4, row + 1, ""));
			sheets[j].addCell(new Label(5, row + 1, ""));
			sheets[j].addCell(new Label(6, row + 1, ""));
			sheets[j].addCell(new Label(7, row + 1, ""));
			sheets[j].addCell(new Label(8, row + 1, ""));
			sheets[j].addCell(new Label(9, row + 1, ""));
			sheets[j].addCell(new Label(10, row + 1, ""));
			sheets[j].addCell(new Number(11, row + 1, totalqt,QWCF));
			sheets[j].addCell(new Number(12, row + 1, ptotalsum,wcfN));
			sheets[j].addCell(new Number(13, row + 1, stotalsum,wcfN));
		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
