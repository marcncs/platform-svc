<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/sorttable.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>

<body>
<script language="javascript">
	function Receive(smid){
			showloading();
			popWin2("../warehouse/completeOrganTradePReceiveAction.do?ID="+smid);
	}
	
	function CancelReceive(smid){
			showloading();
			popWin2("../warehouse/cancelCompleteOrganTradePReceiveAction.do?ID="+smid);
	}
	function Affirm(smid){
			popWin("../warehouse/toAffirmOrganTradesAction.do?ID="+smid,1000,650);
	}
	
	function CancelAffirm(smid){
			showloading();
			popWin2("../warehouse/cancelAffirmOrganTradesAction.do?ID="+smid);
	}
	
	function toaddidcode(billid,bdid,wid,isaudit){
			popWin4("../warehouse/toAddOrganTradesPIdcodeiiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,650,"idcode");
	}
	function toaddbit(billid,bdid,wid,isaudit){
			popWin4("../warehouse/toAddOrganTradesPIdcodeiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,600,"idcode");
	}
	function prints(id){
			popWin("../ditch/printOrganTradesReceiveAction.do?ID="+id,900,600);
	}
	
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td> 渠道换货详情 </td>
    <td align="right"><table   border="0" cellpadding="0" cellspacing="0">
      <tr>
      <td width="60" align="center"><a href="javascript:prints('${ot.id}');">打印</a></td>
        <td style="float: right; padding-right: 20px;">  
        	<c:choose>         	
	       	<c:when test="${ot.pisreceive==0}"><a href="javascript:Receive('${ot.id}')">签收</a></c:when>
                  <c:otherwise><a href="javascript:CancelReceive('${ot.id}')">取消签收</a></c:otherwise>
            </c:choose>
            &nbsp;&nbsp; 
            <c:choose>         	
	       	<c:when test="${ot.pisaffirm==0}"><a href="javascript:Affirm('${ot.id}')">确定</a></c:when>
                  <c:otherwise><a href="javascript:CancelAffirm('${ot.id}')">取消确定</a></c:otherwise>
            </c:choose>
       </td>
      
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
	  	<td width="11%"  align="right">编号：</td>
          <td width="22%">${ot.id}</td>
          <td width="10%" align="right">供货机构：</td>
          <td width="23%">
          <windrp:getname key="organ" p="d" value="${ot.porganid}" /></td>
	      <td width="9%" align="right">机构联系人：</td>
	      <td width="25%">${ot.plinkman }</td>
	  </tr>
	  <tr>
	    <td  align="right">换出仓库：</td>
	    <td><windrp:getname key='warehouse' value='${ot.outwarehouseid}' p='d'/>
		</td>
		<td  align="right">换出地址：</td>
	    <td>${ot.transportaddr }
		</td>
	    
	    <td align="right">联系电话：</td>
	    <td >${ot.tel }</td>
	  </tr>
	  <tr>
	  <td  align="right">换入仓库：</td>
	    <td><windrp:getname key='warehouse' value='${ot.inwarehouseid}' p='d'/>
		</td>
		<td  align="right">换入地址：</td>
	    <td>${ot.rtransportaddr }
		</td>
	  	<td align="right">&nbsp;</td>
	    <td >&nbsp;</td>
	  </tr>
	   <tr>
	    <td  align="right">换货原因：</td>
	    <td colspan="5">${ot.withdrawcause}</td>
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
	  <td align="right">制单机构：</td>
          <td ><windrp:getname key='organ' value='${ot.makeorganid}' p='d'/></td>
          <td  align="right">制单部门：</td>
          <td><windrp:getname key='dept' value='${ot.makedeptid}' p='d'/></td>
  
	    <td align="right">制单人：</td>
	    <td><windrp:getname key='users' value='${ot.makeid}' p='d'/></td>
	  </tr>
	  <tr>
	    <td align="right">制单日期：</td>
	    <td><windrp:dateformat value='${ot.makedate}' p='yyyy-MM-dd'/>
	  	<td align="right" >检货状态：</td>
	    <td ><windrp:getname key="TakeStatus" p="f" value="${ot.takestatus}"/></td>
	    <td  align="right">打印次数：</td>
	    <td >${ot.printtimes}</td>
	    </tr>
	  <tr>
	  	<td width="11%"  align="right">是否复核：</td>
          <td width="22%"><windrp:getname key='YesOrNo' value='${ot.isaudit}' p='f'/></td>
          <td width="10%" align="right">复核人：</td>
          <td width="23%"><windrp:getname key='users' value='${ot.auditid}' p='d'/></td>
	      <td width="9%" align="right">复核日期：</td>
	      <td width="25%"><windrp:dateformat value='${ot.auditdate}' p='yyyy-MM-dd'/></td>
	  </tr>
	  <tr>
	    <td  align="right">是否批准：</td>
	    <td><windrp:getname key='YesOrNo' value='${ot.isratify}' p='f'/></td>
	    <td align="right">批准人：</td>
	    <td><windrp:getname key='users' value='${ot.ratifyid}' p='d'/></td>
	    <td align="right">批准日期：</td>
	    <td><windrp:dateformat value='${ot.ratifydate}' p='yyyy-MM-dd'/>
	    </td>
	    </tr>
	     <tr>
	    <td  align="right">是否确认：</td>
	    <td><windrp:getname key='YesOrNo' value='${ot.isaffirm}' p='f'/></td>
	    <td align="right">确认人：</td>
	    <td><windrp:getname key='users' value='${ot.affirmid}' p='d'/></td>
	    <td align="right">确认日期：</td>
	    <td><windrp:dateformat value='${ot.affirmdate}' p='yyyy-MM-dd'/>
	    </td>
	    </tr>
	     <tr>
	    <td  align="right">是否签收：</td>
	    <td><windrp:getname key='YesOrNo' value='${ot.isreceive}' p='f'/></td>
	    <td align="right">签收人：</td>
	    <td><windrp:getname key='users' value='${ot.receiveid}' p='d'/></td>
	    <td align="right">签收日期：</td>
	    <td><windrp:dateformat value='${ot.receivedate}' p='yyyy-MM-dd'/>
	    </td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>渠道退货产品列表</td>
        </tr>
      </table>
	  </legend>
	<table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="14%">产品编号</td> 
          <td width="25%">产品名称</td>
          <td width="22%">规格</td>
          <td width="11%">批次</td>
          <td width="7%">单位</td>
          <td width="5%">序号</td>          
          <td width="8%">数量</td>
          <td width="8%">批准数量</td>
        </tr>
        <logic:iterate id="p" name="list" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.batch}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
          <td><windrp:idcodebit value="${p.productid}" idcodeclick="toaddidcode('${ot.id}','${p.id}','${ot.inwarehouseid}','${ot.pisreceive}')" bitclick="toaddbit('${ot.id}','${p.id}','${ot.inwarehouseid}','${ot.pisreceive}')"/></td>           
          <td>${p.quantity}</td>
          <td>${p.canquantity}</td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
	  </td>
  </tr>
</table>
</body>
</html>
