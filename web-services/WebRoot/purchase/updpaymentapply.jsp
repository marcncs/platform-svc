<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	document.referForm.pid.value=p.pid;
	document.referForm.pname.value=p.pname;
}

function SelectLinkman(){
	var pid=document.referForm.pid.value;
	if(pid==null||pid=="")
	{
		alert("请选择供应商！");
		return;
	}
	var l=showModalDialog("../common/selectPlinkmanAction.do?pid="+pid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	if ( l == undefined ){
		return;
	}
	document.referForm.plinkman.value=l.lname;
	document.referForm.tel.value=l.ltel;
}
</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
              <td> 修改付款申请 </td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<form name="referForm" method="post" action="updPaymentApplyAction.do" >
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right"><input name="id" type="hidden" id="id" value="${pbf.id}">
	  	  供应商：</td>
          <td width="26%"><input name="pid" type="hidden" id="pid" value="${pbf.pid}">
            <input name="pname" type="text" id="pname" value="${pbf.pname}" size="35" readonly>
            <a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
          <td width="9%" align="right">联系人：</td>
          <td width="22%"><input name="plinkman" type="text" id="plinkman" value="${pbf.plinkman}">
            <a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
          <td width="9%" align="right">联系电话：</td>
	      <td width="25%"><input name="tel" type="text" id="tel" value="${pbf.tel}" ></td>
	  </tr>
	  <tr>
	    <td  align="right">采购人：</td>
	    <td><select name="purchaseid" id="purchaseid">
          <logic:iterate id="d" name="als">
            <option value="${d.userid}" ${d.userid==pbf.purchaseid?"selected":""}>${d.realname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">结算方式：</td>
	    <td>${pbf.paymentmodename}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
		 <tr>
	    <td  align="right">户名：</td>
	    <td><input type="text" name="doorname" value="${pbf.doorname}"></td>
	    <td align="right">开户行：</td>
	    <td><input type="text" name="bankname" value="${pbf.bankname}"></td>
	    <td align="right">帐号：</td>
	    <td><input name="bankaccount" type="text" id="bankaccount" value="${pbf.bankaccount}"></td>
	    </tr>
	  <tr>
	  <tr>
	    <td  align="right">采购部门：</td>
	    <td><select name="purchasedept" id="purchasedept">
          <logic:iterate id="d" name="aldept">
            <option value="${d.id}" <c:if test="${d.id==pbf.purchasedept}"> selected </c:if>>${d.deptname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">相关单据号：</td>
	    <td><input name="billno" type="text" id="billno" value="${pbf.billno}"></td>
	    <td align="right">金额：</td>
	    <td><input name="totalsum" type="text" id="totalsum" value="${pbf.totalsum}"></td>
	    </tr>
	  </table>
	</fieldset>
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
            
              <tr>
                <td width="32%"><div align="center">
                  <input type="submit" name="Submit" value="确定">
                  &nbsp;&nbsp;
                  <input type="reset" name="cancel" onClick="javascript:history.back()" value="取消">
                </div></td>
              </tr>
            
        </table>
		</form>
		</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
