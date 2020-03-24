<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
function check(){
	if ( !Validator.Validate(document.refuse,2) ){
		return false;
	}
	return true;
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
          <td width="869">${menu} </td>
          <td width="364" align="right">
          </td>
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
	  	<td width="9%"  align="right">订购日期：</td>
          <td width="25%"><windrp:dateformat value="${smf.movedate}" p="yyyy-MM-dd"/></td> 
          <td width="9%" align="right">制单机构：</td>
          <td width="23%"><windrp:getname key='organ' value='${smf.makeorganid}' p='d'/></td>
	      <td width="9%" align="right">调出仓库：</td>
	      <td width="25%"><windrp:getname key='warehouse' value='${smf.outwarehouseid}' p='d'/></td>
	  </tr>
	  <tr>
	    <td  align="right">调入机构：</td>
	    <td><windrp:getname key='organ' value='${smf.receiveorganid}' p='d'/></td>
	    <td align="right">调入仓库：</td>
	    <td><windrp:getname key='warehouse' value='${smf.inwarehouseid}' p='d'/>
	    </td>
	    <td align="right"></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td align="right">收货地址：</td>
	    <td colspan="3">${smf.transportaddr}</td>
	    </tr>
	  <tr>
	    <td  align="right">调拨原因：</td>
	    <td colspan="5">${smf.movecause}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${smf.remark}</td>
	    </tr>
	  </table>
	</fieldset>
	<form name="resufe" method="post" action="../warehouse/refuseStockAlterMoveReceiveAction.do" onSubmit="return check();">
	<input type="hidden" name="smid" value="${smid}" >
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>拒签理由</td>
        </tr>
      </table>
	  </legend>
      <table  width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr  >
        	<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;原因:&nbsp;&nbsp;  </td>
        	<td align="left">
        	<textarea name="refuseReason" rows="10" cols="150" dataType="Require" msg="原因不能为空!"></textarea>
        	</td>
        </tr>
      </table>
	  </fieldset>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  	  <tr align="center"> 
              <td width="33%">
              	&nbsp;
              </td>
          </tr>
          <tr align="center"> 
              <td width="33%"><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
              <input type="button" name="Submit2" value="取消"  onClick="window.close();"> 
              </td>
          </tr>
      </form>     
      </table>
</td>
  </tr>
</table>
</body>
</html>
