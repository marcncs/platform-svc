import java.util.List;

public class TraceJson {
	private String url;
	private String primaryCode;
	private String certHolder ;
	private String productName;
	private String standardName;
	private String productionDate;
	private String producer;
	private String batchNumber;
	private String isQualified;
	private String cartonCode;
	private String covertCode;
	private String outPincode;
	private String materialCode;
	private String spec;
	private String expiryDate;
	private String plantName;
	private List<TraceJsonDetail> flows;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPrimaryCode() {
		return primaryCode;
	}
	public void setPrimaryCode(String primaryCode) {
		this.primaryCode = primaryCode;
	}
	public String getCertHolder() {
		return certHolder;
	}
	public void setCertHolder(String certHolder) {
		this.certHolder = certHolder;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStandardName() {
		return standardName;
	}
	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getIsQualified() {
		return isQualified;
	}
	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}
	public String getCartonCode() {
		return cartonCode;
	}
	public void setCartonCode(String cartonCode) {
		this.cartonCode = cartonCode;
	}
	public String getCovertCode() {
		return covertCode;
	}
	public void setCovertCode(String covertCode) {
		this.covertCode = covertCode;
	}
	public String getOutPincode() {
		return outPincode;
	}
	public void setOutPincode(String outPincode) {
		this.outPincode = outPincode;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public List<TraceJsonDetail> getFlows() {
		return flows;
	}
	public void setFlows(List<TraceJsonDetail> flows) {
		this.flows = flows;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	
}
