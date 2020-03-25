package com.winsafe.drp.server;
import java.util.List;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganAnnunciator;
import com.winsafe.drp.dao.AppOrganAwake;
import com.winsafe.drp.dao.AppOrganSafetyIntercalate;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganAnnunciator;
import com.winsafe.drp.dao.OrganAwake;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.MakeCode;

public class OrganSafety {
	public void createWarehouseSafety(){

		try{
			AppOrganSafetyIntercalate aosi = new AppOrganSafetyIntercalate();
			AppOrgan ao = new AppOrgan();
			AppOrganAwake aoa = new AppOrganAwake();
			AppOrganAnnunciator aon = new AppOrganAnnunciator();
			List ols = ao.getAllOrgan();
			//System.out.println("--------ss--"+ols.size());
			for(int o=0;o<ols.size();o++){
				int osafe=0;
				Organ og= (Organ)ols.get(o);
				String oid = og.getId();
				osafe = aosi.getSafetyProductCount(oid);
				if(osafe>0){
					List uls = aoa.getOrganAwakeOID(oid);
					for(int u=0;u<uls.size();u++){
					OrganAwake oa= (OrganAwake)uls.get(u);
					HibernateUtil.currentSession(true);
					OrganAnnunciator oan = new OrganAnnunciator();
					oan.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_annunciator",0,"")));
					oan.setOrganid(oid);
					oan.setUserid(oa.getUserid());
					oan.setIsawake(0);
					aon.addOrganAnnunciator(oan);
					HibernateUtil.commitTransaction();
					}
				}
				
			}

		}catch(Exception e){
			e.printStackTrace();
			HibernateUtil.rollbackTransaction();
		}finally {
			//HibernateUtil.closeSession();
		    }
		}
	}

