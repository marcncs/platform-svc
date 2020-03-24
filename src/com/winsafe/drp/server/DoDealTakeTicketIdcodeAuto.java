package com.winsafe.drp.server;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.IdcodeUpload;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class DoDealTakeTicketIdcodeAuto{

	protected AppIdcodeUpload appiu = new AppIdcodeUpload();
	protected AppProduct ap = new AppProduct();
	protected AppIdcode ai = new AppIdcode();
	protected AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
	protected AppTakeTicket att = new AppTakeTicket();
	protected AppTakeTicketDetail attd = new AppTakeTicketDetail();
	protected AppTakeBill atb = new AppTakeBill();
	protected AppStockMove asam = new AppStockMove();
	protected AppStockMoveDetail asamd = new AppStockMoveDetail();
	protected AppOrgan ao = new AppOrgan();
	protected AppWarehouse aw = new AppWarehouse();
	protected AppOlinkMan aom = new AppOlinkMan();
	protected AppFUnit af =new AppFUnit();
	AppIdcodeUpload app = new AppIdcodeUpload();
	private String filepath;
	private String failAddress;
	private String fileaddress;
	private Map<String, List<TakeTicketIdcode>> ttis;
	private String iuid;
	private IdcodeUpload iu;
	protected int valinum;
	protected int failnum;
	private TakeTicketIdcode existtti;

	public DoDealTakeTicketIdcodeAuto(String filepath, String fileaddress,String failAddress,Map<String, List<TakeTicketIdcode>> ttis, String iuid, int failnum,
			int valinum) {
		this.filepath = filepath;
		this.fileaddress = fileaddress;
		this.failAddress = failAddress;
		this.ttis = ttis;
		this.iuid = iuid;
		this.valinum = valinum;
		this.failnum = failnum;
	}

	public String run() {
		try {
			iu=app.getIdcodeUploadByID(Integer.parseInt(this.iuid));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		for (Map.Entry<String, List<TakeTicketIdcode>> entry : this.ttis.entrySet()) {
			try {
				AppIdcode ai = new AppIdcode();
				HibernateUtil.currentSession();
				List<TakeTicketIdcode> pttis = entry.getValue();
				String ttid = createBill(pttis.get(0));
				String idcodeid="";
				for (TakeTicketIdcode tti : pttis) {
					boolean flag = false;
					//依据ttid和Idcode查询
					existtti = atti.getTakeTicketIdcodeByttidAndIdcode(ttid,tti.getIdcode());
					if(existtti!=null){
						writeTxt(tti.getIdcode() + "[该条码已在当前列表中]");
						continue;
					}
					if(tti.getUnitid()==17){//托条码 验证托中的每箱条码是否在当前列表
						//托中最后一条条码的后几位顺序码
						int lastidocde = Integer.parseInt(tti.getEndno().substring(4,13));
						//托中第一条条码的后几位顺序码
						int firstidocde = Integer.parseInt(tti.getStartno().substring(4,13));
						//条码前四位
						String idcodeprefix = tti.getEndno().substring(0,4);
						//托条码中的箱数量
						int n =lastidocde-firstidocde+1;
						for(int k =n; k>0;k--){
							idcodeid =idcodeprefix +String.format("%09d", lastidocde) ;
							lastidocde--;
							
							existtti = atti.getTakeTicketIdcodeByttidAndIdcode(ttid,idcodeid);
							if(existtti!=null){
								writeTxt(idcodeid + "[该条码已在当前列表中]");
								flag = true;
								break;
							}
						}
					}
					if(!flag){
						tti.setTtid(ttid);
						//将TakeTicketIdcode存入数据库
//						tti.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_idcode", 0, "")));
						atti.addTakeTicketIdcode(tti);
					}
					
				}
				updateBill(pttis, pttis.get(0));
				
				for (TakeTicketIdcode tti : pttis) {
					
					//将托码拆分成箱码
//					if(tti.getUnitid()==17){
//						//托中最后一条条码的后几位顺序码
//						int lastidocde = Integer.parseInt(tti.getEndno().substring(4,13));
//						//托中第一条条码的后几位顺序码
//						int firstidocde = Integer.parseInt(tti.getStartno().substring(4,13));
//						//条码前四位
//						String idcodeprefix = tti.getEndno().substring(0,4);
//						
//						//托条码中的箱数量
//						int n =lastidocde-firstidocde+1;
//						for(int k =n; k>0;k--){
//							idcodeid =idcodeprefix +String.format("%09d", lastidocde) ;
//							lastidocde--;
//							
//							Idcode idcode = ai.getIdcodeById(idcodeid);
//							idcode.setIsuse(0);
//							ai.updIdcode(idcode);
//						}
//					}else{
					idcodeid = tti.getIdcode();
					Idcode idcode = ai.getIdcodeById(idcodeid);
					if (null == idcode) {
						addIdcode(tti);
					} 
//					else {
//						idcode.setIsuse(0);
//						ai.updIdcode(idcode);
//					 }
//				  }
				}
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				writeTxt(entry.getValue().get(0).getIdcode() + "[处理异常]");
				HibernateUtil.rollbackTransaction();
			}
		}
		return updNum();
	}

	/**
	 * 通过tti修改单据
	 * 
	 * @param tti
	 * @throws Exception
	 */
	private void updateBill(List<TakeTicketIdcode> ttis, TakeTicketIdcode templasttti) throws Exception {
		// 将条码根据产品分类
		Map<String, List<TakeTicketIdcode>> productttis = new HashMap<String, List<TakeTicketIdcode>>();
		for (TakeTicketIdcode tti : ttis) {
			// MAP中是否含有当前产品的list
			List<TakeTicketIdcode> toadd = productttis.get(tti.getProductid());
			if (null == toadd || 0 == toadd.size()) {
				// 没有则新增list,然后加入到map中
				toadd = new ArrayList<TakeTicketIdcode>();
				toadd.add(tti);
				productttis.put(tti.getProductid(), toadd);
			} else {
				// 有则取出集合,然后更新集合重新放入map中
				toadd.add(tti);
				productttis.remove(tti.getProductid());
				productttis.put(tti.getProductid(), toadd);
			}
		}
		// 生成TakeTicketDetail和StockMoveDetail
		TakeTicket tt = att.getTakeTicketById(templasttti.getTtid());
		StockMove sam = asam.getStockMoveByID(tt.getBillno());
		Product p;
		for (Map.Entry<String, List<TakeTicketIdcode>> entry : productttis.entrySet()) {
			String productid = entry.getKey();
			List<TakeTicketIdcode> ts = entry.getValue();
			p = ap.getProductByID(productid);
			this.saveSMD(p, sam, ts);
			this.saveTTD(p, templasttti, ts);
		}
	}

	/**
	 * 保存生成的TT明细
	 * 
	 * @param p
	 * @param templasttti
	 * @param ts
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void saveTTD(Product p, TakeTicketIdcode templasttti, List<TakeTicketIdcode> ts) throws NumberFormatException,
			Exception {
		TakeTicketDetail ttd = new TakeTicketDetail();
		ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
		ttd.setBatch("");
		ttd.setCost(p.getCost());
		ttd.setIsread(0);
		ttd.setProductid(p.getId());
		ttd.setProductname(p.getProductname());		
//		ttd.setQuantity(Double.valueOf(String.valueOf(ts.size())));
		double quantity=0.0;
		for(TakeTicketIdcode tti: ts){
//			quantity+=tti.getPackquantity();
			quantity= ArithDouble.add(quantity, tti.getPackquantity());
		}

		//每箱kg数
		Double boxq = af.getXQuantity(p.getId(), 2);
		//每小包装kg数
		Double scaq = af.getXQuantity(p.getId(), p.getScatterunitid());
		//得到整箱数
		int q = ((Double)ArithDouble.div(quantity, boxq)).intValue();
		ttd.setBoxnum(q);
		//得到散数
		double tqu = ArithDouble.sub(quantity, ArithDouble.mul(boxq, (double)q));
		ttd.setScatternum(ArithDouble.div(tqu, scaq));
		
		//总的散数
		ttd.setQuantity(ArithDouble.add(ArithDouble.mul(q, p.getBoxquantity()), ttd.getScatternum()));
		ttd.setSpecmode(p.getSpecmode());
		ttd.setTtid(templasttti.getTtid());
//		ttd.setUnitid(2);
//		ttd.setUnitid(18);//kg
		//设置单位为小包装单位
		ttd.setUnitid(p.getScatterunitid());
		attd.addTakeTicketDetail(ttd);
	}

	/**
	 * 保存生成的转仓单明细
	 * @param p
	 * @param templasttti
	 * @param ts
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void saveSMD(Product p, StockMove s, List<TakeTicketIdcode> ts) throws NumberFormatException, Exception {
		StockMoveDetail smd = new StockMoveDetail();
		smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_move_detail", 0, "")));
		smd.setSmid(s.getId());
		smd.setProductid(p.getId());
		smd.setProductname(p.getProductname());
		smd.setSpecmode(p.getSpecmode());
//		smd.setUnitid(2);
//		smd.setUnitid(18);//kg
		//设置单位为小包装单位
		smd.setUnitid(p.getScatterunitid());
		
		smd.setBatch("");
//		smd.setQuantity(Double.valueOf(String.valueOf(ts.size())));
		double quantity=0.0;
		for(TakeTicketIdcode tti: ts){
//			quantity+=tti.getPackquantity();
			quantity= ArithDouble.add(quantity, tti.getPackquantity());
		}
		
		//每箱kg数
		Double boxq = af.getXQuantity(p.getId(), 2);
		//每小包装kg数
		Double scaq = af.getXQuantity(p.getId(), p.getScatterunitid());
		//得到整箱数
		int q = ((Double)ArithDouble.div(quantity, boxq)).intValue();
		smd.setBoxnum(q);
		//得到散数
		double tqu = ArithDouble.sub(quantity, ArithDouble.mul(boxq, (double)q));
		smd.setScatternum(ArithDouble.div(tqu, scaq));
		
		//总的散数
		smd.setQuantity(ArithDouble.add(ArithDouble.mul(q, p.getBoxquantity()), smd.getScatternum()));
		smd.setTakequantity(0d);
		asamd.addStockMoveDetail(smd);
	}

	/**
	 * 通过tti创建单据
	 * @param tti
	 */
	private String createBill(TakeTicketIdcode tti) {
		String result = "";
		try {
			result = MakeCode.getExcIDByRandomTableName("take_ticket", 2, "TT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Organ ogfrom = ao.getOrganByID(tti.getOrganfromid());
			Organ ogto = ao.getOrganByID(tti.getOrgantoid());
			Warehouse whfrom = aw.getWarehouseByID(tti.getWarehousefromid());
			Warehouse whto = aw.getWarehouseByID(tti.getWarehousetoid());
			Olinkman ol = null;
			Olinkman ol2= null;
			try {
				// 调出机构联系人
				ol = aom.getMainLinkmanByCid(ogfrom.getId());
				// 调入机构联系人
				ol2 = aom.getMainLinkmanByCid(ogto.getId());
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			
			//生成转仓单
			StockMove sm = new StockMove();
			String smid = MakeCode.getExcIDByRandomTableName("stock_move", 2, "SM");
			sm.setId(smid);
			sm.setMovedate(tti.getMakedate());
			sm.setOutwarehouseid(tti.getWarehousefromid());
			sm.setIsaudit(1);
			sm.setAuditdate(new Date());
			if (null != ol) {
				sm.setOlinkman(ol.getName());
				sm.setOtel(ol.getMobile());
			} else {
				sm.setOlinkman("");
				sm.setOtel("");
			}
			sm.setTransportmode(1);
			sm.setTransportaddr(whto.getWarehouseaddr());
			sm.setMakeid(iu.getMakeid());
			sm.setMakeorganid(tti.getOrganfromid());
			sm.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			sm.setInorganid(tti.getOrgantoid());
			sm.setInwarehouseid(tti.getWarehousetoid());
			sm.setMovecause("");
			sm.setRemark("");
			sm.setIsaudit(1);
			sm.setAuditid(iu.getMakeid());
			sm.setIsshipment(0);
			sm.setIsblankout(0);
			sm.setIscomplete(0);
			asam.addStockMove(sm);
			
			//生成TB单
			TakeBill tb = new TakeBill();
			tb.setBlankoutreason("");
			tb.setBsort(2);
			tb.setEquiporganid(ogfrom.getId());
			tb.setId(smid);
			tb.setInwarehouseid(whto.getId());
			tb.setIsaudit(0);
			tb.setIsblankout(0);
			tb.setIsread(0);
			tb.setMakeid(iu.getMakeid());
			tb.setMakedate(new Date());
			tb.setMakeorganid(ogfrom.getId());
			tb.setNccode("");
			tb.setOid(ogfrom.getId());
			tb.setOname(ogfrom.getOrganname());
			tb.setSenddate(new Date());
			if (null != ol2) {
				tb.setRlinkman(ol2.getName());
				tb.setTel(ol2.getMobile());
			} else {
				tb.setRlinkman("");
				tb.setTel("");
			}
			atb.addTakeBill(tb);
			
			//生成TT 单
			TakeTicket tt = new TakeTicket();
			tt.setId(result);
			tt.setBillno(smid);
			tt.setBlankoutreason("");
			tt.setBsort(2);
			tt.setEquiporganid(ogfrom.getId());
			tt.setInwarehouseid(whto.getId());
			tt.setIsaudit(0);
			tt.setIsblankout(0);
			tt.setIsread(0);
			if (null != ol2) {
				tt.setRlinkman(ol2.getName());
				tt.setTel(ol2.getMobile());
			} else {
				tt.setRlinkman("");
				tt.setTel("");
			}
			tt.setMakeid(iu.getMakeid());
			tt.setMakedate(new Date());
			tt.setWarehouseid(whfrom.getId());
			tt.setMakeorganid(ogfrom.getId());
			tt.setOid(ogto.getId());
			tt.setOname(ogto.getOrganname());
			tt.setPrinttimes(0);
			tt.setRemark("");
			att.addTakeTicket(tt);
			
		} catch (Exception e) {
			writeTxt(tti.getIdcode() + "[创建单据错误]");
		}
		return result;
	}

	protected void writeTxt(String str) {
		if(failAddress.equals("")){
			failAddress = fileaddress.substring(0, fileaddress.lastIndexOf(".")) + "_fail.txt";
		}		
		valinum--;
		failnum++;
		BufferedWriter out = null;
		String destFile = filepath +failAddress;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile, true)));
			out.write(str);
			out.write("\r\n");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 更新条码表为未使用及修改条码表部分信息
	 * 
	 * @param idlist
	 *            tt票的idcode集合
	 * @param tt
	 *            tt票
	 * @throws Exception
	 */
	/*public static void setIdcodeNoUse(TakeTicketIdcode tti)
			throws Exception {
		AppIdcode appidcode = new AppIdcode();
//		Idcode ic = appidcode.getIdcodeByWLMII(tti.getStartno(),	tti.getEndno());
//		ic.setPackquantity(ic.getPackquantity()-1.00);
//		Idcode ic = appidcode.getIdcodeById(tti.getIdcode());
//		ic.setIsuse(1);
//		appidcode.updIdcode(ic);
		addIdcode(tti);
	}*/
	
	/**
	 * 通过TT票
	 * 
	 * @param tti
	 *            tt票的idcode
	 * @param tt
	 *            tt票
	 * @throws Exception
	 */
	private static void addIdcode(TakeTicketIdcode tti)
			throws Exception {
		AppProduct ap = new AppProduct();
//		AppBaseResource abr = new AppBaseResource();
//		BaseResource bar = abr.getBaseResourceValue("UploadIdcodeFlag", 3);
		Idcode ic = new Idcode();
		ic.setIdcode(tti.getIdcode());
		ic.setProductid(tti.getProductid());
		ic.setProductname(ap.getProductNameByID(ic.getProductid()));
		ic.setBatch(tti.getBatch());
		ic.setProducedate(tti.getProducedate());
		ic.setVad(tti.getVad());
		ic.setLcode(tti.getLcode());
		ic.setStartno(tti.getStartno());
		ic.setEndno(tti.getEndno());
		ic.setUnitid(tti.getUnitid());
		ic.setQuantity(tti.getQuantity());
		ic.setFquantity(tti.getPackquantity());
		ic.setPackquantity(tti.getPackquantity());
		//是否改变条码状态
//		if(bar.getIsuse()==1){
			ic.setIsuse(0);
//		}else{
//			ic.setIsuse(1);
//		}
		ic.setIsout(0);
		ic.setBillid(tti.getTtid());
		ic.setIdbilltype(0);
		ic.setMakeorganid(tti.getOrganfromid());
		ic.setWarehouseid(tti.getWarehousefromid());
		ic.setWarehousebit(tti.getWarehousebit());
		ic.setProvideid("");
		ic.setProvidename("");
		ic.setMakedate(tti.getMakedate());
		AppIdcode appidcode = new AppIdcode();
		appidcode.addIdcode(ic);
	}
	private String updNum() {
		try {
			appiu.updNum(Integer.valueOf(iuid), 2, valinum, failnum, failAddress);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return "条码上传信息【成功：" + valinum + " 条；失败：" + failnum + " 条】，详细信息可查看条码上传日志";
	}
	
}
