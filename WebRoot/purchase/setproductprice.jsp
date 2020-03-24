<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<script language="javascript">
function formChk(){
if ( !Validator.Validate(document.validateProduct,2) ){
		return false;
	}
	showloading();
	return true;
}
</script>

</head>

<body style="overflow:auto">

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 零售产品价格设置</td>
  </tr>
</table>

    <form action="../purchase/setProductPriceAction.do" method="post" name="validateProduct" onSubmit="return formChk()">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <td width="16%"  align="right"><p>产品编号：</p>
            </td>
          <td width="30%"><input name="productid" type="text" id="productid" value="${pid}" readonly></td>
          <td width="15%" align="right">产品名称：</td>
          <td width="39%"><input name="productname" type="text" id="productname" value="${productname}" readonly></td>
        </tr>
      </table>
		
          <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
            <tr align="center" class="title-top">
              <td width="40%" >单位</td>
              <td width="29%">价格政策</td>
              <td width="21%">价格</td>
            </tr>

            <logic:iterate id="p" name="spals" > 
			<c:forEach items="${p.ppls}" var="d">
            <tr align="center" class="table-back">
              <td  align="left"><input name="unitid" type="hidden" id="unitid" value="${p.unitid}">
              <input name="unitidname" type="text" id="unitidname" value="${p.unitidname}" size="4" readonly></td>
              <td align="left"><input name="policyid" type="hidden" id="policyid" value="${d.policyid}">
              <input name="policyidname" type="text" id="policyidname" value="${d.policyidname}" size="25" readonly></td>
              <td align="left"><input name="unitprice" type="text" value="<windrp:format value='${d.unitprice}'/>" size="10" maxlength="8" onKeyPress="KeyPress(this)" dataType="Double" msg="价格只能是数字!"></td>
            </tr>
			</c:forEach> 
			</logic:iterate> 
        </table>
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td align="center"><input type="submit" name="Submit" value="提交">
                &nbsp;&nbsp;
                <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
            </tr>
          </table>
	</fieldset>
          
          
        </form>

    </td>
  </tr>
</table>
</body>
</html>
