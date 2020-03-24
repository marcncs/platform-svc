<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/prototype.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>

<body>
<script language="javascript">
function formChk(){
	if ( !Validator.Validate(document.listform,2) ){
		return false;
	}
	var canquantity = document.getElementsByName("ratifyquantity");
	var quantity =document.getElementsByName("quantity"); 
	
	for(var i=0;i<quantity.length;i++){
		if (parseFloat(canquantity[i].value) > parseFloat(quantity[i].value)) {
			alert("本次批准数量不能大于 数量!");
			canquantity[i].select();
			return false;
		}	
	}
	return true;
}
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <form name="listform" method="post" action="ratifyOrganWithdrawAction.do" onSubmit="return formChk()">
    <input type="hidden" name="ID" value="${ama.id}">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td> 批准渠道退货 </td>
    <td align="right"><table   border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td style="float: right; padding-right: 20px;">       
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
          <td width="22%">${ama.id}</td>
          <td width="10%" align="right">供货机构：</td>
          <td width="23%">
          <windrp:getname key="organ" p="d" value="${ama.porganid}" /></td>
	      <td width="9%" align="right">机构联系人：</td>
	      <td width="25%">${ama.plinkman }</td>
	  </tr>
	  <tr>
	    <td  align="right">出货仓库：</td>
	    <td><windrp:getname key='warehouse' value='${ama.warehouseid}' p='d'/>
		</td>
	    <td align="right">总金额：</td>
	    <td >${ama.totalsum }</td>
	    <td align="right">联系电话：</td>
	    <td >${ama.tel }</td>
	  </tr>
       <tr>
	    <td  align="right">入货仓库：</td>
	    <td colspan="5"><windrp:warehouse name="inwarehouseid"/></td>
	    </tr>
	   <tr>
	    <td  align="right">退货原因：</td>
	    <td colspan="5">${ama.withdrawcause}</td>
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
	  	<td width="11%"  align="right">制单机构：</td>
          <td width="22%"><windrp:getname key='organ' value='${ama.makeorganid}' p='d'/></td>
          <td width="10%" align="right">制单部门：</td>
          <td><windrp:getname key='dept' value='${ama.makedeptid}' p='d'/></td>
	      <td align="right">制单人：</td>
	      <td><windrp:getname key='users' value='${ama.makeid}' p='d'/></td>
	    </tr>
	  <tr>
	    <td align="right">制单日期：</td>
	    <td><windrp:dateformat value='${ama.makedate}' p='yyyy-MM-dd'/></td>
	  	<td align="right" >检货状态：</td>
	    <td ><windrp:getname key="TakeStatus" p="f" value="${ama.takestatus}"/></td>
	    <td  align="right">打印次数：</td>
	    <td >${ama.printtimes}</td>
	    </tr>
	  <tr>
	  	<td width="11%"  align="right">是否复核：</td>
          <td width="22%"><windrp:getname key='YesOrNo' value='${ama.isaudit}' p='f'/></td>
          <td width="10%" align="right">复核人：</td>
          <td width="23%"><windrp:getname key='users' value='${ama.auditid}' p='d'/></td>
	      <td width="9%" align="right">复核日期：</td>
	      <td width="25%"><windrp:dateformat value='${ama.auditdate}' p='yyyy-MM-dd'/></td>
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
          <td>产品编号</td> 
          <td>产品名称</td>
          <td>规格</td>
          <td>计量单位</td>
          <td>批次</td>
          <td>单价</td>
          <td>数量</td>
          <td>批准数量</td>
          <td>金额</td>
        </tr>
        <logic:iterate id="p" name="list" > 
        <tr class="table-back-colorbar" >
          <td><input type="hidden" name="detailid" value="${p.id}">${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
           <td>${p.batch}</td>
          <td><input type="text" name="unitprice" value="${p.unitprice}" size="8" maxlength="8" onKeyUp="clearNoNum(this)" dataType="Double" msg="单价只能是数字!" require="false"/></td>
          <td><input type="hidden" name="quantity" value="${p.quantity}"/>${p.quantity}</td>
          <td><input type="text" name="ratifyquantity" value="${p.quantity}" size="8" maxlength="8" onKeyUp="clearNoNum(this)" dataType="Double" msg="批准数量只能是数字!" require="false"></td>
          <td><fmt:formatNumber value='${p.subsum}' pattern='0.00'/></td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr align="center">
            <td width="33%"><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
             <input type="button" name="Submit2" value="取消"onClick="window.close();">
            </td>
        </tr>
    </table>
      </form>
	  </td>
  </tr>
</table>
</body>
</html>
