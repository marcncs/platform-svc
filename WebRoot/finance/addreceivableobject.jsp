<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script language="javascript">
	
	function SelectCustomer(){
		var os = document.search.objectsort.value;
		if(os==0){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		document.search.cid.value=o.id;
		document.search.payer.value=o.organname;
		}
		if(os==1){
		var m=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		document.search.cid.value=m.cid;
		document.search.payer.value=m.cname;
		}
		if(os==2){
		var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		document.search.cid.value=p.pid;
		document.search.payer.value=p.pname;
		}

	}

function Clear(){
	document.search.cid.value="";
	document.search.payer.value="";
}

function Check(){
	var cid = document.search.cid.value;
	
	if(cid==""){
		alert("付款方不能为空！");
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
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 新增应收款对象</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	
	<form name="search" method="post" action="addReceivableObjectAction.do" onSubmit="return Check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="13%"  align="right">对象类型：</td>
          <td width="22%"><select name="objectsort" onChange="Clear();">
            <option value="0">机构</option>
			<option value="1" selected>会员</option>
            <option value="2">供应商</option>
          </select></td>
          <td width="8%" align="right">付款方：</td>
          <td width="30%"><input name="cid" type="hidden" id="cid">
            <input name="payer" type="text" id="payer" size="35" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
	      </tr>
	  </table>
	</fieldset>
	
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
       <tr>
          <td><div align="center">
            <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
            <input type="reset" name="cancel" onClick="javascript:window.close()" value="取消">
          </div></td>
        </tr>
      
     
    </table>
	</form>
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
