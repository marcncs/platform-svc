<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	OtherShipmentDetail();
	}

	function addNew(){
	popWin("../warehouse/toAddDrawShipmentBillAction.do",1000,650);
	}

	function Update(){
		if(checkid!=""){
		popWin("toUpdDrawShipmentBillAction.do?ID="+checkid,1000,650);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	function UploadIdcode(){
		popWin("../common/toUploadIdcodeAction.do?billsort=16", 500, 250);
	}
	
	function OtherShipmentDetail(){
		if(checkid!=""){
			document.all.submsg.src="drawShipmentBillDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function excput(){
		search.target="";
		search.action="../warehouse/excPutDrawShipmentBillAction.do";
		search.submit();
	}
	function oncheck(){
		search.target="";
		search.action="../warehouse/listDrawShipmentBillAction.do";
		search.submit();
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printListDrawShipmentBillAction.do";
		search.submit();
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+"的记录吗？如果删除将永远不能恢复!")){
			popWin2("../warehouse/delDrawShipmentBillAction.do?OSID="+checkid);
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
	
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
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
          <td width="772"> 仓库管理&gt;&gt;库存处理&gt;&gt;领用</td>
  </tr>
</table>

      <form name="search" method="post" action="../warehouse/listDrawShipmentBillAction.do" onSubmit="return oncheck();">
         <table width="100%"  border="0" cellpadding="0" cellspacing="0">
         
          <tr class="table-back">
            <td  align="right">制单机构：</td>
            <td>
              <input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
            <input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td align="right">制单部门：</td>
								<td>
								<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
								<input type="text" name="deptname" id="deptname" value="${deptname}" 
								onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')" 
								 readonly>
								</td>
            <td align="right">制单人：</td>
            <td><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
              <input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
										value="${uname}" readonly></td>
            <td>&nbsp;</td>
          </tr>
		  <tr class="table-back"> 
            <td width="8%"  align="right">出货仓库：</td>
            <td width="26%"><input type="hidden" name="WarehouseID" id="WarehouseID" value="${WarehouseID}">
              <input type="text" name="wname" id="wname" 
				onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'w')" 
				value="${wname}" readonly></td>
            <td width="12%" align="right">是否复核：</td>
            <td width="24%"><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" /></td>
            <td width="9%" align="right">是否作废：</td>
            <td width="17%"><windrp:select key="YesOrNo" name="IsBlankOut" p="y|f" value="${IsBlankOut}" /></td>
            <td width="4%">&nbsp;</td>
          </tr>
		   <tr class="table-back">
		   <td width="10%" align="right">是否结案：</td>
            <td width="19%"><windrp:select key="YesOrNo" name="IsEndcase" p="y|f" value="${IsEndcase}"/></td>
            <td  align="right">领用日期：</td>
            <td><input name="BeginDate" value="${BeginDate}"  type="text" onFocus="javascript:selectDate(this)" size="12" readonly>
-
  <input name="EndDate" value="${EndDate}" type="text" onFocus="javascript:selectDate(this)" size="12" readonly></td>
            <td align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value="${KeyWord}"></td>
   
            <td align="right"><span class="SeparatePage">
              <input name="Submit2" type="image" id="Submit" 
					src="../images/CN/search.gif"  title="查询">
            </span></td>
	      </tr>
       
      </table>
 </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
          <td width="50"><a href="javascript:Update();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;修改</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
          <td width="50"><a href="javascript:Del()"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
          <td width="50" ><a href="javascript:UploadIdcode();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上传</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50">
			<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
		    </td>
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
          <td width="50"><a href="javascript:print();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
          <td class="SeparatePage" style="text-align: right;"><pages:pager action="../warehouse/listDrawShipmentBillAction.do" />
          </td>
        </tr>
      </table>
	   </div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
          <tr align="center" class="title-top"> 
            <td width="12%" >编号</td>
            <td width="21%">出货仓库</td>
			<td width="9%">领用日期</td>
            <td width="22%">制单机构</td>
            <td width="15%">制单人</td>
            <td width="8%">是否复核</td>   
            <td width="6%">是否结案</td>         
            <td width="7%">是否作废</td>            
          </tr>
	<logic:iterate id="s" name="alsb" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
            <td><windrp:getname key='warehouse' value='${s.warehouseid}' p='d'/></td>
            <td><windrp:dateformat value='${s.drawdate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='organ' value='${s.makeorganid}' p='d'/></td>
            <td><windrp:getname key='users' value='${s.makeid}' p='d'/></td>
            <td><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
            <td><windrp:getname key='YesOrNo' value='${s.isendcase}' p='f'/></td>
            <td><windrp:getname key='YesOrNo' value='${s.isblankout}' p='f'/></td>            
            </tr>
		  </logic:iterate>
      </table>
	  <br>
      <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:OtherShipmentDetail();"><span>领用出库详情</span></a></li>
          </ul>
        </div>
        <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>	
    
					</div>
</td>
  </tr>
</table>

</body>
</html>
