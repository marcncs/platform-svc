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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<script language=javascript>
 var iteration=0;
 var i=1;
 var chebox=null;
  function addRow(){
    var x = document.all("dbtable").insertRow(dbtable.rows.length);
 
        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);
        var d=x.insertCell(3);
        var e=x.insertCell(4);
        var f=x.insertCell(5);
		var g=x.insertCell(6);
		var h=x.insertCell(7);
		var i=x.insertCell(8);
		a.align="center";
		b.align="center";
		c.align="center";
		d.align="center";
		e.align="center";
		f.align="center";
		g.align="center";
		h.align="center";
		i.align="center";
 
        a.bgColor = "#E4F7D8";
        b.bgColor = "#E4F7D8";
        c.bgColor = "#E4F7D8";
        d.bgColor = "#E4F7D8";
        e.bgColor = "#E4F7D8";
        f.bgColor = "#E4F7D8";
		g.bgColor = "#E4F7D8";
		h.bgColor = "#E4F7D8";
		i.bgColor = "#E4F7D8";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"hidden\" id=\"productid\"><input name=\"productname\" type=\"text\" id=\"productname\" size=\"40\" >";
		c.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" >";
        d.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"8\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly>";
		e.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" >";
        f.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"10\" maxlength=\"10\">";
        g.innerHTML="<select name=\"operatesign\" id=\"select\"><option value=\"+\" selected>增加</option><option value=\"-\">减少</option></select>";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"0\" size=\"8\" maxlength=\"8\">";
		i.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\">";
		i++;
    chebox=document.all("che");  //計算total值

 	//SubTotal(dbtable.rows);
	//TotalSum();
}
	

