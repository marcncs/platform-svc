package com.winsafe.drp.server;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.winsafe.drp.dao.AppRegionManage;
import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.drp.keyretailer.dao.AppSalesAreaCountry;
import com.winsafe.drp.keyretailer.pojo.SalesAreaCountry;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

public class SalesAreaServices {
	
	private Integer topAreaId = -1;
	AppRegionManage arm = new AppRegionManage();
	AppSalesAreaCountry asac = new AppSalesAreaCountry();
	StringBuffer resultMsg = new StringBuffer();
	
	private int bigRegionCount = 0;
	private int middleAreaCount = 0;
	private int smallAreaCount = 0;
	private int areaCount = 0;
	
	Map<String, BigRegion> bigRegionMap = new TreeMap<String, BigRegion>();
	
	Date currentDate = DateUtil.getCurrentDate();
	
	public String importSbonusArea(InputStream inputStream) throws Exception {
		readFile(inputStream);
		addSalesAreas();
		String msg = "导入完成,共导入大区"+bigRegionCount+"条,地区"+middleAreaCount+"条小区"+smallAreaCount+"条关联区/县"+areaCount+"条";
		if(resultMsg.length() > 0) {
			msg = msg + ",以下是详细信息:<br>"+resultMsg.toString();
		}
		return msg;
	}
	
	private void readFile(InputStream inputStream) throws Exception {
		Workbook wb = WorkbookFactory.create(inputStream);
		Sheet sheet = wb.getSheetAt(0);
		Row row = null;
		int lineNo = 0;
		for (int i=1; i<=sheet.getLastRowNum(); i++) {
			lineNo = i+1;
			row = sheet.getRow(i);
			
			String bigRegion = getValue(row.getCell(0));
			if(StringUtil.isEmpty(bigRegion)) {
//				resultMsg.append("第"+lineNo+"行大区为空");
				continue;
			}
			BigRegion br = getBigRegion(bigRegion, lineNo);
			
			String middleRegion = getValue(row.getCell(1));
			if(StringUtil.isEmpty(middleRegion)) {
				continue;
			}
			
			MiddleRegion mr = getMiddleRegion(bigRegion, middleRegion, lineNo, br.getMiddleRegions());
			
			String smallRegion = getValue(row.getCell(2));
			if(StringUtil.isEmpty(smallRegion)) {
				continue;
			}
			
			SmallRegion sr = getSmallRegion(bigRegion, middleRegion, smallRegion, lineNo, mr.getSmallRegions());
			
			String province = getValue(row.getCell(3));
			if(StringUtil.isEmpty(province)) {
				continue;
			}
			String city = getValue(row.getCell(4));
			if(StringUtil.isEmpty(city)) {
				continue;
			}
			String area = getValue(row.getCell(5));
			if(StringUtil.isEmpty(area)) {
				continue;
			}
			
			setAreas(province, city, area, lineNo, sr.getAreas());
		}
	}

	private void addSalesAreas() throws Exception { 
		Map<String, Integer> regionMap = arm.getAllRegionMap();
		Map<String, String> areasMap = arm.getAllAreasMap();
		Set<String> salesAreaCountrySet = arm.getAllSalesAreaCountrySet();
		
		for(String bigRegionKey : bigRegionMap.keySet()) {
			BigRegion br = bigRegionMap.get(bigRegionKey);
			if(!regionMap.containsKey(bigRegionKey)) {
				//新增大区
				addBigRegion(bigRegionKey, br, regionMap);
				bigRegionCount++;
			}
			for(String middleRegionKey : br.getMiddleRegions().keySet()) {
				MiddleRegion mr = br.getMiddleRegions().get(middleRegionKey);
				if(!regionMap.containsKey(middleRegionKey)) {
					//新增地区
					addMiddleRegion(bigRegionKey,middleRegionKey, mr, regionMap);
					middleAreaCount++;
				}
				for(String smallRegionKey : mr.getSmallRegions().keySet()) {
					SmallRegion sr = mr.getSmallRegions().get(smallRegionKey);
					if(!regionMap.containsKey(smallRegionKey)) {
						//新增小区
						addMiddleRegion(middleRegionKey,smallRegionKey, sr, regionMap);
						smallAreaCount++;
					}
					for(String areasKey : sr.getAreas().keySet()) {
						Areas area = sr.getAreas().get(areasKey);
						Integer middleRegionId = regionMap.get(smallRegionKey);
						String areaId = areasMap.get(areasKey);
						if(StringUtil.isEmpty(areaId)) {
							resultMsg.append("第"+area.getLineNo()+"行系统不存在区/县["+areasKey+"]的信息,关联区域失败<br>");
							continue;
						}
						String salesAreaCountryKey = middleRegionId+" "+areaId;
						if(salesAreaCountrySet.contains(salesAreaCountryKey)) {
							resultMsg.append("第"+area.getLineNo()+"行系统已存在区/县["+areasKey+"]与销售区域["+smallRegionKey+"]的关联信息<br>");
							continue;
						}
						//关联区县
						addSalesAreaCountry(middleRegionId, areaId);
						areaCount++;
					}
				}
			}
		}
		
	}

