<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<script language="JavaScript">
var checktagname="";
var checktagsubkey=0;
var checkid="";
	function CheckedObj(obj,objtagname,objtagsubkey){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checktagname=objtagname;
	 checktagsubkey = objtagsubkey;

	}
	function CheckedObj(obj,id){
		
		 for(i=0; i<obj.parentNode.childNodes.length; i++)
		 {
			   obj.parentNode.childNodes[i].className="table-back-colorbar";
		 }
		 
		 obj.className="event";
		 checkid=id;

		}
	
	function addNew(tagname){
		popWin("toAddSystemResourceAction.do?tagName="+tagname,700,400);
	}
	
	function Update(){
		if(checkid!=""){
			popWin("toUpdSystemResourceAction.do?id="+checkid,700,400);
		}else{
			alert("请选择要修改的记录");
		}
	}
	
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 参数列表</td>
        </tr>
      </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<c:if test="${tagName!='CallCenter'}">
			<td width="50" align="center">
				<a href="javascript:addNew('${tagName}');"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td></c:if>
			<td width="50" align="center">
				<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
				<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		  <td style="text-align:right">&nbsp;</td>						
		</tr>
	 </table>
	 </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 600px;" >
		 <FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable"  width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top-lock"> 
            <!--<td width="16%" >编号</td>-->
            <td width="21%" >参数名称</td>
            <td width="20%" >键</td>
            <td width="30%" >值</td>
			<td width="40%" >备注</td>
          </tr>
          <logic:iterate id="f" name="alpl" ><tr class="table-back-colorbar" onClick="CheckedObj(this,'${f.id}');"> 
           <!-- <td height="20">${f.id}</td>-->
            <td height="20">${f.tagname}</td>
            <td>${f.tagkey}</td>
            <td>${f.tagvalue}</td>
			<td title="${f.memo}"><ws:subString value="${f.memo}" length="20"/></td>
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