function SupperSelect(rowx){
	var wid=document.validateProvide.warehouseid.value;
	if(wid==null||wid=="")
	{
		alert("请选择调整仓库！");
		return;
	}
	showModalDialog("toSelectProductAction.do?wid="+wid,null,"dialogWidth:18.5cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	
	var strarrid=getCookie("productid");
	var strarrpordocutname=getCookie("productname");
	var strarrspecmode=getCookie("specmode");
	var strunitid=getCookie("unitid");
	var strarrcountunit=getCookie("countunit");
	var strarrbatch=getCookie("batch");
	var strarrstockpile=getCookie("quantity");
	
	var arrid=strarrid.split(",");
	var arrpordocutname=strarrpordocutname.split(",");
	var arrspecmode = strarrspecmode.split(",");
	var unitid=strunitid.split(",");
	var arrcountunit=strarrcountunit.split(",");
	var arrbatch=strarrbatch.split(",");
	var arrstockpile=strarrstockpile.split(",");
	
	if((dbtable.rows.length-1) <1){
		for(var i=0;i<arrid.length;i++){
				addRow();
				if(i==0){//第一次加时还不是数组
					document.validateProvide.item("productid").value =arrid[0];
					document.validateProvide.item("productname").value =arrpordocutname[0];
					document.validateProvide.item("specmode").value=arrspecmode[0];
					document.validateProvide.item("unitid").value =unitid[0];
					document.validateProvide.item("unit").value =arrcountunit[0];
					document.validateProvide.item("batch").value =arrbatch[0];
					//document.validateProvide.item("quantity").value=0;
					document.validateProvide.item("quantity").value =arrstockpile[0];
					}else{
						document.validateProvide.item("productid")[i].value =arrid[i];
						document.validateProvide.item("productname")[i].value =arrpordocutname[i];
						document.validateProvide.item("specmode")[i].value=arrspecmode[i];
						document.validateProvide.item("unitid")[i].value =unitid[i];
						document.validateProvide.item("unit")[i].value =arrcountunit[i];
						document.validateProvide.item("batch")[i].value =arrbatch[i];
						//document.validateProvide.item("quantity")[i].value=0;
						document.validateProvide.item("quantity")[i].value =arrstockpile[i];
					}
			}
			
			//SubTotal(rowx);
			//TotalSum();
	}else{
		for(var i=0;i<arrid.length;i++){
			addRow();
			document.validateProvide.item("productid")[rowx-1+i].value =arrid[i];//加i是从已经有的row行起算从0行开始
			document.validateProvide.item("productname")[rowx-1+i].value =arrpordocutname[i];
			document.validateProvide.item("specmode")[rowx-1+i].value=arrspecmode[i];
			document.validateProvide.item("unitid")[rowx-1+i].value =unitid[i];
			document.validateProvide.item("unit")[rowx-1+i].value =arrcountunit[i];
			document.validateProvide.item("batch")[rowx-1+i].value =arrbatch[i];
			//document.validateProvide.item("quantity")[rowx-1+i].value=0;
			document.validateProvide.item("quantity")[rowx-1+i].value =arrstockpile[i];
			}
	}
	
}


function deleteR(){
 // 第一行不能放 alert EX.alert("document.table1.chebox[0].checked="+chebox[0].checked);
 //用if來解決chebox在第一次load時其為undifined而不能delete時所使用的方法

    if(chebox!=null){
      if(document.all('dbtable').rows.length==2){
      if(chebox.checked){
         document.all('dbtable').deleteRow(1);  //Row 行是從0開始
      }
    }else{
        //    for (i=chebox.length;i>0;i--){
      for(var i=1;i<=chebox.length;i++){
                if (chebox[i-1].checked) {
 
          document.getElementById('dbtable').deleteRow ( i);
        i=i-1;
        }
 
      }
        }
    }
}


function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	//alert("dbtable.rows.length===="+dbtable.rows.length+"    dbtable.rows.==="+dbtable.rows);
	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value);
		document.validateProvide.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			document.validateProvide.item("subsum")(m).value=sum;	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.validateProvide.item("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.validateProvide.item("subsum")(i).value);
		}
	}
	document.validateProvide.totalsum.value=totalsum;
}


	function Check(){
			//alert("document.activeform.checkall.values");
		if(document.validateProvide.checkall.checked){
			//alert(document.activeform.checkall.values);
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.validateProvide.length;i++){

			if (!document.validateProvide.elements[i].checked)
				if(validateProvide.elements[i].name != "checkall"){
				document.validateProvide.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.validateProvide.length;i++){
			if (document.validateProvide.elements[i].checked)
				if(validateProvide.elements[i].name != "checkall"){
				document.validateProvide.elements[i].checked=false;
				}
		}
	}

	function ChkValue(){
		var outwarehouseid = document.validateProvide.outwarehouseid;
		var inwarehouseid = document.validateProvide.inwarehouseid;
		
		if(outwarehouseid.value==null ||outwarehouseid.value==""){
			alert("请选择调出仓库");
			return false;
		}
		if(inwarehouseid.value==null ||inwarehouseid.value==""){
			alert("请选择调入仓库");
			return false;
		}
		if(outwarehouseid.value==inwarehouseid.value){
			alert("请选择不同的仓库");
			//totalsum.focus();
			return false;
		}

		
		return true;
	}

</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<form name="validateProvide" method="post" action="addStockAdjustAction.do" onSubmit="return ChkValue();">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增库存调拨单</td>
        </tr>
      </table>
      <table width="100%" height="78" border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back">
            <td width="12%"  align="right">调整日期：</td>
            <td width="35%"><input name="adjustdate" type="text" id="adjustdate" onFocus="javascript:selectDate(this)"></td>
            <td width="18%" align="right">调整仓库：</td>
            <td width="35%"><select name="warehouseid" id="warehouseid">
              <option value="">请选择仓库</option>
              <logic:iterate id="w" name="alw" >
                <option value="${w.id}">${w.warehousename}</option>
              </logic:iterate>
            </select></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">调整原因：</td>
            <td><textarea name="adjustcause" cols="40" rows="4" id="adjustcause"></textarea></td>
            <td align="right">备注：</td>
            <td><textarea name="remark" cols="40" rows="4" id="remark"></textarea></td>
          </tr>
          <tr class="table-back"> 
            <td  colspan="4" align="right"><table  border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="back-blue-light2">
                <!--<td width="60"><a href="javascript:addRow();">增加</a></td>-->
                <td width="90">选择产品： <img src="../images/CN/find.gif" width="18" height="18" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" > </td>
                <td width="60"><a href="javascript:deleteR();">删除</a></td>
              </tr>
            </table></td>
          </tr>
        </table> 
      <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="3%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
          <td width="24%" > 产品</td>
          <td width="19%">规格</td>
          <td width="9%"> 单位</td>
          <td width="14%">批次</td>
          <td width="10%"> 单价</td>
          <td width="11%"> 操作符</td>
          <td width="10%">数量</td>
          <td width="10%"> 金额</td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" class="table-back">
            <input type="submit" name="Submit" value="提交">
            &nbsp;&nbsp;
          <input type="button" name="button" value="取消" onClick="history.back();">          </td>
        </tr>
      </table></td>
  </tr>

</table>
</form>
</body>
</html>
