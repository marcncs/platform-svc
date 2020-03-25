package com.winsafe.drp.server;
//package com.winsafe.drp.server;
//import java.sql.Connection;
//import java.util.List;
//
//import com.winsafe.hbm.entity.HibernateUtil;
//import com.winsafe.hbm.util.MakeCode;
//import com.winsafe.drp.dao.AppProductStockpile;
//import com.winsafe.drp.dao.AppWarehouse;
//import com.winsafe.drp.dao.AppWarehouseAnnunciator;
//import com.winsafe.drp.dao.AppWarehouseAnnunciatorDetail;
//import com.winsafe.drp.dao.AppWarehouseSafetyIntercalate;
//import com.winsafe.drp.dao.Warehouse;
//import com.winsafe.drp.dao.WarehouseAnnunciator;
//import com.winsafe.drp.dao.WarehouseAnnunciatorDetail;
//
//public class WarehouseSafety {
//	public void createWarehouseSafety(){
//		try{
//			AppWarehouse aw = new AppWarehouse();
//			AppWarehouseSafetyIntercalate aws = new AppWarehouseSafetyIntercalate();
//			List wls = aw.getCanUseWarehouse();
//			Integer warehouseid,userid;
//			for(int i=0;i<wls.size();i++){
//				Warehouse wo =(Warehouse)wls.get(i);
//				warehouseid = wo.getId();
//				userid = wo.getUserid();
//				

//				WarehouseAnnunciator wa = new WarehouseAnnunciator();
//				AppWarehouseAnnunciator awa = new AppWarehouseAnnunciator();
//				Long waid =Long.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_annunciator",0,""));
//				wa.setId(waid);
//				wa.setWarehouseid(warehouseid);
//				wa.setUserid(userid);
//				wa.setIsawake(Integer.valueOf(0));
//				

//				List sls = aws.getWarehouseSafetyByWID(warehouseid);
//				if(sls.size()>0){
//					AppProductStockpile aps = new AppProductStockpile();
//					AppWarehouseAnnunciatorDetail awad = new AppWarehouseAnnunciatorDetail();
//					for(int s=0;s<sls.size();s++){
//						Object[] wao = (Object[])sls.get(s);
//						WarehouseAnnunciatorDetail wad = new WarehouseAnnunciatorDetail();
//						wad.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_annunciator_detail",0,"")));
//						wad.setWaid(waid);
//						wad.setProductid(wao[1].toString());

//						Double stockpile =aps.getProductStockpileByProductID(wad.getProductid(), warehouseid);
//						//System.out.println("----stockpile==="+stockpile);
//						Double safetyh = Double.valueOf(wao[2].toString());
//						Double safetyl = Double.valueOf(wao[3].toString());
//						//System.out.println("-----safetyh--"+safetyh+"---safetyl---"+safetyl);
//						Double annuncatorSum=0.00;
//						if(stockpile >safetyh){
//							annuncatorSum = stockpile-safetyh;
//						}
//						if(stockpile <safetyl){
//							annuncatorSum = stockpile - safetyl;
//						}
//						if(annuncatorSum !=0){
//						wad.setQuantity(annuncatorSum);

//						//awad.addWarehouseAnnunciatorDetail(wad);
//						}
//					}

//					awa.delWareHouseAnnunciatorByWID(warehouseid);
//					awa.addWarehouseAnnunciator(wa);
//	
//				}
//				
//			}
//

//		}catch(Exception e){
//			
//			e.printStackTrace();
//		}finally {
//		      HibernateUtil.closeSession();

//		    }
//		}
//	}
//
