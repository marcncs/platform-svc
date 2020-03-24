package com.winsafe.drp.action.warehouse;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WritableFont.FontName;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.TTIdcodePDFBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.QRcode.MatrixToImageWriter;
import com.winsafe.hbm.util.DateUtil;

/**
 * 进行生成提货清单的Action
 * 
 * @version 0.1
 * @author wei.li
 * @since 2010/09/18
 */
public class excPutPickTakeBillAction extends BaseAction {
	// 行高
	private static final int rowHeight = 180;
	// 字体名
	private static final FontName fontName = WritableFont.ARIAL;
	// 字体大小
	private static final int fontSize = 8;
	// 产品名列宽
	private static final int productNameColumnWidth = 15;
	// 规格列宽
	private static final int specColumnWidth = 15;
	// 仓位列宽
	private static final int warehousebitColumnWidth = 15;
	// 生产日期列宽
	private static final int producedateColumnWidth = 10;
	// 数量列宽
	private static final int quantityColumnWidth = 10;
	// 备注列宽
	private static final int remarkColumnWidth = 10;

	private AppProduct appProduct = new AppProduct();

	/**
	 * 进行生成提货清单的execute方法，得到各个ID,调方法进行查询，得到List，然后传入生成Excel的方法中
	 * 
	 * @version 0.1
	 * @author wei.li
	 * @since 2010/09/18
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		OutputStream os = response.getOutputStream();
		try {
			String type = request.getParameter("type");

			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=taketicketlist.pdf");
			response.setContentType("application/pdf");

			// 提货清单
			if (type.equals("1")) {

				String ttid = request.getParameter("ttid");
				String scannerNo = request.getParameter("scannerNo");
				AppTakeTicket appTakeTicket = new AppTakeTicket();
				TakeTicket takeTicket = appTakeTicket.getTakeTicketById(ttid);
//				//修改单据中的采集器编号
//				takeTicket.setScannerNo(scannerNo);
				/*
				 * if(!whetherPicked(ttid)){ return null; }
				 */

				AppTakeTicketDetailBatchBit appTakeTicketDetailBatchBit = new AppTakeTicketDetailBatchBit();
				List<TakeTicketDetailBatchBit> attdbs = appTakeTicketDetailBatchBit.getBatchBitByTTID(ttid);
				// 去掉出库数量为0的记录
				List<TakeTicketDetailBatchBit> removeList = new ArrayList<TakeTicketDetailBatchBit>();
				for (TakeTicketDetailBatchBit takeTicketDetailBatchBit : attdbs) {
					if (takeTicketDetailBatchBit.getQuantity() == 0) {
						removeList.add(takeTicketDetailBatchBit);
					}
				}
				attdbs.removeAll(removeList);
				writeXls2(attdbs, takeTicket, os, request, mapping, form,
						response);
				// DBUserLog.addUserLog(userid, 7, "仓库管理>>导出提货清单!");
			}
