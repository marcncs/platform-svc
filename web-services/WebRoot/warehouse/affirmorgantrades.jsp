<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/validator.js"></script>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
        <SCRIPT type="text/javascript" src="../js/showSQ.js"> </SCRIPT>
<script language=javascript>
 
	function formChk(){
		showloading();
		return true;
	}
</script>
</head>
<body style="overflow: auto;">
<form name="validateProvide" method="post" action="../warehouse/affirmOrganTradesAction.do" onSubmit="return formChk()">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td >确定渠道换货单</td>
        </tr>
      </table>
	  
	  <fieldset align="center">
			<legend>
				<table width="50" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							基本信息
						</td>
					</tr>
				</table>
			</legend>
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
				<tr>
					<td width="10%" align="right">
						换货机构：
					</td>
					<td width="23%">
					<input type="hidden" id="id" name="id" value="${ot.id }">
						<windrp:getname key="organ" p="d" value="${ot.makeorganid}"/>
					</td>
					<td width="10%" align="right">
						换货仓库：
					</td>
					<td width="24%">
						<windrp:getname key='warehouse' value='${ot.inwarehouseid}' p='d'/>
					</td>
					<td width="10%" align="right">
						出货仓库：
					</td>
					<td width="23%">
						<windrp:warehouse name="poutwarehouseid" value="${ot.poutwarehouseid}"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						换货方联系人：
					</td>
					<td>${ot.rlinkman }
					</td>
					<td align="right">
						换货方电话：
					</td>
					<td>${ot.rtel }</td>
					<td align="right">
						换货方地址：
					</td>
					<td>
					${ot.rtransportaddr }
					</td>
				</tr>
				<tr>
					<td align="right">
						供货方联系人：
					</td>
					<td>${ot.plinkman }
					</td>
					<td align="right">
						供货方电话：
					</td>
					<td>${ot.tel }</td>
					<td align="right">
						供货方方地址：
					</td>
					<td>
						${ot.transportaddr }
					</td>
				</tr>
				<tr>
					<td align="right">
						总金额：
					</td>
					<td colspan="5">
						${ot.totalsum }
					</td>
				</tr>
				<tr>
					<td align="right">
						换货原因：
					</td>
					<td colspan="5">${ot.withdrawcause }
					</td>
				</tr>
			</table>
		</fieldset>
<fieldset align="center">
	<legend>
		<table width="50" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					产品信息
				</td>
			</tr>
		</table>
	</legend>
      <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td>产品编号</td>
          <td> 产品名称 </td>
          <td>规格</td>
          <td> 单位</td>
          <td>库存</td>
          <td>批次</td>
          <td> 数量</td>
          <td>批准数量</td>
        </tr>
        <c:set var="count" value="2"/>
        <logic:iterate id="p" name="list" >
          <tr class="table-back">
            <td><input name="detailid" id="detailid" type="hidden" value="${p.id}">${p.productid}</td>
            <td >${p.productname}</td>
            <td>${p.specmode}</td>
            <td>
            	<windrp:getname key="CountUnit" p="d" value="${p.unitid}"/>
            </td>
            <td><a href="#" onMouseOver="ShowSQ(this,'${p.productid}');" onMouseOut="HiddenSQ();"><img src="../images/CN/stock.gif" width="16"  border="0"></a></td>
            <td>${p.batch }</td>	
            <td>${p.quantity}</td>
            <td>${p.canquantity}</td>
          </tr>
          <c:set var="count" value="${count+1}"/>
        </logic:iterate>
      </table>

      </fieldset>
      	<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr align="center">
				<td width="33%">
					<input type="submit" name="Submit" value="确定">
					&nbsp;&nbsp;
					<input type="button" name="Submit2" value="取消"
						onClick="window.close();">
				</td>
			</tr>
		</table></td>
  </tr>
</table>
</form>
</body>
</html>