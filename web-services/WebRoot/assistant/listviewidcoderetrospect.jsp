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
<SCRIPT language="javascript" src="../js/showbillnopurview.js"></SCRIPT>

<script language="JavaScript">
	var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}
	
	function excput(){
		var pitablelenght = pitable.rows.length - 1;
		if(pitablelenght <= 0){
			alert("暂无查询数据，不能导出！");
			return false;
		}else{
			var batch = search.batch.value.trim();
			var produceDate = search.produceDate.value.trim();
			if (batch=='' && produceDate==''){
				alert("批次和生产日期必须输入一项！");
				return false;
			}else{
				search.action="../assistant/excPutViewIdcodeRetrospectAction.do";
				search.submit();
				search.action="../assistant/listViewIdcodeRetrospectAction.do";
			}
		}
		//var ProductID = search.ProductID.value.trim();
		//var batch = search.batch.value.trim();
		//var produceDate = search.produceDate.value.trim();
		//if (ProductID==''){
		//	alert("产品不能为空！");
		//	return false;
		//}else{
		//	if (batch=='' && produceDate==''){
		//		alert("批次和生产日期必须输入一项！");
		//		return false;
		//	}else{
		//		search.action="../assistant/excPutViewIdcodeRetrospectAction.do";
		//		search.submit();
		//		search.action="../assistant/listViewIdcodeRetrospectAction.do";
		//	}
		//}
	}
	
	function formChk(){
		var ProductID = search.ProductID.value.trim();
		var batch = search.batch.value.trim();
		var produceDate = search.produceDate.value.trim();
		if (ProductID==''){
			alert("产品不能为空！");
			return false;
		}else{
			if (batch=='' && produceDate==''){
				alert("批次和生产日期必须输入一项！");
				return false;
			}else{
				showloading();
				return true;
			}
		}
	}
	
	function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.search.ProductID.value=p.id;
	document.search.ProductName.value=p.productname;
	}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}	
	function checkNumber(obj){
			obj.value = obj.value.replace(/\D/g,'');
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
          <td width="772"> 防伪防窜货管理&gt;&gt;物流码溯源查询</td>
        </tr>
      </table>
       <form name="search" method="post" action="../assistant/listViewIdcodeRetrospectAction.do" onSubmit="return formChk()">
        
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
         
		  <tr class="table-back"> 
		  	<td width="10%" align="right">产品：</td>
            <td width="20%"><input name="ProductID" type="hidden" id="ProductID" value="${ProductID}">
              <input id="ProductName" name="ProductName" value="${ProductName}" size="30" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a>
              <span class="STYLE1">*</span></td>
            <td width="15%" align="right">批次：</td>
            <td width="15%">
             <input name="pagesize" type="hidden" id="pagesize" value="20">
             <input type="text" name="batch"  value="${batch}" maxlength="16" size="20">
            </td>
            <td width="15%" align="right">生产日期：</td>
            <td width="20%"><input name="produceDate" type="text" size="20" id="produceDate" onFocus="javascript:selectDate(this)" size="10" value="${produceDate}" readonly>
			</td>
            <td width="2%" align="right">&nbsp;</td>
            <td width="4%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
      
      </table>
        </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="title-func-back"> 	
    	<td width="50" >
				<a href="#" onClick="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>		
		<td class="SeparatePage"> <pages:pager action="../assistant/listViewIdcodeRetrospectAction.do"/> 
	</td>
	</tr>
</table>
      </div>
	</td>
				</tr>
				<tr>
					<td>
	<div id="listdiv" style="overflow: auto; height: 600px;width: 100%;">
	<FORM METHOD="POST" name="listform" ACTION="">
      <table id="pitable" class="sortable" width="180%"  border="0" cellpadding="0" cellspacing="1" >
        
          <tr align="center" class="title-top-lock"> 
            <td width="4%" >仓库编号</td>
            <td width="10%" >仓库名称</td>
            <td width="6%" >单据号</td>
            <td width="4%" >单据类型</td>
            <td width="4%" > 客户代码</td>
            <td width="10%" >客户名称</td>
            <td width="4%" >省份</td>
            <td width="5%" >产品内部编号</td>
			<td width="15%" >产品名称</td>
			<td width="5%" >规格</td>
            <td width="5%" >制单日期</td>
            <td width="5%" >生产日期</td>
            <td width="4%" >批次</td>
            <td width="4%" >包装数量</td>
          </tr>
          <logic:iterate id="pi" name="alsb" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${pi.id}');"> 
            <td >${pi.warehouseid}</td>
            <td><windrp:getname key='warehouse' value='${pi.warehouseid}' p='d'/></td>
            <td>
            	<a href="javascript:ToBillByNoPurview('${pi.id}');">${pi.id}</a>
            </td>
            <td>${pi.billname}</td>
            <td>${pi.cid}</td>
            <td>${pi.cname}</td>
            <td><windrp:getname key='countryarea' value='${pi.province}' p='d'/></td>
            <td>${pi.nccode}</td>
			<td>${pi.productname}</td>
			<td>${pi.specmode}</td>
            <td><windrp:dateformat value='${pi.makedate}' p='yyyy-MM-dd'/></td>
            <td>${pi.producedate}</td>
            <td>${pi.batch}</td>
            <td>${pi.packquantity}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <br>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
