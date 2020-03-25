<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
		Detail();
	}

	function addNew(){
		popWin("../machin/toAddAssembleAction.do",1000,650);
	}

	function Update(){
		if(checkid!=""){
		popWin("toUpdAssembleAction.do?ID="+checkid,1000,650);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="assembleDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function excput(){
		search.target="";
		search.action="../machin/excPutAssembleAction.do";
		search.submit();
	}	
	function oncheck(){
		search.target="";
		search.action="../machin/listAssembleAction.do";
		search.submit();
	}
	function print(){
		search.target="_blank";
		search.action="../machin/printListAssembleAction.do";
		search.submit();
	}

	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+" 的组装单吗？如果删除将永远不能恢复!")){
			popWin("../machin/delAssembleAction.do?ID="+checkid,500,250);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function SaleBill(){
		if(checkid!=""){
			popWin("../sales/saleBillAction.do?ID="+checkid,900,600);
		}else{
			alert("请选择你要操作的记录!");
		}
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
          <td width="772">生产组装>>组装 </td>
  </tr>
</table>
 <form name="search" method="post" action="../machin/listAssembleAction.do" onSubmit="return oncheck();">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="9%"  align="right">是否复核：</td>
            <td width="24%"><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}"/></td>
           <!-- <td width="10%" align="right">是否结案：</td>
            <td width="18%"><windrp:select key="YesOrNo" name="IsEndcase" p="y|f"/></td>-->
            <td width="13%" align="right">预计完工日期：</td>
            <td width="23%"><input type="text" name="BeginDate" size="12"  value="${BeginDate}" readonly onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" size="12" readonly value="${EndDate}" onFocus="javascript:selectDate(this)"></td>
            <td class="SeparatePage"><input name="Submit" type="image" border="0" src="../images/CN/search.gif"></td>
          </tr>
        
      </table>
</form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Del()"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
         <td width="50">
		<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
	    </td>
	    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		<td width="51">
			<a href="javascript:print();"><img
					src="../images/CN/print.gif" width="16" height="16"
					border="0" align="absmiddle">&nbsp;打印</a>
		</td>
		<td width="1">
			<img src="../images/CN/hline.gif" width="2" height="14">
		</td>
          <td class="SeparatePage"><pages:pager action="../machin/listAssembleAction.do"/></td>
        </tr>
      </table>
      </div>
					</td>
				</tr>
				<tr>
					<td>
					<div id="listdiv" style="overflow-y: auto; height: 650px;" >
					<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        
          <tr align="center" class="title-top"> 
            <td width="12%" >编号</td>
            <td width="11%" >组装产品编码</td>
            <td width="18%">组装产品名称</td>
            <td width="14%" >规格</td>
            <td width="7%" >单位</td>
            <td width="11%" >数量</td>
            <td width="9%" >预计完工日期</td>
            <td width="8%" >是否复核</td>
            <!--<td width="10%" >是否结案</td>-->
          </tr>
	<logic:iterate id="s" name="alpi" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
            <td >${s.aproductid}</td>
            <td >${s.aproductname}</td>
            <td >${s.aspecmode}</td>
            <td ><windrp:getname key='CountUnit' value='${s.aunitid}' p='d'/></td>
            <td >${s.aquantity}</td>
            <td ><windrp:dateformat value='${s.completeintenddate}' p='yyyy-MM-dd'/></td>
            <td ><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
            <!--<td ><windrp:getname key='YesOrNo' value='${s.isendcase}' p='f'/></td>-->
          </tr>
		  </logic:iterate>
        
      </table>
      </form>
	  <br>
	  <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>组装详情</span></a></li>
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
