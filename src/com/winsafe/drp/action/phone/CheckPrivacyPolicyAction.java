package com.winsafe.drp.action.phone;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class CheckPrivacyPolicyAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppBaseResource appBaseResource = new AppBaseResource();
		// 隐私声明版本号
		String privacyVersion = request.getParameter("privacy_version");
		// 隐私声明类别。1：BCSChinaCISforSales(拜耳作物客户信息系统)，2：ProductTraceTool(拜追踪)，3：ProductVerificationTool(拜验证)
		String type = request.getParameter("type");
		
		if (StringUtils.isBlank(privacyVersion) || StringUtils.isBlank(type)) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "参数不能为空");
			return null;
		}

		String dbPrivacyVersion; //数据库保存的当前隐私声明版本号
		Boolean needUpdatePrivacy; //是否需要更新隐私声明

		BaseResource baseResource = appBaseResource.getBaseResourceValue("PrivacyPolicy", Integer.parseInt(type));
		if (baseResource == null) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "未找到该类型的隐私声明");
			return null;
		}
		dbPrivacyVersion = baseResource.getTagsubvalue();
		if (dbPrivacyVersion.trim().equalsIgnoreCase(privacyVersion.trim())) {
			//两个版本一致，则不需要更新，否则都需要更新
			needUpdatePrivacy = false;
		} else {
			needUpdatePrivacy = true;
		}
		
		Map<String, Object> returnData = new HashMap<String, Object>();
		returnData.put("needUpdate", needUpdatePrivacy);
		returnData.put("currentVersion", dbPrivacyVersion);

		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, returnData);
		return null;
	}
}
