package com.winsafe.erp.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map; 

import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.erp.metadata.FileType;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.StringUtil;

public class DealerTransferService extends FileTransferService {
	
	private String TAG_NAME = "dealerTime";
	
	/**
	 * 初始化数据
	 */
	public void init() { 
		title="机构类别（TD/BKD/零售商/大农户/合作社/其他）,机构编号,机构企业内部编号（TD的soldto code）,"
				+ "机构名称,上级机构编号,上级机构,省,市,区/县,大区,地区,小区,审核状态,是否撤消,省份代码,城市代码,区县代码"; 
		columnNames = Arrays.asList("organmodel","id","oecode","organname","parentid","poname","province","city","area","bigarea","middlearea","smallarea","status","isrepeal",
				"provinceid","cityid","areasid");
	}

	public void createTransferFile() throws Exception {
		init();
		String startTime = getStartTime(TAG_NAME);
		String endTime = getEndTime();
		
		List<Map<String,String>> dataList = appTl.getDealersToTransfer(startTime, endTime);
		for(Map<String,String> data : dataList) {
			if(!StringUtil.isEmpty(data.get("status"))) {
				ValidateStatus status = ValidateStatus.parseByValue(Integer.valueOf(data.get("status")));
				data.put("status", status.getName());
			} else {
				data.put("status", ValidateStatus.NOT_AUDITED.getName());
			}
			data.put("organname", ESAPIUtil.decodeForHTML(data.get("organname")));
			data.put("poname", ESAPIUtil.decodeForHTML(data.get("poname")));
			if(!StringUtil.isEmpty(data.get("organmodel"))) {
				DealerType type = DealerType.parseByValue(Integer.valueOf(data.get("organmodel")));
				data.put("organmodel", type.getName());
			}
		}
		createFileAndAddLog(dataList, FileType.DEALER, endTime, TAG_NAME);
	}
	
	public static void main(String[] args) throws Exception {
		new DealerTransferService().createTransferFile();
		HibernateUtil.commitTransaction();
//		StringBuffer sql = new StringBuffer();
//		sql.append("select o.organmodel,o.id,o.oecode,o.organname,o.parentid,po.organname poname,");
//		sql.append("pca.areaname province,cca.areaname city,aca.areaname,bsba.name_loc bigarea, ");
//		sql.append("msba.name_loc middlearea, sba.name_loc smallarea,");
//		sql.append("case when o.isrepeal = 1 then '是' else '否' END isrepeal,");
//		sql.append("o.validate_status status ");
//		System.out.println(sql);
	}
	
}
