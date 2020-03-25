<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/validator.js"></script>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function formChk(){
if ( !Validator.Validate(document.validateWarehouse,2) ){
		return false;
	}
	showloading();
	return true;
}

var cobj ="";
function getResult(getobj,toobj){
	cobj = toobj;
	var areaID = $F(getobj);
	//var y = $F('lstYears');
	var url = '../sales/listAreasAction.do';
	var pars = 'parentid=' + areaID;
   var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showResponse}
				);

}

function showResponse(originalRequest){
	var city = originalRequest.responseXML.getElementsByTagName("area"); 
	var strid = new Array();
	var str = new Array();
	for(var i=0;i<city.length;i++){
		var e = city[i];
		str[i] =new Array(e.getElementsByTagName("areaid")[0].firstChild.data , e.getElementsByTagName("areaname")[0].firstChild.data);
	}
	buildSelect(str,document.getElementById(cobj));//赋值给city选择框
}

function buildSelect(str,sel) {//赋值给选择框
	sel.options.length=0;
	sel.options[0]=new Option("选择","")
	for(var i=0;i<str.length;i++) {
			sel.options[sel.options.length]=new Option(str[i][1],str[i][0])
	}
}
</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增仓库</td>
        </tr>
      </table>
	  <form name="validateWarehouse" method="post" action="../sys/addWarehouseAction.do" onSubmit="return formChk()">
      <input type="hidden" name="oid" value="${oid}">
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%" height="20" align="right">仓库名：</td>
          <td width="21%"><input name="warehousename" type="text" id="warehousename" dataType="Require" msg="必须录入仓库名!"  value="${wf.warehousename}">
            <font color="#FF0000">*</font></td>
            <%--
          <td width="15%" align="right">所属部门：</td>
          <td width="23%"><input type="hidden" name="dept" id="dept">
		<input type="text" name="deptname" id="deptname" onClick="selectDUW(this,'dept','${sjoid}','d')" value="请选择" readonly></td>
	       --%>
	      <td height="20" align="right">内部编码：</td>
	      <td><input name="nccode" type="text" id="nccode" maxlength="16" dataType="Require" msg="请输入内部编码!"><font color="#FF0000">*</font></td>
	      <td width="15%" align="right">负责人：</td>
	      <td width="25%">
	      <input type="text" id="username"  name="username" dataType="Require" msg="必须录入负责人!" value="${ol.name}" style="width:110px" /> <font color="#FF0000">*</font> </td>
	      <%-- 
	      <select id="username" name="username">
	      <c:forEach var="ol" items="${olist}">
	      	<option value="${ol.name}">${ol.name }</option>
	      </c:forEach>
	      
	      </select>
	      --%>
	  
	  </tr>
	  <tr>
	    <td height="20" align="right">联系电话：</td>
	    <td><input name="warehousetel" type="text" id="warehousetel" require="true" dataType="PhoneOrMobile" msg="必须录入正确的联系电话!"> <font color="#FF0000">*</font></td>
	    <td align="right">仓库属性：</td>
	    <td><windrp:select key="WarehouseProperty" name="warehouseproperty" p="n|f" /></td>
	    <td align="right">是否可用：</td>
	    <td><windrp:select key="UseSign" name="useflag" p="n|f" value="1"/></td>
	    </tr>
         <tr>
	    <td height="20" align="right">区域：</td>
	    <td colspan="3"><select name="province"  id="province" onChange="getResult('province','city');" onkeydown="if(event.keyCode==13)event.keyCode=9 ">
          <option value="">-省份-</option>
          <logic:iterate id="c" name="cals">
            <option value="${c.id}">${c.areaname}</option>
          </logic:iterate>
        </select>
-
<select name="city" id="city"  onChange="getResult('city','areas');" onkeydown="if(event.keyCode==13)event.keyCode=9 ">
  <option value="">-城市-</option>
</select>
-
<select name="areas" id="areas" onkeydown="if(event.keyCode==13)event.keyCode=9 ">
  <option value="">-地区-</option>
</select></td>
        <td align="right" >是否自动签收：</td>
	    <td><windrp:select key="YesOrNo" name="isautoreceive" p="n|f" value="${isPd ? '0' :'1' }" /></td>
	    </tr>  
	    <tr>
	    	<td colspan="4"></td>
	    	<td align="right" >是否负库存：</td>
	    	<td><windrp:select key="YesOrNo" name="isMinusStock" p="n|f" value="0"/></td>
	    </tr> 
         <tr>
		    <td height="20" align="right">仓库简称：</td>
		    <td><input name="nccode" type="text" id="nccode" maxlength="16"></td>
		    <td height="20" align="right">仓库库存下限值：</td>
		    <td><input name="belowNumber" type="text" id="belowNumber" maxlength="16" onkeyup="this.value=this.value.replace(/\D/g,'')"></td>
		    <td height="20" align="right">仓库库存上限值：</td>
		    <td><input name="highNumber" type="text" id="highNumber" maxlength="16" onkeyup="this.value=this.value.replace(/\D/g,'')"></td>
	    </tr>	
	    <tr>
	    <td height="20" align="right">仓库地址：</td>
	    <td colspan="5"><input name="warehouseaddr" type="text" id="warehouseaddr" datatype="Require" msg="必须录入仓库地址!" maxlength="120" size="80" onClick="setAddr(this)">
	      <span class="STYLE1">*</span></td>
	    </tr>
	  <tr>
	    <td height="20" align="right">备注：</td>
	    <td colspan="5"><input name="remark" type="text" id="remark" size="80" maxlength="120"></td>
	    </tr>
	  </table>
	</fieldset>
	  
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center"> 
            <td> <input type="submit" name="Submit" value="确定">&nbsp;&nbsp; <input name="cancel" type="button" id="cancel" value="取消" onClick="window.close();"></td>

          </tr>
	  </table>
	  </form>
    </td>
  </tr>
</table>
</body>
</html>
