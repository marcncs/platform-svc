<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var arrid=new Array();
	var arrtotalsum=new Array();
	var arrmakedate = new Array();

	function Affirm(){
		var flag=false;
		var k=0;
		if(document.listform.soid==undefined){
			alert("请选择你要操作的记录!");
			return;
		}
		if(document.listform.soid.length>1){
			for(var i=0;i<document.listform.soid.length;i++){
				if(document.listform.soid[i].checked){
					arrid[k]=document.listform.soid[i].value;//k保证只有选中的才放到数组里
					arrtotalsum[k]=document.listform.totalsum[i].value;
					arrmakedate[k]=document.listform.makedate[i].value;
					
					k++;
					flag=true;//只要选中一个就设为true
				}
			}
		}else{
				arrid[0]=document.listform.soid.value;//k保证只有选中的才放到数组里
				arrtotalsum[0]=document.listform.totalsum.value;
				arrmakedate[k]=document.listform.makedate.value;
				flag=true;//只要选中一个就设为true
		}
		
		if(flag){
			var so={soid:arrid.slice(0),totalsum:arrtotalsum.slice(0),makedate:arrmakedate.slice(0)};
			window.returnValue=so;

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
	
	this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}

</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择零售单</td>
        </tr>
      </table>
      <form name="search" method="post" action="../sales/selectSaleOrderAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
		      <td width="9%"  align="right"><input name="cid" type="hidden" id="cid" value="${cid}">
	          制单日期：</td>
              <td width="23%" ><input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
-
  <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)"></td>
              <td width="8%"  align="right">关键字：</td>
            <td width="45%" ><input type="text" name="KeyWord"></td>
            <td width="15%" align="right" ><span class="SeparatePage">
              <input name="Submit2" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
            </span></td>
          </tr>
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td class="SeparatePage"><pages:pager action="../sales/selectSaleOrderAction.do"/>
          </td>
        </tr>
      </table>
	   </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		  <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
      
          <tr align="center" class="title-top">
            <td width="3%"><input type="checkbox" name="checkall" onClick="Check();" ></td> 
            <td width="21%" >零售单号</td>
            <td width="55%">金额</td>
            <td width="21%">制单日期</td>
          </tr>
          <logic:iterate id="c" name="sls" > 
          <tr class="table-back-colorbar">
            <td><input name="soid" type="checkbox" id="soid" value="${c.id}" ></td> 
            <td >${c.id}</td>
            <td>${c.totalsum}
              <input name="totalsum" type="hidden" id="totalsum" value="${c.totalsum}"></td>
            <td>${c.makedate}
              <input name="makedate" type="hidden" id="makedate" value="${c.makedate}"></td>
          </tr>
          </logic:iterate> 
      
      </table>
        </form>
	  </div>
    </td>
  </tr>
</table>
</body>
</html>
