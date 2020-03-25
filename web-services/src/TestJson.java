import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.util.BaseResult;

import net.sf.json.JSONObject;

public class TestJson {
	public static void main(String[] args) {
		TraceJson tj = new TraceJson();
		tj.setUrl("https://rtci.bayer.cn/RTCI/qr");
		tj.setPrimaryCode("11216643710210416793567999930455");
		tj.setCertHolder("拜耳股份公司");
		tj.setStandardName("氟吡菌酰胺41.7%悬浮剂");
		tj.setProductName("路富达");
		tj.setProductionDate("2018-10-29");
		tj.setProducer("拜耳股份公司");
		tj.setBatchNumber("PQ18100003");
		tj.setIsQualified("合格");
		tj.setCartonCode("00040522960104258772");
		tj.setCovertCode("180119244693");
		tj.setOutPincode("010000008174768710PQ18100003112018102900040522960104258772");
		tj.setMaterialCode("81747687");
		tj.setPlantName("拜耳作物科学杭州工厂");
		tj.setSpec("100毫升");
		tj.setExpiryDate("2020-09-30");
		List<TraceJsonDetail> flows = new ArrayList<>();
		tj.setFlows(flows);
		TraceJsonDetail tjd = new TraceJsonDetail();
		tjd.setBillNo("OM201908280008");
		tjd.setBillType("发货单");
		tjd.setDeliveryDate("2019-08-28 09:07:55");
		tjd.setFromOrg("贵州格润惠通农业有限公司");
		tjd.setFromW("贵州格润惠通农业有限公司贵阳仓库");
		tjd.setReceiveDate("2019-08-28 09:07:54");
		tjd.setSapNo("");
		tjd.setToOrg("贵州省遵义市王勇仓库");
		tjd.setToW("贵州省遵义市王勇仓库默认仓库");
		flows.add(tjd);
		tjd = new TraceJsonDetail();
		tjd.setBillNo("OM201904260369");
		tjd.setBillType("发货单");
		tjd.setDeliveryDate("2019-04-26 15:57:41");
		tjd.setFromOrg("上海地库（上海）");
		tjd.setFromW("上海地库（上海）默认仓库");
		tjd.setReceiveDate("2019-05-04 15:14:01");
		tjd.setSapNo("0805385651");
		tjd.setToOrg("贵州格润惠通农业有限公司");
		tjd.setToW("贵州格润惠通农业有限公司贵阳仓库");
		flows.add(tjd);
		JSONObject jsonObject = JSONObject.fromObject(tj);
		System.out.println(jsonObject.toString());
	}
}
