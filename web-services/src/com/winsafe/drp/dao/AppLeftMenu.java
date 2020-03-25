package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.NumberUtil;

public class AppLeftMenu {

	public void addLeftMenu(LeftMenu LeftMenu) throws Exception{
		
		EntityManager.save(LeftMenu);
		
	}
	
	public void updLeftMenu(LeftMenu LeftMenu) throws Exception{
		
		EntityManager.saveOrUpdate(LeftMenu);
		
	}
	
	public void delLeftMenu(int id) throws Exception{
		
		String sql="delete from LeftMenu where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public LeftMenu getLeftMenuById(int id) throws Exception{
		LeftMenu LeftMenu=null;
		String sql="from LeftMenu where id="+id;
		LeftMenu=(LeftMenu)EntityManager.find(sql);
		return LeftMenu;
	}
	
	public String getNameById(String id) throws Exception{
		String leftMenuName = "";
		LeftMenu LeftMenu = getLeftMenuById(Integer.valueOf(id));
		if(LeftMenu != null){
			leftMenuName = LeftMenu.getLmenuname();
		}
		return leftMenuName;
	}
	
	public List getLeftMenuByLevel(int parentid,int level) throws Exception{
		String sql="from LeftMenu l where l.lmenuparentid="+parentid+" and l.isVisible=1 and l.id > 1 and l.lmenulevel="+level +" order by l.id ";
		return EntityManager.getAllByHql(sql);
	}
	
	/**
	 * 查出所有最小模块
	 * Create Time 2014-11-21 下午01:48:36 
	 * @return
	 * @throws Exception
	 * @author lipeng
	 */
	public List getMinLeftMenu() throws Exception{
		List<LeftMenu> result = new ArrayList<LeftMenu>();
		String countSql = "select max(lmenulevel) from LeftMenu";
		Integer maxLevel = EntityManager.getCount(countSql);
		String sql="from LeftMenu l where l.lmenuparentid != 0 and l.isVisible=1 and l.lmenulevel=" + maxLevel ;
		result.addAll(EntityManager.getAllByHql(sql));
		for(int i = maxLevel; i>1 ; i--){
			String subSql="from LeftMenu where lmenulevel=" + (i-1) + " and lmenuparentid != 0 and isVisible=1 " +
					"and id not in (select lmenuparentid from LeftMenu where lmenulevel=" + i + " and lmenuparentid != 0 and isVisible=1  ) "  ;
			List tmpList = EntityManager.getAllByHql(subSql);
			result.addAll(tmpList);
		}
		Collections.sort(result,new Comparator<LeftMenu>() {
			@Override
			public int compare(LeftMenu o1, LeftMenu o2) {
				int o1Sort = NumberUtil.removeNull(o1.getSort()).intValue();
				int o2Sort = NumberUtil.removeNull(o2.getSort()).intValue();
				
				return o1Sort - o2Sort;
			}
		});
		return result;
	}
	
	
	public List getLeftMenuByParentid(int parentid, int level, int roleid) throws Exception{
		List  list = new ArrayList();
		ResultSet rs = null;
		//通过做外连接选取出所有的权限
		String sql = "select l.id, l.lmenuname, l.lmenulevel, r.roleid "+
		"from left_menu l left join (select rm.roleid,rm.lmid from role_left_menu rm where rm.roleid="+roleid+") r "+ 
		"on l.id=r.lmid where l.lmenuparentid="+parentid+" and l.lmenulevel="+level+" and l.isvisible=1 order by l.sort";
		rs = EntityManager.query2(sql);
		try{
			 while (rs.next()){
				LeftMenuBean leftmenu = new LeftMenuBean();	
				leftmenu.setId(rs.getInt(1));
				leftmenu.setLmenuname(rs.getString(2));
				leftmenu.setLmenulevel(rs.getInt(3));
				leftmenu.setRoleid(rs.getInt(4));
				list.add(leftmenu);
			};
		}catch ( Exception e ){
			throw e;
		}finally{
			if ( rs != null ){
				rs.close();
			}
		}
		
		return list;
	}
	
	public JSONArray getOperateByLmid(int lmid, int roleid) throws Exception{
		JSONArray  oarray = new JSONArray();
		ResultSet rs = null;
		String sql = "select o.id, o.operatename,r.roleid "+
		"from operate o left join (select rm.roleid,rm.operateid from role_menu rm where rm.roleid="+roleid+") r "+
		"on o.id=r.operateid where o.moduleid="+lmid+" and o.isvisible=1 order by o.id";
		rs = EntityManager.query2(sql);
		try{
			 while (rs.next()){
				JSONObject operate = new JSONObject();	
				operate.put("id", rs.getLong(1));
				operate.put("operatename", rs.getString(2));
				operate.put("roleid", rs.getLong(3));
				oarray.put(operate);
			};
		}catch ( Exception e ){
			throw e;
		}finally{
			if ( rs != null ){
				rs.close();
			}
		}
		
		return oarray;
	}
	
	
	
	public List getLeftMenuByUseridSuper(int userid) throws Exception{
		List list = new ArrayList();
		ResultSet rs = null;
		String sql = "select a.id, a.lmenuname, a.lmenuurl,c.userid "+
		"from Left_Menu a left join (select b.lmid, b.userid from User_Left_Menu b where b.userid="+userid+") c "+ 
		"on a.id=c.lmid where a.lmenulevel=1 order by a.lmenuorder";
		rs = EntityManager.query2(sql);
		try{
			while (rs.next()){
				UserLeftMenu ulm = new UserLeftMenu();
				ulm.setLmid(rs.getLong(1));
				ulm.setLmenuname(rs.getString(2));
				ulm.setLmenuurl(rs.getString(3));
				ulm.setUserid(rs.getLong(4));
				list.add(ulm);
			} ;
		}catch ( Exception e ){
			throw e;
		}finally{
			if ( rs != null ){
				rs.close();
			}
		}
		if ( list.size() == 0 ){
			return null;
		}
		return list;
	}
	
