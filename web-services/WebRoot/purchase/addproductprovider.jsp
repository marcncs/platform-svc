<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectProvide(){
		var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	document.addform.pvid.value=p.pid;
	document.addform.providename.value=p.pname;
}

var allccode=0;
	function ajaxChkCcode(){
	var productid = $F('pdid');
	var provideid = $F('pvid');
	var url = 'ajaxValidateProvideProductAction.do?productid='+productid+'&provideid='+provideid;
	var pars = '';
   	var myAjax = new Ajax.Request(
				url,
				{method: 'post', parameters: pars, onComplete: showResponse1}
				);
    }
	
   function showResponse1(originalRequest){
		var data = eval('(' + originalRequest.responseText + ')');
		var lk = data.count;
		if ( lk == undefined ){
		}else{
			if(lk>=1){
			alert("该产品供应商已存在！");
			allccode=1;
			}else{
			allccode=0;
			}
		}
	
	}

	
function formChk(){
	if ( !Validator.Validate(document.addform,2) ){
		return false;
	}
 	var pdid = $F('pvid');
	if(pdid==null||pdid==""){
		alert("产品供应商不能为空！");
		return false;
	}else{
		if(allccode==1){
		alert("该产品供应商已存在！");
		return false;
		}
	}
	showloading();
	return true;
 	}
 
</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增产品供应商</td>
        </tr>
      </table>
	  <form name="addform" method="post" action="../purchase/addProductProviderAction.do" onSubmit="return formChk();">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right"> <input name="pdid" type="hidden" id="pdid" value="${PDID}">
	  	  产品供应商：</td>
          <td width="29%"><input name="pvid" type="hidden" id="pvid" onpropertychange="ajaxChkCcode()">
            <input name="providename" type="text" id="providename" size="35" dataType="Require" msg="产品供应商不能为空!"   readonly><a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a></td>
          <td width="7%" align="right">价格：</td>
          <td width="18%"><input name="price" type="text" id="price" value="0.00" maxlength="8" onKeyUp="clearNoNum(this)" dataType="Double" msg="价格只能是数字!" require="false"></td>
          <td width="9%" align="right">报价日期：</td>
	      <td width="25%"><input name="pricedate" type="text" id="pricedate" onFocus="selectDate(this);" readonly></td>
	  </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr> 
            <td > <div align="center">
  <input type="submit" name="Submit" value="确定">
  &nbsp;&nbsp;
  <input name="cancel" type="button" id="cancel" value="取消" onClick="javascript:window.close();">
            </div></td>
          </tr>
      </table>
	  </form>
    </td>
  </tr>
</table>
</body>
</html>
