<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
	
	var arrid=new Array();
	var arrpordocutname=new Array();
	var arrspecmode = new Array();
	var unitid=new Array();
	var arrcountunit=new Array();
	var arrbatch = new Array();
	var arrstockpile=new Array();
	
	function Affirm(){
		
		
		var flag=false;
		var k=0;
		if(document.listform.pid.length>1){
		for(var i=0;i<document.listform.pid.length;i++){
			if(document.listform.pid[i].checked){
				arrid[k]=document.listform.pid[i].value;//k保证只有选中的才放到数组里
				arrpordocutname[k]=document.listform.productname[i].value;
				arrspecmode[k]=document.listform.specmode[i].value;
				unitid[k]=document.listform.unitid[i].value;
				arrcountunit[k]=document.listform.countunit[i].value;
				arrbatch[k]=document.listform.batch[i].value;
				arrstockpile[k]=document.listform.stockpile[i].value;
				k++;
				flag=true;//只要选中一个就设为true
			}
		}
		}else{
				arrid[0]=document.listform.pid.value;//k保证只有选中的才放到数组里
				arrpordocutname[0]=document.listform.productname.value;
				arrspecmode[k]=document.listform.specmode.value;
				unitid[0]=document.listform.unitid.value;
				arrcountunit[0]=document.listform.countunit.value;
				arrbatch[0]=document.listform.batch.value;
				arrstockpile[0]=document.listform.stockpile.value;
				flag=true;//只要选中一个就设为true
		}
		
		if(flag){
			setCookie("productid",arrid);
			setCookie("productname",arrpordocutname);
			setCookie("specmode",arrspecmode);
			setCookie("unitid",unitid);
			setCookie("countunit",arrcountunit);
			setCookie("batch",arrbatch);
			setCookie("stockpile",arrstockpile);
			window.close();
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Check(){
		if(document.listform.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.listform.length;i++){

			if (!document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.listform.length;i++){
			if (document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=false;
				}
		}
	}
	
	

</script>
</head>

<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择产品</td>
	    
        </tr>
      </table>
      <form name="search" method="post" action="selectWarehouseProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="16%" align="right"><input name="pid" type="hidden" id="pid" value="${wid}">
            名称关键字：</td>
            <td width="39%"><input type="text" name="KeyWord" value="${KeyWord}">
            <input type="submit" name="Submit" value="查询"></td> 
		  	<td width="14%"  align="right">&nbsp;</td>
            <td width="31%" >&nbsp;</td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
	  	    <td width="2%"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="7%" >产品编号</td>
            <td width="18%">产品名称</td>
            <td width="14%">规格</td>
            <td width="10%">单位</td>
            <td width="23%">批次</td>
            <td width="15%">仓库</td>
            <td width="11%">现有数量</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
		  <tr align="center" class="table-back" > 
		  	<td> <input type="checkbox" name="pid" value="${p.productid}" ></td>
            <td >${p.productid}</td>
            <td><input type="hidden" name="productname" value="${p.productname}">${p.productname}</td>
            <td><input type="hidden" name="specmode" value="${p.specmode}">${p.specmode}</td>
            <td><input type="hidden" name="unitid" value="${p.countunit}">
              <input type="hidden" name="countunit" value="${p.unitname}">
              ${p.unitname}</td>
            <td><input name="batch" type="hidden" id="batch" value="${p.batch}">
              ${p.batch}</td>
            <td>              ${p.warehourseidname}</td>
            <td><input name="stockpile" type="hidden" id="stockpile" value="${p.stockpile}">
              ${p.stockpile}</td>
		  </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Affirm();">确定</a></td>
                <td width="60">取消</td>
              </tr>
            </table></td>
          <td width="70%" align="right"> <presentation:pagination target="/sales/selectWarehouseProductAction.do"/>          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
