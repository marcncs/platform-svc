package com.winsafe.drp.action.sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFwmCreateCode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FwmCreate;
import com.winsafe.hbm.util.MakeCode;

public class AddMarubiBarcodeLogging extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String type = request.getParameter("type");
			String productname = request.getParameter("productname").trim();
			productname = new AppProduct().getProductByID(productname).getProductname();
			productname = productname.replace("*", "-");
			productname = productname.replace("/", "-");
			String productcode = request.getParameter("productcode").trim();
			String daoformat = request.getParameter("daoformat").trim();
			String date = request.getParameter("date").trim();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//得到时间后转成日期型
			Date riqi = format.parse(date);
			int fwmnum = Integer.parseInt(request.getParameter("fwmnum").trim());
			//得到企业名称ID
			String enterpriseName = request.getParameter("enterpriseName");
			String enterpriseId = request.getParameter("enterpriseId");
			AppFwmCreateCode afcc = new AppFwmCreateCode();
			FwmCreate fwmcreate = new FwmCreate();
			String fwmid = Math.round(Math.random() * (99999 - 10000) + 10000) + "01";
			fwmcreate.setId(Integer.valueOf(fwmid.trim()));
			fwmcreate.setFwmClientName(enterpriseName);
			fwmcreate.setFwmClientCode(enterpriseId);
			fwmcreate.setFwmProductName(productname);
			fwmcreate.setFwmProductCode("");
			fwmcreate.setFwmDaoFormat(daoformat);
			fwmcreate.setFwmSellArea("");
			fwmcreate.setFwmCreateDate(riqi);
			fwmcreate.setFwmLength(16);
			fwmcreate.setFwmNum(fwmnum);
			fwmcreate.setFwmBuMaLv(0);
			fwmcreate.setProductbrand("");
			fwmcreate.setOrdercode("");
			//0代表未生成，1代表生成中，2代表已生成
			fwmcreate.setIsDeal(0);
			//1代表上海酒业
			fwmcreate.setAtype("1");
			//1代表已申请，0代表未申请
			fwmcreate.setIsApply(1);
			//0代表未审批，1代表已审批
			fwmcreate.setIsApprove(0);
			//设置条码规则
			fwmcreate.setCodeRuleId(null);
			//设置产品类别编号（物流码前缀）
			fwmcreate.setIcode(productcode);
			//设置产品箱对最小单位的比例
			fwmcreate.setRelation("");
			String codeApplyID = MakeCode.getExcIDByRandomTableName("code_apply", 2, "FW");
			//设置申请编号
			fwmcreate.setFwmCodeFile(codeApplyID);
 			afcc.AddFwmCreate(fwmcreate);
			request.setAttribute("id", fwmcreate.getId());
			request.setAttribute("result", "申请成功");
			return mapping.findForward("add");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "新增失败!");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
	}
}
