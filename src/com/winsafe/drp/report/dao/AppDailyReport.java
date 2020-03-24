package com.winsafe.drp.report.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.winsafe.drp.dao.DailyProductOut;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.report.pojo.DailyReport;
import com.winsafe.hbm.util.DateUtil;

public class AppDailyReport {
	/**
	 * 查询日报
	 * Create Time 2013-3-26 下午01:26:53 
	 * @param psi
	 * @param sortColumn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DailyProductOut> queryDailyReportInfo(String formatdate)
	{
		String hql = "select dpo,u,o from DailyProductOut as dpo,Users as u,Organ as o where dpo.salemanid=u.userid and dpo.salemanid=o.salemanId" +
				" and convert(varchar(7),makedate,120)='" + formatdate + "' order by dpo.regionpid,dpo.regionid,dpo.salemanid,dpo.makedate";
		List list = EntityManager.getAllByHql(hql);
		
		List<DailyProductOut> dpoList = new ArrayList<DailyProductOut>();
		if(list!=null && !list.isEmpty()){
			Object[] oo = null;
			DailyProductOut dpo = null;
			Users u = null;
			Organ o = null;
			for(int i=0;i<list.size();i++){
				oo = (Object[]) list.get(i);
				dpo = (DailyProductOut) oo[0];
				u = (Users) oo[1];
				o = (Organ) oo[2];
				/*dpo.setSalemanname(u.getRealname());
				dpo.setParentoecode(o.getOecode());
				dpo.setParentname(o.getOrganname());*/
				dpoList.add(dpo);
			}
		}
		return dpoList;
	}
	
	//productStruct
	@SuppressWarnings("unchecked")
	public List<DailyReport> getAllDailyReportByDealdate(String dealdate,String condition) throws Exception{
		String sql = "select regionpid,regionpcode,regionpname,regionpuserid,regionpusername,regionid,regioncode,regionname,regionuid,regionuname,price,"+
					"dealdate,outorganid,outorganoecode,outorganname,inorganid,inparentorganid,inparentorganname,inparentorganoecode,inorganoecode,"+
					"inorganname,productid,productname,specmode,unitid,quantity,boxnum,scatternum,salemanid,salemanname,officeid,"+
					"makedate,productStruct,regionusercode,salemancode,salemanbelong,salemanrank,regionpimporttarget,regionpchmantarget,"+
					"regionpchbabytarget,regionptotaltarget,regionimporttarget,regionchmantarget,regionchbabytarget,regiontotaltarget,"+
					"dealerimporttarget,dealerchmantarget,dealerchbabytarget,dealertotaltarget,frontimporttarget,frontchmantarget,"+
					"frontchbabytarget,fronttotaltarget,salemanimporttarget,salemanchmantarget,salemanchbabytarget,salemantotaltarget,"+
					"salemanimportcount,salemanchmancount,salemanchbabycount,salemantotalcount"+
					" from daily_report " + 
					" where " + 
					//取当前月份数据
					" LEFT(dealdate, 6)='" + dealdate + "' " + condition +
					//根据 大区、办事处、主管、经销商、网点、产品品类、产品、日期排序
					" order by regionpid,regionid,salemanid,inparentorganid,inorganid,productstruct,productid,dealdate";
		//查询
		List list = EntityManager.jdbcquery(sql);
		//组织数据
		List<DailyReport> drList = new ArrayList<DailyReport>();
		if(list!=null && !list.isEmpty()){
			Map map = null;
			DailyReport dr = null;
			for(int i=0;i<list.size();i++){
				map = (Map) list.get(i);
				dr = new DailyReport();
				dr.setRegionpid(String.valueOf(map.get("regionpid")));
				dr.setRegionpcode(String.valueOf(map.get("regionpcode")));
				dr.setRegionpname(String.valueOf(map.get("regionpname")));
				dr.setRegionpuserid(String.valueOf(map.get("regionpuserid")));
				
				dr.setRegionpusername(String.valueOf(map.get("regionpusername")));
				dr.setRegionid(map.get("regionid")==null?null:Integer.valueOf(String.valueOf(map.get("regionid"))));
				dr.setRegioncode(String.valueOf(map.get("regioncode")));
				dr.setRegionname(String.valueOf(map.get("regionname")));
				dr.setRegionuid(String.valueOf(map.get("regionuid")));
				dr.setRegionuname(String.valueOf(map.get("regionuname")));
				dr.setPrice(map.get("price")==null?0:Double.valueOf(String.valueOf(map.get("price"))));
				dr.setDealdate(String.valueOf(map.get("dealdate")));
				
				dr.setOutorganid(String.valueOf(map.get("outorganid")));
				dr.setOutorganoecode(String.valueOf(map.get("outorganoecode")));
				dr.setOutorganname(String.valueOf(map.get("outorganname")));
				dr.setInorganid(String.valueOf(map.get("inorganid")));
				dr.setInparentorganid(String.valueOf(map.get("inparentorganid")));
				dr.setInparentorganname(String.valueOf(map.get("inparentorganname")));
				dr.setInparentorganoecode(String.valueOf(map.get("inparentorganoecode")));
				dr.setInorganoecode(String.valueOf(map.get("inorganoecode")));
				dr.setInorganname(String.valueOf(map.get("inorganname")));
				dr.setProductid(String.valueOf(map.get("productid")));
				dr.setProductname(String.valueOf(map.get("productname")));
				dr.setSpecmode(String.valueOf(map.get("specmode")));
				dr.setUnitid(map.get("unitid")==null?null:Integer.valueOf(String.valueOf(map.get("unitid"))));
				dr.setQuantity(map.get("quantity")==null?0:Double.valueOf(String.valueOf(map.get("quantity"))));
				dr.setBoxnum(map.get("boxnum")==null?0:Double.valueOf(String.valueOf(map.get("boxnum"))));
				dr.setScatternum(map.get("scatternum")==null?0:Double.valueOf(String.valueOf(map.get("scatternum"))));
				dr.setSalemanid(String.valueOf(map.get("salemanid")));
				
				dr.setSalemanname(String.valueOf(map.get("salemanname")));
				dr.setOfficeid(String.valueOf(map.get("officeid")));
				
				dr.setMakedate(DateUtil.StringToDate(map.get("makedate").toString()));
				dr.setProductStruct(String.valueOf(map.get("productstruct")));
				dr.setRegionusercode(String.valueOf(map.get("regionusercode")));
				dr.setSalemancode(String.valueOf(map.get("salemancode")));
				dr.setSalemanbelong(String.valueOf(map.get("salemanbelong")));
				dr.setSalemanrank(String.valueOf(map.get("salemanrank")));
				dr.setRegionpimporttarget(map.get("regionpimporttarget")==null?0:Double.valueOf(String.valueOf(map.get("regionpimporttarget"))));
				dr.setRegionpchmantarget(map.get("regionpchmantarget")==null?0:Double.valueOf(String.valueOf(map.get("regionpchmantarget"))));
				
				dr.setRegionpchbabytarget(map.get("regionpchbabytarget")==null?0:Double.valueOf(String.valueOf(map.get("regionpchbabytarget"))));
				dr.setRegionptotaltarget(map.get("regionptotaltarget")==null?0:Double.valueOf(String.valueOf(map.get("regionptotaltarget"))));
				dr.setRegionimporttarget(map.get("regionimporttarget")==null?0:Double.valueOf(String.valueOf(map.get("regionimporttarget"))));
				dr.setRegionchmantarget(map.get("regionchmantarget")==null?0:Double.valueOf(String.valueOf(map.get("regionchmantarget"))));
				
				dr.setRegionchmantarget(map.get("regionchmantarget")==null?0:Double.valueOf(String.valueOf(map.get("regionchmantarget"))));
				dr.setRegionchbabytarget(map.get("regionchbabytarget")==null?0:Double.valueOf(String.valueOf(map.get("regionchbabytarget"))));
				dr.setRegiontotaltarget(map.get("regiontotaltarget")==null?0:Double.valueOf(String.valueOf(map.get("regiontotaltarget"))));
				dr.setRegionpimporttarget(map.get("regionpimporttarget")==null?0:Double.valueOf(String.valueOf(map.get("regionpimporttarget"))));
				dr.setDealerimporttarget(map.get("dealerimporttarget")==null?0:Double.valueOf(String.valueOf(map.get("dealerimporttarget"))));
				dr.setDealerchmantarget(map.get("dealerchmantarget")==null?0:Double.valueOf(String.valueOf(map.get("dealerchmantarget"))));
				dr.setDealerchbabytarget(map.get("dealerchbabytarget")==null?0:Double.valueOf(String.valueOf(map.get("dealerchbabytarget"))));
				dr.setDealertotaltarget(map.get("dealertotaltarget")==null?0:Double.valueOf(String.valueOf(map.get("dealertotaltarget"))));
				
				dr.setFrontimporttarget(map.get("frontimporttarget")==null?0:Double.valueOf(String.valueOf(map.get("frontimporttarget"))));
				dr.setFrontchmantarget(map.get("frontchmantarget")==null?0:Double.valueOf(String.valueOf(map.get("frontchmantarget"))));
				dr.setFrontchbabytarget(map.get("frontchbabytarget")==null?0:Double.valueOf(String.valueOf(map.get("frontchbabytarget"))));
				dr.setFronttotaltarget(map.get("fronttotaltarget")==null?0:Double.valueOf(String.valueOf(map.get("fronttotaltarget"))));
				dr.setSalemanimporttarget(map.get("salemanimporttarget")==null?0:Double.valueOf(String.valueOf(map.get("salemanimporttarget"))));
				dr.setSalemanchmantarget(map.get("salemanchmantarget")==null?0:Double.valueOf(String.valueOf(map.get("salemanchmantarget"))));
				dr.setSalemanchbabytarget(map.get("salemanchbabytarget")==null?0:Double.valueOf(String.valueOf(map.get("salemanchbabytarget"))));
				dr.setSalemantotaltarget(map.get("salemantotaltarget")==null?0:Double.valueOf(String.valueOf(map.get("salemantotaltarget"))));
				dr.setSalemanimportcount(map.get("salemanimportcount")==null?0:Double.valueOf(String.valueOf(map.get("salemanimportcount"))));
				
				dr.setSalemanchmancount(map.get("salemanchmancount")==null?0:Double.valueOf(String.valueOf(map.get("salemanchmancount"))));
				dr.setSalemanchbabycount(map.get("salemanchbabycount")==null?0:Double.valueOf(String.valueOf(map.get("salemanchbabycount"))));
				dr.setSalemantotalcount(map.get("salemantotalcount")==null?0:Double.valueOf(String.valueOf(map.get("salemantotalcount"))));
				
				drList.add(dr);
				
			}
		}
		
		return drList;
	}
}
