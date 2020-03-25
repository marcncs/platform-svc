<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html>

<head>
<title>发送短信</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	
	function ChkValue(){
		var sc = document.idcodeform.sc;
		var hascheck = false;
		for(i=0; i<sc.length; i++){
			if (sc[i].checked){
				hascheck= true;
				break;
			}
		}		
		if ( !hascheck){
			alert("请选择导出字段！");
			return false;
		}
		document.idcodeform.MakeOrganID.value=opener.xlsform.MakeOrganID.value;
		document.idcodeform.IDCode.value=opener.xlsform.IDCode.value;		
		document.idcodeform.BillID.value=opener.xlsform.BillID.value;
		document.idcodeform.ProvideID.value=opener.xlsform.ProvideID.value;
		document.idcodeform.BeginDate.value=opener.xlsform.BeginDate.value;
		document.idcodeform.EndDate.value=opener.xlsform.EndDate.value;
		document.idcodeform.WarehouseID.value=opener.xlsform.WarehouseID.value;
		document.idcodeform.KeyWord.value=opener.xlsform.KeyWord.value;
		return true;
	}
	
	
	
	
</script>
</head>
<html:errors/>
<body>
<form method="post" name="idcodeform"  action="excPutIdcodeAction.do" onSubmit="return ChkValue();">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
              <td width="772"> 选择导出字段</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>选择</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <td width="155"  ><input type="checkbox" name="sc" value="1" checked>
            序号</td>
          <td width="137"><input type="checkbox" name="sc" value="2" checked>
            产品编号</td>
          <td width="171"><input type="checkbox" name="sc" value="3" checked>
            产品名称</td>
          <td width="305"><input type="checkbox" name="sc" value="4">
            制单机构</td>
     	</tr>
		<tr>
		     <td width="155"  ><input type="checkbox" name="sc" value="5">
            入库单号</td>
			<td  ><input type="checkbox" name="sc" value="6">
            仓库</td>
          <td ><input type="checkbox" name="sc" value="7">
            供应商</td>
          <td ><input type="checkbox" name="sc" value="8">
            入库日期</td>
        </tr>
        
        <tr>         
          <td colspan="4" align="center"><input name="submit" type="submit" value="提交" >
              <input name="button" type="button" onClick="window.close();" value="关闭"></td>
        </tr>
      </table>
	</fieldset>		</td>
      </tr>
    </table></td>
  </tr>
</table>
<input type="hidden" name="MakeOrganID" >
<input type="hidden" name="IDCode" >
<input type="hidden" name="BillID" >
<input type="hidden" name="ProvideID">
<input type="hidden" name="BeginDate" >
<input type="hidden" name="EndDate" >
<input type="hidden" name="WarehouseID" >
<input type="hidden" name="KeyWord">
</form>
</body>
</html>
