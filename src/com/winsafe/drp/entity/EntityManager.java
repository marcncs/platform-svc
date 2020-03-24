package com.winsafe.drp.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.jdbc.Work;

import cn.banny.util.io.JDiskSerial;

import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DbUtil;

@SuppressWarnings("unchecked")
public class EntityManager 
{
	private static Logger logger = Logger.getLogger(EntityManager.class);
	public static int RECORDS_PER_PAGE = 5;
	 
	private static String[] Affiche = { "ID", "AfficheTitle", "AfficheContent", "AfficheType", "Ponderance", "AfficheOrganID", "AfficheDeptID", "MakeOrganID",
			"MakeDeptID", "MakeID", "MakeDate" };

	private static String[] AfficheBrowse = { "ID", "AfficheID", "UserID", "IsBrowse" };

	private static String[] AlterMoveApply = { "ID", "MoveDate", "OutOrganID", "PaymentMode", "InvMsg", "TransportMode", "IsAudit", "IsRatify", "IsAgree",
			"IsBlankOut", "MakeOrganID", "MakeID", "MakeDate", "MakeDeptID" };

	private static String[] ApproveFlow = { "ID", "FlowName", "Memo" };

	private static String[] ApproveFlowDetail = { "ID", "AFID", "ApproveID", "ActID" };

	private static String[] ApproveFlowLog = { "ID", "AFID", "BillNO", "ApproveID", "ApproveContent", "Approve", "ApproveDate" };

	private static String[] Assemble = { "ID", "AProductID", "AProductName", "Adept", "CompleteIntendDate", "MakeID", "MakeDate", "IsAudit", "IsEndcase" };

	private static String[] AssembleRelation = { "ID", "ARProductID", "ARProductName", "ARSpecMode", "IsAudit", "MakeOrganID", "MakeDeptID", "MakeID",
			"MakeDate" };

	private static String[] AssembleRelationDetail = { "ID", "ARID", "ProductID", "ProductName", "SpecMode" };

	private static String[] BorrowIncome = { "ID", "WarehouseID", "WarehouseOut", "CID", "CName", "IncomeDate", "IsAudit", "IsBacktrack", "IsTransWithdraw",
			"MakeID", "MakeDate" };

	private static String[] TakeTicketIdcode = { "ID", "TTID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode" };
	
	private static String[] CalendarAwake = { "ID", "AwakeContent", "AwakeDateTime", "AwakeModel", "IsAwake", "IsDel", "UserID" };

	private static String[] CallCenterEvent = { "ID", "CallNum", "CalledNum", "EventDate", "UserID", "EventType", "OpType", "CID", "SeatNum" };

	private static String[] CallCenterMsg = { "ID", "SrcNum", "TagNum", "SentDate" };

	private static String[] Car = { "ID", "CarBrand", "CarSort", "PurchaseDate", "Worth", "IsLeisure", "MakeID", "MakeDate" };

	private static String[] CashBank = { "ID", "CBName", "MakeOrganID", "MakeID" };

	private static String[] CashBankAdjust = { "ID", "CBID", "IsAudit", "AuditDate", "MakeID", "MakeDate", "MakeOrganID" };

	private static String[] CashWasteBook = { "ID", "CBID", "Memo" };

	private static String[] CIntegral = { "ID", "OrganID", "CID", "ISort" };

	private static String[] CIntegralDeal = { "ID", "BillNo", "OrganID", "ct.cid", "CID", "ISort" };

	private static String[] CodeUnit = { "UCode", "UName", "UnitID" };

	private static String[] ConsignMachin = { "ID", "PID", "CProductID", "CProductName", "CompleteIntendDate", "IsAudit", "IsEndcase", "MakeOrganID", "MakeID" };

	private static String[] ContactLog = { "ID", "CID", "ContactDate", "ContactMode", "ContactProperty", "ContactContent", "Feedback", "LinkmanID",
			"NextContact", "MakeOrganID", "MakeDeptID", "MakeID" };

	private static String[] ContactDitch = { "ID", "DID", "ContactDate", "ContactMode", "ContactProperty", "ContactContent", "Feedback", "LinkmanID",
			"NextContact", "UserID" };

