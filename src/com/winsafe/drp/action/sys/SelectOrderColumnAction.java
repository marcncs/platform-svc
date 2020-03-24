package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.JsonUtil;

public class SelectOrderColumnAction extends BaseAction {
@Override
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		throws Exception {

	String[] columns = request.getParameterValues("orderColumn");
	String[] columnnames = request.getParameterValues("columnname");
	String[] orders = request.getParameterValues("orderby");
	
	String sqlName = "";
	String orderSql = "";
	
	if(columns==null || columnnames==null && orders==null){
		JSONObject json = new JSONObject();
		json.put("sqlName", sqlName);
		json.put("orderSql", orderSql);
		JsonUtil.setJsonObj(response, json);
		return null;
	}
	
	for(int i=0;i<columns.length;i++){
		if("1".endsWith(orders[i])){
			sqlName += columnnames[i] +" 降序,";
			orderSql+=columns[i] +" desc,";
		}else{
			sqlName += columnnames[i] +" 升序,";
			orderSql+=columns[i] +",";
		}
		
	}
	
	sqlName = sqlName.substring(0,sqlName.length()-1);
	orderSql = orderSql.substring(0,orderSql.length()-1);
	
	JSONObject json = new JSONObject();
	json.put("sqlName", sqlName);
	json.put("orderSql", orderSql);
	JsonUtil.setJsonObj(response, json);
	
	return null;
}
}
