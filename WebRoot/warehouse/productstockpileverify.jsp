<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>


<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/validator.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script language="javascript" src="../js/selectDate.js"></script>
<script language="javascript">
function formcheck(){
	if ( !Validator.Validate(document.refer,2) ){
		return false;
	}
	showloading();
	return true;
}
</script>
</head>
<html:errors/>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
              <td width="772">库存产品检验</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<form name="refer" method="post"  action="../warehouse/productStockPileVerifyAction.do" onSubmit="return formcheck()">
		<input type="hidden" value="${ids }" name="ids"/>
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  <input type="hidden" value="${pid }" name="pid">
	    <td  align="right" nowrap>检验状态：</td>
	    <td><windrp:select key="VerifyStatus"  value="${verifyStatus}" name="verifyStatus" p="n|f"/></td>

          </tr>
          <tr>
          	<td width="23%">检验时间：</td>
        	<td width="23%"><input name="verifydate" type="text" onFocus="javascript:selectDate(this)" size="12" value="<windrp:dateformat value="${verifydate}" p="yyyy-MM-dd" />" readonly></td>
          </tr>
          <tr>
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%" colspan="2">
	                <textarea name="remark" cols="180" rows="4" id="remark"  
	                dataType="Limit" max="256"  msg="备注在256个字之内" require="false">${remark}</textarea>
	                <br><span class="td-blankout">(备注长度不能超过256字符)</span>
                </td>
            </tr>
	  </table>
	</fieldset>
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr align="center">
                <td ><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                    <input type="button" name="cancel" onClick="window.close()" value="取消"></td>
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
