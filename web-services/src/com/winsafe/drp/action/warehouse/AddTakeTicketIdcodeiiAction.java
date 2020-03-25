package com.winsafe.drp.action.warehouse;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.server.AddTakeTicketIdcodeiiService;
import com.winsafe.drp.util.DBUserLog;

public class AddTakeTicketIdcodeiiAction extends BaseAction
{
	private static Logger logger = Logger.getLogger(AddTakeTicketIdcodeiiAction.class);
	//条码新增service
	private AddTakeTicketIdcodeiiService idcodeiiService = new AddTakeTicketIdcodeiiService();
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//初始化
		initdata(request);
		String idcode = request.getParameter("idcode");
		//TTid编号
		String billid = request.getParameter("billid");
		String productid = request.getParameter("prid");
		
		String result = "";
		
		try
		{
			//通过条码新增service进行条码的检查及新增
			idcodeiiService.dealIdcode(billid, idcode, productid,userid+"", users.getMakeorganid());
			
			result = "条码新增成功！";
			addMsg(request, response, result);
			DBUserLog.addUserLog(request, "单据号:"+billid+" 添加条码:"
					+ idcode);
			return null;
		} catch (IdcodeException e) {
			result = e.getMessage();
			addMsg(request, response, result);
		} catch (Exception e) {
			logger.error("",e);
			result = "条码新增失败! ";
			addMsg(request, response, result);
		}

		return null;
	}

	private void addMsg(HttpServletRequest request, HttpServletResponse response, String msg) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("result", msg);
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.close();
	}
	
	/**
	 * 将拆出的散码保存到Idcode
	* @param idcode
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Oct 25, 2012 5:28:28 PM
	 */
	
	public class Nidcode{
		public String batch;
		public String productid;
		public String productDate;
		public String warehousebit = "000";
		public int issplit=0;
		public Integer unitid;
		public String scanFlag;
		public Double packQuantity=1d;
		public String startno;
		public String endno;
		public String warehouseid;
		public Product product;
	}
}


