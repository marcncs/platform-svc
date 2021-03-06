<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Audit(oiid){
		showloading();
		popWin2("../warehouse/auditOtherIncomeAction.do?ID="+oiid);
	}
	
	function CancelAudit(oiid){
		showloading();
		popWin2("../warehouse/cancelAuditOtherIncomeAction.do?ID="+oiid);
	}

	function OtherIncome(oiid){
			window.open("../warehouse/otherIncomeAction.do?ID="+oiid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	function toaddidcode(pidid, batch, piid){
			window.open("../warehouse/toAddOtherIncomeIdcodeAction.do?pidid="+pidid+"&batch="+batch+"&piid="+piid,"newwindow","height=400,width=600,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function toaddidcode(billid,bdid,wid,isaudit){
			popWin("../warehouse/toAddOtherIncomeIdcodeiiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,650);
	}
	function toaddbit(billid,bdid,wid,isaudit){
			popWin("../warehouse/toAddOtherIncomeIdcodeiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,600);
	}	
	function PrintOtherIncome(id){
			popWin4("../warehouse/printOtherIncomeAction.do?ID="+id,900,600,"print");
	}
	function checkPrice(oiid){
		var unitprice = document.all("unitprice");	
		var productname = document.all("productname");	
		var iszero=false;
		var pname="";
		if(unitprice.length){
			for(i=0; i<unitprice.length; i++){
				if ( parseFloat(unitprice[i].value)==0 ){
					iszero=true;
					pname=productname[i].value;
					break;
				}
			}
		}else{
			if ( parseFloat(unitprice.value)==0 ){
				iszero=true;
				pname=productname.value;
			}
		}
		if ( iszero ){
			if(window.confirm(pname+" 单价为0，您确认要复核吗？")){	
				Audit(oiid);
			}
		}else{
			Audit(oiid);
		}
	}
	
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="982">盘盈单详情 </td>
          <td width="251" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:PrintOtherIncome('${oif.id}');">打印</a></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${oif.isaudit==0}"><a href="javascript:Audit('${oif.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${oif.id}')">取消复核</a></c:otherwise>
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
	  	<td width="9%"  align="right">入货仓库：</td>
          <td width="26%"><windrp:getname key='warehouse' value='${oif.warehouseid}' p='d'/></td>
          <td width="9%" align="right">入库类别：</td>
          <td width="22%"><windrp:getname key='IncomeSort' value='${oif.incomesort}' p='f'/></td>
	      <td width="9%" align="right">相关单据：</td>
	      <td width="25%">${oif.billno}</td>
	  </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${oif.remark}</td>
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
	  	<td width="9%"  align="right">制单机构：</td>
          <td width="26%"><windrp:getname key='organ' value='${oif.makeorganid}' p='d'/></td>
          <td width="9%" align="right">制单人：</td>
          <td width="22%"><windrp:getname key='users' value='${oif.makeid}' p='d'/> </td>
	      <td width="9%" align="right">制单日期：</td>
	      <td width="25%">${oif.makedate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td><windrp:getname key='YesOrNo' value='${oif.isaudit}' p='f'/></td>
	    <td align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${oif.auditid}' p='d'/></td>
	    <td align="right">复核日期：</td>
	    <td>${oif.auditdate}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>盘盈单产品列表</td>
        </tr>
      </table>
	  </legend>
	  <form name="listform" method="post">
      <table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
      
        <tr align="center" class="title-top">
          <td width="12%">产品编号</td>
          <td width="19%" >产品名称</td>
          <td width="21%">规格</td>
          <td width="7%">单位</td>
          <!--
		  <td width="7%">序号</td>
		    -->
          <td width="12%">批次</td>
          <td width="10%">单价</td>
          <td width="9%">数量</td>
          <td width="10%">金额</td>
        </tr>
        <logic:iterate id="p" name="als" >
          <tr class="table-back-colorbar">
            <td>${p.productid}</td>
            <td ><input type="hidden" name="productname" value="${p.productname}">${p.productname}</td>
            <td>${p.specmode}</td>
            <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
			<!--  
			<td><windrp:idcodebit value="${p.productid}" idcodeclick="toaddidcode('${oif.id}','${p.id}','${oif.warehouseid}','${oif.isaudit}')" bitclick="toaddbit('${oif.id}','${p.id}','${oif.warehouseid}','${oif.isaudit}')"/></td>
            -->
            <td>${p.batch}</td>
            <td><input type="hidden" name="unitprice" value="${p.unitprice}">${p.unitprice}</td>
            <td>${p.quantity}</td>
            <td>${p.subsum}</td>
          </tr>
        </logic:iterate>
        
      </table>
      </form>
	  </fieldset>
      
</td>
  </tr>
</table>
</body>
</html>
