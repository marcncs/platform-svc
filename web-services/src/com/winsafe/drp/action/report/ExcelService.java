package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.winsafe.drp.dao.ReportForm;

public class ExcelService {
	// 标题
	private String title;
	// 检索条件
	private String[] condition;
	// 检索条件以及对应的值
	private Map<String, String> searchCondition;
	// 表头
	private String[] header;
	// 数据
	private Map<String, Object> valueMap;

	//设置单元格样式
	private WritableCellFormat wcfFC = null;
	private WritableCellFormat wchT = null;
	private WritableCellFormat seachT = null;

	/**
	 * @param list
	 * @param os
	 * @param request
	 * @throws Exception
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void writeXls(List<ReportForm> list, OutputStream os, HttpServletRequest request,String ReportForm) throws Exception, RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(os);

		int snum = 1;
		int m = 0;
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			// 整体行
			int rows = 1;
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 50000;
			sheets[j].mergeCells(0, start, 23, start);

			// 标题
			sheets[j].addCell(new Label(0, start, title, wchT));
			// 检索条件
			for (int i = 0; i < condition.length; i++) {
				//没三个条件换一次行
				if (m % 6 == 0) {
					m = 0;
					rows++;
				}
				sheets[j].addCell(new Label(m++, start + rows, condition[i], seachT));
				sheets[j].addCell(new Label(m++, start + rows, searchCondition.get(condition[i]) == null ? "" : searchCondition.get(condition[i])));
			}

			// 表头
			m = 0;
			for (int i = 0; i < header.length; i++) {
				sheets[j].addCell(new Label(m++, start + rows + 1, header[i], wcfFC));
			}

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + (rows + 2);
				Object object = list.get(i);
				m = 0;
				//获取字段
				Field[] fds = object.getClass().getDeclaredFields();
				for (int k = 0; k < fds.length; k++) {
					//判断是否为显示字段
					if (ReportForm.contains(fds[k].getName().toLowerCase())) {
						//获取字段对应的值
						Object objectValue = getObjectValue(fds[k].getName(), object);
						if (objectValue != null) {
							sheets[j].addCell(new Label(m++, row, String.valueOf(typeUtil(objectValue.getClass(), objectValue.toString()))));
						} else {
							sheets[j].addCell(new Label(m++, row, ""));
						}
					}
				}
			}
		}
		workbook.write();
		workbook.close();
		os.flush();
		os.close();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, String> getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(Map<String, String> searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String[] getHeader() {
		return header;
	}

	public void setHeader(String[] header) {
		this.header = header;
	}

	public String[] getCondition() {
		return condition;
	}

	public void setCondition(String[] condition) {
		this.condition = condition;
	}

	public Map<String, Object> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, Object> valueMap) {
		this.valueMap = valueMap;
	}

	public WritableCellFormat getWcfFC() {
		return wcfFC;
	}

	public void setWcfFC(WritableCellFormat wcfFC) {
		this.wcfFC = wcfFC;
	}

	public WritableCellFormat getWchT() {
		return wchT;
	}

	public void setWchT(WritableCellFormat wchT) {
		this.wchT = wchT;
	}

	public WritableCellFormat getSeachT() {
		return seachT;
	}

	public void setSeachT(WritableCellFormat seachT) {
		this.seachT = seachT;
	}

	/**
	 * 获取字段对应的值
	 * @param fieldName
	 * @param object
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object getObjectValue(String fieldName, Object object) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Method method = object.getClass().getMethod(methodName, new Class[] {});
		Object value = method.invoke(object, new Object[] {});
		return value;
	}

	/**根据字段类型还回值类型
	 * @param clazz
	 * @param str
	 * @return
	 */
	private Object typeUtil(Class clazz, String str) {

		SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");

		if (clazz == Integer.class || clazz.getName().equalsIgnoreCase("int")) {
			return Integer.parseInt(str);
		} else if (clazz == Date.class || clazz.getName().equalsIgnoreCase("Date")) {
			Date d = null;
			try {
				d = smp.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return d;
		} else if (clazz == Double.class || clazz.getName().equalsIgnoreCase("Double")) {

			return Double.parseDouble(str);
		} else if (clazz == Float.class || clazz.getName().equalsIgnoreCase("float")) {

			return Float.parseFloat(str);
		} else if (clazz == Long.class || clazz.getName().equalsIgnoreCase("long")) {

			return Long.parseLong(str);
		} else if (clazz == Short.class || clazz.getName().equalsIgnoreCase("Short")) {

			return Short.parseShort(str);

		} else if (clazz == Boolean.class || clazz.getName().equalsIgnoreCase("boolean")) {

			return Boolean.parseBoolean(str);

		}
		return str;
	}
}
