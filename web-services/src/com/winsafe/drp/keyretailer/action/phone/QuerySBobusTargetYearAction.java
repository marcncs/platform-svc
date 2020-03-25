package com.winsafe.drp.keyretailer.action.phone;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;


public class QuerySBobusTargetYearAction extends BaseAction {
	private Logger logger = Logger.getLogger(QuerySBobusTargetYearAction.class);
	
	private AppSBonusConfig appSBonusConfig = new AppSBonusConfig();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SBonusConfig sbc = appSBonusConfig.getCurrentPeriodConfig(DateUtil.getCurrentDateString(), 4);
			Map<String, String> map = new HashMap<String, String>();
			if(sbc != null) {
				map.put("year", sbc.getYear().toString());
			} else {
				Calendar today = Calendar.getInstance();
				map.put("year", String.valueOf(today.get(Calendar.YEAR)));
			}
			// 如果要下载的信息不为空，则进行下载操作
			ResponseUtil.writeJsonMsg(response,Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG,
					map,1,"APP","年份查询",false);
		} catch (Exception e) {
			logger.error("", e);
			HibernateUtil.rollbackTransaction();
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "系统异常");
		}
		return null;
	}
}
