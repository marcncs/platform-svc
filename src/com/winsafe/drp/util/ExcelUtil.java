package com.winsafe.drp.util;

import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtil {
	
	public static String getValue(Cell cell) {
		if(cell == null) {
			return "";
		}
	    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	        return String.valueOf(new BigDecimal(cell.getNumericCellValue())).trim();
	    } else {
	        return String.valueOf(cell.getStringCellValue()).trim();
	    }
	}
}
