package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.hbm.util.DateUtil;

/**
 * @author : jerry
 * @version : 2009-9-17 下午05:05:01
 * www.winsafe.cn
 */
public class ToSaleOrderTrendAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String year = DateUtil.getCurrentYear();
		List<Map> list = new ArrayList<Map>();
		Map<String,Integer> map = null;
		for(int i=0;i<=10;i++){
			map = new HashMap<String, Integer>();
			if(i<5){
				map.put("year", Integer.valueOf(year)-(5-i));
			}else{
				map.put("year", Integer.valueOf(year)+(i-5));
			}
			list.add(map);
		}
		request.setAttribute("beginyear", year);
		request.setAttribute("endyear", Integer.valueOf(year)+1);
		request.setAttribute("list", list);
		return mapping.findForward("show");
	}

}
