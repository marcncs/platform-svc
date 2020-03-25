package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPaymentApply {

	public List<PaymentApply> getPaymentApply(HttpServletRequest request,
			int pageSize, String pWhereClause) throws Exception {

		String hql = " from PaymentApply as pa " + pWhereClause
				+ " order by pa.id desc";

		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public void addPaymentApply(PaymentApply r) throws Exception {

		EntityManager.save(r);

	}

	public void delPaymentApply(Long id) throws Exception {

		String sql = " delete from Payment_Apply where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	public PaymentApply getPaymentApplyByID(Integer id) throws Exception {
		PaymentApply r = new PaymentApply();
		String sql = " from PaymentApply as r where id=" + id;
		r = (PaymentApply) EntityManager.find(sql);
		return r;
	}

	public void updPaymentApply(PaymentApply pa) throws Exception {

		// String sql =
		// "update paymentapply set RegieName='"+r.getRegiename()+"',RegieType="+r.getRegietype()+",Acreage="+r.getAcreage()+",Actuality="+r.getActuality()+",RegieAddr='"+r.getRegieaddr()+"',RegieNob='"+r.getRegienob()+"',Province="+r.getProvince()+",City="+r.getCity()+",ShopDate='"+shopdate+"',UserID="+r.getUserid()+",IsUse="+r.getIsuse()+",Remark='"+r.getRemark()+"' where ID="+r.getId();
		EntityManager.update(pa);

	}
}
