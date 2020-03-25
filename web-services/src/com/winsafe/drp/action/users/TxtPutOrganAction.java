package com.winsafe.drp.action.users;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

/**
 * @author : jerry
 * @version : 2009-8-22 下午02:31:07 www.winsafe.cn
 */
public class TxtPutOrganAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pid = request.getParameter("OtherKey");
		try {

			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			OrganService organs = new OrganService();
			String Condition = " o.sysid like '"
					+ organs.getOrganByID(users.getMakeorganid()).getSysid()
					+ "%' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Organ" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "OrganName",
					"OMobile", "OECode");
			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppOrgan app = new AppOrgan();
			List<Organ> list = app.getOrganByWhere(whereSql);
						
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=Organ.txt");
			response.setContentType("application/octet-stream;charset=UTF-8");
			
			writeTxt(list, response.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	private void writeTxt(List<Organ> olist, OutputStream os){
		BufferedOutputStream bos = null;
		try{
			bos = new BufferedOutputStream(os);			
			for ( Organ o : olist ){
				bos.write(StringUtil.fillBack(o.getId(), Constants.TXT_DL_ORGAN, Constants.TXT_FILL_STRING).getBytes());
				bos.write(StringUtil.fillBack(ESAPIUtil.decodeForHTML(o.getOrganname()), Constants.TXT_DL_ORGANNAME, Constants.TXT_FILL_STRING).getBytes());
				bos.write("\r\n".getBytes());
				
			}
			bos.flush();
			bos.close();
		}catch ( Exception e ){
			e.printStackTrace();
		}finally{
			if ( bos != null ){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	

}
