<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/function.js"></SCRIPT>
<script type=text/javascript src="../js/prototype.js"></script>
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/showSQ.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
  function addRow(p){
    var x = document.all("dbtable").insertRow(dbtable.rows.length);
 
        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);
        var d=x.insertCell(3);
        var e=x.insertCell(4);
        var f=x.insertCell(5);
		var g=x.insertCell(6);
		var h=x.insertCell(7);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";		
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"' size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" value='"+p.productname+"' size=\"35\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" value='"+p.specmode+"' size=\"35\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+p.unitid+"' size=\"8\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" value='"+p.unitidname+"' readonly>";
		f.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" value='"+p.batch+"' readonly>";
		g.innerHTML="<a href=\"#\" onMouseOver=\"ShowSQ(this,'"+p.productid+"');\" onMouseOut=\"HiddenSQ();\"><img src=\"../images/CN/stock.gif\" width=\"16\" border=\"0\"></a>";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"8\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
}

function SupperSelect(){
var wid=document.validateProvide.warehouseid.value;
	if(wid==null||wid=="")
	{
		alert("请选择仓库！");
		return;
	}
	var p=showModalDialog("../common/toSelectWarehouseBatchProductAction.do?wid="+wid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return ;}
	for(var i=0;i<p.length;i++){	
		if ( isready('productid', p[i].productid) && isready('batch', p[i].batch)){
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
        var productid = document.validateProvide.productid;
		var warehouseid = document.validateProvide.warehouseid;
		if(warehouseid.value==null||warehouseid.value==""){
			alert("仓库不能为空");
			return false;
		}
		if ( !Validator.Validate(document.validateProvide,2) ){
		return false;
		}
		if ( productid==undefined){
			alert("请选择产品!");	
			return false;
		}
		showloading();
		return true;
	}

	function SelectOrgan(){
		var from = document.validateProvide;
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		from.organid.value=p.id;
		from.orgname.value=p.organname;
		from.owname.value="";
			
	}	
</script>

</head>
<body style="overflow:auto">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增盘亏单</td>
        </tr>
      </table>
       <form name="validateProvide" method="post" action="addOtherShipmentBillAction.do" onSubmit="return ChkValue();">
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
	     <td width="10%" align="right">出货机构：</td>
         <td>
	      <input name="organid" type="hidden" id="organid">
		  <input name="orgname" type="text" id="orgname" size="30" dataType="Require" msg="必须录入出货机构!" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
		<span class="style1">*</span>
	    </td>
   	      <td width="10%" align="right">出货仓库：</td>
		<td width="24%">
			<input type="hidden" name="warehouseid" id="warehouseid">
			<input type="text" name="owname" id="owname" onClick="selectDUW(this,'warehouseid',$F('organid'),'rw','')" value="" readonly>	
			<span class="STYLE1">*</span>
		</td>	
											
          <td width="9%" align="right">出库类别：</td>
          <td width="23%"><windrp:select key="ShipmentSort" name="shipmentsort" p="n|f"/></td>
	  </tr>
	  <tr>
	      <td width="9%" align="right">相关单据：</td>
	      <td width="25%"><input type="text" name="billno" maxlength="32"></td>
	    <td  align="right">需求日期：</td>
	    <td><input name="requiredate" type="text" id="requiredate" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" onFocus="javascript:selectDate(this)" readonly></td>
	    <td align="right"><input type="checkbox" name="isaccount"/>&nbsp;</td>
	    <td>是否记账</td>
	    </tr>
	  </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr> 
                  <td width="100%" ><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td><img src="../images/CN/selectp.gif" border="0" style="cursor:pointer" onClick="SupperSelect()" ></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<td width="2%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="10%">产品编号</td>
                <td width="19%" > 产品名称</td>
                <td width="18%">规格型号</td>
                <td width="10%"> 单位</td>
                <td width="10%">批次</td>
                <td width="10%">库存</td>
                <td width="10%"> 数量</td>
                </tr>
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="2%" ><a href="javascript:deleteR('che','dbtable');"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="10%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="64%" align="right">&nbsp;</td>
                <td width="15%">&nbsp;</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false"></textarea></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center"><input type="Submit" name="Submit" value="提交">              &nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
