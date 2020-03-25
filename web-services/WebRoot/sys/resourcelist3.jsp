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
var checkistime="";
	function CheckedObj(obj,objtagname,objtagsubkey, objistime){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checktagname=objtagname;
	 checktagsubkey = objtagsubkey;
	 checkistime = objistime;
	}
	
	function addNew(tagname){
		popWin("toAddBaseResourceAction.do?tagName="+tagname,700,400);
	}
	
	function Update(){
		if(checktagname!=""){
			popWin("toUpdBaseResourceAction.do?tagName="+checktagname+"&tagSubKey="+checktagsubkey+"&istime="+checkistime+"&fromPage=3",700,400);
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
          <td width="772"> 资源列表</td>
        </tr>
      </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<ws:hasAuth operationName="/sys/toUpdBaseResourceAction.do">
			<td width="50" align="center">
				<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
				<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			</ws:hasAuth>
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
            <td width="21%" >资源类英文名</td>
<!--            <td width="28%" >键</td>-->
            <td width="35%" >值</td>
<!--			<td width="35%" >是否可用</td>-->
          </tr>
          <logic:iterate id="f" name="alpl" ><tr class="table-back-colorbar" onClick="CheckedObj(this,'${f.tagname}','${f.tagsubkey}', '${istime}');"> 
           <!-- <td height="20">${f.id}</td>-->
            <td height="20">${f.tagname}</td>
<!--            <td>${f.tagsubkey}</td>-->
            <td>${f.tagsubvalue}</td>
<!--			<td><windrp:getname key='YesOrNo' value='${f.isuse}' p='f'/></td>-->
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
