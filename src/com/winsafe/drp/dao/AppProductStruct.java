package com.winsafe.drp.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppProductStruct {

  
  public List getProductStructChild(int parentId) throws Exception {
    String hql = " from ProductStruct where useflag= 1  ";
    if (parentId >= 0) {
      hql += "  and parentid = " + parentId;
    }
    hql += " order by id ";
    return EntityManager.getAllByHql(hql);
  }

  
  public ProductStruct getProductStructById(String psid) throws Exception {
    String hql = " from ProductStruct as ps where ps.structcode = '" + psid+"'";
    return (ProductStruct) EntityManager.find(hql);
  }

  
  public List getProductStructCanUse() throws Exception {
    String sql =" from ProductStruct as ps  order by ps.structcode";
    return EntityManager.getAllByHql(sql);
  }
  
  public String getName(String structcode) throws Exception {
	  if ( structcode == null || "".equals(structcode) || "0".equals(structcode)){
		  return "";
	  }
	  ProductStruct ps = getProductStructById(structcode);
	  if ( ps != null ){
		  return ps.getSortname();
	  }
	  return "";
  }
  
  public String getEnName(String structcode) throws Exception {
	  if ( structcode == null || "".equals(structcode) || "0".equals(structcode)){
		  return "";
	  }
	  ProductStruct ps = getProductStructById(structcode);
	  if ( ps != null ){
		  return ps.getSortnameen();
	  }
	  return "";
  }


  public void addNewProductStruct(Object productstruct) throws Exception {    
    EntityManager.save(productstruct);    
  }
  
  public void updProductStruct(Object productstruct) throws Exception {    
	    EntityManager.update(productstruct);    
  }

  public List getProductStructByParentID(int pagesize, String pWhereClause,
		  SimplePageInfo pPgInfo) throws Exception {
    int targetPage = pPgInfo.getCurrentPageNo();
    String sql = "select ps.id,ps.sortname,ps.remark,ps.parentid,ps.useflag from ProductStruct as ps " +
        pWhereClause + "  order by ps.id ";

    return EntityManager.getAllByHql(sql, targetPage, pagesize);
  }

  
  public void updProductStructByID(Long id,String sortname,Long parentid,Integer useflag,String remark) throws
      HibernateException, Exception {    
    String sql = "update product_struct set sortname='"+sortname+"',parentid="+parentid+",useflag="+useflag+",remark='"+remark+"' where id="+id;
    EntityManager.updateOrdelete(sql);
    
  }
  
  public List getTreeByCate(String strparentid) {
		String sql="";
		int length=strparentid.length()+2;
		if(strparentid!=null&&strparentid.equals("-1"))
			sql="select a.id,a.structcode,a.sortname from ProductStruct as a where length(a.structcode)=1";
		else 
			sql="select a.id,a.structcode,a.sortname from ProductStruct as a where a.structcode like '"+strparentid+"%' and length(structcode)="+length;
		return EntityManager.getAllByHql(sql);
	}
  
	public String getAcodeByParent(String strparentid)throws Exception{
		
		String stracode=null;
		int length=strparentid.length()+2;
		String sql="from ProductStruct as a where a.structcode like'"+strparentid+"%' and length(structcode)="+length+" order by a.structcode desc " ;
		ProductStruct as=(ProductStruct)EntityManager.find(sql);
		long intacode=0;
		if(as==null){
			intacode=Long.valueOf(strparentid+"01");
		}else{
			intacode=Long.valueOf(as.getStructcode()).intValue()+1;
		}
		//System.out.println("intcode============="+intacode);
		stracode=String.valueOf(intacode);
		//=(AreasScenery)list.get(0);
		return stracode;
	}
	
	public void upd(String acode,String areaname)throws Exception{		
		String sql="update product_struct set SortName='"+areaname+"'  where StructCode='"+acode+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void del(String dcode)throws Exception{		
		String sql="delete from product_struct where structcode='"+dcode+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public int checkHasSubStructByCode(String structcode)throws Exception{
		int psidlength = structcode.length()+3;
		String sql="select count(p.id) from ProductStruct as p where p.structcode like '"+structcode+"%' and length(p.structcode)="+psidlength;
		return EntityManager.getRecordCount(sql);
	}
	
 	public List getChild(String parentid) {
		String sql=" from ProductStruct as a where a.structcode like '"+parentid+"%' order by a.structcode";
		return EntityManager.getAllByHql(sql);
	}
 	
 	
 	  public ProductStruct getProductStructBySortName(String psidname) throws Exception {
 		    String hql = " from ProductStruct as ps where ps.sortname = '" + psidname+"'";
 		    return (ProductStruct) EntityManager.find(hql);
 		  }


	public Map<String, ProductStruct> getAllMap() throws Exception {
		Map<String, ProductStruct> resultMap = new HashMap<String, ProductStruct>();
		List<ProductStruct> list = getProductStructCanUse();
		for(ProductStruct pstruct : list){
			if(pstruct == null) continue;
			resultMap.put(pstruct.getStructcode(), pstruct);
		}
		return resultMap;
	}
	
}
