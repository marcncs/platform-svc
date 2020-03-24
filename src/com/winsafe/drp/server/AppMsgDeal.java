package com.winsafe.drp.server;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.winsafe.drp.dao.AppCIntegral;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppMsg;
import com.winsafe.drp.dao.AppMsgTemplate;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Msg;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderDetail;
import com.winsafe.drp.util.FileConstant;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.MakeCode;

public class AppMsgDeal {
	private Logger logger=Logger.getLogger(AppMsgDeal.class);
	private final Timer timer = new Timer();
	private AppMsgTemplate appmtl = new AppMsgTemplate();

	private final int minutes;
	
	private AppMsg am = new AppMsg();

	public AppMsgDeal(int minutes) {
		this.minutes = minutes;
	}

	public void start() {
		timer.schedule(new TimerTask() {
			public void run() {
//				playSound();
//				//接收短信
//				recevieMsg();
//				//发送短信
//				sendMsg();
			}
			//发送短信
			private void sendMsg(){
				try{
					
					List<Msg> mls = am.getSendMsgNoDeal();
					for ( Msg msg : mls ){
						//System.out.println("==============old="+msg.getMobileno());
						//System.out.println("==============new="+am.filternums(msg.getMobileno()));
						String qurl = "http url" 
							   + FileConstant.msguserid + "&password=" + java.net.URLEncoder.encode(FileConstant.msgpassword,"UTF-8")
							   + "&destnumbers=" + am.filternums(msg.getMobileno())
							   + "&msg=" + java.net.URLEncoder.encode(msg.getMsgcontent(), "UTF-8")
							   + "&sendtime=" + ""; 
						org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
					    org.dom4j.Document doc = reader.read(new java.net.URL(qurl));
					    //String xml=  new String(doc.asXML().getBytes(),"UTF-8");
						//System.out.println(xml);
					    if ("0".endsWith(doc.valueOf("/root/@return"))) {
					        // System.out.println("=====发送短信成功======="+msg.getId());
					    	// 节点"/root/@return" 的值为 0 时调用成功
					    	HibernateUtil.currentSession(true);					    	
					         am.updMsgDeal(msg.getId());
					        HibernateUtil.commitTransaction();
					        //logger.info(msg.getMobileno()+"发送短信成功");
					    }else{
					         // 节点"/root/@return" 的值不为 0 时调用失败
					    	//logger.info(msg.getMobileno()+"发送短信失败");
					    	//System.out.println("=====发送短信失败=======");
					    }
					}
				}catch ( Exception e ){
					logger.error("处理发送短信错误:"+e.getMessage());
				}
			}
			
			//接收短信处理
			private void recevieMsg(){
				try{
					String qurl ="http url" 
							   + FileConstant.msguserid + "&password=" + java.net.URLEncoder.encode(FileConstant.msgpassword,"UTF-8");
					
					org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
				    org.dom4j.Document doc = reader.read(new java.net.URL(qurl));	
				    if ("0".endsWith(doc.valueOf("/root/@return"))) {
				         //logger.info("=====接收短信成功=======");
				         Element root = doc.getRootElement();      
				         Iterator it = root.elementIterator("row");
				         while ( it.hasNext() ){				        	 			        	 
				        	 Msg msg = new Msg();
				        	 msg.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("msg", 0, "")));
			        		 msg.setMsgsort(0);
			        		 msg.setMakeorganid("");
			        		 msg.setMakedeptid(0);
			        		 msg.setMakeid(0);
			        		 msg.setIsdeal(0);
			     			 msg.setIsaudit(0);
			     			 msg.setAuditid(0);
			     			 
			     			 Element e = (Element)it.next();
				        	 Iterator cit = e.elementIterator();	
				        	 while ( cit.hasNext() ){
				        		 Element ce = (Element)cit.next();
				        		 if ( ce == null ){
				        			 continue;
				        		 }				        		 
				        		 if (ce.getName().equalsIgnoreCase("sender") ){
				        			 //System.out.println("sender="+ce.getText()); 
				        			 msg.setMobileno(ce.getText());
				        		 }
				        		 if (ce.getName().equalsIgnoreCase("uptime") ){
				        			 //System.out.println("name="+ce.getText()); 
				        			 msg.setMakedate(DateUtil.StringToDatetime(ce.getText()));
				        		 }
				        		 if (ce.getName().equalsIgnoreCase("msg") ){
				        			 //System.out.println("name="+ce.getText()); 
				        			 msg.setMsgcontent(ce.getText());
				        		 }					        		 
				        	 }
				        	 HibernateUtil.currentSession(true);		
				        	 am.addMsg(msg);
				        	 HibernateUtil.commitTransaction();
				         }					    	
				    }else{					        
				    	//logger.info("=====接收短信失败=======");
				    }
				}catch ( Exception e ){
					logger.error("处理接收短信错误:"+e.getMessage());
				}
				
			}

			private void playSound() {

				try {
					List mls = am.getReceiveMsgNoDeal();
					//System.out.println("--=====执行msg");
					if (mls.size() > 0) {
						AppCustomer ac = new AppCustomer();
						Integer id = 0;
						String cid = "";
						String cname = "";
						String msgContent = "";
						String mobileNo = "";
						for (int i = 0; i < mls.size(); i++) {
							Msg m = (Msg) mls.get(i);
							id = m.getId();
							msgContent = m.getMsgcontent().trim();
							mobileNo = m.getMobileno().trim();
							Customer c = ac.getCustomerByMobile(mobileNo);
							if ( c == null ){
								continue;
							}
							cid= c.getCid();
							cname =c.getCname();
							if (msgContent.toLowerCase().indexOf("jf") == 0) {//查积分
								AppCIntegral aci = new AppCIntegral();
								double cintegral = aci.getCIntegralByCID(cid);
								HibernateUtil.currentSession(true);
								Msg ms = new Msg();
								ms.setId(Integer
										.valueOf(MakeCode
												.getExcIDByRandomTableName(
														"msg", 0, "")));
								ms.setMsgsort(1);
								ms.setMobileno(mobileNo);
								ms.setMsgcontent(appmtl.getIntegralMsg(cname, cintegral));								
								ms.setMakeorganid("1");
				        		ms.setMakedeptid(19);
				        		ms.setMakeid(1);
				        		ms.setIsdeal(0);
				     			ms.setIsaudit(1);
				     			ms.setAuditid(1);
				     			ms.setAuditdate(DateUtil.getCurrentDate());
								ms.setMakedate(DateUtil
										.StringToDatetime(DateUtil
												.getCurrentDateTime()));
								am.addMsg(ms);

								am.updMsgDeal(id);
								HibernateUtil.commitTransaction();
							}
							if ( msgContent.toLowerCase().indexOf("dd")==0 ) {//订单
								//String msgso = msgContent.substring(2,msgContent.length());
								//System.out.println("----------A--"+msgso);
								AppSaleOrder aso = new AppSaleOrder();
								SaleOrder so = aso.getLastSaleOrderByMobile(mobileNo);
								if ( so == null ){
									continue;
								}
								AppSaleOrderDetail asod = new AppSaleOrderDetail();
								List sodls = asod.getSaleOrderDetailBySoid(so.getId());
								String sendcontent = "";
								for(int o =0;o<sodls.size();o++){
									SaleOrderDetail ob=(SaleOrderDetail)sodls.get(o);
									sendcontent += ob.getProductname()+":"+ob.getQuantity()+Internation.getStringByKeyPositionDB(
											"CountUnit", Integer.parseInt(ob.getUnitid().toString()))+",";
								}
								
								HibernateUtil.currentSession(true);
								Msg ms = new Msg();
								ms.setId(Integer.valueOf(MakeCode
												.getExcIDByRandomTableName(
														"msg", 0, "")));
								ms.setMsgsort(1);
								ms.setMobileno(mobileNo);
								ms.setMsgcontent(appmtl.searchSaleOrderMsg(cname, so.getId(), sendcontent, so.getTotalsum()));	
								ms.setMakeorganid("");
				        		ms.setMakedeptid(0);
				        		ms.setMakeid(0);
				        		ms.setIsdeal(0);
				        		ms.setIsaudit(1);
				     			ms.setAuditid(1);
				     			ms.setAuditdate(DateUtil.getCurrentDate());
								ms.setMakedate(DateUtil
										.StringToDatetime(DateUtil
												.getCurrentDateTime()));
								am.addMsg(ms);
								am.updMsgDeal(id);
								HibernateUtil.commitTransaction();
								
							}
							if ( msgContent.toLowerCase().indexOf("dj")==0 ) {//等级
								Integer rate = ac.getCustomer(cid).getRate();
								String ratename=Internation.getStringByKeyPositionDB("PricePolicy",
										rate);
								HibernateUtil.currentSession(true);
								Msg ms = new Msg();
								ms.setId(Integer.valueOf(MakeCode
												.getExcIDByRandomTableName(
														"msg", 0, "")));
								ms.setMsgsort(1);
								ms.setMobileno(mobileNo);
								ms.setMsgcontent(appmtl.getRateMsg(cname, ratename));
								ms.setMakeorganid("1");
				        		ms.setMakedeptid(0);
				        		ms.setMakeid(0);
				        		ms.setIsdeal(0);
				        		ms.setIsaudit(1);
				     			ms.setAuditid(1);
				     			ms.setAuditdate(DateUtil.getCurrentDate());
								ms.setMakedate(DateUtil
										.StringToDatetime(DateUtil
												.getCurrentDateTime()));
								am.addMsg(ms);

								am.updMsgDeal(id);
								HibernateUtil.commitTransaction();
							}
							if ( msgContent.toLowerCase().indexOf("xf") == 0) {//消费金额
								AppSaleOrder aso = new AppSaleOrder();
								String msgxf = msgContent.substring(2,msgContent.length()).trim();
								//System.out.println("------------------->"+msgxf);
								String xfWhere = " where CONVERT(varchar(100), so.makedate, 23) like '"+msgxf+"%' and so.isblankout=0";
								double xfd= aso.getSaleOrderTotal(xfWhere);
								HibernateUtil.currentSession(true);
								Msg ms = new Msg();
								ms.setId(Integer.valueOf(MakeCode
												.getExcIDByRandomTableName(
														"msg",0, "")));
								ms.setMsgsort(1);
								ms.setMobileno(mobileNo);
								ms.setMsgcontent(appmtl.getMoneyMsg(cname, xfd));
								ms.setMakeorganid("");
				        		ms.setMakedeptid(0);
				        		ms.setMakeid(0);
				        		ms.setIsdeal(0);
				        		ms.setIsaudit(1);
				     			ms.setAuditid(1);
				     			ms.setAuditdate(DateUtil.getCurrentDate());
								ms.setMakedate(DateUtil
										.StringToDatetime(DateUtil
												.getCurrentDateTime()));
								am.addMsg(ms);

								am.updMsgDeal(id);
								HibernateUtil.commitTransaction();
							}

						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("处理短信业务出错："+e.getMessage());
				}

			}
		}, 18000, 18000); //30分钟

	}

	public static void main(String[] args) {
		AppMsgDeal eggTimer = new AppMsgDeal(2);
		eggTimer.start();
	}

}