	private static String[] CorrelationDocument = { "ID", "CID", "DocumentName", "RealPathName", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate" };

	private static String[] CountryArea = { "ID", "AreaName", "ParentID", "Rank" };

	private static String[] Customer = { "CID", "CName", "CPYCode", "Vocation", "Mobile", "Sort", "CustomerType", "CustomerStatus", "Province", "City",
			"Areas", "MakeDate", "SpecializeID", "MakeID", "IsDel", "IsActivate", "MemberSex", "MakeOrganID", "Source" };

	private static String[] CustomerUsers = { "ID", "CID", "UID", "IsCreate" };

	private static String[] CustomerSort = { "ID", "SortName" };

	private static String[] DemandPrice = { "ID", "CID", "MakeID", "MakeDate", "IsAudit" };

	private static String[] DemandPriceDetail = { "ID", "DPID", "ProductID" };

	private static String[] Dept = { "ID", "DeptName" };

	private static String[] Ditch = { "ID", "DName", "DitchRank", "Prestige", "Vocation", "UserID", "MakeID", "MakeDate" };

	private static String[] DrawShipmentBill = { "ID", "WarehouseID", "DrawDate", "IsAudit", "IsBlankOut", "MakeOrganID", "MakeID", "MakeDate", "MakeDeptID" };

	private static String[] DrawShipmentBillDetail = { "ProductID", "ProductName", "SpecMode", "UnitID", "Batch", "UnitPrice", "Quantity", "SubSum" };

	private static String[] DrawShipmentBillIdcode = { "ID", "DSID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo",
			"IDCode" };

	private static String[] FeeWasteBook = { "ID", "CID", "CName", "FeeDept", "FeeID", "Porject", "RecordDate", "Memo" };

	private static String[] FleeProduct = { "ID", "QueryID", "Province", "FindProvince", "CID", "SynCode", "CName", "ProductID", "ProductName", "SpecMode",
			"StartNo", "EndNo", "SendDate", "IsDeal", "MakeOrganID", "MakeID", "MakeDate" };

	private static String[] GatherNotify = { "ID", "CID", "CName", "Clinkman", "SaleDept", "BillNo", "IsEndcase", "MakeID", "MakeDate" };

	private static String[] Hap = { "ID", "CID", "HapContent", "HapKind", "HapStatus", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate" };

	private static String[] HarmShipmentBill = { "ID", "WarehouseID", "ShipmentDept", "HarmDate", "IsAudit", "IsEndcase", "MakeOrganID", "MakeID", "MakeDate",
			"IsBlankOut", "MakeDeptID" };

	private static String[] HarmShipmentBillDetail = { "ProductID", "ProductName", "SpecMode", "UnitID", "Batch", "UnitPrice", "Quantity", "SubSum" };

	private static String[] ICode = { "ID", "ProductID", "LCode" };

	private static String[] Idcode = { "ID", "ProductID", "ProductName", "IDCode", "IsUse", "ProduceDate", "ValiDate", "LCode", "StartNo", "EndNo",
			"WarehouseBit", "BillID", "IdBillType", "MakeOrganID", "Batch", "WarehouseID", "ProvideID", "ProvideName", "MakeDate" };

	private static String[] IdcodeReset = { "ID", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate", "IsAudit" };

	private static String[] IdcodeUpload = { "ID", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate", "FileName", "BillSort", "IsDeal" };

	private static String[] IncomeLog = { "ID", "ROID", "Drawee", "MakeOrganID", "FundAttach", "IncomeSum", "BillNum", "IncomeDate", "PaymentMode", "IsAudit",
			"IsReceive", "MakeID" };

	private static String[] IntegralRule = { "ID", "RMode", "RKey" };

	private static String[] IntegralOrder = { "ID", "CID", "CName", "MakeOrganID", "ConsignmentDate", "TransportMode", "ReceiveMan", "MakeID", "IsAudit",
			"IsShipment", "InvMsg", "IsEndcase", "IsBlankOut", "BillType" };

	private static String[] IntegralOrderDetail = { "ID", "IOID", "ProductID", "ProductName", "SpecMode", "UnitID", "UnitPrice", "Quantity", "SubSum" };

	private static String[] IntegralI = { "ID", "OSort", "OID", "OName", "OMobile", "BillNo", "ISort", "RIncome", "AIncome", "MakeDate", "EquipOrganID",
			"OrganID" };

	private static String[] IntegralO = { "ID", "OSort", "OID", "OName", "OMobile", "BillNo", "ISort", "ROut", "AOut", "MakeDate", "EquipOrganID", "OrganID" };

	private static String[] IntegralDetail = { "ID", "IID", "OSort", "OID", "OName", "OMobile", "BillNo", "ISort", "MakeDate", "EquipOrganID", "OrganID" };

	// private static String[] IncomeLogApprove = { "ID", "ILID", "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] Largess = { "ID", "CID", "LargessName", "LargessDescribe", "LFee", "LDate", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate" };

	private static String[] Linkman = { "ID", "CID", "Name", "Sex", "Birthday", "OfficeTel", "Mobile", "Addr", "IsMain", "UserID", "MakeDate" };

	private static String[] Loan = { "ID", "UID", "LoanDate", "Purpose", "MakeID", "MakeDate", "IsAudit" };

	private static String[] LoanObject = { "ID", "UID", "PromiseDate", "MakeOrganID", "MakeID" };

	private static String[] LoanOut = { "ID", "UID", "UName", "SaleDept", "ConsignmentDate", "TransportMode", "ReceiveMan", "MakeID", "IsAudit", "IsReceive",
			"IsTransSale" };

	private static String[] MakeConf = { "ID", "TableName", "CurrentValue", "RunMode", "Profix", "Extent", "CHName" };

	private static String[] Menu = { "ID", "MenuName", "OperateID", "Url" };

	private static String[] MobileArea = { "ID", "MobileNum", "Areas", "CardType" };

	private static String[] MoveCanuseOrgan = { "ID", "OID", "OName", "UID" };

	private static String[] ObjIntegral = { "OID", "OSort", "OName", "OMobile", "OrganID", "KeysContent" };

	private static String[] OIntegralDeal = { "ID", "BillNo", "OID", "ISort" };

	private static String[] OIntegralSett = { "ID", "MakeOrganID", "OID", "MakeDeptID", "MakeID", "IsAudit" };

	private static String[] Olinkman = { "ID", "CID", "Name", "Sex", "Birthday", "OfficeTel", "Mobile", "Addr", "IsMain", "UserID", "MakeDate" };

	private static String[] Operate = { "ID", "OperateName", "ModuleID" };

	private static String[] Organ = { "id", "organname", "OrganPrint", "ParentID", "Rank", "Province", "City", "Areas", "IsRepeal","bigRegionId","officeId","organType","organModel" , "isKeyRetailer","creationTime","modificationTime"};

	private static String[] OrganAwake = { "ID", "OrganID", "UserID" };

	private static String[] OrganScan = { "ID", "OrganID", "SCB", "IsScan" };

	private static String[] OrganGrade = { "ID", "GradeName" };

	private static String[] OrganPrice = { "ID", "OrganID", "ProductID", "PolicyID", "UnitPrice" };

	private static String[] OrganPriceii = { "ID", "OrganID", "ProductID", "UnitPrice" };

	private static String[] OrganProduct = { "ID", "OrganID", "ProductID" };

	private static String[] OrganRole = { "ID", "OrganID", "OName", "RoleID" };

	private static String[] OtherIncome = { "ID", "WarehouseID", "IncomeSort", "IncomeDept", "IncomeDate", "IsAudit", "MakeID", "MakeDate", "MakeOrganID",
			"MakeDeptID" ,"isaccount"};

	// private static String[] OtherIncomeApprove = { "ID", "OIID", "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] OtherIncomeDetail = { "ID", "OIID", "ProductID", "ProductName", "SpecMode", "UnitID", "Batch", "UnitPrice", "Quantity", "SubSum" };

	private static String[] OtherIncomeIdcode = { "ID", "OIID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode" };

	private static String[] OtherShipmentBill = { "ID", "WarehouseID", "ShipmentSort", "ShipmentDept", "RequireDate", "IsRefer", "ApproveStatus", "IsAudit",
			"IsRatify", "MakeID", "MakeDate", "MakeOrganID", "MakeDeptID","isaccount" };

	// private static String[] OtherShipmentBillApprove = { "ID", "OSID",
	// "ApproveID", "ApproveContent", "Approve", "ApproveDate" };

	private static String[] OtherShipmentBillDetail = { "ID", "OSID", "ProductID", "ProductName", "SpecMode", "UnitID", "Batch", "UnitPrice", "Quantity",
			"SubSum" };

	private static String[] OtherShipmentBillIdcode = { "ID", "OSID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo",
			"IDCode" };

	private static String[] Outlay = { "ID", "OutlayID", "OutlayDept", "FundSrc", "ACID", "IsAudit", "IsEndcase", "IsBlankOut", "MakeDate", "MakeOrganID",
			"MakeID" };

	private static String[] OutlayDetail = { "ID", "OID", "ProductID", "Narrate", "UserID", "UserDept" };

	// private static String[] OutlayApprove = { "ID", "OID", "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] Pact = { "ID", "PactCode", "PactType", "UserID", "CID", "CDeputy", "SignDate", "Provide", "PDeputy", "SignAddr", "PactScopy" };

	private static String[] Payable = { "ID", "POID", "PayableSum", "ApproveStatus", "IsAudit", "MakeOrganID", "MakeID", "MakeDate" };

	private static String[] PayableDetail = { "ID", "ProductID", "ProductName", "SpecMode", "IsClose", "MakeDate" };

	// private static String[] PayableApprove = { "ID", "PID", "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] PayableObject = { "ID", "ObjectSort", "Payee", "MakeOrganID", "MakeID", "MakeDate" };

	private static String[] PaymentApply = { "ID", "PID", "PName", "PurchaseDept", "MakeID", "MakeDate", "IsEndcase" };

	private static String[] PaymentLog = { "ID", "POID", "Remark", "IsAudit", "AuditID", "AuditDate", "PayMode", "IsPay", "MakeID", "MakeDeptID",
			"MakeOrganID", "FundSrc", "paymode" };

	// private static String[] PaymentLogApprove = { "ID", "PLID", "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] PaymentLogDetail = { "ID", "PLID", "PayeeID", "PayMode", "PaySum", "BillNum", "Memo" };

	private static String[] PaymentMode = { "ID", "IRID", "PaymentName" };

	private static String[] Paysrc = { "ID", "SrcName", "", "SrcMemo", "ParentID", "Rank" };

	private static String[] PhoneBook = { "ID", "NameSex", "Phone", "Mobile", "Email", "Qq", "Msn", "Birthday", "AddrRemark", "SortID", "UserID" };

	private static String[] PhoneBookSort = { "ID", "SortName" };

	private static String[] Doc = { "ID", "MakeID", "SortID" };

	private static String[] DocSort = { "ID", "SortName" };

	private static String[] Product = { "ID", "ProductName", "PSID", "Brand", "ProductCode", "SpecMode", "PYCode", "AbcSort", "Country", "Wise", "Variety",
			"Manor", "PGrade", "YearNum", "Color", "Odor", "OraFeel", "DrinkScore", "ConfAdvice", "UseFlag", "MakeID", "MakeDate", "IsIDCode", "productType" };

	private static String[] ProductRedeploy = { "ID", "RedeployID", "RedeployDept", "RedeployMemo", "MakeID", "MakeDate" };

	private static String[] ProductRedeployDetail = { "ID", "ProductID", "StandardPurchase", "StandardSale", "Cost" };

	private static String[] ProductHistory = { "ID", "ProductID", "StandardPurchase", "StandardSale", "StandardStock", "UseFlag", "UpdateID", "LastDate" };

	private static String[] ProductIncome = { "ID", "HandwordCode", "WarehouseID", "IncomeDate", "IsAudit", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate" };

	private static String[] ProductIncomeDetail = { "ID", "PIID", "ProductID", "ProductName", "SpecMode", "UnitID", "Batch", "UnitPrice", "Quantity", "SubSum",
			"IsSettlement" };

	private static String[] ProductIncomeIdcode = { "ID", "PIID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode" };

	// private static String[] ProductIncomeApprove = { "ID", "PIID",
	// "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] ProductProperty = { "ID", "ProductID", "PropertyCode" };

	private static String[] ProductStockpileAll = { "ID", "ProductID", "PSProductName", "PSPYCode", "PSSpecMode", "CountUnit", "Batch", "BarCode",
			"WarehouseID", "Stockpile", "PrepareOut", "IsLock" };

	private static String[] ProductStockpile = { "ID", "ProductID", "PSProductName", "PSPYCode", "PSSpecMode", "CountUnit", "BarCode", "WarehouseID",
			"WarehouseBit", "Stockpile", "PrepareOut", "IsLock" ,"Batch"};

	private static String[] ProductStruct = { "ID", "StructCode", "SortName", "Remark" };

	private static String[] Project = { "ID", "CID", "PContent", "PStatus", "MakeID", "MakeDate" };

	private static String[] Provider = { "PID", "PName", "Tel", "Mobile", "Vocation", "TaxCode", "Genre", "Addr", "AbcSort", "Province", "City", "Areas",
			"MakeOrganID", "MakeDeptID", "MakeID", "MakeDate", "UseFlag" };

	private static String[] Plinkman = { "ID", "PID", "Name", "OfficeTel", "Mobile", "HomeTel", "Addr", "IsMain" };

	private static String[] Role = { "ID", "RoleName" };

	private static String[] MemberGrade = { "ID", "GradeName" };

	private static String[] MemberGradeRule = { "ID", "GradeName" };

	private static String[] MoveApply = { "ID", "MoveDate", "OutOrganID", "TransportMode", "IsAudit", "IsRatify", "MakeDeptID", "IsAgree", "IsBlankOut",
			"MakeOrganID", "MakeID", "MakeDate", "InOrganID" };

	private static String[] ProviderProduct = { "ID", "PID", "ProductID", "PvProductName", "PvSpecMode", "Price", "PriceDate" };

	private static String[] PurchaseBill = { "ID", "POID", "PID", "PurchaseDept", "PurchaseID", "ReceiveDate", "MakeOrganID", "MakeID", "MakeDate", "PayMode",
			"IsAudit", "IsRatify" };

	private static String[] PurchaseBillDetail = { "ID", "PBID", "ProductID", "ProductName", "SpecMode", "UnitID", "UnitPrice", "Quantity", "SubSum" };

	// private static String[] PurchaseBillApprove = { "ID", "PBID",
	// "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] PurchaseOrder = { "ID", "PPID", "PurchaseSort", "PID", "PurchaseDept", "PurchaseID", "PayCondition", "ReceiveDate", "IsRefer",
			"ApproveStatus", "ApproveDate", "MakeID", "MakeDate", "IsEndcase", "IsBlankOut" };

	private static String[] PurchaseOrderDetail = { "ID", "POID", "ProductID", "ProductName", "SpecMode", "UnitID", "UnitPrice", "Quantity", "SubSum" };

	// private static String[] PurchaseOrderApprove = { "ID", "POID",
	// "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] PurchaseIncome = { "ID", "WarehouseID", "PBID", "ProvideID", "ProvideName", "ObtainCode", "PurchaseSort", "PurchaseDept",
			"PurchaseID", "IncomeDate", "IsAudit", "IsTally", "MakeID", "MakeDate", "MakeOrganID", "MakeDeptID" };

	private static String[] PurchaseIncomeDetail = { "ID", "PIID", "ProductID", "ProductName", "SpecMode", "UnitID", "Batch", "UnitPrice", "Quantity",
			"SubSum", "IsSettlement" };

	private static String[] PurchaseIncomeIdcode = { "ID", "PIID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo",
			"IDCode" };

	private static String[] PurchaseInquire = { "ID", "PPID", "InquireTitle", "PID", "PLinkman", "CreateDate", "ValidDate", "SeeToID", "Remark", "MakeID",
			"MakeDeptID", "MakeOrganID", "IsAudit" };

	private static String[] PurchaseInvoice = { "ID", "InvoiceCode", "InvoiceType", "MakeInvoiceDate", "InvoiceDate", "ProvideID", "PurchaseSort",
			"PurchaseDept", "PurchaseMan", "MakeID", "IsAudit" };

	private static String[] PurchasePlan = { "ID", "PurchaseSort", "PlanDate", "PlanDept", "PlanID", "IsAudit", "IsRatify", "IsComplete", "Remark",
			"MakeOrganID" };

	private static String[] PurchasePlanDetail = { "ProductID", "ProductName", "SpecMode" };

	private static String[] PurchaseTrades = { "ID", "WarehouseInID", "WarehouseOutID", "ProvideID", "TradesMode", "TradesDate", "IsAudit", "MakeOrganID",
			"MakeID", "MakeDeptID", "MakeDate", "IsReceive" };

	private static String[] PurchaseTradesDetail = { "ID", "ProductID", "ProductName", "SpecMode" };

	private static String[] PurchaseTradesIdcode = { "ID", "PTID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo",
			"IDCode" };

	private static String[] PurchaseWithdraw = { "ID", "PID", "MakeOrganID", "MakeID", "MakeDeptID", "MakeDate", "WithdrawCause", "IsAudit", "IsBlankOut",
			"IsEndcase" };

	private static String[] PurchaseWithdrawDetail = { "ID", "ProductID", "ProductName", "SpecMode" };

	// private static String[] PurchaseWithdrawApprove = { "ID", "PWID",
	// "ApproveID", "ActID", "Approve" };

	// private static String[] PurchaseIncomeApprove = { "ID", "PIID",
	// "ApproveID", "ApproveContent", "Approve", "ApproveDate" };

	// private static String[] QualityPass = { "ID", "AGID", "PurchaseSort",
	// "PID", "PurchaseDept", "QualityDept", "IsAudit", "MakeID",
	// "MakeDate" };

	private static String[] ReceivableObject = { "ID", "OID", "ObjectSort", "Payer", "MakeDate", "MakeOrganID", "BillNo", "MakeID", "PromiseDate" };

	private static String[] Receivable = { "ID", "ROID", "ReceivableSum", "MakeOrganID", "MakeID", "MakeDate", "IsAudit", "IsClose" };

	private static String[] ReceivableDetail = { "ID", "ProductID", "ProductName", "SpecMode", "RID", "IsClose", "MakeDate" };

	// private static String[] ReceivableApprove = { "ID", "RID", "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] Reckoning = { "ID", "UID", "MakeID", "MakeDate", "IsAudit" };

	private static String[] Retail = { "ID", "CName", "Tel", "WarehouseID", "MakeID", "MakeDate", "IsAudit", "IsSettlements" };

	private static String[] RetailDetail = { "ID", "RID", "" };

	private static String[] SaleInvoice = { "ID", "InvoiceCode", "InvoiceType", "MakeInvoiceDate", "InvoiceDate", "InvoiceSum", "CID", "CNAME", "SaleDept",
			"SaleMan", "Memo", "IsAudit", "MakeID", "MakeDate", "UpdateID", "LastDate", "UserID", "MakeOrganID" };

	private static String[] SaleIndent = { "ID", "CustomerBillID", "CID", "CName", "SaleType", "SaleDept", "MakeDate", "TransportMode", "ReceiveMan", "MakeID",
			"IsAudit", "IsEndcase", "MakeOrganID", "MakeDeptID" };

	private static String[] SaleIndentDetail = { "ID", "SOID", "ProductID", "ProductName", "SpecMode", "UnitPrice", "UserID" };

	private static String[] SaleOrder = { "ID", "CustomerBillID", "CID", "CName", "CMobile", "MakeOrganID", "ConsignmentDate", "TransportMode", "ReceiveMan",
			"MakeID", "IsAudit", "IsShipment", "InvMsg", "Source", "IsEndcase", "IsBlankOut", "PaymentMode", "EquipOrganID", "MakeDeptID", "IsAccount",
			"MakeDate", "IsDayBalance" };

	private static String[] SaleOrderDetail = { "ID", "SOID", "ProductID", "ProductName", "SpecMode", "UnitPrice", "UserID" };

	private static String[] VocationOrder = { "ID", "CustomerBillID", "CID", "CName", "MakeOrganID", "ConsignmentDate", "TransportMode", "ReceiveMan",
			"MakeID", "IsAudit", "IsShipment", "InvMsg", "Source", "IsEndcase", "IsBlankOut", "PaymentMode", "EquipOrganID" };

	private static String[] VocationOrderDetail = { "ID", "VOID", "ProductID", "ProductName", "SpecMode", "UnitPrice", "UserID" };

	private static String[] PeddleOrder = { "ID", "CID", "CName", "SaleDept", "ReceiveMan", "MakeID", "EquipOrganID", "IsAudit", "InvMsg", "IsBlankOut",
			"CMobile", "PaymentMode", "MakeOrganID", "ReceiveTel", "IsAccount", "IsDayBalance" };

	private static String[] PeddleOrderDetail = { "ID", "POID", "ProductID", "ProductName", "SpecMode", "UserID" };
	private static String[] TrackApply = { "ID", "applyOrgId", "applyUserId", "idCode", "codeType", "createDate","status","remark","errorMsg" };

	// private static String[] SaleOrderApprove = { "ID", "SOID", "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] SaleRepair = { "ID", "CID", "WarehouseInID", "WarehouseOutID", "MakeID", "MakeDate", "TotalSum", "IsBacktrack", "ApproveStatus",
			"ApproveDate", "IsAudit", "IsBlankOut" };

	private static String[] SaleTrades = { "ID", "WarehouseInID", "WarehouseOutID", "CID", "TradesMode", "TradesDate", "IsAudit", "MakeID", "MakeOrganID",
			"MakeDate", "IsBacktrack", "IsBlankOut" };

	private static String[] SaleTradesDetail = { "ID", "ProductID", "ProductName", "OldBatch", "WarehouseID", "Batch" };

	private static String[] SaleTradesIdcode = { "ID", "STID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode" };

	private static String[] SendTime = { "ID", "IRID", "STime", "ETime" };

	private static String[] SampleBill = { "ID", "CID", "MakeID", "MakeDate", "IsAudit" };

	// private static String[] SampleBillApprove = { "ID", "SBID", "ApproveID",
	// "Approve" };

	private static String[] SampleBillDetail = { "ID", "SBID" };

	private static String[] ServiceAgreement = { "ID", "CID", "SContent", "SStatus", "Rank", "SDate", "IsAllot", "MakeID" };

	private static String[] ServiceDetail = { "ID", "SAID" };

	private static String[] ServiceExecute = { "ID", "SAID", "UserID", "IsAffirm" };

	// private static String[] Settlement = { "ID", "SettlementDate",
	// "SettlementID", "ProvideID", "ProvideName", "SettlementSum",
	// "IsRefer", "ApproveStatus", "ApproveDate", "IsAudit", "IsRatify",
	// "IsCreate" };

	// private static String[] SettlementApprove = { "ID", "SID", "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] ShipmentBill = { "ID", "CID", "CName", "Linkman", "Tel", "SaleDept", "ReceiveID", "RequireDate", "TransportMode", "IsAudit",
			"IsTrans", "MakeID", "MakeDate", "MakeOrganID", "MakeDeptID", "IsBlankOut" };

	private static String[] ShipmentBillDetail = { "ID", "SBID", "ProductID", "ProductName", "SpecMode", "Batch" };

	private static String[] StockCheck = { "ID", "WarehouseID", "CheckDate", "ReckonDate", "CheckDept", "IsAudit", "IsCreate", "IsTally", "MakeOrganID",
			"MakeDeptID", "MakeID", "IsBar" };

	private static String[] StockCheckDetail = { "ID", "SCID", "WarehouseBit", "ProductID", "ProductName", "SpecMode", "Batch" };

	private static String[] StockCheckIdcode = { "ID", "SCID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode",
			"CIDCode" };

	private static String[] StockMove = { "ID", "MoveDate", "OutWarehouseID", "InWarehouseID", "InOrganID", "IsAudit", "MakeID", "MakeDate", "IsShipment",
			"IsBlankOut", "IsComplete", "ReceiveID", "ReceiveDate", "MakeOrganID", "MakeDeptID" ,"inorganid","outorganid" };

	private static String[] StockMoveDetail = { "ID", "SAMID", "ProductID", "ProductName", "SpecMode" };

	private static String[] StockMoveIdcode = { "ID", "SMID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode" };

	private static String[] StockAlterMove = { "ID", "MoveDate", "MakeOrganID", "outwarehouseid", "OLinkman", "OTel", "ReceiveOrganID", "InWarehouseID",
			"IsAudit", "MakeID", "MakeDate", "IsShipment", "IsTally", "IsBlankOut", "IsComplete", "ReceiveID", "MakeDeptID","NCcode","NCcode2","outOrganId","Bsort" }; 

	private static String[] StockAlterMoveDetail = { "ID", "SAMID", "ProductID", "ProductName", "SpecMode" };

	private static String[] StockAlterMoveIdcode = { "ID", "SAMID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo",
			"IDCode" };

	private static String[] ProductInterconvert = { "ID", "MoveDate", "OutWarehouseID", "InWarehouseID", "IsRefer", "ApproveStatus", "ApproveDate", "IsAudit",
			"MakeID", "MakeDate", "IsShipment", "IsComplete", "ReceiveID", "ReceiveDate", "MakeOrganID", "MakeDeptID" };

	private static String[] ProductInterconvertIdcode = { "ID", "PIID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo",
			"IDCode" };

	private static String[] ProductPriceHistory = { "id", "productId", "unitId", "unitPrice", "startTime","endTime","makeUserId","makeDate","makeOrganId","memo" };

	private static String[] OrganPriceHistory = { "ID", "ProductID", "UnitID", "PolicyID", "ModifyDate", "OrganID" };

	private static String[] ProductInterconvertDetail = { "id", "SAMID", "ProductID", "ProductName", "SpecMode" };

	// private static String[] StockMoveApprove = { "ID", "SMID", "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] StockWasteBook = { "ID", "ProductID", "Batch", "WarehouseID", "WarehouseBit", "BillCode", "RecordDate" };

	private static String[] StuffShipmentBill = { "ID", "WarehouseID", "ShipmentDept", "RequireDate", "IsAudit", "MakeID", "MakeDate" };

	private static String[] StuffShipmentBillDetail = { "ID", "SSID", "ProductID", "ProductName", "SpecMode", "Batch" };

	// private static String[] StuffShipmentBillApprove = { "ID", "SSID",
	// "ApproveID", "ApproveContent", "Approve", "ApproveDate" };

	private static String[] Suit = { "ID", "CID", "CName", "SuitContent", "SuitWay", "IsDeal", "DealWay", "DealUser", "MakeID", "MakeDate", "MakeOrganID" };

	private static String[] TakeBill = { "ID", "OID", "OName", "RLinkman", "Tel", "MakeID", "MakeDate", "IsAudit", "IsBlankOut", "MakeOrganID", "MakeDeptID",
			"BSort", "IsRead" };

	private static String[] TakeTicket = { "ID", "OID", "Tel", "BSort", "WarehouseID", "MakeOrganID", "MakeID", "MakeDate", "BillNo", "EquipOrganID",
			"IsAudit", "IsBlankOut", "IsRead","inOId" };

	private static String[] TakeTicketDetail = { "ID", "TTID", "ProductID", "ProductName", "SpecMode", "Batch", "IsRead" };

	private static String[] BarcodeInventoryIdcode = { "ID", "OSID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode" };

	private static String[] Task = { "ID", "ObjSort", "CID", "CName", "TPTitle", "ConclusionDate", "EndTime", "Priority", "TaskSort", "Status", "TPContent",
			"MakeOrganID", "MakeDeptID", "MakeID", "MakeDate", "IsAllot", "OverStatus", "OverDate" };

	private static String[] TaskExecute = { "ID", "TPID", "UserID", "IsAffirm", "IsOver", "OverDate" };

	private static String[] UserArea = { "ID", "UserID", "AreaID" };
	
	private static String[] UserApply = { "userType","isApproved"};

	private static String[] UserLog = { "ID", "UserID", "LogTime", "ModelType" };

	private static String[] UserRole = { "ID", "UserID", "RoleID", "IsPopedom" };

	private static String[] Users = { "UserID", "LoginName", "RealName", "NameEN", "Sex", "Birthday", "Mobile", "OfficeTel", "HomeTel", "Addr", "CreateDate",
			"LoginTimes", "Dept", "ParentID", "Status", "IsOnline", "MakeDeptID", "MakeOrganID","UserType" };

	private static String[] UserVisit = { "ID", "UserID", "VisitOrgan", "VisitDept", "VisitUsers" };

	private static String[] Warehouse = { "ID", "WarehouseName", "Dept", "UserID", "WarehouseTel", "WarehouseProperty", "WarehouseAddr", "UseFlag", "Remark", "MakeOrganId" };

	private static String[] WarehouseBit = { "ID", "WID", "WBID" };

	private static String[] OrganAnnunciator = { "ID", "OrganID", "UserID", "IsAwake" };

	private static String[] OrganSafetyIntercalate = { "ID", "OrganID", "ProductID", "Safety" };

	private static String[] WarehouseVisit = { "ID", "WID", "UserID" };

	private static String[] WebIndent = { "ID", "CID", "CName", "EquipOrganID", "ConsignmentDate", "TransportMode", "ReceiveMan", "MakeID", "IsAudit",
			"IsShipment", "InvMsg", "IsEndcase", "IsBlankOut", "PaymentMode", "Tel" };

	private static String[] Withdraw = { "ID", "CID", "WarehouseID", "MakeOrganID", "MakeID", "MakeDate", "WithdrawCause", "Tel", "CName", "CMobile",
			"IsAudit", "IsBlankOut", "PaymentMode", "WithdrawSort" };

	private static String[] WithdrawDetail = { "ProductID", "ProductName", "SpecMode" };

	private static String[] WithdrawIdcode = { "ID", "WID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode" };

	// private static String[] WithdrawApprove = { "ID", "WID", "ApproveID",
	// "ApproveContent", "Approve", "ApproveDate" };

	private static String[] WorkReport = { "ID", "ReportContent", "ReportSort", "ReferDate", "IsRefer", "ApproveStatus", "ApproveDate", "Remark",
			"MakeOrganID", "MakeDeptID", "MakeID", "MakeDate" };

	private static String[] WorkReportApprove = { "ID", "ReportID", "ApproveID", "ActID", "ApproveContent", "Approve" };

	private static String[] WorkReportExecute = { "ID", "ReportID", "ApproveID", "ApproveContent", "Approve", "ApproveDate" };

	private static String[] Equip = { "ID", "CID", "CName", "CLinkman", "Tel", "Motorman", "CarBrand", "EquipDate", "MakeOrganID", "MakeDeptID", "MakeID" };

	private static String[] EquipDetail = { "ID", "EID", "SBID", "EraSum" };

	private static String[] ViewCIntegralDeal = { "BillNo", "OrganID", "CID", "MakeDate", "Mobile" };

	private static String[] ViewCIntegralTotal = { "cid", "cname", "mobile", "makeorganid", "makedate" };

	private static String[] ViewOIntegralTotal = { "oid", "organname", "makedate" };

	private static String[] ViewCIntegralDetail = { "cid", "cname", "mobile", "organid", "makedate" };

	private static String[] ViewCIntegralDuihuanDetail = { "cid", "cname", "mobile", "makeorganid", "organid" };

	private static String[] ViewCIntegralDuihuanTotal = { "cid", "cname", "mobile", "makeorganid", "organid" };

	private static String[] ViewOIntegralDeal = { "BillNo", "OID", "MakeDate" };

	private static String[] Msg = { "MsgSort", "MakeOrganID", "MakeDeptID", "MakeID", "IsAudit", "IsDeal" };

	private static String[] MsgTemplate = { "TemplateType", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate" };

	private static String[] Repository = { "RTID", "MakeOrganID", "MakeDeptID", "MakeID" };

	private static String[] AdjustCIntegral = { "IsAudit", "MakeOrganID", "MakeDeptID", "MakeID" };

	private static String[] AdjustOIntegral = { "IsAudit", "MakeOrganID", "MakeDeptID", "MakeID" };

	private static String[] ViewSaleProductTotal = { "productid", "productname", "makeorganid", "equiporganid", "billtype", "makeid" };

	private static String[] ViewSaleCustomerTotal = { "makeorganid", "equiporganid", "billtype", "makeid", "cid", "cname", "id" };

	private static String[] ViewSaleBill = { "makeorganid", "cid", "cname", "makeid", "cmobile", "id" };

	private static String[] ViewWithdrawProduct = { "productid", "productname", "specmode", "unitprice" };

	private static String[] Questions = { "ID", "Title", "Content", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate" };

	private static String[] Respond = { "ID", "QID", "Content", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate" };

	private static String[] SaleForecast = { "ID", "ObjSort", "CID", "CName", "ForeStartDate", "ForeEndDate", "TotalSum", "MakeOrganID", "MakeDeptID",
			"MakeID", "MakeDate" };

	private static String[] SupplySaleApply = { "ID", "MoveDate", "OutOrganID", "TotalSum", "PaymentMode", "InvMsg", "TransportMode", "InOrganID",
			"InWarehouseID", "IsAudit", "IsRatify", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate", "IsBlankOut", "PrintTimes", "IsTrans" };

	private static String[] SupplySaleMove = { "ID", "MoveDate", "OutWarehouseID", "SupplyOrganID", "TotalSum", "PaymentMode", "InvMsg", "TransportMode",
			"InOrganID", "InWarehouseID", "TransportAddr", "IsMakeTicket", "IsAudit", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate", "IsShipment",
			"IsTally", "IsBlankOut", "ReceiveOrganID", "ReceiveDeptID", "IsComplete", "PrintTimes", "TakeStatus" };

	private static String[] SupplySaleMoveDetail = { "ID", "SSMID", "ProductID", "ProductName", "SpecMode" };

	private static String[] SupplySaleMoveIdcode = { "ID", "SSMID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo",
			"IDCode" };

	private static String[] OrganInvoice = { "ID", "InvoiceCode", "InOrOut", "OrganID", "OrganName", "InvoiceContent", "InvoiceType", "MakeInvoiceDate",
			"InvoiceDate", "InvoiceSum", "InvoiceTitle", "SendAddr", "Memo", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate", "IsAudit", "AuditID",
			"AuditDate", "UpdateID", "LastDate"

	};

	private static String[] OrganWithdraw = { "ID", "POrganID", "PLinkman", "Tel", "WarehouseID", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate", "IsAudit",
			"IsRatify", "IsAffirm", "IsReceive", "IsBlankOut", "TakeStatus", "IsComplete","ReceiveOrganid","receiveorganid","InWarehouseID" };

	private static String[] OrganWithdrawDetail = { "ID", "SAMID", "ProductID", "ProductName", "SpecMode" };

	private static String[] OrganWithdrawIdcode = { "ID", "OWID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode" };

	private static String[] OrganTrades = { "ID", "POrganID", "RTransportAddr", "OutWarehouseID", "InWarehouseID", "MakeOrganID", "MakeDeptID", "MakeID",
			"MakeDate", "IsAudit", "IsRatify", "IsAffirm", "PIsReceive", "PReceiveID", "PReceiveDate", "PIsAffirm", "IsReceive", "IsBlankOut", "TakeStatus" };

	private static String[] OrganTradesDetail = { "ID", "OTID", "ProductID", "ProductName", "SpecMode" };

	private static String[] OrganTradesPIdcode = { "ID", "OTID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode" };

	private static String[] OrganTradesTIdcode = { "ID", "OTID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode" };

	private static String[] UploadProducReport = { "ID", "ProID", "ProName", "ProDT", "LotNo", "ProRule", "ItemCode", "LineNo", "ProCode", "PackCode",
			"BoxCode", "CartonCode", "PalletCode", "RecType", "Remark", "OptTime", "WarehouseId", "isInCome"};

	private static String[] ViewWlmIdcode = { "id", "warehouseid", "cid", "syncode", "cname", "productid", "productid", "specmode", "billname", "billtype",
			"makedate", "producedate", "batch", "startno", "endno", "organid" };

	private static String[] WlmIdcodeLog = { "ID", "Province", "City", "Areas", "WlmIdcode", "WarehouseID", "CID", "SynCode", "CName", "ProductID",
			"ProductName", "SpecMode", "OrganID", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate","QuerySort" };

	private static String[] UploadPrLog = { "ID", "IsDeal", "FilePath", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate" };
	private static String[] ReceiveIncome = { "ID", "HandwordCode", "WarehouseID", "IncomeDate", "IsAudit", "MakeOrganID", "MakeDeptID", "MakeID", "MakeDate" };

	private static String[] ReceiveIncomeIdcode = { "ID", "PIID", "ProductID", "IsIDCode", "WarehouseBit", "Batch", "ProduceDate", "StartNo", "EndNo", "IDCode" };
	
	private static String[] TakeTicketDetailBatchBit = { "id", "ttid", "ttdid",
		"productid", "productname", "warehouseBit", "batch", "quantity","realQuantity" };
	
	private static String[] RegionArea = { "ID", "regioncodeid", "areaid" };
	
	private static String[] RegionUsers={"UserID","UserName","userlogin"};
	private static String[] Region={"RegionCode","SortName","ParentId","ParentName"};
	private static String[] SuggestInspect={"id","typeId","typeName","makeName","makeDate","siid","customerCode","disWareHouseName","souWareHouseName","isPost","isMerge","isRemove","mergeId","isOut" };
	private static String[] SuggestInspectDetail={"id","seqNumber","siid","productId","productName","productCode","unit","quantity","isGift"};
	private static String[] CustomerMatchOrder={"id","siName","productLine","organName","customerLevel","matchOrder","outOrder","remark","organId" };
	
	private static String[] OtherIncomeAll = { "ID", "WarehouseID", "IncomeSort", "IncomeDept", "IncomeDate", "IsAudit", "MakeID", "MakeDate", "MakeOrganID",
		"MakeDeptID" ,"isaccount" ,"organid"};

	private static String[] OtherIncomeDetailAll = { "ID", "OIID", "ProductID", "ProductName", "SpecMode", "UnitID", "Batch", "UnitPrice", "Quantity", "SubSum" };
	
	private static String[] OtherShipmentBillAll = { "ID", "WarehouseID", "ShipmentSort", "ShipmentDept", "RequireDate", "IsRefer", "ApproveStatus", "IsAudit",
		"IsRatify", "MakeID", "MakeDate", "MakeOrganID", "MakeDeptID","isaccount","organid" };


	private static String[] OtherShipmentBillDAll = { "ID", "OSID", "ProductID", "ProductName", "SpecMode", "UnitID", "Batch", "UnitPrice", "Quantity",
		"SubSum" };
	
	private static String[] BarcodeInventory = { "ID", "WarehouseID", "ShipmentSort", "ShipmentDept", "RequireDate", "IsRefer", "ApproveStatus", "IsAudit",
		"IsRatify", "MakeID", "MakeDate", "MakeOrganID", "MakeDeptID","isaccount","organid","isapprove" };
	
	private static String[] AmountInventory = { "ID", "WarehouseID", "ShipmentSort", "ShipmentDept", "RequireDate", "IsRefer", "ApproveStatus", "IsAudit",
		"IsRatify", "MakeID", "MakeDate", "MakeOrganID", "MakeDeptID","isaccount","organid","isapprove" };
	
	private static String[] PurchaseSalesReport = { "ID", "ProductID", "Batch", "WarehouseID", "WarehouseBit", "BillCode", "RecordDate" };
	
	private static String[] InventoryReport = { "ID", "ProductID", "Batch", "WarehouseID", "WarehouseBit", "BillCode"};
	
	private static String[] StockPileAgeing = {"tagMinValue","tagMaxValue","tagColor"};
	
	private static String[] PrintJob = {"printJobId", "plantCode", "processOrderNumber", "materialName", "packSize", "batchNumber", "numberOfCases", "totalNumber","printingStatus"};
	
	private static String[] UploadSAPLog = {"id", "isDeal", "fileType","makeId","errorType","billNo"};
	
	private static String[] UploadProduceLog = {"id", "isDeal", "fileType","makeId"};
	private static String[] UploadCartonSeqLog = {"id", "isDeal", "fileType","makeId"};
	
	private static String[] CartonCode = {"printJobId"};
	
	private static String[] Notification = {"deliveryNo", "logisticCompany", "deliveryDate", "estimateDate", "shipToCompany", "consigneeName", "consigneeMobile", "sendStatus"};
	
	private static String[] Invoice = {"invoiceNumber","partnSold","deliveryNumber","invoiceLineItem","materialCode","batchNumber"};
	
	private static String[] AppUpdate = {"appName","publishName","appVersion","publisher"};
	
	private static String[] AppUpdateLog = {"appName","appVersion","scannerImeiN"};
	
	private static String[] BarcodeUpload = { "ID", "MakeOrganID", "FileName"};
	private static String[] Query = { "proNumber", "findDt", "telNumber", "chkTrue", "findType", "compFlag", "profile", "remark" , "areas" ,"city" ,"queryNum"};
	
	private static String[] CovertUploadReport = { "id","lineNo", "line_No","materialCode", "material_Code","batch", "uploadPrId","upload_Pr_Id", "uploadDate", "upload_Date", "uploadUser", "upload_User"};
	
	private static String[] CovertErrorLog = { "tdCode", "covertCode", "errorType", "uploadDate", "uploadUser"};
	private static String[] IdcodeUploadLog = {"billNo", "idcodeUploadId", "uploadDate", "uploadUser", "bsort", "errMsg", "errCode", "idcode", "uploadOrganId"};
	
	private static String[] Sms = {"mobileNo", "content", "sendStatus","type"};
	
	private static String[] InventoryUploadLog = { "id", "isDeal", "filePath", "makeOrganID", "makeDeptID", "makeID", "makeDate" };
	private static String[] BillImportConfig = { "id", "organId", "templateNo", "fieldName", "columnName", "defaultValue" };
	private static String[] ProductConfig = { "id", "organId", "erpProductId", "mCode","productId" };
	private static String[] UnitInfo = { "id", "organId", "productId", "unitId","unitCount","modifiedDate","modifiedUserID","isactive" };
	private static String[] ProductPlan = { "id", "PONO", "organId","makeId", "productId","mbatch","pbatch","proDate","packDate","boxnum","copys","approvalFlag","approvalMan","temp","isUpload" };
	private static String[] SuggestionBox = { "id", "suggestionMsg", "makeDate", "ip","imeiNumber" };
	
	private static String[] Wlinkman = { "id", "warehouseid", "name",  "mobile"};
	
	private static String[] PackSeparate = { "id", "warehouseId", "organId", "isAudit", "makeId", "makeDate", "makeOrganId", "isAccount"};
	
	private static String[] SalesConsumHistory = { "id", "productId", "organId", "warehouseId", "makeId", "makeDate", "makeOrganId", "isAccount"};
	
	private static String[] Member = { "loginname", "mobile"};
	
	private static String[] OrganUploadLog = {"isDeal", "makeId", "makeOrganId"};
	
	private static String[] SBonusSetting = {"productName", "spec", "accountType", "activeFlag"};
	
	private static String[] SBonusAppraise = {"year", "month"};
	
	private static String[] SBonusAccount = {"year", "month"};
	private static String[] SBonusDetail = {"organId", "oppOrganId", "bonusType", "mcode"};
	
	private static String[] STransferRelation = {"organizationId", "oppOrganId"};
	
	private static String[] SBonusTarget = {"fromOrganId", "toOrganId","productName","year"};
	
	private static String[] SBonusLog = {"bonusType"};
	
	private static String[] SBonusConfig = {"isCounted"};
	
	private static String[] DupontCodeUploadLog = {"fileName"};
	
	private static String[] DupontPrimaryCode = {"primaryCode","dpCartoncode","bcsCartoncode"};
	
	private static String[] QualityInspection = {"mCode","batch","inspector","isQualified"}; 
	
	private static String[] ApplyQrCode = {"id"};
	
	private static String[] ApplyScratchCode = {"id"};
	
	private static String[] CartonSeqLog = {"organId"};
	
	private static String[] TransferLog = {"fileType","status"};
	
	private static String[] PopularProduct = {"auditStatus"};
	
	private static String[] ProductFeedback = {"auditStatus"};
	
	 
	private static HashMap mt = new HashMap(); 
	static 
	{
		// mt.put("AdsumGoods", AdsumGoods);
		mt.put("BarcodeUpload", BarcodeUpload);
		mt.put("BarcodeInventoryIdcode", BarcodeInventoryIdcode);
		mt.put("Region", Region);
		mt.put("AmountInventory", AmountInventory);
		mt.put("RegionUsers",RegionUsers);
		mt.put("RegionArea", RegionArea);
		mt.put("ReceiveIncomeIdcode", ReceiveIncomeIdcode);
		mt.put("ReceiveIncome", ReceiveIncome);
		mt.put("Affiche", Affiche);
		mt.put("AfficheBrowse", AfficheBrowse);
		mt.put("AlterMoveApply", AlterMoveApply);
		mt.put("ApproveFlow", ApproveFlow);
		mt.put("ApproveFlowDetail", ApproveFlowDetail);
		mt.put("ApproveFlowLog", ApproveFlowLog);
		mt.put("Assemble", Assemble);
		mt.put("AssembleRelation", AssembleRelation);
		mt.put("AssembleRelationDetail", AssembleRelationDetail);
		mt.put("BorrowIncome", BorrowIncome);
		mt.put("CalendarAwake", CalendarAwake);
		mt.put("CallCenterEvent", CallCenterEvent);
		mt.put("CallCenterMsg", CallCenterMsg);
		mt.put("CashBank", CashBank);
		mt.put("CashBankAdjust", CashBankAdjust);
		mt.put("CashWasteBook", CashWasteBook);
		mt.put("CodeUnit", CodeUnit);
		mt.put("ConsignMachin", ConsignMachin);
		mt.put("CIntegral", CIntegral);
		mt.put("CIntegralDeal", CIntegralDeal);
		mt.put("ContactLog", ContactLog);
		mt.put("ContactDitch", ContactDitch);
		mt.put("CorrelationDocument", CorrelationDocument);
		mt.put("CountryArea", CountryArea);
		mt.put("Customer", Customer);
		mt.put("CustomerUsers", CustomerUsers);
		mt.put("CustomerSort", CustomerSort);
		mt.put("DemandPrice", DemandPrice);
		mt.put("DemandPriceDetail", DemandPriceDetail);
		mt.put("Dept", Dept);
		mt.put("Ditch", Ditch);
		mt.put("GatherNotify", GatherNotify);
		mt.put("DrawShipmentBill", DrawShipmentBill);
		mt.put("DrawShipmentBillDetail", DrawShipmentBillDetail);
		mt.put("DrawShipmentBillIdcode", DrawShipmentBillIdcode);
		mt.put("FeeWasteBook", FeeWasteBook);
		mt.put("FleeProduct", FleeProduct);
		mt.put("Hap", Hap);
		mt.put("HarmShipmentBill", HarmShipmentBill);
		mt.put("HarmShipmentBillDetail", HarmShipmentBillDetail);
		mt.put("ICode", ICode);
		mt.put("Idcode", Idcode);
		mt.put("IdcodeUpload", IdcodeUpload);
		mt.put("IdcodeReset", IdcodeReset);
		mt.put("IncomeLog", IncomeLog);
		mt.put("IntegralRule", IntegralRule);
		mt.put("IntegralOrder", IntegralOrder);
		mt.put("IntegralOrderDetail", IntegralOrderDetail);
		mt.put("IntegralI", IntegralI);
		mt.put("IntegralO", IntegralO);
		mt.put("IntegralDetail", IntegralDetail);
		// mt.put("IncomeLogApprove", IncomeLogApprove);
		mt.put("Largess", Largess);
		mt.put("Linkman", Linkman);
		mt.put("Loan", Loan);
		mt.put("LoanObject", LoanObject);
		mt.put("LoanOut", LoanOut);
		mt.put("MakeConf", MakeConf);
		mt.put("Menu", Menu);
		mt.put("MoveCanuseOrgan", MoveCanuseOrgan);
		mt.put("MobileArea", MobileArea);
		mt.put("ObjIntegral", ObjIntegral);
		mt.put("OIntegralDeal", OIntegralDeal);
		mt.put("OIntegralSett", OIntegralSett);
		mt.put("Olinkman", Olinkman);
		mt.put("Operate", Operate);
		mt.put("Organ", Organ);
		mt.put("OrganAwake", OrganAwake);
		mt.put("OrganScan", OrganScan);
		mt.put("OrganGrade", OrganGrade);
		mt.put("OrganPrice", OrganPrice);
		mt.put("OrganPriceii", OrganPriceii);
		mt.put("OrganProduct", OrganProduct);
		mt.put("OrganRole", OrganRole);
		mt.put("OtherIncome", OtherIncome);
		mt.put("OtherIncomeDetail", OtherIncomeDetail);
		mt.put("OtherIncomeIdcode", OtherIncomeIdcode);
		mt.put("OtherShipmentBill", OtherShipmentBill);
		mt.put("OtherShipmentBillDetail", OtherShipmentBillDetail);
		mt.put("OtherShipmentBillIdcode", OtherShipmentBillIdcode);
		mt.put("Outlay", Outlay);
		mt.put("OutlayDetail", OutlayDetail);
		mt.put("Pact", Pact);
		mt.put("Payable", Payable);
		mt.put("PayableDetail", PayableDetail);
		mt.put("PayableObject", PayableObject);
		mt.put("PaymentApply", PaymentApply);
		mt.put("PaymentLog", PaymentLog);
		mt.put("PaymentLogDetail", PaymentLogDetail);
		mt.put("PaymentMode", PaymentMode);
		mt.put("Paysrc", Paysrc);
		mt.put("PhoneBook", PhoneBook);
		mt.put("Doc", Doc);
		mt.put("PhoneBookSort", PhoneBookSort);
		mt.put("DocSort", DocSort);
		mt.put("Product", Product);
		mt.put("ProductRedeploy", ProductRedeploy);
		mt.put("ProductRedeployDetail", ProductRedeployDetail);
		mt.put("ProductHistory", ProductHistory);
		mt.put("ProductIncome", ProductIncome);
		mt.put("ProductIncomeDetail", ProductIncomeDetail);
		mt.put("ProductIncomeIdcode", ProductIncomeIdcode);
		mt.put("ProductProperty", ProductProperty);
		mt.put("ProductStockpileAll", ProductStockpileAll);
		mt.put("ProductStockpile", ProductStockpile);
		mt.put("ProductStruct", ProductStruct);
		mt.put("Project", Project);
		mt.put("Provider", Provider);
		mt.put("Plinkman", Plinkman);
		mt.put("Role", Role);
		mt.put("MemberGrade", MemberGrade);
		mt.put("MemberGradeRule", MemberGradeRule);
		mt.put("MoveApply", MoveApply);
		mt.put("ProviderProduct", ProviderProduct);
		mt.put("PurchaseBill", PurchaseBill);
		mt.put("PurchaseBillDetail", PurchaseBillDetail);
		// mt.put("PurchaseBillApprove", PurchaseBillApprove);
		mt.put("PurchaseOrder", PurchaseOrder);
		mt.put("PurchaseOrderDetail", PurchaseOrderDetail);
		// mt.put("PurchaseOrderApprove", PurchaseOrderApprove);
		mt.put("PurchaseIncome", PurchaseIncome);
		mt.put("PurchaseIncomeDetail", PurchaseIncomeDetail);
		mt.put("PurchaseIncomeIdcode", PurchaseIncomeIdcode);
		mt.put("PurchaseInquire", PurchaseInquire);
		mt.put("PurchaseInvoice", PurchaseInvoice);
		mt.put("PurchasePlan", PurchasePlan);
		mt.put("PurchasePlanDetail", PurchasePlanDetail);
		mt.put("PurchaseTrades", PurchaseTrades);
		mt.put("PurchaseTradesDetail", PurchaseTradesDetail);
		mt.put("PurchaseTradesIdcode", PurchaseTradesIdcode);
		mt.put("PurchaseWithdraw", PurchaseWithdraw);
		mt.put("PurchaseWithdrawDetail", PurchaseWithdrawDetail);
		mt.put("ReceivableObject", ReceivableObject);
		mt.put("Receivable", Receivable);
		mt.put("ReceivableDetail", ReceivableDetail);
		// mt.put("ReceivableApprove", ReceivableApprove);
		mt.put("Reckoning", Reckoning);
		mt.put("Retail", Retail);
		mt.put("RetailDetail", RetailDetail);
		mt.put("SaleInvoice", SaleInvoice);
		mt.put("SaleIndent", SaleIndent);
		mt.put("SaleIndentDetail", SaleIndentDetail);
		mt.put("SaleOrder", SaleOrder);
		mt.put("SaleOrderDetail", SaleOrderDetail);
		mt.put("VocationOrder", VocationOrder);
		mt.put("VocationOrderDetail", VocationOrderDetail);
		mt.put("PeddleOrder", PeddleOrder);
		mt.put("PeddleOrderDetail", PeddleOrderDetail);
		// mt.put("SaleOrderApprove", SaleOrderApprove);
		mt.put("SaleRepair", SaleRepair);
		mt.put("SaleTrades", SaleTrades);
		mt.put("SaleTradesDetail", SaleTradesDetail);
		mt.put("SaleTradesIdcode", SaleTradesIdcode);
		mt.put("SendTime", SendTime);
		mt.put("SampleBill", SampleBill);
		// mt.put("SampleBillApprove", SampleBillApprove);
		mt.put("SampleBillDetail", SampleBillDetail);
		mt.put("ServiceAgreement", ServiceAgreement);
		mt.put("ServiceDetail", ServiceDetail);
		mt.put("ServiceExecute", ServiceExecute);
		mt.put("ShipmentBill", ShipmentBill);
		mt.put("ShipmentBillDetail", ShipmentBillDetail);
		mt.put("StockCheck", StockCheck);
		mt.put("StockCheckDetail", StockCheckDetail);
		mt.put("StockCheckIdcode", StockCheckIdcode);
		mt.put("StockMove", StockMove);
		mt.put("StockMoveDetail", StockMoveDetail);
		mt.put("StockMoveIdcode", StockMoveIdcode);
		mt.put("StockAlterMove", StockAlterMove);
		mt.put("StockAlterMoveDetail", StockAlterMoveDetail);
		mt.put("StockAlterMoveIdcode", StockAlterMoveIdcode);
		mt.put("ProductInterconvert", ProductInterconvert);
		mt.put("ProductInterconvertDetail", ProductInterconvertDetail);
		mt.put("ProductInterconvertIdcode", ProductInterconvertIdcode);
		// mt.put("StockMoveApprove", StockMoveApprove);
		mt.put("StockWasteBook", StockWasteBook);
		mt.put("StuffShipmentBill", StuffShipmentBill);
		mt.put("StuffShipmentBillDetail", StuffShipmentBillDetail);
		// mt.put("StuffShipmentBillApprove", StuffShipmentBillApprove);
		mt.put("Suit", Suit);
		mt.put("TakeBill", TakeBill);
		mt.put("TakeTicket", TakeTicket);
		mt.put("TakeTicketDetail", TakeTicketDetail);
		mt.put("TakeTicketIdcode", TakeTicketIdcode);
		mt.put("Task", Task);
		// mt.put("Teardown", Teardown);
		mt.put("TaskExecute", TaskExecute);
		mt.put("UserArea", UserArea);
		mt.put("UserLog", UserLog);
		mt.put("UserRole", UserRole);
		mt.put("Users", Users);
		mt.put("UserVisit", UserVisit);
		mt.put("Warehouse", Warehouse);
		mt.put("WarehouseBit", WarehouseBit);
		mt.put("OrganAnnunciator", OrganAnnunciator);
		mt.put("OrganSafetyIntercalate", OrganSafetyIntercalate);
		mt.put("WarehouseVisit", WarehouseVisit);
		mt.put("WebIndent", WebIndent);
		mt.put("Withdraw", Withdraw);
		mt.put("WithdrawDetail", WithdrawDetail);
		mt.put("WithdrawIdcode", WithdrawIdcode);
		mt.put("WorkReport", WorkReport);
		mt.put("WorkReportApprove", WorkReportApprove);
		mt.put("WorkReportExecute", WorkReportExecute);
		mt.put("Car", Car);
		mt.put("Equip", Equip);
		mt.put("EquipDetail", EquipDetail);
		mt.put("ProductPriceHistory", ProductPriceHistory);
		mt.put("OrganPriceHistory", OrganPriceHistory);
		mt.put("ViewCIntegralDeal", ViewCIntegralDeal);
		mt.put("ViewCIntegralTotal", ViewCIntegralTotal);
		mt.put("ViewCIntegralDetail", ViewCIntegralDetail);
		mt.put("ViewCIntegralDuihuanDetail", ViewCIntegralDuihuanDetail);
		mt.put("ViewCIntegralDuihuanTotal", ViewCIntegralDuihuanTotal);
		mt.put("ViewOIntegralDeal", ViewOIntegralDeal);
		mt.put("ViewOIntegralTotal", ViewOIntegralTotal);
		mt.put("Msg", Msg);
		mt.put("MsgTemplate", MsgTemplate);
		mt.put("Repository", Repository);
		mt.put("AdjustCIntegral", AdjustCIntegral);
		mt.put("AdjustOIntegral", AdjustOIntegral);
		mt.put("ViewSaleProductTotal", ViewSaleProductTotal);
		mt.put("ViewSaleCustomerTotal", ViewSaleCustomerTotal);
		mt.put("ViewSaleBill", ViewSaleBill);
		mt.put("ViewWithdrawProduct", ViewWithdrawProduct);

		mt.put("Questions", Questions);
		mt.put("Respond", Respond);
		mt.put("SaleForecast", SaleForecast);
		mt.put("SupplySaleMove", SupplySaleMove);
		mt.put("SupplySaleMoveDetail", SupplySaleMoveDetail);
		mt.put("SupplySaleMoveIdcode", SupplySaleMoveIdcode);
		mt.put("SupplySaleApply", SupplySaleApply);
		mt.put("OrganInvoice", OrganInvoice);
		mt.put("OrganWithdraw", OrganWithdraw);
		mt.put("OrganWithdrawDetail", OrganWithdrawDetail);
		mt.put("OrganWithdrawIdcode", OrganWithdrawIdcode);
		mt.put("OrganTrades", OrganTrades);
		mt.put("OrganTradesDetail", OrganTradesDetail);
		mt.put("OrganTradesPIdcode", OrganTradesPIdcode);
		mt.put("OrganTradesTIdcode", OrganTradesTIdcode);
		mt.put("UploadProducReport", UploadProducReport);
		mt.put("ViewWlmIdcode", ViewWlmIdcode);
		mt.put("WlmIdcodeLog", WlmIdcodeLog);
		mt.put("UploadPrLog", UploadPrLog);
		
		mt.put("TakeTicketDetailBatchBit", TakeTicketDetailBatchBit);
		mt.put("SuggestInspect", SuggestInspect);
		mt.put("SuggestInspectDetail", SuggestInspectDetail);
		mt.put("CustomerMatchOrder", CustomerMatchOrder);
		
		mt.put("OtherIncomeAll", OtherIncomeAll);
		mt.put("OtherIncomeDetailAll", OtherIncomeDetailAll);
		
		mt.put("OtherShipmentBillAll", OtherShipmentBillAll);
		mt.put("OtherShipmentBillDAll", OtherShipmentBillDAll);
		mt.put("BarcodeInventory", BarcodeInventory);
		mt.put("PurchaseSalesReport", PurchaseSalesReport);
		mt.put("InventoryReport", InventoryReport);
		mt.put("StockPileAgeing", StockPileAgeing);
		
		mt.put("PrintJob", PrintJob);
		mt.put("UploadSAPLog", UploadSAPLog);
		mt.put("CartonCode", CartonCode);
		mt.put("Notification", Notification);
		mt.put("Invoice", Invoice);
		mt.put("AppUpdate", AppUpdate);
		mt.put("Query", Query);
		mt.put("CovertUploadReport", CovertUploadReport);
		mt.put("CovertErrorLog", CovertErrorLog);
		mt.put("IdcodeUploadLog", IdcodeUploadLog);
		mt.put("Sms", Sms);
		mt.put("TrackApply", TrackApply);
		mt.put("AppUpdateLog", AppUpdateLog);
		mt.put("InventoryUploadLog",InventoryUploadLog);
		mt.put("BillImportConfig",BillImportConfig);
		mt.put("ProductConfig",ProductConfig);
		mt.put("UnitInfo",UnitInfo);
		mt.put("ProductPlan",ProductPlan);
		mt.put("SuggestionBox",SuggestionBox);
		mt.put("Wlinkman",Wlinkman);
		
		mt.put("PackSeparate",PackSeparate);
		
		mt.put("Member",Member);
		
		mt.put("OrganUploadLog",OrganUploadLog);
		
		mt.put("SBonusSetting",SBonusSetting);
		
		mt.put("SBonusAppraise",SBonusAppraise);
		mt.put("SBonusAccount",SBonusAccount);
		mt.put("SBonusDetail",SBonusDetail);
		
		mt.put("STransferRelation",STransferRelation);
		mt.put("SBonusTarget",SBonusTarget);
		
		mt.put("SBonusLog",SBonusLog);
		mt.put("SBonusConfig",SBonusConfig);
		
		mt.put("DupontCodeUploadLog",DupontCodeUploadLog);
		
		mt.put("DupontPrimaryCode",DupontPrimaryCode);
		mt.put("UserApply",UserApply); 
		mt.put("UploadProduceLog",UploadProduceLog);
		mt.put("UploadCartonSeqLog",UploadCartonSeqLog);
		mt.put("QualityInspection",QualityInspection);
		mt.put("ApplyQrCode",ApplyQrCode);
		mt.put("CartonSeqLog",CartonSeqLog);
		mt.put("TransferLog",TransferLog);
		mt.put("PopularProduct",PopularProduct);
		
		mt.put("ProductFeedback",ProductFeedback);
		mt.put("ApplyScratchCode",ApplyScratchCode);
	}

	public static int getCount(String queryStr)
	{
		Session session = null;
		int count = 0;
		try
		{
			session = HibernateUtil.currentSession();
			count = ((Number)session.createQuery(queryStr).iterate().next()).intValue();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			count = 0;
		}

		return count;
	}

	
	public static int getRecordCount(String queryStr)
	{
		Session session = null;
		int rt = 0;
		try
		{
			session = HibernateUtil.currentSession();
			List list = session.createQuery(queryStr).list();
			if (list != null && !list.isEmpty())
			{
				Object o = list.get(0);
				if (o != null)
				{
					rt = Integer.parseInt(o.toString());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			rt = 0;
		}

		return rt;
	}
	
	public static int getRecordCountBySql(String queryStr, Map<String,Object> param)
	{
		Session session = null;
		int rt = 0;
		try
		{
			session = HibernateUtil.currentSession();
			Query query = session.createSQLQuery(queryStr);
			DbUtil.setParamForSql(param, query);
			Object o = query.uniqueResult();
			if (o != null)
			{
				rt = Integer.parseInt(o.toString());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			rt = 0;
		}

		return rt;
	}
	
	public static int getRecordCountBySql(String queryStr)
	{
		Session session = null;
		int rt = 0;
		try
		{
			session = HibernateUtil.currentSession();
			Object o = session.createSQLQuery(queryStr).uniqueResult();
			if (o != null)
			{
				rt = Integer.parseInt(o.toString());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			rt = 0;
		}

		return rt;
	}
	
	public static String getStringBySql(String queryStr)
	{
		Session session = null;
		String rt = "";
		try
		{
			session = HibernateUtil.currentSession();
			Object o = session.createSQLQuery(queryStr).uniqueResult();
			if (o != null)
			{
				rt = o.toString();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return rt;
	}


	public static int getRecordSum(String queryStr)
	{
		Session session = null;
		int rt = 0;
		try
		{
			session = HibernateUtil.currentSession();
			List list = session.createQuery(queryStr).list();
			if (list != null && !list.isEmpty())
			{
				Object o = list.get(0);
				if (o != null)
				{
					rt = Integer.parseInt(o.toString());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			rt = 0;
		}

		return rt;
	}

	/**
	 * 得到所有数字的∑
	 * @param queryStr 查询语句
	 * @return 总数量合
	 */
	public static double getdoubleSum(String queryStr)
	{
		Session session = null;
		double rt = 0.00;
		try
		{
			session = HibernateUtil.currentSession();
			List list = session.createQuery(queryStr).list();
			if (list != null && !list.isEmpty())
			{
				//得到第一条数据的数量
				Object o = list.get(0);
				if (o != null)
				{
					rt = Double.parseDouble(o.toString());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			rt = 0.00;
		}
		return rt;
	}

	/**
	 * 得到所有数字的∑
	 * @param queryStr 查询语句
	 * @return 总数量合
	 */
	public static double getdoubleSum2(String queryStr)
	{
		Session session = null;
		double rt = 0.00;
		try
		{
			session = HibernateUtil.currentSession();
			List list = session.createQuery(queryStr).list();
			if (list != null && !list.isEmpty())
			{
				//循环累加
				for (Object o : list)
				{
					if (o != null)
					{
						rt += Double.parseDouble(o.toString());
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			rt = 0.00;
		}
		return rt;
	}

	public static String getString(String queryStr)
	{
		Session session = null;
		String rt = "0";
		try
		{
			session = HibernateUtil.currentSession();
			// System.out.println("queryStr:"+queryStr+" Result
			// is:"+session.iterate(queryStr).next());
			Object obj = session.createQuery(queryStr).iterate().hasNext() ? session.createQuery(queryStr).iterate().next() : null; // .iterate(queryStr);

			if (obj != null)
			{
				rt = obj.toString();
			}
			else
			{
				rt = "";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			rt = "0";
		}

		return rt;
	}

	public static List getAllByHql(String queryStr, int targetPage, int pagesize)
	{
		List list = new ArrayList();
		Session session = null;

		if (targetPage <= 0 && targetPage != -1 || pagesize < 1)
			return list;
		try
		{
			session = HibernateUtil.currentSession();
			Query query = session.createQuery(queryStr);
			if (targetPage != -1)
			{
				query.setFirstResult((targetPage - 1) * pagesize);
				query.setMaxResults(pagesize);
			}
			list = query.list();

		}
		catch(Exception e)
		{
			// log.error(e.getMessage());
			e.printStackTrace();
			logger.error("",e);

		}

		return list;
	}

	public static List getAllByHqlReadOnly(String query)
	{
		List list = getAllByHql(query, -1);
		for(Object obj : list){
			if(obj != null){
				//将对像从缓存中去除
				HibernateUtil.currentSession().evict(obj);
			}
		}
		return list;
	}
	
	public static List getAllByHql(String query)
	{
		return getAllByHql(query, -1);
	}

	public static List getAllByHql(String queryStr, int targetPage)
	{
		return getAllByHql(queryStr, targetPage, RECORDS_PER_PAGE);
	}
	
	/**
	 * findByWhereSql
	 * @author jason.huang
	 * @param queryStr
	 * @param pageSize
	 * @return list
	 * 
	 */
	public static List findByWhereSql(String queryStr) throws Exception
	{
		List list = new ArrayList();
		Session session = null;
		try
		{
			session = HibernateUtil.currentSession();
			Query query = session.createQuery(queryStr);
			list = query.list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("PageQuery getPageList error:" + e.getMessage());
		}

		return list;
	}
	
	
	public static int getPages(String queryStr, int pageSize)
	{

		int length = queryStr.indexOf("order by") > 0 ? queryStr.indexOf("order by") : queryStr.length();
		String hql = "select count(*) " + queryStr.substring(queryStr.indexOf("from"), length);
		int pages = -1;

		Session session = null;
		try
		{
			session = HibernateUtil.currentSession();

			int size = ((Integer) session.createQuery(hql).iterate().next()).intValue();
			pages = (size / pageSize) + ((size % pageSize) > 0 ? 1 : 0);
			// HibernateUtil.closeSession();
		}
		catch(Exception e)
		{
			// log.error(e.getMessage());
			e.printStackTrace();
		}

		return pages;
	}

	public static int[] getPagesAndRecordcount(String queryStr, int pageSize)
	{
		int length = queryStr.indexOf("order by") > 0 ? queryStr.indexOf("order by") : queryStr.length();
		String hql = "select count(*) " + queryStr.substring(queryStr.indexOf("from"), length);
		int[] retValue = new int[2];
		
		Session session = null;
		try
		{
			session = HibernateUtil.currentSession();

			Iterator it = session.createQuery(hql).iterate();
			int size = 0;
			if (it.hasNext())
			{
				Object ob = it.next();
				if (ob != null)
				{
					size = Integer.parseInt(ob.toString());
				}
			}
			retValue[0] = size;
			retValue[1] = (size / pageSize) + ((size % pageSize) > 0 ? 1 : 0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return retValue;
	}

	/*
	 * //带distinct特殊记录 public static int[] getExtraPagesAndRecordcount(String queryStr, int pageSize,String fieldName) { int length =
	 * queryStr.indexOf("order by") > 0 ? queryStr.indexOf("order by") : queryStr.length(); String hql = "select count(distinct("+fieldName+")) " +
	 * queryStr.substring(queryStr.indexOf("from"), length); int[] retValue = new int[2];
	 * 
	 * Session session = null; try { session = HibernateUtil.currentSession(); int size = ( (Integer) session.iterate(hql).next()).intValue(); retValue[0] =
	 * size; retValue[1] = (size / pageSize) + ( (size % pageSize) > 0 ? 1 : 0); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return retValue; }
	 */

	@SuppressWarnings("deprecation")
	public static int getRecordCountQuery(String sql) throws HibernateException, SQLException
	{
		Session session;
		Statement s = null;
		ResultSet st = null;
		int result = 0;
		try
		{
			session = HibernateUtil.currentSession();

			s = session.connection().createStatement();
			st = s.executeQuery(sql);
			while (st.next())
			{
				result = Integer.parseInt(st.getString(1));
			}
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			st.close();
		}
		return result;
	}

	public static int getPagesOfGroupBy(String queryStr, int pageSize)
	{
		// String hql="select count(*)
		// "+queryStr.substring(queryStr.indexOf("from"),queryStr.indexOf("order
		// by"));
		int pages = -1;

		List lst = getAllByHql(queryStr);
		if (lst != null)
		{
			int size = lst.size();
			pages = (size / pageSize) + ((size % pageSize) > 0 ? 1 : 0);
		}

		return pages;
	}

	public static List getTopObjects(String queryStr, int n)
	{
		return getAllByHql(queryStr, 1, n);
	}

	public static int getPages(String queryStr)
	{

		return getPages(queryStr, RECORDS_PER_PAGE);
	}

	public static boolean getResultByNativeSql(String sql){
		Session session;
		try {
			session = HibernateUtil.currentSession(true);
			Object obj = session.createSQLQuery(sql).uniqueResult();
			if(obj!=null){
				return true;
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static void updateOrdelete(String hql) throws HibernateException, Exception, SQLException
	{
		Session session;
		Statement s = null;
		try
		{
			session = HibernateUtil.currentSession(true);
			try
			{
				s = session.connection().createStatement();
				s.executeUpdate(hql);
				session.flush();
			}
			catch(HibernateException ex)
			{
				ex.printStackTrace();
				throw ex;
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();
				throw ex;
			}

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}finally{
			if (s != null)
			{
				s.close();
			}
		}

	}
	
	@SuppressWarnings("deprecation")
	public static void updateOrdeleteClose(String hql) throws HibernateException, Exception, SQLException
	{
		Session session;
		Statement s = null;
		try
		{
			session = HibernateUtil.currentSession(true);
			try
			{
				s = session.connection().createStatement();
				s.executeUpdate(hql);
				session.flush();
			}
			catch(HibernateException ex)
			{
				ex.printStackTrace();
				throw ex;
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();
				throw ex;
			}

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			s.close();
		}
		

	}
	
	public static int executeUpdate(String hql) throws HibernateException, Exception, SQLException
	{
		Session session;
        int i;
		try
		{
			session = HibernateUtil.currentSession(true);
			try
			{
				Query query = session.createSQLQuery(hql);
				i = query.executeUpdate();
			}
			catch(HibernateException ex)
			{
				ex.printStackTrace();
				throw ex;
			}

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		return i;

	}

	@SuppressWarnings("deprecation")
	public static ResultSet query(String sql) throws HibernateException, SQLException
	{
		Session session;
		Statement s = null;
		ResultSet result;
		try
		{
			session = HibernateUtil.currentSession();

			s = session.connection().createStatement();
			result = s.executeQuery(sql);
			result.next();

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public static ResultSet query2(String sql) throws HibernateException, SQLException
	{
		Session session;
		Statement s = null;
		ResultSet result;
		try
		{
			session = HibernateUtil.currentSession();

			s = session.connection().createStatement();
			result = s.executeQuery(sql);

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public static List jdbcquery(String sql) throws HibernateException, SQLException
	{
		Session session;
		Statement s = null;
		ResultSet result = null;
		List list = null;
		try
		{
			session = HibernateUtil.currentSession();
			s = session.connection().createStatement();
			result = s.executeQuery(sql);
			list = converResultSetToList(result);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (result != null)
			{
				result.close();
			}
			if (s != null)
			{
				s.close();
			}
		}
		return list;
	}

	private static List converResultSetToList(ResultSet rs) throws SQLException
	{
		List list = new ArrayList();
		ResultSetMetaData meteData = rs.getMetaData();
		rs.setFetchSize(50);
		int columnCount = meteData.getColumnCount();
		Map map = null;
		while (rs.next())
		{
			map = new HashMap();
			for (int i = 1; i <= columnCount; i++)
			{
				map.put(meteData.getColumnName(i).toLowerCase(), rs.getString(i));
			}
			list.add(map);
		}
		return list;
	}

	public static void update(Object o) throws HibernateException, SQLException
	{
		Session s;
		try
		{
			s = HibernateUtil.currentSession(true);
			s.update(o);
			s.flush();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void update2(Object o) throws HibernateException, SQLException
	{
		Session s;
		try
		{
			s = HibernateUtil.currentSession(true);
			s.update(o);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public static void update(Object[] obj) throws HibernateException
	{
		Session session;
		try
		{
			session = HibernateUtil.currentSession(true);
			for (int i = 0; i < obj.length; i++)
			{
				session.update(obj[i]);
			}
			session.flush();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void updateByBeach(List list) throws HibernateException
	{
		Session session;
		try
		{
			session = HibernateUtil.currentSession(true);
			
			for(int i=0;i<list.size();i++){
				Object obj=list.get(i);
				 session.update(obj);
			}
			session.flush();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public static void save(Object obj) throws HibernateException
	{
		Session session;
		try
		{
			session = HibernateUtil.currentSession(true);
			session.save(obj);
			session.flush();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}

	}
	
	public static void save3(Object obj) throws Exception
	{
		Session session;
	
		session = HibernateUtil.currentSession(true);
		session.save(obj);
	}

	public static void save(Object[] obj) throws HibernateException
	{
		Session session;
		try
		{
			session = HibernateUtil.currentSession(true);
			for (int i = 0; i < obj.length; i++)
			{
				session.save(obj[i]);
			}
			session.flush();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}

	}

	public static void saveOrUpdate(Object obj) throws HibernateException
	{
		Session session;
		try
		{
			session = HibernateUtil.currentSession(true);
			session.saveOrUpdate(obj);
			session.flush();
		}
		catch(HibernateException e)
		{

			// log.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}

	}

	public static Object load(Class entityName, Serializable id) throws HibernateException
	{
		Object obj = null;
		try
		{
			Session session = HibernateUtil.currentSession();
			obj = session.load(entityName, id);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}

		return obj;
	}

	public static void delete(Object obj) throws HibernateException
	{
		try
		{
			Session session = HibernateUtil.currentSession(true);
			session.delete(obj);
			session.flush();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}

	}

	public static Object find(String query) throws HibernateException
	{
		Object obj = null;
		try
		{
			Session session = HibernateUtil.currentSession();
			List list = session.createQuery(query).list();
			if (list.size() == 0)
			{
				return null;
			}
			obj = list.get(0);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}

		return obj;
	}
	
	public static Object findReadOnly(String query) throws HibernateException
	{
		Object object = find(query);
		if(object != null){
			//将对像从缓存中去除
			HibernateUtil.currentSession().evict(object);
		}
		return object;
	}

	public static void delete(String hql) throws HibernateException
	{
		try
		{
			Session session = HibernateUtil.currentSession(true);
			session.delete(hql);
			session.flush();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 主要功能：根据SQL语句删除对象
	 * @param hql 查询语句(批量删除语句)
	 * @throws HibernateException
	 */
	public static void deleteByHql(String hql) throws HibernateException
	{
		try
		{
			Session session = HibernateUtil.currentSession(true);
			Query q = session.createQuery(hql);
			// 先查出对应的数据
			List deleteList = q.list();
			// 遍历删除
			if (deleteList.size() > 0)
			{
				for (Object object : deleteList)
				{
					session.delete(object);
				}
			}
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
	}


	public static String getTmpWhereSql(Map map, Object[] tablename) throws Exception
	{
		StringBuffer buf = new StringBuffer("WHERE ");
		try
		{
			Map tmpMap = scatterMap(map);

			for (int j = 0; j < tablename.length; j++)
			{
				String[] tmpArray = (String[]) mt.get(tablename[j]);
				for (int i = 0; i < tmpArray.length; i++)
				{
					String tmpColumnName = tmpArray[i];
					if (map.containsKey(tmpColumnName))
					{
						Object tmpObjVal = tmpMap.get(tmpColumnName);
						String tmpValue = null;

						if (!(tmpObjVal instanceof String))
						{
							tmpValue = "" + tmpObjVal;
						}
						else
						{
							tmpValue = (String) tmpObjVal;
						}
						if (tmpValue != null && (tmpValue.trim().length() > 0) && !tmpValue.trim().equals("all"))
						{
							// buf.append("(");
							buf.append(tmpColumnName + "='" + tmpValue.trim() + "'");
							// buf.append(")");
							buf.append(" and ");
						}
						else
						{
							tmpValue = null;
						}
					}
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		String bufStr = buf.toString();
		return bufStr.toLowerCase();
	}
	
	public static String getTmpWhereSqlForHql(Map map, Object[] tablename, Map<String, Object> param) throws Exception
	{
		StringBuffer buf = new StringBuffer("WHERE ");
		try
		{
			Map tmpMap = scatterMap(map);

			for (int j = 0; j < tablename.length; j++)
			{
				String[] tmpArray = (String[]) mt.get(tablename[j]);
				for (int i = 0; i < tmpArray.length; i++)
				{
					String tmpColumnName = tmpArray[i];
					if (map.containsKey(tmpColumnName))
					{
						Object tmpObjVal = tmpMap.get(tmpColumnName);
						String tmpValue = null;

						if (!(tmpObjVal instanceof String))
						{
							tmpValue = "" + tmpObjVal;
						}
						else
						{
							tmpValue = (String) tmpObjVal;
						}
						if (tmpValue != null && (tmpValue.trim().length() > 0) && !tmpValue.trim().equals("all"))
						{
							// buf.append("(");
//							buf.append(tmpColumnName + "='" + tmpValue.trim() + "'");
							buf.append(tmpColumnName + "=:" + tmpColumnName);
							param.put(tmpColumnName.toLowerCase(), tmpValue.trim());
							// buf.append(")");
							buf.append(" and ");
						}
						else
						{
							tmpValue = null;
						}
					}
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		String bufStr = buf.toString();
		return bufStr.toLowerCase();
	}
	
	public static String getTmpWhereSqlForSql(Map map, Object[] tablename, Map<String, Object> param) throws Exception
	{
		StringBuffer buf = new StringBuffer("WHERE ");
		try
		{
			Map tmpMap = scatterMap(map);

			for (int j = 0; j < tablename.length; j++)
			{
				String[] tmpArray = (String[]) mt.get(tablename[j]);
				for (int i = 0; i < tmpArray.length; i++)
				{
					String tmpColumnName = tmpArray[i];
					if (map.containsKey(tmpColumnName))
					{
						Object tmpObjVal = tmpMap.get(tmpColumnName);
						String tmpValue = null;

						if (!(tmpObjVal instanceof String))
						{
							tmpValue = "" + tmpObjVal;
						}
						else
						{
							tmpValue = (String) tmpObjVal;
						}
						if (tmpValue != null && (tmpValue.trim().length() > 0) && !tmpValue.trim().equals("all"))
						{
							// buf.append("(");
//							buf.append(tmpColumnName + "='" + tmpValue.trim() + "'");
							buf.append(tmpColumnName + "=?");
							param.put(tmpColumnName.toLowerCase(), tmpValue.trim());
							// buf.append(")");
							buf.append(" and ");
						}
						else
						{
							tmpValue = null;
						}
					}
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		String bufStr = buf.toString();
		return bufStr.toLowerCase();
	}
	
	public static String getTmpWhereSqlAlias(Map map, Object[] tablename,Object[] alias) throws Exception
	{
		StringBuffer buf = new StringBuffer("WHERE ");
		try
		{
			Map tmpMap = scatterMap(map);

			for (int j = 0; j < tablename.length; j++)
			{
				String[] tmpArray = (String[]) mt.get(tablename[j]);
				for (int i = 0; i < tmpArray.length; i++)
				{
					String tmpColumnName = tmpArray[i];
					if (map.containsKey(tmpColumnName))
					{
						Object tmpObjVal = tmpMap.get(tmpColumnName);
						String tmpValue = null;

						if (!(tmpObjVal instanceof String))
						{
							tmpValue = "" + tmpObjVal;
						}
						else
						{
							tmpValue = (String) tmpObjVal;
						}
						if (tmpValue != null && (tmpValue.trim().length() > 0) && !tmpValue.trim().equals("all"))
						{
							// buf.append("(");
							buf.append(alias[j] + "." +tmpColumnName + "='" + tmpValue.trim() + "'");
							// buf.append(")");
							buf.append(" and ");
						}
						else
						{
							tmpValue = null;
						}
					}
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		String bufStr = buf.toString();
		return bufStr.toLowerCase();
	}
	public static String getTmpWhereSql2(Map map, Object[] tablename) throws Exception
	{
		StringBuffer buf = new StringBuffer("WHERE ");
		try
		{
			Map tmpMap = scatterMap(map);

			for (int j = 0; j < tablename.length; j++)
			{
				String[] tmpArray = (String[]) mt.get(tablename[j]);
				for (int i = 0; i < tmpArray.length; i++)
				{
					String tmpColumnName = tmpArray[i];
					if (map.containsKey(tmpColumnName))
					{
						Object tmpObjVal = tmpMap.get(tmpColumnName);
						String tmpValue = null;

						if (!(tmpObjVal instanceof String))
						{
							tmpValue = "" + tmpObjVal;
						}
						else
						{
							tmpValue = (String) tmpObjVal;
						}
						if (tmpValue != null && (tmpValue.trim().length() > 0) && !tmpValue.trim().equals("all"))
						{
							// buf.append("(");
							buf.append(tmpColumnName + "='" + tmpValue.trim() + "'");
							// buf.append(")");
							buf.append(" and ");
						}
						else
						{
							tmpValue = null;
						}
					}
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		String bufStr = buf.toString();
		return bufStr;
	}
	
	public static String getTmpWhereSql2ForSql(Map map, Object[] tablename, Map<String,Object> param) throws Exception
	{
		StringBuffer buf = new StringBuffer("WHERE ");
		try
		{
			Map tmpMap = scatterMap(map);

			for (int j = 0; j < tablename.length; j++)
			{
				String[] tmpArray = (String[]) mt.get(tablename[j]);
				for (int i = 0; i < tmpArray.length; i++)
				{
					String tmpColumnName = tmpArray[i];
					if (map.containsKey(tmpColumnName))
					{
						Object tmpObjVal = tmpMap.get(tmpColumnName);
						String tmpValue = null;

						if (!(tmpObjVal instanceof String))
						{
							tmpValue = "" + tmpObjVal;
						}
						else
						{
							tmpValue = (String) tmpObjVal;
						}
						if (tmpValue != null && (tmpValue.trim().length() > 0) && !tmpValue.trim().equals("all"))
						{
							// buf.append("(");
							buf.append(tmpColumnName + "=?");
							param.put(tmpColumnName, tmpValue.trim());
							// buf.append(")");
							buf.append(" and ");
						}
						else
						{
							tmpValue = null;
						}
					}
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		String bufStr = buf.toString();
		return bufStr;
	}
	
	public static String getTmpWhereSql2ForHql(Map map, Object[] tablename, Map<String,Object> param) throws Exception
	{
		StringBuffer buf = new StringBuffer("WHERE ");
		try
		{
			Map tmpMap = scatterMap(map);

			for (int j = 0; j < tablename.length; j++)
			{
				String[] tmpArray = (String[]) mt.get(tablename[j]);
				for (int i = 0; i < tmpArray.length; i++)
				{
					String tmpColumnName = tmpArray[i];
					if (map.containsKey(tmpColumnName))
					{
						Object tmpObjVal = tmpMap.get(tmpColumnName);
						String tmpValue = null;

						if (!(tmpObjVal instanceof String))
						{
							tmpValue = "" + tmpObjVal;
						}
						else
						{
							tmpValue = (String) tmpObjVal;
						}
						if (tmpValue != null && (tmpValue.trim().length() > 0) && !tmpValue.trim().equals("all"))
						{
							// buf.append("(");
							buf.append(tmpColumnName + "=:" + tmpColumnName);
							param.put(tmpColumnName, tmpValue.trim());
							// buf.append(")");
							buf.append(" and ");
						}
						else
						{
							tmpValue = null;
						}
					}
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		String bufStr = buf.toString();
		return bufStr;
	}
	

	public static Map scatterMap(Map pOrgMap)
	{
		Map tmpRstMap = new HashMap();
		Set tmpKeySet = pOrgMap.keySet();
		for (Iterator tmpIt = tmpKeySet.iterator(); tmpIt.hasNext();)
		{
			Object tmpKey = tmpIt.next();
			Object tmpVal = pOrgMap.get(tmpKey);
			if (tmpVal instanceof Object[])
			{
				tmpVal = ((Object[]) tmpVal)[0];
			}
			tmpRstMap.put(tmpKey, tmpVal);
		}
		return tmpRstMap;
	}

	public static String getDiskAddress()
	{
		String da = "";
		try
		{
			// for(int i = 0; i < 9; i++) {
			JDiskSerial ds = JDiskSerial.get();
			// System.out.println(ds.disk(0));
			if (ds.disk(0).toString().indexOf(":") > 0)
			{
				da = ds.disk(0).getSerialNumber();
				// System.out.println("---"+disksn);
			}
			// }
		}
		catch(Exception e)
		{
			System.out.println("restart tomcat");
			e.printStackTrace();
		}
		return da;
	}

	public static String getMACAddress()
	{

		String address = "";
		String os = System.getProperty("os.name");
		if (os != null && os.startsWith("Windows"))
		{
			try
			{
				String command = "cmd.exe /c ipconfig /all";
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = br.readLine()) != null)
				{
					if (line.indexOf("Physical Address") > 0)
					{
						int index = line.indexOf(":");
						index += 2;
						address = line.substring(index);
						break;
					}
				}
				br.close();
				return address.trim();
			}
			catch(IOException e)
			{
			}
		}

		return address;
	}
	/**
	 * 查询指定条数的记录
	 * Create Time: Oct 12, 2011 2:56:17 PM 
	 * @param hql
	 * @param maxResults
	 * @return
	 * @author dufazuo
	 */
	public static List queryListWithCount(String hql, int maxResults)
	{
		List list = null;
		Session session = null;
		try
		{
			session = HibernateUtil.currentSession();
			Query query = session.createQuery(hql);
			query.setFirstResult(0);
			query.setMaxResults(maxResults);
			list = query.list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static Object queryResultByNativeSql(String sql){
		Session session;
		Object obj = null;
		try {
			session = HibernateUtil.currentSession(true);
			obj = session.createSQLQuery(sql).uniqueResult();
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
	@SuppressWarnings("deprecation")
	public static void updByJDBC(String sql) throws SQLException{
		Session session;
		Statement s = null;
		try
		{
			session = HibernateUtil.currentSession();
			s = session.connection().createStatement();
			s.executeUpdate(sql);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (s != null)
			{
				s.close();
			}
		}
	
	}
	
	
	public static void main(String[] args) {
		System.out.println(getDiskAddress());
	}
	
	public static void batchSave(Object obj) throws HibernateException
	{
		Session session;
		try
		{
			session = HibernateUtil.currentSession(true);
			List objList = (List)obj;
			for(int i = 0; i < objList.size(); i++) {
				session.save(objList.get(i));
				if(i % 100 == 0) {
					session.flush();
					session.clear();
				}
			}
			session.flush();
			session.clear();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}

	}


	public static void executeWork(Work work) {
		Session session;
		try
		{
			session = HibernateUtil.currentSession(true);
			try
			{
				session.doWork(work);
			}
			catch(HibernateException ex)
			{
				ex.printStackTrace();
				throw ex;
			}

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void executeBatch(List<String> batchSqls) {
		Session session;
		try
		{
			session = HibernateUtil.currentSession(true);
			try
			{
				Work work = getWork(batchSqls);
				session.doWork(work);
			}
			catch(HibernateException ex)
			{
				ex.printStackTrace();
				throw ex;
			}

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	private static Work getWork(final List<String> batchSqls) {
		Work work = new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				Statement statement = conn.createStatement();
				try {
					for(int i = 0; i < batchSqls.size(); i++) {
						statement.addBatch(batchSqls.get(i));
					}
					statement.executeBatch();
				} finally {
					if(statement!=null) {
						statement.close();
					}
				}
			}
		};  
		
		return work;
	}
	
	public static int[] executeBatchWithResult(List<String> batchSqls) throws SQLException {
		Session session;
		Statement s = null;
		session = HibernateUtil.currentSession(true);
		try
		{
			s = session.connection().createStatement();
			for(String sql : batchSqls) {
				s.addBatch(sql);
			}
			return s.executeBatch();
		}
		catch(HibernateException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if (s != null)
			{
				s.close();
			}
		}

	}


	public static Object findWithLock(String hql, String entity) {
		Object obj = null;
		try
		{
			Session session = HibernateUtil.currentSession();
			Query query = session.createQuery(hql);
			query.setLockMode(entity, LockMode.UPGRADE);
			List list = query.list();
			if (list.size() == 0)
			{
				return null;
			}
			obj = list.get(0);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}

		return obj;
	}
	
	public static long getRecordCountLongBySql(String queryStr)
	{
		Session session = null;
		long rt = 0;
		try
		{
			session = HibernateUtil.currentSession();
			Object o = session.createSQLQuery(queryStr).uniqueResult();
			if (o != null)
			{
				rt = Long.parseLong(o.toString());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			rt = 0;
		}

		return rt;
	}
	
	public static Object getRecordCountObjctBySql(String queryStr)
	{
		Session session = null;
		Object o = null;
		try
		{
			session = HibernateUtil.currentSession();
			o = session.createSQLQuery(queryStr).uniqueResult();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return o;
	}
	
	
	public static List getAllByHql(String hql, Map<String, Object> map) throws Exception
	{
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery(hql);
		//如果查询条件不为空则匹配查询条件
		if (null != map && map.size() > 0)
		{
			for (String key : map.keySet())
			{
				query.setParameter(key, map.get(key));
			}
		}
		return query.list();
	}
	
	public static List getAllMapListBySql(String hql, Map<String, Object> map) throws Exception
	{
		Session session = HibernateUtil.currentSession();
		Query query = session.createSQLQuery(hql);
		//如果查询条件不为空则匹配查询条件
		if (null != map && map.size() > 0)
		{
			int i = 0;
            Set<Map.Entry<String, Object>> set = map.entrySet();
            for (Map.Entry<String, Object> entry : set) {
                query.setParameter(i, entry.getValue());
                i++;
            }
		}
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	public static List getAllListBySql(String hql, Map<String, Object> map) throws Exception
	{
		Session session = HibernateUtil.currentSession();
		Query query = session.createSQLQuery(hql);
		//如果查询条件不为空则匹配查询条件
		if (null != map && map.size() > 0)
		{
			int i = 0;
            Set<Map.Entry<String, Object>> set = map.entrySet();
            for (Map.Entry<String, Object> entry : set) {
                query.setParameter(i, entry.getValue());
                i++;
            }
		}
		return query.list();
	}
	
	@SuppressWarnings("deprecation")
	public static List jdbcquery(String sql, Map<String, Object> map) throws HibernateException, SQLException
	{
		Session session;
		PreparedStatement s = null;
		ResultSet result = null;
		List list = null;
		try
		{
			session = HibernateUtil.currentSession();
			s = session.connection().prepareStatement(sql);
			DbUtil.setParamForSql(map, s);
			result = s.executeQuery();
			list = converResultSetToList(result);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (result != null)
			{
				result.close();
			}
			if (s != null)
			{
				s.close();
			}
		}
		return list;
	}
	
	private static List converResultSetToList(List<Map> rs) throws SQLException
	{
		while (rs.size()>0)
		{
			List list = new ArrayList();
			for (Map rsMap : rs)
			{
				Map map = new HashMap();
				for(Object key : rsMap.keySet()) {
					map.put(key.toString().toLowerCase(), rsMap.get(key));
				}
				list.add(map);
			}
			return list;
		}
		return rs;
	}
	
	public static int executeUpdateByHql(String hql, Map<String, Object> map) throws HibernateException, Exception, SQLException
	{
		Session session;
        int i;
		try
		{
			session = HibernateUtil.currentSession(true);
			try
			{
				Query query = session.createQuery(hql);
				//如果查询条件不为空则匹配查询条件
				if (null != map && map.size() > 0)
				{
		            Set<Map.Entry<String, Object>> set = map.entrySet();
		            for (Map.Entry<String, Object> entry : set) {
		                query.setParameter(entry.getKey(), entry.getValue());
		            }
				}
				i = query.executeUpdate();
			}
			catch(HibernateException ex)
			{
				ex.printStackTrace();
				throw ex;
			}

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		return i;

	}
	
	public static int executeUpdateBySql(String hql, Map<String, Object> map) throws HibernateException, Exception, SQLException
	{
		Session session;
        int i;
		try
		{
			session = HibernateUtil.currentSession(true);
			try
			{
				Query query = session.createSQLQuery(hql);
				if (null != map && map.size() > 0)
				{
					int index = 0;
		            Set<Map.Entry<String, Object>> set = map.entrySet();
		            for (Map.Entry<String, Object> entry : set) {
		                query.setParameter(index, entry.getValue());
		                index++;
		            }
				}
				i = query.executeUpdate();
			}
			catch(HibernateException ex)
			{
				ex.printStackTrace();
				throw ex;
			}

		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}
		return i;

	}
	
	public static Object find(String hql, Map<String, Object> map) throws HibernateException
	{
		Object obj = null;
		try
		{
			Session session = HibernateUtil.currentSession();
			Query query = session.createQuery(hql);
			if (null != map && map.size() > 0)
			{
	            for (Map.Entry<String, Object> entry : map.entrySet()) {
	                query.setParameter(entry.getKey(), entry.getValue());
	            }
			}
			List list = query.list();
			if (list.size() == 0)
			{
				return null;
			}
			obj = list.get(0);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw e;
		}

		return obj;
	}
}
