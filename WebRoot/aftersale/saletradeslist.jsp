<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
	var checkid="";
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
		popWin("toAddSaleTradesAction.do",1100,650);
	}

	function Update(){
		if(checkid!=""){
		popWin("toUpdSaleTradesAction.do?ID="+checkid,1100,650);
		}else{
		alert("请选择你要操作的记录!");
		}
	}


	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为:"+checkid+"的记录吗？如果删除将永远不能恢复!")){
			popWin2("../aftersale/delSaleTradesAction.do?id="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
		document.all.submsg.src="../aftersale/saleTradesDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	function excput(){
		search.target="";
		search.action="../aftersale/excPutSaleTradesAction.do";
		search.submit();
	}
	function oncheck(){
		search.target="";
		search.action="../aftersale/listSaleTradesAction.do";
		search.submit();
	}
	function print(){
		search.target="_blank";
		search.action="../aftersale/printListSaleTradesAction.do";
		search.submit();
		
	}
	function DownTxt(){
		search.target="";
		search.action="txtPutSaleTradesAction.do";
		search.submit();
	}
	function UploadIdcode(){
		popWin("../common/toUploadIdcodeAction.do?billsort=15", 500, 250);
	}
	
	function SelectCustomer(){
		var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if(c==undefined){return;}
		document.search.CID.value=c.cid;
		document.search.cname.value=c.cname;
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
          <td width="772">零售管理>>零售换货</td>
  </tr>
</table>
<form name="search" method="post" action="../aftersale/listSaleTradesAction.do" onSubmit="return oncheck();">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="11%"  align="right">会员名称：</td>
            <td width="26%"><input name="CID" type="hidden" id="CID" value="${CID}">
              <input name="cname" type="text" id="cname" value="${cname}" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"> </a></td>
            <td width="8%" align="right">是否复核：</td>
            <td width="16%"><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" /></td>
            <td width="10%" align="right">制单日期：</td>
            <td width="25%"><input name="BeginDate" type="text" onFocus="javascript:selectDate(this)" size="10" value="${BeginDate}" readonly>
-
  <input name="EndDate" type="text" onFocus="javascript:selectDate(this)" size="10" value="${EndDate}" readonly></td>
            <td width="4%">&nbsp;</td>
          </tr>
          <tr class="table-back">
            <td  align="right">是否作废：</td>
            <td><windrp:select key="YesOrNo" name="IsBlankOut" p="y|f" value="${IsBlankOut}"/></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="SeparatePage">
              <input name="Submit2" type="image" id="Submit" 
					src="../images/CN/search.gif"  title="查询">
            </td>
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
          <td width="50"><a href="javascript:UploadIdcode();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上传</a></td>	
        <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
        <td width="50"><a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>
             <td width="50"><a href="javascript:excput();"><img 
            src="../images/CN/outputExcel.gif" width="16" height="16" 
            border="0" align="absmiddle">&nbsp;导出</a></td>
		  <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		  <td width="50"><a href="javascript:print();"><img
			src="../images/CN/print.gif" width="16" height="16"
			border="0" align="absmiddle">&nbsp;打印</a>
			</td>
			<td width="1">
				<img src="../images/CN/hline.gif" width="2" height="14">
			</td>
          <td class="SeparatePage"><pages:pager action="../aftersale/listSaleTradesAction.do" />
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
            <td width="13%"  >编号</td>
            <td width="14%" >客户名称</td>
            <td width="9%" >联系人</td>
            <td width="10%" >换货入货仓库</td>           
            <td width="14%" >制单机构</td>
            <td width="9%" >制单人</td>
            <td width="10%" >制单日期</td>
            <td width="7%" >是否复核</td>
			<td width="7%" >是否作废</td>
			<td width="7%" >是否发货</td>
          </tr>
          <logic:iterate id="s" name="also" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
            <td>${s.cname} <a href="../sales/listMemberAction.do?CID=${s.cid}"><img src="../images/CN/go.gif" width="10" height="10" border="0"></a></td>
            <td>${s.clinkman}</td>
            <td><windrp:getname key='warehouse' value='${s.warehouseinid}' p='d'/></td>            
            <td><windrp:getname key='organ' value='${s.makeorganid}' p='d'/></td>
            <td><windrp:getname key='users' value='${s.makeid}' p='d'/></td>
            <td><windrp:dateformat value='${s.makedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
			<td><windrp:getname key='YesOrNo' value='${s.isblankout}' p='f'/></td>
			<td><windrp:getname key='YesOrNo' value='${s.isendcase}' p='f'/></td>
            </tr>
          </logic:iterate>
      </table>
	  <br>
	     <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>换货详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 

frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>

	</div>
</td>
  </tr>
</table>
</body>
</html>
