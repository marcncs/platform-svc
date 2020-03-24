<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>

<body>
<script language="javascript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 var pdmenu = getCookie("PPdCookie");
	 switch(pdmenu){
	 	case "1":ProvideCompare(); break;
		case "2":PurchaseInquire(); break;		
		default:ProvideCompare();
	 }
	}
	
	function ProvideCompare(){
		setCookie("PPdCookie","1");
		if(checkid!=""){
			document.all.submsg.src="productProviderCompareAction.do?PDID="+checkid;
		}else{
			alert("请选择产品!");
		}
	}

	function PurchaseInquire(){
		setCookie("PPdCookie","2");
		//if(checkid>0){
			document.all.submsg.src="listPurchaseInquireForPlanAction.do?ppid=${ppf.id}";
		//}else{
		//	alert("请选择你要操作的记录!");
		//}
	}
	
	
	function Audit(ppid){
			popWin("../purchase/auditPurchasePlanAction.do?PPID="+ppid,500,250);
	}
	
	function CancelAudit(ppid){
			popWin("../purchase/cancelAuditPurchasePlanAction.do?PPID="+ppid,500,250);
	}
	
	function Ratify(ppid){
			popWin("../purchase/ratifyPurchasePlanAction.do?PPID="+ppid,500,250);
	}
	
	function CancelRatify(ppid){
			popWin("../purchase/cancelRatifyPurchasePlanAction.do?PPID="+ppid,500,250);
	}
	
	function PurchasePlan(ppid){
			popWin("../purchase/purchasePlanAction.do?ID="+ppid,900,600);
	}
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="964"> 采购计划单详情 </td>
    <td width="253" align="right"><table width="180"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="60" align="center"><a href="javascript:PurchasePlan('${ppf.id}');">打印</a></td>
        <td width="60" align="center"><c:choose>
          <c:when test="${ppf.isaudit==0}"><a href="javascript:Audit('${ppf.id}');">复核</a></c:when>
          <c:otherwise> <a href="javascript:CancelAudit('${ppf.id}')">取消复核</a></c:otherwise>
        </c:choose></td>
        <td width="60" align="center"><c:choose>
          <c:when test="${ppf.isratify==0}"><a href="javascript:Ratify('${ppf.id}')">批准</a></c:when>
          <c:otherwise><a href="javascript:CancelRatify('${ppf.id}')">取消批准</a></c:otherwise>
        </c:choose></td>
      </tr>
    </table></td>
  </tr>
</table>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="11%"  align="right">采购类型：</td>
          <td width="22%"><windrp:getname key='PurchaseSort' value='${ppf.purchasesort}' p='d'/></td>
          <td width="10%" align="right">相关单据号：</td>
          <td width="23%">${ppf.billno}</td>
	      <td width="9%" align="right">计划部门：</td>
	      <td width="25%"><windrp:getname key='dept' value='${ppf.plandept}' p='d'/> </td>
	  </tr>
	  <tr>
	    <td  align="right">计划人：</td>
	    <td><windrp:getname key='users' value='${ppf.planid}' p='d'/></td>
	    <td align="right">计划日期：</td>
	    <td ><windrp:dateformat value='${ppf.plandate}' p='yyyy-MM-dd'/></td>
         <td align="right">制单机构：</td>
	    <td ><windrp:getname key='organ' value='${ppf.makeorganid}' p='d'/></td>
	    </tr>
      <tr>
	    <td  align="right">制单部门：</td>
	    <td><windrp:getname key='dept' value='${ppf.makedeptid}' p='d'/></td>
	    <td align="right">制单人：</td>
	    <td ><windrp:getname key='users' value='${ppf.makeid}' p='d'/> </td>
         <td align="right">制单日期：</td>
	    <td >${ppf.makedate}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td>${ppf.remark}</td>
	    <td align="right">&nbsp;</td>
	    <td colspan="3">&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>状态信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="11%"  align="right">是否复核：</td>
          <td width="22%"><windrp:getname key='YesOrNo' value='${ppf.isaudit}' p='f'/></td>
          <td width="10%" align="right">复核人：</td>
          <td width="23%"><windrp:getname key='users' value='${ppf.auditid}' p='d'/></td>
	      <td width="9%" align="right">复核日期：</td>
	      <td width="25%"><windrp:dateformat value='${ppf.auditdate}' p='yyyy-MM-dd'/></td>
	  </tr>
	  <tr>
	    <td  align="right">是否批准：</td>
	    <td><windrp:getname key='YesOrNo' value='${ppf.isratify}' p='f'/></td>
	    <td align="right">批准人：</td>
	    <td><windrp:getname key='users' value='${ppf.ratifyid}' p='d'/></td>
	    <td align="right">批准日期：</td>
	    <td><windrp:dateformat value='${ppf.ratifydate}' p='yyyy-MM-dd'/>
	    </td>
	    </tr>
	  
	  <tr>
	    <td  align="right">是否转采购单：</td>
	    <td><windrp:getname key='YesOrNo' value='${ppf.iscomplete}' p='f'/></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>采购计划单产品列表</td>
        </tr>
      </table>
	  </legend>
<table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="11%">产品编号</td> 
          <td width="17%" >产品名称</td>
          <td width="16%">规格</td>
          <td width="8%">单位</td>
          <td width="8%">单价</td>
          <td width="8%">数量</td>
           <td width="8%">已转数量</td>
          <td width="10%">需求日期</td>
          <td width="10%">建议定购日期</td>
          <td width="12%">说明</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.productid}');">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
          <td><fmt:formatNumber value='${p.unitprice}' pattern='0.00'/></td>
          <td>${p.quantity}</td>
          <td>${p.changequantity}</td>
          <td><windrp:dateformat value='${p.requiredate}' p='yyyy-MM-dd'/></td>
          <td><windrp:dateformat value='${p.advicedate}' p='yyyy-MM-dd'/></td>
          <td>${p.requireexplain}</td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
      <br>
       <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:ProvideCompare();"><span>产品供应商</span></a></li>
             <li><a href="javascript:PurchaseInquire();"><span>询价记录</span></a></li>
          </ul>
        </div>
        <div>
        <IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
        </div>
       </div>	  
	</td>
  </tr>
</table>

</body>
</html>