//			// 条码清单
//			else if (type.equals("2")) {
//				// 2011-5-19 liujin
//				String BeginDate = request.getParameter("BeginDate");
//				String EndDate = request.getParameter("EndDate");
//
//				AppTakeTicketIdcode appTakeTicketIdcode = new AppTakeTicketIdcode();
//				List<TakeTicketIdcode> takeTicketIdcodeList = appTakeTicketIdcode
//						.getTakeTicketIdcodeByDate(BeginDate + " 0:00:00",
//								EndDate + " 23:59:59");
//				writePdf(takeTicketIdcodeList, os, request, mapping, form,
//						response);
//			} else {
//			}

			/*
			 * // 得到Session中的SQL语句 String whereSql = (String)
			 * request.getSession().getAttribute( "whereSql"); String pickId =
			 * null;
			 * 
			 * AppTakeBill asl = new AppTakeBill(); AppTakeTicketDetail asld =
			 * new AppTakeTicketDetail(); List<TakeTicket> pils = new ArrayList<TakeTicket>();
			 * List li = new ArrayList(); // 将得到的SQL语句送到方法中进行查询 List<TakeBill>
			 * pil = asl.getTakeBill1(whereSql); // 将得到数据的集合进行过滤 pil =
			 * RuleUtil.filterBillByWHRule(request, pil, UserManager
			 * .getUser(request).getUserid()); for (TakeBill take : pil) {
			 * pickId = take.getId(); pils = asld.getTakeTicketByTtid(pickId);//
			 * 传值查询得到TakeTicket类型的list if (pils.size() > 0 &&
			 * pils.get(0).getIsaudit() == 0) { li.add(pils.get(0).getId());//
			 * 得到take_ticket表中的billno的ID } else { // 暂无逻辑 } } List<TakeTicketDetail>
			 * sals = new ArrayList<TakeTicketDetail>(); List<TakeTicketDetail>
			 * allList = new ArrayList<TakeTicketDetail>(); for (int i = 0; i <
			 * li.size(); i++) { sals = asld.getTakeTicketDetailByTtid((String)
			 * li.get(i));// 传值查询得到TakeTicketDetail类型的list
			 * allList.addAll(sals);// 得到sals循环的所有值的allList }
			 * 
			 * List<TakeTicketDetail> tempList = new ArrayList<TakeTicketDetail>();//
			 * 再声明TakeTicketDetail类型的临时list
			 * 
			 * for (TakeTicketDetail takeTicketDetail : allList) { //
			 * 遍历循环allList if (tempList.size() == 0) { // 判断临时list是否为空
			 * tempList.add(takeTicketDetail); // 为空就添加allList } else {
			 * TakeTicketDetail tempDetail = isExistInList(tempList,
			 * takeTicketDetail);// 将临时的list和循环allList的对象传入方法中 if (tempDetail ==
			 * null) { // 判断返回接收的对象是否为空 tempList.add(takeTicketDetail); //
			 * 为空就添加allList } else {
			 * tempDetail.setQuantity(tempDetail.getQuantity() +
			 * takeTicketDetail.getQuantity()); } //
			 * 否则就给返回接收的TakeTicketDetail对象加值，值为临时list中的数据+allList中的数据 } }
			 * 
			 * String pid = null; String date = null; List<ProductStockpile>
			 * list2 = new ArrayList<ProductStockpile>();//
			 * 声明ProductStockpile类型的list List list = new ArrayList(); for
			 * (TakeTicketDetail t : tempList) { pid = t.getProductid();//
			 * 得到take_ticket_detail表中的ProductID和Batch date = t.getBatch(); list2 =
			 * this.freightList(request, pid, date);//
			 * 传值进入本类中的方法，查询得到ProductStockpile类型的list
			 * list.add(list2.get(0).getStockpile());// 将值再传入到list中 }
			 * 
			 * String wid = null; String wbid = null; for (ProductStockpile p :
			 * list2) { wid = p.getWarehouseid();//
			 * 得到product_stockpile表中的WarehouseID和WarehouseBit wbid =
			 * p.getWarehousebit(); } List<WarehouseBit> list3 = this
			 * .getstockstatWrea(request, wid, wbid);//
			 * 传值进入本类中的方法，查询得到WarehouseBit类型的list
			 */
			// 声明写入流os

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.close();
		}
		return null;
	}

	public void writePdf(List<TakeTicketIdcode> takeTicketIdcodeList,
			OutputStream os, HttpServletRequest request, ActionMapping mapping,
			ActionForm form, HttpServletResponse response)
			throws NumberFormatException, Exception {

		BaseFont bfChinese = null;
		BaseFont bfSun = null;
		String win = "C:\\WINDOWS\\Fonts\\SIMSUN.TTC,1"; // 读取windows内部的字体,1为宋体
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED); // 设置字体
			bfSun = BaseFont.createFont(win, BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED); // 设置字体

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Font fontChinese = new Font(bfChinese, 11); // 设置字体及大小
		Font fornChinese2 = new Font(bfChinese, 18);
		try {
			Document document = new Document(PageSize.A4, 10, 10, 10, 10); // 生成的PDF文件所有元素的容器,定义纸张大小和页面的左右上下边距
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			// 创建一个byte型别数组的缓冲区，用实例向数组中读出byte型数据
			try {
				//
				PdfWriter.getInstance(document, ba); // 指定Document对象的类型,生成PDF文档格式
				document.open();
				Paragraph p = new Paragraph(); // Paragraph创建段落
				Chunk chunk = new Chunk("条码清单", new Font(bfChinese, 20));
				p.add(chunk);
				p.setAlignment(Element.ALIGN_CENTER);
				p.setSpacingAfter(10f);
				document.add(p); // 容器添加段落

				String BeginDate = request.getParameter("BeginDate");
				String EndDate = request.getParameter("EndDate");
				Paragraph pDate = new Paragraph(); // Paragraph创建段落
				Chunk chunkDate = new Chunk("" + BeginDate + " 至 " + EndDate,
						new Font(bfChinese, 11));
				pDate.add(chunkDate);
				pDate.setAlignment(Element.ALIGN_RIGHT);
				pDate.setSpacingAfter(10f);
				document.add(pDate); // 容器添加段落

				Paragraph p2 = new Paragraph();
				Chunk chunk1 = new Chunk("条码明细", new Font(bfSun, 12, Font.BOLD));
				p2.add(chunk1);
				p2.setAlignment(Element.ALIGN_LEFT);
				p2.setSpacingAfter(10f);
				document.add(p2);

				// 2011-5-20 注释掉TT清单
				// float[] tabl = { 16f, 20f, 10f, 12f, 12f };
				// PdfPTable table = new PdfPTable(tabl);
				// table.setWidthPercentage(100f);
				// PdfPCell h1 = new PdfPCell(new Paragraph("IS系统检货小票",
				// fontChinese));
				// PdfPCell h2 = new PdfPCell(new Paragraph("产品名称",
				// fontChinese));
				// PdfPCell h3 = new PdfPCell(new Paragraph("产品批次",
				// fontChinese));
				// PdfPCell h4 = new PdfPCell(new Paragraph("出库数量",
				// fontChinese));
				// PdfPCell h5 = new PdfPCell(new Paragraph("备注", fontChinese));
				// table.addCell(h1);
				// table.addCell(h2);
				// table.addCell(h3);
				// table.addCell(h4);
				// table.addCell(h5);
				// PdfPCell cell;
				// int currRow = 9;
				// int currRow1 = 9;
				// int currRow2 = 9;
				// int currProductRow = 10;
				// int currBitRow = 10;
				// String producti = null;
				// AppProductStockpile appProductStockpile = new
				// AppProductStockpile();
				// for (TakeTicketDetailBatchBit batchBit : batchBits) {
				// currRow++;
				// producti = batchBit.getProductid();
				// currProductRow = currRow;
				// // 每for循环一次currRow自加1
				//
				// // ttid
				// cell = new PdfPCell(new Paragraph(batchBit.getTtid(),
				// fontChinese));
				// table.addCell(cell);
				//
				// // 产品名称
				// Product product = appProduct.getProductByID(producti);
				// String pnamespec = product.getProductname()
				// + product.getSpecmode();
				// cell = new PdfPCell(new Paragraph(pnamespec, fontChinese));
				// table.addCell(cell);
				//
				// // 产品批次
				// cell = new PdfPCell(new Paragraph(batchBit.getBatch(),
				// fontChinese));
				// table.addCell(cell);
				//
				// // 出库数量
				// cell = new PdfPCell(new Paragraph(String.valueOf(batchBit
				// .getQuantity()), fontChinese));
				// table.addCell(cell);
				//
				// cell = new PdfPCell(new Paragraph("", fontChinese));
				// table.addCell(cell);
				//
				// }
				// document.add(table);

				// // 增加条码清单
				// Paragraph p3 = new Paragraph();
				// Chunk chunk2 = new Chunk("条码明细", new Font(bfSun, 12,
				// Font.BOLD));
				// p3.add(chunk2);
				// p3.setAlignment(Element.ALIGN_LEFT);
				// p3.setSpacingAfter(10f);
				// document.add(p3);
				PdfPCell cell;

				float[] idcodeWith = { 20f, 20f, 8f, 8f, 6f, 14f };
				PdfPTable idcodetable = new PdfPTable(idcodeWith);

				BigDecimal totalQuantity = new BigDecimal(0);

				// 确定条码条数
				if (takeTicketIdcodeList.size() == 0) {
					Paragraph perror = new Paragraph();
					Chunk chunkerror = new Chunk("该时间段下无条码", new Font(bfSun,
							12, Font.BOLD));
					perror.add(chunkerror);
					perror.setAlignment(Element.ALIGN_LEFT);
					perror.setSpacingAfter(10f);
					document.add(perror);
				} else {

					idcodetable.setWidthPercentage(100f);
					PdfPCell h5 = new PdfPCell(new Paragraph("条码", fontChinese));
					PdfPCell h6 = new PdfPCell(new Paragraph("产品名称/规格",
							fontChinese));
					PdfPCell h7 = new PdfPCell(new Paragraph("起始物流码",
							fontChinese));
					PdfPCell h8 = new PdfPCell(new Paragraph("结束物流码",
							fontChinese));
					PdfPCell h9 = new PdfPCell(new Paragraph("小计(箱)", fontChinese));
					PdfPCell h10 = new PdfPCell(
							new Paragraph("上传时间", fontChinese));
					idcodetable.addCell(h5);
					idcodetable.addCell(h6);
					idcodetable.addCell(h7);
					idcodetable.addCell(h8);
					idcodetable.addCell(h9);
					idcodetable.addCell(h10);

					// List<TTIdcodePDFBean> formbean =
					// initTTIdcodeBean(ttidcodes);
					for (TakeTicketIdcode ttIdcodePDF : takeTicketIdcodeList) {

						// 条码
						cell = new PdfPCell(new Paragraph(ttIdcodePDF
								.getIdcode(), fontChinese));
						idcodetable.addCell(cell);

						// 产品名称/规格
						Product product = appProduct.getProductByID(ttIdcodePDF
								.getProductid());
						String pnamespec = product.getProductname()
								+ product.getSpecmode();
						cell = new PdfPCell(new Paragraph(pnamespec,
								fontChinese));
						idcodetable.addCell(cell);
						// 起始码
						cell = new PdfPCell(new Paragraph(ttIdcodePDF
								.getStartno(), fontChinese));
						idcodetable.addCell(cell);
						// 终止码
						cell = new PdfPCell(new Paragraph(ttIdcodePDF
								.getEndno(), fontChinese));
						idcodetable.addCell(cell);
						// 小计
						cell = new PdfPCell(new Paragraph(ttIdcodePDF
								.getQuantity().toString(), fontChinese));
						idcodetable.addCell(cell);
						// 时间

						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String makeTime = format.format(ttIdcodePDF
								.getMakedate());
						cell = new PdfPCell(
								new Paragraph(makeTime, fontChinese));
						idcodetable.addCell(cell);

						totalQuantity = totalQuantity.add(BigDecimal
								.valueOf(ttIdcodePDF.getQuantity()));

					}
				}
				document.add(idcodetable);
				// 确定条码条数
				if (takeTicketIdcodeList.size() > 0) {
					Paragraph total = new Paragraph();
					Chunk totalChunk = new Chunk("总计(箱): " + totalQuantity,
							new Font(bfSun, 11, Font.BOLD));
					total.add(totalChunk);
					total.setAlignment(Element.ALIGN_RIGHT);
					total.setSpacingAfter(10f);
					document.add(total);
				}

			} catch (DocumentException de) {
				System.err.println(de.getMessage());
			}
			
			document.close(); // 关闭容器
			response.setContentLength(ba.size());
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			out.flush();
			out.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
/*
	private List<TTIdcodePDFBean> initTTIdcodeBean(
			List<TakeTicketIdcode> idcodes) {
		List<TTIdcodePDFBean> resultList = new ArrayList<TTIdcodePDFBean>();
		for (TakeTicketIdcode takeTicketIdcode : idcodes) {
			// 第一条条码直接添加入
			if (resultList.size() == 0) {
				TTIdcodePDFBean ttIdcodePDFBean = new TTIdcodePDFBean();
				ttIdcodePDFBean.setProductid(takeTicketIdcode.getProductid());
				ttIdcodePDFBean.setStartno(takeTicketIdcode.getStartno());
				ttIdcodePDFBean.setEndno(takeTicketIdcode.getEndno());
				ttIdcodePDFBean.setQuantity(takeTicketIdcode.getQuantity());
				resultList.add(ttIdcodePDFBean);
			} else {
				// 判断是否连号
				TTIdcodePDFBean ttIdcodePDFBean = existInListByNumberAndProduct(
						takeTicketIdcode, resultList);
				// 如果不连号
				if (ttIdcodePDFBean == null) {
					TTIdcodePDFBean newBean = new TTIdcodePDFBean();
					newBean.setProductid(takeTicketIdcode.getProductid());
					newBean.setStartno(takeTicketIdcode.getStartno());
					newBean.setEndno(takeTicketIdcode.getEndno());
					newBean.setQuantity(takeTicketIdcode.getQuantity());
					resultList.add(newBean);
				} else {
					ttIdcodePDFBean.setEndno(StringUtil.fillZero(Integer
							.valueOf(takeTicketIdcode.getEndno()), 7));
					ttIdcodePDFBean.setQuantity(ttIdcodePDFBean.getQuantity()
							+ takeTicketIdcode.getQuantity());
				}
			}
		}

		return resultList;
	}
	*/

	private TTIdcodePDFBean existInListByNumberAndProduct(
			TakeTicketIdcode takeTicketIdcode, List<TTIdcodePDFBean> resultList) {
		for (TTIdcodePDFBean ttIdcodePDFBean : resultList) {
			if (ttIdcodePDFBean.getProductid().equals(
					takeTicketIdcode.getProductid())
					&& Integer.valueOf(ttIdcodePDFBean.getEndno()) + 1 == Integer
							.valueOf(takeTicketIdcode.getStartno())) {
				return ttIdcodePDFBean;
			}
		}
		return null;
	}

	public void writeXls(List<TakeTicketDetail> list, List list2,
			List<WarehouseBit> list3, OutputStream os,
			HttpServletRequest request, ActionMapping mapping, ActionForm form,
			HttpServletResponse response) throws NumberFormatException,
			Exception {

		int row = 9;
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		WritableSheet sheet = workbook.createSheet("提货清单报表", 0);
		int start = 2;
		sheet.mergeCells(0, start, 6, start);
		sheet.mergeCells(0, start + 1, 6, start);
		sheet.addCell(new Label(0, start, "提货清单", wchT));
		sheet.addCell(new Label(0, start + 2, "发货日期:", seachT));
		sheet.addCell(new Label(1, start + 2, null));
		sheet.addCell(new Label(2, start + 2, "应发总数:", seachT));
		sheet.addCell(new Label(3, start + 2, null));
		sheet.addCell(new Label(4, start + 2, "叉车司机:", seachT));
		sheet.addCell(new Label(5, start + 2, null));

		sheet.addCell(new Label(0, start + 3, "车    号:", seachT));
		sheet.addCell(new Label(1, start + 3, null));
		sheet.addCell(new Label(2, start + 3, "客户名称:", seachT));
		sheet.addCell(new Label(3, start + 3, null));
		sheet.addCell(new Label(4, start + 3, "对应IC卡号:", seachT));
		sheet.addCell(new Label(5, start + 3, null));

		sheet.addCell(new Label(0, start + 4, "发货明细:", seachT));
		sheet.addCell(new Label(1, start + 4, null));

		sheet.addCell(new Label(0, start + 6, "导出机构:", seachT));
		sheet.addCell(new Label(1, start + 6, request
				.getAttribute("porganname").toString()));
		sheet.addCell(new Label(2, start + 6, "导出人:", seachT));
		sheet.addCell(new Label(3, start + 6, request.getAttribute("pusername")
				.toString()));
		sheet.addCell(new Label(4, start + 6, "导出时间:", seachT));
		sheet.addCell(new Label(5, start + 6, DateUtil.getCurrentDateTime()));
		// 设置字体、行高、列宽
		sheet.setRowView(0, rowHeight);
		sheet.setRowView(1, rowHeight);
		sheet.setColumnView(0, productNameColumnWidth);

		sheet.setColumnView(1, producedateColumnWidth);
		sheet.setColumnView(2, warehousebitColumnWidth);
		sheet.setColumnView(3, quantityColumnWidth);
		sheet.setColumnView(4, remarkColumnWidth);
		WritableCellFormat cellFormat = new WritableCellFormat();
		WritableFont font = new WritableFont(fontName, fontSize);
		cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		cellFormat.setBorder(jxl.format.Border.ALL,
				jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
		cellFormat.setFont(font);
		cellFormat.setWrap(true);

		// 设置页边距
		SheetSettings settings = sheet.getSettings();
		settings.setLeftMargin(0.2);
		settings.setRightMargin(0.2);
		settings.setTopMargin(0.2);
		settings.setBottomMargin(0.2);
		settings.setPrintHeaders(false);
		settings.setHeaderMargin(0.2);
		settings.setFooterMargin(0.2);
		settings.setDefaultRowHeight(-10);

		// 设置表头
		Label productNum = new Label(0, row, "产品编号");
		productNum.setCellFormat(cellFormat);
		sheet.addCell(productNum);
		Label productName = new Label(1, row, "产品名称");
		productName.setCellFormat(cellFormat);
		sheet.addCell(productName);
		Label specHeader = new Label(2, row, "产品规格");
		specHeader.setCellFormat(cellFormat);
		sheet.addCell(specHeader);
		Label produceDate = new Label(3, row, "产品批次");
		produceDate.setCellFormat(cellFormat);
		sheet.addCell(produceDate);
		Label quantityHeader = new Label(4, row, "出库数量");
		quantityHeader.setCellFormat(cellFormat);
		sheet.addCell(quantityHeader);
		Label freightName = new Label(5, row, "仓位名称");
		freightName.setCellFormat(cellFormat);
		sheet.addCell(freightName);
		Label freightNum = new Label(6, row, "库存数量");
		freightNum.setCellFormat(cellFormat);
		sheet.addCell(freightNum);

		// 合并单元格
		int currRow = 9;
		int currRow1 = 9;
		int currRow2 = 9;
		int currProductRow = 10;
		int currBitRow = 10;
		String producti = null;
		// 设置第一行数据
		if (null != list && 0 != list.size()) {
			producti = list.get(0).getProductid();
			// 第2行的第0列
			Label firstProductName = new Label(0, row + 1, list.get(0)
					.getProductid());
			firstProductName.setCellFormat(cellFormat);
			sheet.addCell(firstProductName);
			// 第2行的第1列
			Label firstSpecMode = new Label(1, row + 1, list.get(0)
					.getProductname());
			firstSpecMode.setCellFormat(cellFormat);
			sheet.addCell(firstSpecMode);
			// 第2行的第2列
			Label firstBitMode1 = new Label(2, row + 1, list.get(0)
					.getSpecmode());
			firstBitMode1.setCellFormat(cellFormat);
			sheet.addCell(firstBitMode1);
		} else {
			// 暂无逻辑
		}

		for (TakeTicketDetail t : list) {
			currRow++;
			if (producti.equals(t.getProductid())) {
				// 无操作
			} else {
				sheet.mergeCells(0, currProductRow, 0, currRow - 1);
				sheet.mergeCells(1, currProductRow, 1, currRow - 1);
				producti = t.getProductid();
				currProductRow = currRow;
				// 第2行的第currRow列,每for循环一次currRow自加1
				Label firstProductNameB = new Label(0, currRow, t
						.getProductid());
				firstProductNameB.setCellFormat(cellFormat);
				sheet.addCell(firstProductNameB);
				// 第2行的第currRow列,每for循环一次currRow自加1
				Label firstSpecModeB = new Label(1, currRow, t.getProductname());
				firstSpecModeB.setCellFormat(cellFormat);
				sheet.addCell(firstSpecModeB);
				// 第2行的第currRow列,每for循环一次currRow自加1
				Label firstBitMode1B = new Label(2, currRow, t.getSpecmode());
				firstBitMode1B.setCellFormat(cellFormat);
				sheet.addCell(firstBitMode1B);
			}

			Label firstBitMode2B = new Label(3, currRow, t.getBatch());
			firstBitMode2B.setCellFormat(cellFormat);
			sheet.addCell(firstBitMode2B);

			Label firstBitMode3B = new Label(4, currRow, String.valueOf(t
					.getQuantity()));
			firstBitMode3B.setCellFormat(cellFormat);
			sheet.addCell(firstBitMode3B);

			// 循环输出仓位
			for (WarehouseBit alp : list3) {
				currRow1++;
				Label firstBitMode4 = new Label(5, currRow1, alp.getBitName());
				firstBitMode4.setCellFormat(cellFormat);
				sheet.addCell(firstBitMode4);
			}
		}
		// 输出库存量
		for (int i = 0; i < list2.size(); i++) {
			currRow2++;
			Label firstBitMode5 = new Label(6, currRow2, String.valueOf(list2
					.get(i)));
			firstBitMode5.setCellFormat(cellFormat);
			sheet.addCell(firstBitMode5);
		}
		// 合并单元格
		if (null != list && 0 != list.size()) {
			sheet.mergeCells(0, currProductRow, 0, currRow);
			sheet.mergeCells(1, currProductRow, 1, currRow);
			sheet.mergeCells(2, currBitRow, 2, currRow);
		} else {
			// 暂无逻辑
		}
		workbook.write();
		workbook.close();
		os.close();

	}

	public void writeXls2(List<TakeTicketDetailBatchBit> batchBits,
			TakeTicket takeTicket, OutputStream os, HttpServletRequest request,
			ActionMapping mapping, ActionForm form, HttpServletResponse response)
			throws NumberFormatException, Exception {

//		String sql = "from TakeBill "
//				+ request.getSession().getAttribute("whereSql").toString()
//				+ " order by makedate desc";
//		String filepath = request.getRealPath("") + "\\scanfile\\download\\";

//		TxtPutTakeTicketThreadAction txtputtaketicketthreadaction = new TxtPutTakeTicketThreadAction(
//				sql, filepath);
//		txtputtaketicketthreadaction.start();

		BaseFont bfChinese = null;
		BaseFont bfSun = null;
		String win = "C:\\WINDOWS\\Fonts\\SIMSUN.TTC,1"; // 读取windows内部的字体,1为宋体
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED); // 设置字体
			bfSun = BaseFont.createFont(win, BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED); // 设置字体

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Font fontChinese = new Font(bfChinese, 12); // 设置字体及大小
		Font fornChinese2 = new Font(bfChinese, 18);
		try {
			Document document = new Document(PageSize.A4, 10, 10, 0, 10); // 生成的PDF文件所有元素的容器,定义纸张大小和页面的左右上下边距
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			// 创建一个byte型别数组的缓冲区，用实例向数组中读出byte型数据
			try {
				//
				PdfWriter.getInstance(document, ba); // 指定Document对象的类型,生成PDF文档格式
				document.open();
				//添加二维码
				String msg = takeTicket.getScannerNo() + "," + takeTicket.getId();
				Image image = MatrixToImageWriter.getQRcodeImage(msg,80,80,"jpg");
				com.lowagie.text.Image jpg = com.lowagie.text.Image.getInstance(image, new Color(BufferedImage.TYPE_INT_BGR));
				//设置图片的对齐方式
				jpg.setAlignment(com.lowagie.text.Image.ALIGN_LEFT);
				document.add(jpg);
				
				Paragraph p = new Paragraph(); // Paragraph创建段落
				Chunk chunk = new Chunk("提货清单", new Font(bfChinese, 20));
				p.add(chunk);
				p.setAlignment(Element.ALIGN_CENTER);
				p.setSpacingAfter(20f);
				document.add(p); // 容器添加段落
				
				float[] widths = { 20f, 20f, 20f };
				PdfPTable t = new PdfPTable(widths); // 创建只有列的PDF表格
				t.setWidthPercentage(100f);
				PdfPCell t1 = new PdfPCell(new Paragraph("发货日期："
						+ Dateutil.getCurrentDateString(), fontChinese));
				int totalBoxQuantity=0;
				Double totalScatterQuantity = 0d;
				for (TakeTicketDetailBatchBit takeTicketDetailBatchBit : batchBits) {
					totalBoxQuantity+=takeTicketDetailBatchBit.getBoxnum();
					totalScatterQuantity = ArithDouble.add(totalScatterQuantity, takeTicketDetailBatchBit.getScatternum());
				}

				String remark = takeTicket.getRemark();
				String iccard = "";
				String carNum = "";
				String ERPNO = "";
				
				String Nccode =takeTicket.getNccode();
				if(Nccode==null){
					Nccode="";
				}
				String Nccode2 =takeTicket.getNccode2();
				if(Nccode2==null){
					Nccode2="";
				}
				
				try {
					iccard = remark.split("\r\n")[0];
					carNum = remark.split("\r\n")[1];
					ERPNO = remark.split("\r\n")[2];
				} catch (Exception e) {
					//System.err.println("明细信息解析异常!卡号车号IC卡号格式不对");
				}
//				PdfPCell t2 = new PdfPCell(new Paragraph("应发总数："
//						+ String.valueOf(totalBoxQuantity)+"箱"+String.valueOf(totalScatterQuantity.intValue())+"散", fontChinese));
				PdfPCell t2 = new PdfPCell(new Paragraph("应发总数："
						+String.valueOf(totalScatterQuantity.intValue()), fontChinese));
				PdfPCell t3 = new PdfPCell(new Paragraph("IC卡号：" + iccard,
						fontChinese));
				t.addCell(t1);
				t.addCell(t2);
				t.addCell(t3);
				document.add(t); // 容器添加表格

				float[] widths1 = { 100f };
				PdfPTable pdfp = new PdfPTable(widths1);
				pdfp.setWidthPercentage(100f);
				PdfPCell pdfp1 = new PdfPCell(new Paragraph("客户名称："
						+ takeTicket.getOname(), fontChinese));
				pdfp.addCell(pdfp1);
				document.add(pdfp);

				float[] widths2 = { 33.3f, 66.7f };
				PdfPTable pdfpta = new PdfPTable(widths2);
				pdfpta.setWidthPercentage(100f);
				PdfPCell pdfpta1 = new PdfPCell(new Paragraph("车号：" + carNum,
						fontChinese));
				PdfPCell pdfpta2 = new PdfPCell(new Paragraph(
						"电脑出库单号：" + ERPNO, fontChinese));
				pdfpta.addCell(pdfpta1);
				pdfpta.addCell(pdfpta2);
				document.add(pdfpta);

				float[] widths3 = { 33.3f, 66.7f };
				PdfPTable pdfptable = new PdfPTable(widths3);
				pdfptable.setWidthPercentage(100f);
				PdfPCell pdfptable1 = new PdfPCell(new Paragraph("IS系统检货小票："
						+ takeTicket.getId(), fontChinese));
				PdfPCell pdfptable2 = new PdfPCell(new Paragraph("IS系统订购单号："
						+ takeTicket.getBillno(), fontChinese));
				pdfptable.addCell(pdfptable1);
				pdfptable.addCell(pdfptable2);
				document.add(pdfptable);
				
				float[] widths34 = { 33.3f, 66.7f };
				PdfPTable pdfpta34 = new PdfPTable(widths34);
				pdfpta34.setWidthPercentage(100f);
				PdfPCell pdfpta341 = new PdfPCell(new Paragraph("外部单号：" + Nccode,
						fontChinese));
				PdfPCell pdfpta342 = new PdfPCell(new Paragraph(
						"配送单号：" + Nccode2, fontChinese));
				pdfpta34.addCell(pdfpta341);
				pdfpta34.addCell(pdfpta342);
				document.add(pdfpta34);
				

				int height = 30;
				float[] widths4 = { 33.3f, 33.3f, 33.3f, 33.3f, 35.3f, 31.3f };
				PdfPTable pdfname = new PdfPTable(widths4);
				pdfname.setWidthPercentage(100f);
				PdfPCell pdfname1 = new PdfPCell(new Paragraph("叉车司机签字：",
						fontChinese));
				PdfPCell pdfname2 = new PdfPCell();
				PdfPCell pdfname3 = new PdfPCell(new Paragraph("调度员签字：",
						fontChinese));
				PdfPCell pdfname4 = new PdfPCell();
				PdfPCell pdfname5 = new PdfPCell(new Paragraph("流动理货员签字：",
						fontChinese));
				PdfPCell pdfname6 = new PdfPCell();
				pdfname1.setFixedHeight(height);
				pdfname2.setFixedHeight(height);
				pdfname3.setFixedHeight(height);
				pdfname4.setFixedHeight(height);
				pdfname5.setFixedHeight(height);
				pdfname6.setFixedHeight(height);
				pdfname.addCell(pdfname1);
				pdfname.addCell(pdfname2);
				pdfname.addCell(pdfname3);
				pdfname.addCell(pdfname4);
				pdfname.addCell(pdfname5);
				pdfname.addCell(pdfname6);
				document.add(pdfname);

				Paragraph p2 = new Paragraph();
				Chunk chunk1 = new Chunk("提货明细", new Font(bfSun, 12, Font.BOLD));
				p2.add(chunk1);
				p2.setAlignment(Element.ALIGN_LEFT);
				p2.setSpacingAfter(10f);
				document.add(p2);

				float[] tabl = { 36f, 10f,10f, 12f, 12f, 10f, 10f, 10f };
				PdfPTable table = new PdfPTable(tabl);
				table.setWidthPercentage(100f);
				PdfPCell h1 = new PdfPCell(new Paragraph("产品名称", fontChinese));
				PdfPCell h11 = new PdfPCell(new Paragraph("内部编号", fontChinese));
				PdfPCell h2 = new PdfPCell(new Paragraph("产品批次", fontChinese));
				PdfPCell h3 = new PdfPCell(new Paragraph("产品仓位", fontChinese));
				PdfPCell h4 = new PdfPCell(new Paragraph("当前库存数", fontChinese));
				PdfPCell h5 = new PdfPCell(new Paragraph("出库数量", fontChinese));
				PdfPCell h6 = new PdfPCell(new Paragraph("剩余数量", fontChinese));
				PdfPCell h7 = new PdfPCell(new Paragraph("备注", fontChinese));
				table.addCell(h1);
				table.addCell(h11);
				table.addCell(h2);
				table.addCell(h3);
				table.addCell(h4);
				table.addCell(h5);
				table.addCell(h6);
				table.addCell(h7);
				PdfPCell cell;
				int currRow = 9;
				int currRow1 = 9;
				int currRow2 = 9;
				int currProductRow = 10;
				int currBitRow = 10;
				String producti = null;
				AppProduct ap = new AppProduct();
				AppProductStockpile appProductStockpile = new AppProductStockpile();
				for (TakeTicketDetailBatchBit batchBit : batchBits) {
					currRow++;
					producti = batchBit.getProductid();
					currProductRow = currRow;
					// 每for循环一次currRow自加1
					// 产品名称
					cell = new PdfPCell(new Paragraph(
							batchBit.getProductname(), fontChinese));
					table.addCell(cell);
					
					//内部编号
					cell = new PdfPCell(new Paragraph(ap.getProductByID(producti).getNccode(), fontChinese));
					table.addCell(cell);

					// 产品批次
					cell = new PdfPCell(new Paragraph(batchBit.getBatch(),
							fontChinese));
					table.addCell(cell);
					// 产品仓位
					cell = new PdfPCell(new Paragraph(batchBit
							.getWarehouseBit(), fontChinese));
					table.addCell(cell);
					ProductStockpile productStockpile = appProductStockpile
							.getProductStockpileByPidWid(batchBit
									.getProductid(), takeTicket
									.getWarehouseid(), batchBit
									.getWarehouseBit(), batchBit.getBatch());

					// 库存数量
					/*cell = new PdfPCell(new Paragraph(String
							.valueOf(productStockpile.getStockpile()
									+ batchBit.getQuantity()), fontChinese));
					table.addCell(cell);*/
					//richieYu 取消当前库存显示 20110519
					cell = new PdfPCell(new Paragraph("", fontChinese));
					table.addCell(cell);
					// 出库数量
//					cell = new PdfPCell(new Paragraph(String.valueOf(batchBit
//							.getBoxnum())+"箱"+String.valueOf(batchBit
//									.getScatternum().intValue())+"散", fontChinese));
					cell = new PdfPCell(new Paragraph(String.valueOf(batchBit
									.getScatternum().intValue()), fontChinese));
					table.addCell(cell);

					cell = new PdfPCell(new Paragraph("", fontChinese));
					table.addCell(cell);

					cell = new PdfPCell(new Paragraph("", fontChinese));
					cell.setFixedHeight(height);
					table.addCell(cell);
				}
				document.add(table);

			} catch (DocumentException de) {
				System.err.println(de.getMessage());
			}
			
//			Image jpg = Image.getInstance("E:" + File.separator + "new.png");
//			document.add(jpg);
			
			document.close(); // 关闭容器
			response.setContentLength(ba.size());
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			out.flush();
			out.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到库存量的方法，由execute方法中的参数调用，返回ProductStockpile类型的List
	 * 
	 * @version 0.1
	 * @author wei.li
	 * @since 2010/09/18
	 */
	/*
	public List<ProductStockpile> freightList(HttpServletRequest request,
			String pid, String date) {
		List<ProductStockpile> pls = new ArrayList<ProductStockpile>();
		super.initdata(request);
		try {
			AppProductStockpile aps = new AppProductStockpile();
			pls = aps.getStockStatPile(pid, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pls;
	}
	*/

	/**
	 * 得到仓位的方法，由execute方法中的参数调用，返回WarehouseBit类型的List
	 * 
	 * @version 0.1
	 * @author wei.li
	 * @since 2010/09/18
	 */
	/*
	public List getstockstatWrea(HttpServletRequest request, String wid,
			String wbid) {
		List<WarehouseBit> wrea = new ArrayList();
		super.initdata(request);
		try {
			AppProductStockpile aps = new AppProductStockpile();
			wrea = aps.getStockStatWrea(wid, wbid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wrea;
	}
*/
	/**
	 * 判断批次和产品是否相等的方法
	 * 
	 * @version 0.1
	 * @author yu
	 * @since 2010/09/25
	 */
	private TakeTicketDetail isExistInList(List<TakeTicketDetail> tempList,
			TakeTicketDetail takeTicketDetail) {// 接收两个参数
		for (TakeTicketDetail tempDetail : tempList) { // 将传入进来的临时list进行遍历循环
			if (tempDetail.getBatch().equals(takeTicketDetail.getBatch())
					&& tempDetail.getProductid().equals(
							takeTicketDetail.getProductid())) {
				return tempDetail; // 如果临时list中的批次、产品跟TakeTicketDetail对象中的批次、产品的值一致，返回遍历临时list的对象
			} else {
				// 暂无逻辑
			}
		}
		return null;
	}

	private boolean whetherPicked(String ttid) throws Exception {
		AppTakeTicketDetail appTakeTicketDetail = new AppTakeTicketDetail();
		List<TakeTicketDetail> takeTicketDetails = appTakeTicketDetail
				.getTakeTicketDetailByTtid(ttid);
		for (TakeTicketDetail takeTicketDetail : takeTicketDetails) {
			if (takeTicketDetail.getIsPicked() == null
					|| takeTicketDetail.getIsPicked() == 0) {
				return false;
			}
		}
		return true;
	}
}
