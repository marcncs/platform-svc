<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
	var parray=new Array();
	function Affirm(){	
		var flag=false;
		var k=0;
		if(document.listform.bid==undefined){return;}
		if(document.listform.bid.length>1){
		for(var i=0;i<document.listform.bid.length;i++){
			if(document.listform.bid[i].checked){
				var p=eval('(' +document.listform.pobj[i].value+ ')'); 
				parray.push(p);
				flag=true;//只要选中一个就设为true
			}
		}
		}else{
				var p=eval('(' +document.listform.pobj.value+ ')'); 	
				parray.push(p);		 
				flag=true;//只要选中一个就设为true
		}
		
		if(flag){		 
			window.returnValue=parray;		 
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
          <td width="772"> 选择送货单</td>
	    
        </tr>
      </table>
      <form name="search" method="post" action="selectBillAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="16%"  align="right"><input name="wid" type="hidden" id="wid" value="${wid}">
            名称关键字：</td>
            <td width="84%"><input type="text" name="KeyWord" maxlength="60"></td>
			<td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
	   <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		  <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td class="SeparatePage"><pages:pager action="../equip/selectBillAction.do"/></td>
        </tr>
      </table>
	   </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
	  <FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top">
            <td width="3%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="23%" >送货单号</td>
            <td width="19%">付款方式</td>
            <td width="33%">开票信息</td>
            <td width="22%">总金额</td>
            
          </tr>
          <logic:iterate id="p" name="sls" >
            <tr align="center" class="table-back-colorbar" >
              <td><input type="checkbox" name="bid" value="${p.id}" ></td>
              <td >${p.id}</td>
              <td>${p.paymentmodename}</td>
              <td> ${p.invmsgname}</td>
              <td>  ${p.totalsum}</td>
             <input type="hidden" name="pobj" id="pobj" value="{sbid:'${p.id}',paymentmode:'${p.paymentmode}',paymentmodename:'${p.paymentmodename}',invmsg:'${p.invmsg}',invmsgname:'${p.invmsgname}',totalsum:'${p.totalsum}'}">
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
