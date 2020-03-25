<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
	
	
		//--------------------------------start -----------------------	
function getRowObj(obj) 
{ 
var i = 0; 
while(obj.tagName.toLowerCase() != "tr"){ 
obj = obj.parentNode; 
if(obj.tagName.toLowerCase() == "table")return null; 
} 
return obj; 
} 

//根据得到的行对象得到所在的行数 
function getRowNo(obj){ 
var trObj = getRowObj(obj); 
var trArr = trObj.parentNode.children; 
for(var trNo= 0; trNo < trArr.length; trNo++){ 
if(trObj == trObj.parentNode.children[trNo]){ 
return trNo+1; 
} 
} 
}  
	
	function setvalue(obj){
		var ri = getRowNo(obj);
 
		var pl= document.listform.oid;
		if ( !pl.length ){
			var billno=document.listform.item("oid").value;		
			opener.window.addItemValue(billno);
		}else{
			var billno=document.listform.item("oid")[ri-2].value;
			opener.window.addItemValue(billno);
		}
	}
//--------------------------------end -----------------------	

</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择单据</td>
        </tr>
      </table>
      <form name="search" method="post" action="../aftersale/listViewSaleBillAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
         <tr class="table-back">
            <td  align="right">制单机构：</td>
            <td><input name="MakeOrganID" type="hidden" id="MakeOrganID">
              <input name="oname2" type="text" id="oname" size="30" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"> </a></td>
            <td align="right">制单人：</td>
            <td><input type="hidden" name="MakeID" id="MakeID">
              <input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F('MakeOrganID'),'ou')"
										value="请选择" readonly></td>
            <td align="right">制单日期：</td>
            <td><input type="text" name="BeginDate" size="10" onFocus="javascript:selectDate(this)" readonly>
              -
              <input type="text" name="EndDate" size="10" onFocus="javascript:selectDate(this)" readonly></td>
         </tr>
		 
		  <tr class="table-back"> 
            <td width="13%"  align="right">单据号：</td>
            <td width="25%" ><input type="text" name="KeyWord"></td>
            <td width="7%"  align="right">&nbsp;</td>
            <td width="21%" >&nbsp;</td>
            <td width="10%" >&nbsp;</td>
            <td width="24%" ><span class="SeparatePage">
              <input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif"  title="查询">
            </span></td>
		  </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td style="text-align: right;"><pages:pager action="../aftersale/listViewSaleBillAction.do" />
          </td>
        </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="6%" >单据编号</td>
            <td width="18%">制单机构</td>
			<td width="9%">制单人</td>			
			<td width="35%">制单日期</td>
			<td width="25%">金额</td>
			<td width="7%">选择</td>
          </tr>
          <logic:iterate id="c" name="also" > 
          <tr class="table-back" > 
            <td ><input type="hidden" name="oid" value="${c.id}">${c.id}</td>
            <td><input type="hidden" name="oname" value="${c.makeorganidname}">${c.makeorganidname}</td>
			<td><input type="hidden" name="otel" value="${c.makeidname}">${c.makeidname}</td>
			<td>${c.makedate}</td>
			<td><input type="hidden" name="oaddr" value="${c.totalsum}">${c.totalsum}</td>			
			<td><a href="#" onClick="setvalue(this)">选择</a></td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
