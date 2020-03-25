package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppCashBankAdjust {

	public List getCashBankAdjust(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = " from CashBankAdjust as cba " + pWhereClause
				+ " order by cba.id desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public void addCashBankAdjust(CashBankAdjust bank) throws Exception {

		EntityManager.save(bank);

	}

	public void updCashBankAdjust(CashBankAdjust bank) throws Exception {

		EntityManager.update(bank);

	}

	public void delCashBankAdjust(String id) throws Exception {

		String sql = "delete from cash_bank_adjust where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public CashBankAdjust getCashBankAdjustById(String id) throws Exception {
		CashBankAdjust bank = null;
		String sql = "from CashBankAdjust where id='" + id + "'";
		bank = (CashBankAdjust) EntityManager.find(sql);
		return bank;
	}

	public List getAllCashBankAdjust() throws Exception {
		String sql = "from CashBankAdjust order by id";
		return EntityManager.getAllByHql(sql);
	}

	public void updIsAudit(String id, Integer userid, Integer audit)
			throws Exception {

		String sql = "update Cash_Bank_Adjust set isaudit=" + audit
				+ ",auditid=" + userid + ",auditdate='"
				+ DateUtil.getCurrentDateString() + "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

}
