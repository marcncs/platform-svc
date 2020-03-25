<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>转生产数据为产成品入库单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
	

function SupperSelect(rowx){
	var p = showModalDialog("../common/toSelectOrganProductAction.do",null,"dialogWidth:21cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if (p==undefined){return;}
	for(var i=0;i<p.length;i++){	
		if ( isready('productid', p[i].productid) ){
			continue;
		}
		addRow(p[i]);	
		
	}
	
}

	function Check(){
		var pid = document.all("che");
		var checkall = document.all("checkall");
		if (pid==undefined){return;}
		if (pid.length){
			for(i=0;i<pid.length;i++){
					pid[i].checked=checkall.checked;
			}
		}else{
			pid.checked=checkall.checked;
		}		
	}	

	function ChkValue(){
		var warehouseid = document.validateProvide.warehouseid;
		var incomedate = document.validateProvide.incomedate;
		var lotNo =document.validateProvide.lotNo;

		if(warehouseid.value==null||warehouseid.value==""){
			alert("仓库不能为空");
			//totalsum.focus();
			return false;
		}

		if(incomedate.value==null||incomedate.value==""){
			alert("入库日期不能为空");
			//totalsum.focus();
			return false;
		}
		
		if ( lotNo.value==""){
			alert("请选择批次号！");
			return false;
		}
		
		if ( !Validator.Validate(document.validateProvide,2) ){
			return false;
		}

		showloading();
		validateProvide.submit();
	}
	
	
</script>
</head>

<body style="overflow:auto">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 转生产数据为产成品入库单</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="../machin/exportProductIncomeAction.do">
         
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
         <tr> 
            <td >
			
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">入货仓库：</td>
          <td width="21%"><windrp:warehouse name="warehouseid"/></td>
          <td width="13%" align="right">手工单号：</td>
          <td width="23%"><input name="handwordcode" type="text" id="handwordcode"></td>
	      <td width="9%" align="right">入库日期：</td>
	      <td width="25%"><input name="incomedate" type="text" id="incomedate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"></td>
	  </tr>
	  <tr>
	    <td  align="right">入库类别：</td>
	    <td><windrp:select key="ProductIncomeSort" name="productincomesort" p="n|f"/></td>
	    <td width="13%" align="right">生产批号：</td>
          <td width="23%"><input name="LotNo" type="text" id="lotNo"></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	      <tr heigh=50>
            <td  align="center"> &nbsp;&nbsp; </td>
          </tr>
          <tr>
            <td  align="right"><input type="button" name="Submit" onClick="ChkValue();" value="提交">  &nbsp;&nbsp; &nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;</td>
          </tr>
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
