package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppApproveFlowService {

	private AppApproveFlowDetail apdetial;
	private AppApproveFlowLog aplog;
	
	public AppApproveFlowService(){		
		aplog = new AppApproveFlowLog();
	}
	
	/**
	 * 获取审阅流详情
	 * @param afid 
	 * @return
	 * @throws Exception
	 */
	public List getFlowDetail(String afid) throws Exception{
		apdetial = new AppApproveFlowDetail();
		return apdetial.getApproveFlowDetailByAFID(afid);
	}
	
	/**
	 * 提交审批流
	 * @param detaillist 审阅流详情
	 * @param billno 单据编号
	 * @throws Exception
	 */
	public void referApproveFlow(List detaillist, String billno) throws Exception{
		//List detaillist = apdetial.getApproveFlowDetailByAFID(afid);		
		for (int i=0; i<detaillist.size(); i ++){
			ApproveFlowDetail afd = (ApproveFlowDetail)detaillist.get(i);
			ApproveFlowLog afl = new ApproveFlowLog();
			afl.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("approve_flow_log",0,"")));
			afl.setAfid(afd.getAfid());
			afl.setBillno(billno);
			afl.setApproveid(afd.getApproveid());
			afl.setApproveorder(i+1);
			afl.setOperate(i==0?1:0);
			afl.setActid(afd.getActid());
			afl.setApprovecontent("");
			afl.setApprove(0);
			aplog.addApproveFlowLog(afl);
		}	
	}
	
	public ApproveFlowLog getApproveFlowLog(Integer id) throws Exception{
		return aplog.getApproveFlowLogByid(id);
	}
	
	/**
	 * 获取当前用户待审批流程
	 * @param userid 用户编号
	 * @param biiino 单据编号
	 * @return
	 * @throws Exception
	 */
	public ApproveFlowLog getApproveFlowLogByUserid(Integer userid, String billno)throws Exception{
		return aplog.getApproveFlowLogByUserid(userid, billno);
	}
	
	/**
	 * 获取当前用户待审批流程
	 * @param afid 审阅流编号
	 * @param userid 用户编号
	 * @return
	 * @throws Exception
	 */
	public List waitApproveFlowLog(int pagesize, String pWhereClause, SimplePageInfo pPgInfo) throws Exception {
		return aplog.waitApproveFlowLog(pagesize, pWhereClause, pPgInfo);
	}
	
	/**
	 * 更新审批内容
	 * @return int 单据的审批状态
	 * @param afl ApproveFlowLog
	 * @throws Exception
	 */
	public int UpdateApproveFlowLog(ApproveFlowLog afl) throws Exception{
		afl.setOperate(0);
		
		aplog.updApproveFlowLog(afl);
		
		aplog.updOperateByNextOrder(afl.getBillno(), afl.getApproveorder()+1);
		
		return getSupperApprove(afl.getApprove(), afl.getBillno());
	}
	
	/**
	 * 获取单据的审批状态
	 * @param currentApprove 当前审批状态
	 * @param billno 单据编号
	 * @return int 单据的审批状态
	 * @throws Exception
	 */
	private int getSupperApprove(int currentApprove, String billno) throws Exception{
		
		if ( 0 == currentApprove ){
			return 1;
		}
		
		if ( 2 == currentApprove ){
			return 3;
		}
		
		if ( 1 == currentApprove ){
			if ( aplog.judgeApprove(billno) ){
				return 2;
			}
		}
		
		return 1;
	}
	
	public void delApproveFlowLogByBillno(String billno) throws Exception{
		 aplog.delApproveFlowLogByBillID(billno);
	}
}
