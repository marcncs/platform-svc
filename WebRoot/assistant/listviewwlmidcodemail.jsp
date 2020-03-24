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
	search.action="../assistant/excPutViewWlmIdcodeAction.do";
	search.submit();
	search.action="../assistant/listViewWlmIdcodeAction.do";
	}
	
	function formChk(){
		var province = search.province.value;
		var wlmidcode = search.wlmidcode.value.trim();
		if ( province==''){
			alert("请选择省份！");
			return false;
		}
		if ( wlmidcode==''){
			alert("物流码不能为空！");
			return false;
		}
		return true;
	}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
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
          <td width="772"> 防伪防窜货管理&gt;&gt;物流码查询邮件</td>
        </tr>
      </table>
     <form name="search" method="post" action="../assistant/listViewWlmIdcodeMailAction.do" onSubmit="return formChk()"> 
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
 
        <input type="hidden" name="op" value="1">
		  <tr class="table-back"> 
            <td width="19%"  align="right">省份：</td>
            <td width="33%"><select name="province" >
              <option value="">-省份-</option>
              <logic:iterate id="c" name="cals">
                <option value="${c.id}" ${c.id==province?"selected":""}>${c.areaname}</option>
              </logic:iterate>
            </select></td>
            <td width="8%" align="right">物流码：</td>
            <td width="34%"><input type="text" name="wlmidcode" value="${wlmidcode}" maxlength="32"></td>
            <td width="2%" align="right">&nbsp;</td>
            <td width="4%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
      
      </table>
       </form> 
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="title-func-back"> 	
    	<td width="50" >
				<a href="#" onClick="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>		
		<td class="SeparatePage"> <pages:pager action="../assistant/listViewWlmIdcodeMailAction.do?op=0"/> 
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
      <table class="sortable" width="180%"  border="0" cellpadding="0" cellspacing="1" >
        
          <tr align="center" class="title-top-lock"> 
            <td width="4%"  >仓库编号</td>
            <td width="6%" >仓库名称</td>
            <td width="5%" > 客户代码</td>
            <td width="5%" >内部编码</td>
            <td width="10%" >客户名称</td>
            <td width="4%" >省份</td>
            <td width="5%" >产品编号</td>
	<td width="9%" >产品名称</td>
	<td width="10%" >规格</td>
            <td width="4%" >单据类型</td>
            <td width="5%" >制单日期</td>
            <td width="5%" >生产日期</td>
            <td width="6%" >批次</td>
            <td width="5%" >起始物流码</td>
            <td width="5%" >结束物流码</td>
            <td width="4%" >包装数量</td>
          </tr>
          <logic:iterate id="pi" name="alsb" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${pi.id}');"> 
            <td >${pi.warehouseid}</td>
            <td><windrp:getname key='warehouse' value='${pi.warehouseid}' p='d'/></td>
            <td>${pi.cid}</td>
            <td>${pi.syncode}</td>
            <td>${pi.cname}</td>
            <td><windrp:getname key='countryarea' value='${pi.province}' p='d'/></td>
            <td>${pi.productid}</td>
			<td>${pi.productname}</td>
			<td>${pi.specmode}</td>
            <td>${pi.billname}</td>
            <td><windrp:dateformat value='${pi.makedate}' p='yyyy-MM-dd'/></td>
            <td>${pi.producedate}</td>
            <td>${pi.batch}</td>
            <td>${pi.startno}</td>
            <td>${pi.endno}</td>
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
