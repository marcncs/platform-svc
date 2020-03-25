import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.owasp.esapi.ESAPI;

import com.winsafe.hbm.util.StringUtil;

public class Test2 {
	public static void main(String[] args) {
//		System.out.println(ESAPI.encoder().canonicalize("世马(金装)"));
		String startTime = "";
		String endTime = "";
		if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO transaction_temp_dup_delivery(IDCODE, ORGANTYPE) ");
		sql.append("select tti.IDCODE,o.ORGANTYPE from ( ");
		sql.append("select TTi.idcode from TAKE_TICKET tt ");
		sql.append("join organ o on TT.OID=o.ID and TT.BSORT=1 and (o.ORGANTYPE = 1 or (o.ORGANTYPE = 2 and o.ORGANMODEL=1)) and TT.ISAUDIT=1 ");
		sql.append("and TT.AUDITDATE > ").append(startTime).append(" and TT.AUDITDATE <= ").append(endTime).append("  ");
		sql.append("join TAKE_TICKET_IDCODE tti on tt.id=tti.ttid ");
		sql.append("GROUP BY TTi.idcode ");
		sql.append("UNION ");
		sql.append("select TTi.idcode from TAKE_TICKET_IDCODE tti ");
		sql.append("join TAKE_TICKET tt on tt.id=tti.ttid ");
		sql.append("join organ o on TT.OID=o.ID and TT.BSORT=1 and (o.ORGANTYPE = 1 or (o.ORGANTYPE = 2 and o.ORGANMODEL=1)) and TT.ISAUDIT=1 ");
		sql.append("and tti.makedate > ").append(startTime).append(" and tti.makedate < ").append(endTime).append("  ");
		sql.append("and tti.makedate > TT.AUDITDATE ");
		sql.append("GROUP BY TTi.idcode ");
		sql.append(") temp  ");
		sql.append("join TAKE_TICKET_IDCODE tti on tti.idcode=temp.idcode ");
		sql.append("join TAKE_TICKET tt on tt.id=tti.ttid ");
		sql.append("join organ o on TT.OID=o.ID and TT.BSORT=1 and (o.ORGANTYPE = 1 or (o.ORGANTYPE = 2 and o.ORGANMODEL=1)) and TT.ISAUDIT=1 ");
		sql.append("GROUP BY o.ORGANTYPE, tti.IDCODE ");
		sql.append("having count(tti.IDCODE) > 1 ");
	}
}
