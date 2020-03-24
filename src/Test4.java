import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.InventoryDetailReport;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.SalesConsumeDetailReportForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.metadata.PrimaryCodeStatus;
import com.winsafe.sap.metadata.SyncStatus;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;

import net.sf.json.JSONObject;

public class Test4 {
	public static void main(String[] args) {
		/*PrimaryCode pcode = new PrimaryCode();
		pcode.setCreateDate(new Date());
		JSONObject object = JSONObject.fromBean(pcode);
		System.out.println(object.toString());
		pcode = new PrimaryCode();
		object = JSONObject.fromBean(pcode);
		System.out.println(object.toString());*/
		//String hql = "from PrintJob where (primaryCodeStatus ="+PrimaryCodeStatus.GENERATED.getDatabaseValue()+" or (productId in (select id from Product where isidcode=1) and confirmFlag = 1)) and syncStatus in ("+SyncStatus.NOT_UPLOADED.getValue()+","+SyncStatus.UPLOAD_ERROR.getValue()+")";
		//System.out.println(hql);
		Map paraMap = new HashMap<>();
		
		paraMap.put("EndDate", Dateutil.getCurrentDateTime());
		StringBuffer sql = new StringBuffer();
		SalesConsumeDetailReportForm queryForm = new SalesConsumeDetailReportForm();
		queryForm.setBeginDate("2019-10-01 00:00:00");
		queryForm.setEndDate("2019-10-25 23:59:59");
		//PD调拨回来的
		sql.append(" \r\n --TD转仓回来的-加销售");
		sql.append(" \r\n select DISTINCT tt.billNo billNo");
		sql.append(" \r\n ,r.sortname regionName");
		sql.append(" \r\n ,ino.province provinceId");
		sql.append(" \r\n ,ino.oecode oecode");
		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,tt.inoid organId ");
		sql.append(" \r\n ,ino.organname organName");
		sql.append(" \r\n ,ttd.productid productId  ");
		sql.append(" \r\n ,pstr.sortname psName");
		sql.append(" \r\n ,p.productname productName");
		sql.append(" \r\n  ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.mcode mCode");
		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
		sql.append(" \r\n ,p.sunit unitId ");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n ,swb.cycleoutquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
		sql.append(" \r\n ,sm.movedate makeDate,w.warehousename ");
//		sql.append(" \r\n ,pj.production_date produceDate ");
//		sql.append(" \r\n ,pj.expiry_date expiryDate ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1 and tt.bsort = 2 ");
		sql.append(" \r\n join STOCK_MOVE sm on sm.id=tt.billno");
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sm.SHIPMENTDATE >=to_date("+queryForm.getBeginDate()+",'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sm.SHIPMENTDATE < to_date("+queryForm.getEndDate()+",'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1 and ino.ISREPEAL = 0");
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id  ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		sql.append(" \r\n union all");
		//调拨给其他PD的
		sql.append(" \r\n --转仓给其他TD-减销售");
		sql.append(" \r\n select DISTINCT tt.billNo billNo");
		sql.append(" \r\n ,r.sortname regionName");
		sql.append(" \r\n ,o.province provinceId");
		sql.append(" \r\n ,o.oecode oecode");
		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,tt.oid organId ");
		sql.append(" \r\n ,o.organname organName");
		sql.append(" \r\n ,ttd.productid productId  ");
		sql.append(" \r\n ,pstr.sortname psName");
		sql.append(" \r\n ,p.productname productName");
		sql.append(" \r\n ,p.productnameen productNameen");
		sql.append(" \r\n ,p.mcode mCode");
		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
		sql.append(" \r\n ,p.sunit unitId ");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n ,-swb.cycleoutquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
		sql.append(" \r\n ,sm.movedate makeDate,w.warehousename ");
//		sql.append(" \r\n ,pj.production_date produceDate ");
//		sql.append(" \r\n ,pj.expiry_date expiryDate ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1 and tt.bsort = 2 ");
		sql.append(" \r\n join STOCK_MOVE sm on sm.id=tt.billno");
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sm.SHIPMENTDATE >=to_date("+queryForm.getBeginDate()+",'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sm.SHIPMENTDATE < to_date("+queryForm.getEndDate()+",'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1");
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		//大区条件
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		
		System.out.println(sql);
//		logger.debug(sql.toString());
	
	
	}
}
