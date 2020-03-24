package com.winsafe.drp.action.sales;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.util.AjaxUtil;
import com.winsafe.drp.util.BaseResult;


public class ListAreasAction extends BaseAction{

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception{
        try{
            AppCountryArea aa = new AppCountryArea();
            if("1".equals(request.getParameter("type"))) {
            	String code = request.getParameter("code");
        		List<CountryArea> list = aa.getCountryAreaByParentid(Integer.valueOf(code));
        		JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "数据查询成功！", list));
        		AjaxUtil.ajaxReturn(jsonObject, response);
            } else {
            	String strparentid = request.getParameter("parentid");
                int parentid = Integer.valueOf(strparentid);
            	List<CountryArea> als = aa.getCountryAreaByParentid(parentid);
                response.setContentType("text/xml;charset=utf-8");
                response.setHeader("Cache-Control", "no-cache");
                
                Document document = DocumentHelper.createDocument();
                Element root = document.addElement("allarea");
                Element area = null;
                Element areaid = null;
                Element areaname = null;
                for(CountryArea ca : als ){
                    area = root.addElement("area");
                    areaid = area.addElement("areaid");
                    areaid.setText(ca.getId().toString());
                    areaname = area.addElement("areaname");
                    areaname.setText(ca.getAreaname());

                }
                PrintWriter out = response.getWriter();
                String s = root.asXML();
                out.write(s);
                out.close();
            }
        }catch(Exception e){ 
            e.printStackTrace();
        }
    // return new ActionForward(mapping.getInput());
    return null;
    }
}