	private void addSalesAreaCountry(Integer middleRegionId, String areaId) throws Exception {
		SalesAreaCountry sac = new SalesAreaCountry();
		sac.setCountryAreaId(Integer.valueOf(areaId));
		sac.setSalesAreaId(middleRegionId);
		sac.setMakeDate(currentDate); 
		asac.addSalesAreaCountry(sac);
	}

	private void addMiddleRegion(String middleRegionKey, String smallRegionKey,
			SmallRegion sr, Map<String, Integer> regionMap) {
		RegionItemInfo region = new RegionItemInfo();
		region.setMdate(currentDate);
		region.setName(sr.getName());
		region.setRank(2);
		region.setpId(regionMap.get(middleRegionKey));
		arm.addRegion(region);
		regionMap.put(smallRegionKey, region.getId());
		
	}

	private void addMiddleRegion(String bigRegionKey, String middleRegionKey,
			MiddleRegion mr, Map<String, Integer> regionMap) {
		RegionItemInfo region = new RegionItemInfo();
		region.setMdate(currentDate);
		region.setName(mr.getName());
		region.setRank(1);
		region.setpId(regionMap.get(bigRegionKey));
		arm.addRegion(region);
		regionMap.put(middleRegionKey, region.getId());
		
	}

	private void addBigRegion(String bigRegionKey, BigRegion bigRegion, Map<String, Integer> regionMap) {
		RegionItemInfo region = new RegionItemInfo();
		region.setMdate(currentDate);
		region.setName(bigRegion.getName());
		region.setRank(0);
		region.setpId(topAreaId);
		arm.addRegion(region);
		regionMap.put(bigRegionKey, region.getId());
	}

	private void setAreas(String province, String city, String area,
			int lineNo, Map<String, Areas> areas) {
		String key = province +" "+ city +" "+ area;
		if(!areas.containsKey(key)) {
			Areas a = new Areas();
			a.setLineNo(lineNo);
			areas.put(key, a);
		}
	}

	private SmallRegion getSmallRegion(String bigRegionName, String middleRegionName,
			String smallRegionName, int lineNo, Map<String, SmallRegion> smallRegions) {
		String key = bigRegionName + middleRegionName + smallRegionName;
		if(smallRegions.containsKey(key)) {
			return smallRegions.get(key);
		} else {
			SmallRegion sr = new SmallRegion();
			sr.setLineNo(lineNo);
			sr.setName(smallRegionName);
			sr.setAreas(new TreeMap<String, Areas>());
			smallRegions.put(key, sr);
			return sr;
		}
	}

	private MiddleRegion getMiddleRegion(String bigRegionName, String middleRegionName, int lineNo,
			Map<String, MiddleRegion> middleRegions) {
		String key = bigRegionName + middleRegionName;
		if(middleRegions.containsKey(key)) {
			return middleRegions.get(key);
		} else {
			MiddleRegion mr = new MiddleRegion();
			mr.setLineNo(lineNo);
			mr.setName(middleRegionName);
			mr.setSmallRegions(new TreeMap<String, SmallRegion>());
			middleRegions.put(key, mr);
			return mr;
		}
	}

	/**
	 * @param name
	 * @param lineNo
	 * @return
	 */
	private BigRegion getBigRegion(String name, int lineNo) {
		String key = name;
		if(bigRegionMap.containsKey(key)) {
			return bigRegionMap.get(key);
		} else {
			BigRegion br = new BigRegion();
			br.setLineNo(lineNo);
			br.setName(name);
			br.setMiddleRegions(new TreeMap<String, MiddleRegion>());
			bigRegionMap.put(key, br);
			return br;
		}
	}

	private String getValue(Cell cell) {
	    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	        return String.valueOf((int)cell.getNumericCellValue()).trim();
	    } else {
	        return String.valueOf(cell.getStringCellValue()).trim();
	    }
	}
	
	public class BigRegion {
		private String name;
		private int lineNo;
		private Map<String, MiddleRegion> middleRegions;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getLineNo() {
			return lineNo;
		}
		public void setLineNo(int lineNo) {
			this.lineNo = lineNo;
		}
		public Map<String, MiddleRegion> getMiddleRegions() {
			return middleRegions;
		}
		public void setMiddleRegions(Map<String, MiddleRegion> middleRegions) {
			this.middleRegions = middleRegions;
		}
	}
	
	public class MiddleRegion {
		private String name;
		private int lineNo;
		private Map<String, SmallRegion> smallRegions;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getLineNo() {
			return lineNo;
		}
		public void setLineNo(int lineNo) {
			this.lineNo = lineNo;
		}
		public Map<String, SmallRegion> getSmallRegions() {
			return smallRegions;
		}
		public void setSmallRegions(Map<String, SmallRegion> smallRegions) {
			this.smallRegions = smallRegions;
		}
	}
	
	public class SmallRegion {
		private String name;
		private int lineNo;
		private Map<String, Areas> areas;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getLineNo() {
			return lineNo;
		}
		public void setLineNo(int lineNo) {
			this.lineNo = lineNo;
		}
		public Map<String, Areas> getAreas() {
			return areas;
		}
		public void setAreas(Map<String, Areas> areas) {
			this.areas = areas;
		}
	}
	
	public class Areas {
		private int lineNo;
		public int getLineNo() {
			return lineNo;
		}
		public void setLineNo(int lineNo) {
			this.lineNo = lineNo;
		}
	}
}
