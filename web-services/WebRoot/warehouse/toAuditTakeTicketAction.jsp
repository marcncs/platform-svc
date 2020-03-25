<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>${menu }详情</title>
<script src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<script language="javascript">

	function Audit(piid){
		if(formChk()){
			document.search.submit();
		}
	}
	function CancelAudit(piid){
		showloading();
		popWin("../warehouse/cancelAuditTakeTicketAction.do?id="+piid+"");
	}
	function toAudit(id,billno){
		Audit(id);
	}
	function formChk(){
		if ( !Validator.Validate(document.search,2) ){
			return false;
		}
		//判断出库数量与计划数量是否一致
		var quantityArr = $("form[name='search'] input[name='realQuantity']");
		var isEqual = true;
		var isCorrectFormat = false;
		//判断出库数量是否为0
		$.each(quantityArr,function(i,n){
			//实际数量
			var realQuantity = $(n).val();
			if(realQuantity < 0){
				alert("请输入大于等于0的数");
				isCorrectFormat = true;
			}
		});
		if(isCorrectFormat){
			return false;		
		}
		$.each(quantityArr,function(i,n){
			//实际数量
			var realQuantity = $(n).val();
			//计划数量
			var planQuantity =  $(n).parent().prev().html();
			if(realQuantity != parseFloat(planQuantity)){
				$(n).css("color","red");
				isEqual = false;
			}
		});
		// 如果有不相等,则提示
		if(!isEqual){
			if(confirm("产品确认数量与计划数量不一致,是否继续?")){
				return checkIdcodeQuantity();
			}else{
				return false;
			}
		}else{
			return checkIdcodeQuantity();
		}
		return false;
	}
	function checkIdcodeQuantity(){
		var flag = false;
		// ajax同步检查条码数量
   		$.ajax({
			type:"POST",
			url:"../warehouse/ajaxCheckTakeTicketIdcodeAction.do",
			data:$("#search").serialize(),
			dataType:"json",
			async: false,			
			success:function(msg){
				var popMsg = msg.returnMsg + "\r\n是否继续?";
				var code = msg.returnCode;
				if(code == 0){
					flag = true;
				}else{
					if(confirm(popMsg)){
						flag = true;
					}else{
						flag = false;
					}
				}
			}
   	   	});
   	   	return flag;
	}
</script>
</head>
<body style="overflow: auto;">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="925"> ${menu }详情 </td>
          <td width="308" align="right">
          <table width="300"  border="0" cellpadding="0" cellspacing="0">
         	<tr>
	              <ws:hasAuth operationName="/warehouse/auditTakeTicketAction.do">
	              <td width="100" align="right" >
		              <c:choose>
		                  <c:when test="${sof.isaudit==0}"><a href="javascript:toAudit('${sof.id}','${sof.billno}');">确认</a></c:when>
		              </c:choose>
	              </td>
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
	  	<td width="11%"  align="right">出货仓库：</td>
          <td width="27%"><windrp:getname key='warehouse' value='${sof.warehouseid}' p='d'/></td>
          <td width="8%" align="right">单据编号：</td>
          <td width="22%">${sof.billno}</td>
	      <td width="10%" align="right">对象名称：</td>
	      <td width="22%">${sof.oname}</td>
	  </tr>
	  <tr>
	  	<td width="11%"  align="right">收货人：</td>
          <td width="27%">${sof.rlinkman}</td>
          <td width="8%" align="right">联系电话：</td>
          <td width="22%">${sof.tel}</td>
	      <td width="10%" align="right">收货仓库：</td>
	      <td width="22%"><windrp:getname key='warehouse' value='${sof.inwarehouseid}' p='d'/></td>
	  </tr>
	  <tr>
	    <td  align="right">检货人：</td>
	    <td ><windrp:getname key='users' value='${sof.takeid}' p='d'/></td>
	    <td align="right">打印次数：</td>
	    <td>${sof.printtimes}</td>
		<td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	   <tr>
	    <td  align="right">备注：</td>
	    <td colspan="3" ><windrp:getname key="ReturnReason" p="f" value="${sof.remark}" /></td>
	        <td align="right">是否查看:</td>
         <td><windrp:getname key="YesOrNo" p="f" value="${IsRead}" /></td>
       
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
          <td width="26%"><windrp:getname key='users' value='${sof.makeid}' p='d'/></td>
          <td width="8%" align="right"></td>
          <td width="22%"></td>
	      <td width="11%" align="right">制单日期：</td>
	      <td width="22%">${sof.makedate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td><windrp:getname key='YesOrNo' value='${sof.isaudit}' p='f'/></td>
	    <td align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${sof.auditid}' p='d'/></td>
	    <td align="right">复核日期：</td>
	    <td>${sof.auditdate}</td>
	    </tr>
	  </table>
	</fieldset>
	<form name="search" id="search" method="post" action="../warehouse/auditTakeTicketAction.do" onSubmit="return formChk(${sof.id});">
		<input type="hidden" name="ttid" id="ttid"  value="${sof.id}" />
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=(String) request.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>";/>
		<fieldset align="center"> <legend>
	      <table width="110" border="0" cellpadding="0" cellspacing="0">
	        <tr>
	          <td>检货物品清单列表</td>
	        </tr>
	      </table>
		  </legend>
	      <table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
	        <tr align="center" class="title-top">
	          <td width="7%">产品编号</td>
	          <td width="10%">物料号</td>
	          <td width="20%" >产品名称</td>
	          <td width="10%">规格</td>
			  <td width="5%">单位</td>
			  <td width="8%">计划数量</td>
			  <td width="5%">确认数量</td>
	        </tr>
	        <logic:iterate id="p" name="als" > 
	        <tr class="table-back-colorbar">
	          <td><input type="hidden" name="pid" value="${p.productid }"/>${p.productid}</td> 
	           <td>${p.nccode}</td> 
	          <td >${p.productname}</td>
	          <td>${p.specmode}</td>
	          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
	          <td><windrp:format value="${p.quantity}" p="###,##0.00" /></td>
	          <c:if test="${isMinusStock == 1 }">
	         	 <td><input name="realQuantity"  type="text" value="<windrp:format value="${p.quantity}" p="#" />" dataType="Integer"  onkeyup="clearNoInt(this)"  msg="确认数量格式不正确!"></td>
	          </c:if>
	          <c:if test="${ empty isMinusStock || isMinusStock == 0 }">
	         	 <td><input name="realQuantity"  type="text" value="<windrp:format value="${p.quantity}" p="#" />" dataType="Range" min="0" 
	         	  max="<windrp:format value="${p.stockQuantity }" p="#.00" />" onkeyup="clearNoInt(this)"  msg="确认数量应小于库存数量[${p.stockQuantity }]!"></td>
	          </c:if>
	        </tr>
	        </logic:iterate> 
	      </table>
		  </fieldset>
	  </form>
</td>
  </tr>
</table>
<% session.setAttribute("sof", request.getAttribute("sof"));
%>
</body>
</html>