	public List getLeftMenuByUserid(int userid) throws Exception{
		List list = new ArrayList();
		ResultSet rs = null;
		String sql = "select a.id, a.lmenuname, a.lmenuurl,c.userid "+
		"from Left_Menu a left join (select b.lmid, b.userid from User_Left_Menu b where b.userid="+userid+") c "+ 
		"on a.id=c.lmid ";
		rs = EntityManager.query2(sql);
		try{
			 while (rs.next()){
				UserLeftMenu ulm = new UserLeftMenu();
				ulm.setLmid(rs.getLong(1));
				ulm.setLmenuname(rs.getString(2));
				ulm.setLmenuurl(rs.getString(3));
				ulm.setUserid(rs.getLong(4));
				list.add(ulm);
			};
		}catch ( Exception e ){
			throw e;
		}finally{
			if ( rs != null ){
				rs.close();
			}
		}
		if ( list.size() == 0 ){
			return null;
		}
		return list;
	}
	
	public List getLeftMenuByUseridParentId(int userid,int parentId) throws Exception{
		List list = new ArrayList();
		ResultSet rs = null;
		String sql = "select a.id, a.lmenuname, a.lmenuurl,c.userid "+
		"from Left_Menu a left join (select b.lmid, b.userid from User_Left_Menu b where b.userid="+userid+") c "+ 
		"on a.id=c.lmid where a.lmenuparentid= " + parentId;
		rs = EntityManager.query2(sql);
		try{
			 
			while (rs.next()) {
				UserLeftMenu ulm = new UserLeftMenu();
				ulm.setLmid(rs.getLong(1));
				ulm.setLmenuname(rs.getString(2));
				ulm.setLmenuurl(rs.getString(3));
				ulm.setUserid(rs.getLong(4));
				list.add(ulm);
			} ;
		}catch ( Exception e ){
			throw e;
		}finally{
			if ( rs != null ){
				rs.close();
			}
		}
		if ( list.size() == 0 ){
			return null;
		}
		return list;
	}
	
	public static String  getOpreateHtmlTarace(String url){
		return getOpreateTrace(url, Constants.MENUS_HTML_SEPARATE);
	}
	
	public static String  getOpreateTarace(String url){
		return getOpreateTrace(url, Constants.MENUS_SEPARATE);
	}
	
	public static String  getOpreateTrace(String url,String separate){
		String menusUrl = "";
		try {
			Object[] objects = getMenuByUrl(url);
			if(objects != null){
				LeftMenu leftMenu = (LeftMenu) objects[0];
				Operate operate = (Operate) objects[1];
				menusUrl = getParentMenusById(leftMenu.getId(), operate.getOperatename(),separate);
			}
		} catch (Exception e) {
			
		}
		return menusUrl;
	}
	
	public static String  getMenusHtmlTrace(String url){
		return getMenuTrace(url, Constants.MENUS_HTML_SEPARATE);
	}
	
	public static String  getMenusTrace(String url){
		return getMenuTrace(url, Constants.MENUS_SEPARATE);
	}
	
	public static String  getMenuTrace(String url,String separate){
		String menusUrl = "";
		try {
			Object[] objects = getMenuByUrl(url);
			if(objects != null){
				LeftMenu leftMenu = (LeftMenu) objects[0];
				Operate operate = (Operate) objects[1];
					menusUrl = getParentMenusById(leftMenu.getLmenuparentid(), leftMenu.getLmenuname(),separate);
			}
		} catch (Exception e) {
			
		}
		return menusUrl;
	}
	public static Object[] getMenuByUrl(String url){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select lm,op from Menu m ");
		sqlBuffer.append(" , Operate op, LeftMenu lm where m.operateid=op.id ");
		sqlBuffer.append("  and op.moduleid = lm.id  ");
		sqlBuffer.append(" and m.url = '" + url + "'  ");
		return (Object[]) EntityManager.find(sqlBuffer.toString());
	}
	
	public static String getParentMenusById(int id,String menus,String separate) throws Exception{
		LeftMenu leftMenu=null;
		String sql="from LeftMenu where id="+id;
		leftMenu=(LeftMenu)EntityManager.find(sql);
		if(leftMenu != null){
			menus = leftMenu.getLmenuname() + separate+ menus;
			if( leftMenu.getLmenulevel()>1){
				return getParentMenusById(leftMenu.getLmenuparentid(),menus,separate);
			}
		}
		return menus;
	}
	
	public static Integer getMenuIdByUrl(String url){
		Integer menuId = null;
		Object[] objects = getMenuByUrl(url);
		if(objects != null){
			LeftMenu leftMenu = (LeftMenu) objects[0];
			if(leftMenu != null){
				menuId = leftMenu.getId();
			}
		}
		return menuId;
	}
	
	public static String getMenuNameByUrl(String url){
		String menuName = null;
		Object[] objects = getMenuByUrl(url);
		if(objects != null){
			LeftMenu leftMenu = (LeftMenu) objects[0];
			if(leftMenu != null){
				menuName = leftMenu.getLmenuname();
			}
		}
		return menuName;
	}
}
