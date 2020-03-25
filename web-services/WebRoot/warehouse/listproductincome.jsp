<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
ProductIncomeDetail();
	}
	
	function addNew(){
		popWin("../warehouse/toAddProductIncomeAction.do",1000,650);
	}

	function Update(){
		if(checkid!=""){
		popWin("../warehouse/toUpdProductIncomeAction.do?ID="+checkid,1000,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function excput(){
		search.target="";
		search.action="../warehouse/excPutProductIncomeAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listProductIncomeAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printListProductIncomeAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listProductIncomeAction.do";
	}
	
	function ProductIncomeDetail(){
		if(checkid!=""){
			document.all.submsg.src="productIncomeDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+"的记录吗？如果删除将永远不能恢复!")){
			popWin2("../warehouse/delProductIncomeAction.do?ID="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.MakeOrganID.value=p.id;
	document.search.oname.value=p.organname;
	clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
	document.search.WarehouseID.value="";
	document.search.wname.value="";
	
	}
	
	function UploadIdcode(){
		popWin("../common/toUploadIdcodeAction.do?billsort=3", 500, 250);
	}
	
	function DownTxt(){
		excputform.target="";
			excputform.action="txtPutProductIncomeAction.do";
			excputform.submit();
		}
	
	function downloadIdcode(){
		popWin("../warehouse/toTxtPutFwcodeAction.do", 500, 250);
		}
		
		this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}
	
	
	function ProConfim(){
		if(checkid!=""){
			if(window.confirm("是否确认生产数据？")){
				popWin2("../warehouse/confimProductIncomeAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="bodydiv">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 出入库管理&gt;&gt;入库&gt;&gt;产成品入库</td>
        </tr>
      </table>
      <form name="search" method="post" action="listProductIncomeAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="12%"  align="right">制单机构：</td>
            <td width="20%"><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="10%" align="right">制单部门：</td>
            <td width="20%"><input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
            <input type="text" name="deptname" id="deptname" value="${deptname}" 
								onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')" 
								 readonly></td>
            <td width="9%" align="right">制单人：</td>
            <td width="25%"><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
            <input type="text" name="uname" id="uname" onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')" value="${uname}" readonly></td>
            <td width="4%">&nbsp;</td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">入货仓库：</td>
            <td><input type="hidden" name="WarehouseID" id="WarehouseID" value="${WarehouseID}">
<input type="text" name="wname" id="wname" onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'w')" value="${wname}" readonly></td>
            <td align="right">是否复核：</td>
            <td><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" /></td>
            <td align="right">入库日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" onFocus="javascript:selectDate(this)" size="10" value="${BeginDate}" readonly>
-
  <input name="EndDate" type="text" id="EndDate" onFocus="javascript:selectDate(this)" value="${EndDate}" size="10" readonly></td>
            <td align="right">&nbsp;</td>
            
          </tr>
           <tr class="table-back"> 
            <td  align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value="${KeyWord}" maxlength="60"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right"><span class="SeparatePage">
              <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
            </span></td>
            
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
<!--           <td width="50" ><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>-->
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50" ><a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50" ><a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50" ><a href="javascript:UploadIdcode();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上传</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
			<td width="50"><a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16"border="0" align="absmiddle">&nbsp;打印</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			
			<td width="80"><a href="javascript:ProConfim();"><img src="../images/CN/import.gif" width="16" height="16"border="0" align="absmiddle">&nbsp;生产确认</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
<!--			<td width="100"><a href="javascript:downloadIdcode();"><img src="../images/CN/downpda.gif" width="16" height="16"border="0" align="absmiddle">&nbsp;下载防伪数据</a></td>-->
										
          <td class="SeparatePage"><pages:pager action="../warehouse/listProductIncomeAction.do"/></td>
        </tr>
      </table>
      </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 600px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
		
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        
          <tr align="center" class="title-top"> 
        
            
            <td  width="140px">编号</td>
            <td  width="140px">内部编号</td>
      <!--  <td  align="center">手工单号</td>--> 
            <td style="width: auto;" >入货仓库</td>
            <td  width="100px" >入库日期</td>            
            <td width="120px" >制单机构</td>
    <!--    <td  align="center" >制单部门</td>--> 
            <td  width="90px" >制单人</td>
            <td  width="50px" >是否复核</td>
            <td  width="50px" >是否确认</td>
          </tr>
          <logic:iterate id="pi" name="alpi" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${pi.id}');"> 
            <td>${pi.id}</td>
            <td>${pi.nccode}</td>
       <!--    <td>${pi.handwordcode}</td> -->  
            <td><windrp:getname key='warehouse' value='${pi.warehouseid}' p='d'/></td>
            <td><windrp:dateformat value='${pi.incomedate}' p='yyyy-MM-dd'/></td>            
            <td><windrp:getname key='organ' value='${pi.makeorganid}' p='d'/></td>  
       <!--     <td><windrp:getname key='dept' value='${pi.makedeptid}' p='d'/></td>-->
            <td><windrp:getname key='users' value='${pi.makeid}' p='d'/></td>
            <td><windrp:getname key='YesOrNo' value='${pi.isaudit}' p='f'/></td>
            <td><windrp:getname key='YesOrNo' value='${pi.confimState}' p='f'/></td>
          </tr>
          </logic:iterate> 
        
      </table>
      
      </form>
      <br>
       <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:ProductIncomeDetail();"><span>产成品入库详情</span></a></li>
          </ul>
        </div>
        <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>	
	  </div>
      </td>
  </tr>
</table>
<form  name="excputform" method="post" action="">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeDeptID" id ="MakeDeptID" value="${MakeDeptID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="WarehouseID" id ="WarehouseID" value="${WarehouseID}">
<input type="hidden" name="IsAudit" id ="IsAudit" value="${IsAudit}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
</body>
</html>
