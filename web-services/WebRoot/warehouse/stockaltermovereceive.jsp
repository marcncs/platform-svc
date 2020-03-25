<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<script type="text/javascript" src="../js/prototype.js"></script>
<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>发货单签收</title>
<script language="javascript">
	function Receive(omid){
		if(formChk()){
			showloading();	
			document.search.submit();
		}
	}
	function CancelReceive(omid){
			showloading();
			window.open("../warehouse/cancelCompleteStockAlterMoveReceiveAction.do?OMID="+omid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	function formChk(){
		//有差异数量的必须输入备注信息
		//判断签收数量与计划数量是否一致
		var quantityArr = $("form[name='search'] input[name='receiveQuantity']");
		$.each(quantityArr,function(i,n){
			//实际数量
			var realQuantity = $(n).val();
			//计划数量
			var planQuantity =  $(n).parent().prev().html();
			//备注
			var remark = $(n).parent().next().children("input[name='receiveRemark']")
			if(parseFloat(realQuantity) != parseFloat(planQuantity)){
				$(n).css("color","red");
				$(remark).css("color","red");
				$(remark).attr("dataType","Require");
			}
		});
		if ( !Validator.Validate(document.search,2) ){
			return false;
		}
		return true;
	}
	function checknum(obj){
		obj.value = obj.value.replace(/[^\d]/g,"");
	}
	function selectWIn(dom){
		var id = $('#receiveorganid').val();
		selectDUW(dom,'inwarehouseid',id,'w2','inWarehouseName')
		$('#inWarehouseName').html($('#wname').val());
	}
	function changeWarehouse() {
		$('#inWarehouseName').html($('#wname').val());
	}
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="869">发货单签收详情 </td>
          <td width="364" align="right"><table width="100"  border="0" cellpadding="0" cellspacing="0">
            <tr>
               <ws:hasAuth operationName="/warehouse/completeStockAlterMoveReceiveAction.do">
               <td width="80" align="center"><c:choose>
                  <c:when test="${smf.iscomplete==0}">
                  	<input type="button"  value="签收"	onclick="javascript:Receive('${smf.id}');" />
                  </c:when>
              </c:choose></td>
               </ws:hasAuth>
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
	  	<td width="9%"  align="right">单据日期：</td>
          <td width="25%"><windrp:dateformat value="${smf.movedate}" p="yyyy-MM-dd"/></td> 
          <td width="9%" align="right">制单机构：</td>
          <td width="23%"><windrp:getname key='organ' value='${smf.makeorganid}' p='d'/></td>
	      <td width="9%" align="right">调出仓库：</td>
	      <td width="25%"><windrp:getname key='warehouse' value='${smf.outwarehouseid}' p='d'/></td>
	  </tr>
	  <tr>
	  	<td align="right">
			企业内部单号：
		</td>
	    <td>${smf.nccode}</td>
	    <td  align="right">调入机构：</td>
	    <td><windrp:getname key='organ' value='${smf.receiveorganid}' p='d'/></td>
	    <td align="right">调入仓库：</td>
	    <td id="inWarehouseName"><windrp:getname key='warehouse' value='${smf.inwarehouseid}' p='d'/>
	    </td>
	  </tr>
	  <tr>
	    <td align="right">收货地址：</td>
	    <td colspan="3">${smf.transportaddr}</td>
	    </tr>
	  <tr>
	    <td  align="right">发货原因：</td>
	    <td colspan="5">${smf.movecause}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${smf.remark}</td>
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
	  	<td width="11%"  align="right">制单人：</td>
          <td width="23%"><windrp:getname key='users' value='${smf.makeid}' p='d'/></td>
          <td width="9%" align="right">制单日期：</td>
          <td width="23%">${smf.makedate}</td>
          <td width="16%"  align="right">是否检货：</td>
	    	<td width="18%"><windrp:getname key='YesOrNo' value='${smf.takestatus}' p='f'/></td>
	      
	  </tr>
	  <tr>
	  <td width="11%" align="right">是否复核：</td>
	      <td width="23%"><windrp:getname key='YesOrNo' value='${smf.isaudit}' p='f'/></td>
	    <td  align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${smf.auditid}' p='d'/></td>
	    <td align="right">复核日期：</td>
	    <td>${smf.auditdate}</td>
	   
	    </tr>
	  <tr>
	   <td align="right">是否发货：</td>
	    <td><windrp:getname key='YesOrNo' value='${smf.isshipment}' p='f'/></td>
	    <td  align="right">发货人：</td>
	    <td><windrp:getname key='users' value='${smf.shipmentid}' p='d'/></td>
	    <td align="right">发货日期：</td>
	    <td>${smf.shipmentdate}</td>
	   
	    </tr>
	  <tr>
	   <td align="right">是否完成配送：</td>
	    <td><windrp:getname key='YesOrNo' value='${smf.istally}' p='f'/></td>
	    <td  align="right">完成配送人：</td>
	    <td><windrp:getname key='users' value='${smf.tallyid}' p='d'/></td>
	    <td align="right">完成配送时间：</td>
	    <td>${smf.tallydate}</td>
	    
	    </tr>	 
	  <tr>
	    <td  align="right">是否签收：</td>
	    <td><windrp:getname key='YesOrNo' value='${smf.iscomplete}' p='f'/></td>
	    <td align="right">签收人：</td>
	    <td><windrp:getname key='users' value='${smf.receiveid}' p='d'/></td>
	    <td  align="right">签收日期：</td>
	    <td>${smf.receivedate}</td>
	  </tr>
	  </table>
	</fieldset>
	<form name="search" method="post" action="../warehouse/completeStockAlterMoveReceiveAction.do" onSubmit="return formChk();">
		<input type="hidden" name="omid" id="omid"  value="${smid}" />
		<input name="receiveorganid" type="hidden" id="receiveorganid" value="${smf.receiveorganid}">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>入库仓库</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	    <td align="right" width="9%">调入仓库：</td>
	    <td><input type="hidden" name="inwarehouseid" id="inwarehouseid" value="${smf.inwarehouseid}" dataType="Require" msg="该单据是'种子'客户单据，请选择'种子'客户仓库签收!"/>
		<input type="text" name="wname" id="wname" onClick="selectWIn(this)" onmousedown="changeWarehouse()"  value="<windrp:getname key='warehouse' value='${smf.inwarehouseid}' p='d'/>" readonly>
		<span class="style1">*<c:if test="${isSeedCustomer == 1}">该单据是'种子'客户单据，请选择'种子'客户仓库签收。若无'种子'客户仓库，请勿处理该单据，可向客服反应该情况</c:if></span>
		</td>
	  </tr>
	  <tr>
		<td align="right">
			换仓原因：
		</td>
		<td colspan="5">
		<textarea name="movecause" cols="100" style="width: 100%" rows="3"
		id="movecause" dataType="Limit" max="256" msg="换仓原因必须在256个字之内"
			require="false" value="">${smf.movecause}</textarea><br>
		<span class="td-blankout">(换仓原因不能超过256个字符!)</span>
			
		</td>
	</tr>
	  </table>
	</fieldset>	
	<fieldset align="center"> <legend>
      <table width="90" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>调拨单产品列表</td>
        </tr>
      </table>
	  </legend>
      <table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="11%">产品编号</td>
          <td width="11%">物料号</td>
          <td width="25%" >产品名称</td>
          <td width="8%">规格</td>
          <td width="5%">单位</td>
          <td width="8%">数量</td>
          <td width="6%">发货数量</td>
          <td width="6%">签收数量</td>
          <td width="6%">备注</td>
        </tr>
        <logic:iterate id="p" name="als" >
          <tr class="table-back-colorbar">
            <td><input type="hidden" name="pid" value="${p.productid }"/>${p.productid}</td>
            <td>${p.nccode}</td>
            <td >${p.productname}</td>
            <td>${p.specmode}</td>
            <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
            <td>${p.quantity}</td>
            <td>${p.takequantity}</td>
            <td><input name="receiveQuantity"  type="text" value="<windrp:format value="${p.takequantity}" p="#" />" dataType="Integer"  onkeyup="checknum(this)"  msg="签收数量格式不正确!"></td>
            <td><input name="receiveRemark"  type="text" value="${p.receiveRemark}"  msg="请输入备注信息!" maxlength="50"></td>
          </tr>
        </logic:iterate>
      </table>
	  </fieldset>
	  </form>
</td>
  </tr>
</table>
</body>
</html>
