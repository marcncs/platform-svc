<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>机构间转仓申请单审核</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/validator.js"></script>
<script type="text/javascript" src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/showSQ.js"> </SCRIPT>
<script language="javascript" src="../js/jquery-1.11.1.min.js"></script>
<script language="JavaScript">

function Check(){
	if ( !Validator.Validate(document.addusers,2) ){
		return false;
		}
	var canquantity = document.getElementsByName("canquantity");
	var quantity =document.getElementsByName("quantity"); 
	
	for(var i=0;i<quantity.length;i++){
		var isEuqal = true;
		if(parseFloat(canquantity[i].value) <= 0) {
			alert("请输入大于0的数");
			return false;
		}
		if (parseFloat(canquantity[i].value) > parseFloat(quantity[i].value)) {
			alert("本次批准数量不能大于 数量!");
			canquantity[i].select();
			return false;
		}	
		if (parseFloat(canquantity[i].value) != parseFloat(quantity[i].value)) {
			isEuqal = false;
		}	
		// 如果有不相等,则提示
		if(!isEuqal){
			if(confirm("产品批准数量与数量不一致,是否继续?")){
				return true;
			}else{
				return false;
			}
		}
	}
	return true;
}
	
function noPass(){

	var reason = $("#reason").val();
	if(reason.trim()=="") {
		alert("请输入原因");
		return;
	}
	document.referForm.noflag.value=1;	
	document.referForm.submit();

}
	
</script>
</head>

<body style="overflow: auto;">

<form name="referForm" id="referForm" method="post" action="ratifyMoveApplyAction.do" onSubmit="return Check();">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 机构间转仓申请单审核</td>
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
          <td width="13%"  align="right"><input name="id" type="hidden" id="id" value="${smf.id}">
            	需求日期：</td>
	      <td width="25%">${smf.movedate}</td>
	      <td width="9%" align="right"> 调出机构：</td>
	      <td width="24%"><windrp:getname key="organ" p="d" value="${smf.outorganid}"/></td>
	      <td width="10%" align="right">调出仓库：</td>
	      <td ><windrp:getname key='warehouse' value='${smf.outwarehouseid}' p='d'/></td>
	    </tr>
	    <tr>
	      <td width="9%" align="right"> 调入机构：</td>
	      <td width="24%"><windrp:getname key="organ" p="d" value="${smf.inorganid}"/></td>
	    <td width="9%"  align="right">
            	调入仓库：</td>
	      <td width="25%"><windrp:getname key='warehouse' value='${smf.inwarehouseid}' p='d'/></td>
	      <td width="10%" align="right"></td>
	      <td width="23%"></td>
	    </tr>
	    <tr>
	    <td width="10%" align="right">联系电话：</td>
	      <td width="23%">${smf.otel }</td>
          <td  align="right">收货地址：</td>
	      <td >${smf.transportaddr}</td>
	      <td width="10%" align="right">联系人：</td>
	      <td width="23%">${smf.olinkman }</td>
	      </tr>
	    <tr>
          <td  align="right">机构间转仓原因：</td>
	      <td colspan="5">${smf.movecause}</td>
	      </tr>
	      <tr>
			<td align="right">
				不通过原因：
			</td>
			<td  colspan="6">
				<input name="noflag" type="hidden" id="noflag" >
				<textarea id="reason" name="reason" rows="2" cols="100"></textarea>
			</td>
			</tr>
	    <tr>
          <td  align="right">备注：</td>
	      <td colspan="5">${smf.remark}</td>
	      </tr>
	  </table>
	</fieldset>
	  <fieldset align="center">
		<legend>产品信息</legend>
      <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td>编号</td>
          <td>产品编号</td>
          <td>产品名称 </td>
          <td>规格</td>
          <td>单位</td>
          <td>库存</td>
          <td>数量</td>
          <td>批准数量</td>
        </tr>
        <c:set var="count" value="2"/>
        <logic:iterate id="p" name="als" >
          <tr class="table-back">
            <td>
            	<input type="hidden" name="detailid" id="detailid" value="${p.id }">${p.id }
            </td>
            <td>${p.productid}</td>
            <td >${p.productname}</td>
            <td>${p.specmode}</td>
            <td><windrp:getname key="CountUnit" p="d" value="${p.unitid}"/></td>
            <td><a href="#" onMouseOver="ShowSQ(this,'${p.productid}');" ><img src="../images/CN/stock.gif" width="16"  border="0"></a></td>
            <td><input id="quantity" name="quantity" type="hidden" value="<windrp:format value="${p.quantity}" p="#####0.00" />"/><windrp:format value="${p.quantity}" p="#####0.00" /></td>
            <td><input name="canquantity" type="text" value="<windrp:format value="${p.quantity}" p="#####0.00" />" dataType='Currency' msg='必须录入数值类型!' onKeyPress='KeyPress(this)' id="canquantity" size="8" maxlength="8"></td>
          </tr>
          <c:set var="count" value="${count+1}"/>
        </logic:iterate>
      </table>
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
        <tr align="center" class="table-back">
          <td width="3%" ></td>
          <td width="11%">&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="69%" align="right">
          </td>
          <td width="10%"></td>
        </tr>
      </table>
      </fieldset>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center"><input type="submit" name="Submit"  value="通过">&nbsp;&nbsp;
          <input type="button" name="Button" value="不通过" onClick="javascript:noPass();"></td>
        </tr>
      </table>
      </td>
  </tr>
</table>
</form>
</body>
</html>
