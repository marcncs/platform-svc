<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
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
	}
	
	function addNew(){
	window.open("toAddWarehouseAction.do","","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function Update(){
		if(checkid!=""){
			window.open("toUpdMsgTemplateAction.do?ID="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录");
		}
	}
	
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="memberGradeDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}


	
	function Visit(){
		if(checkid!=""){
window.open("toVisitMemberGradeAction.do?ID="+checkid,"newwindow","height=250,width=470,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 呼叫中心/短信>>短信模版设置</td>
        </tr>
      </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">			
			<td width="50" align="center">
				<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
				<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
		  <td class="SeparatePage"><pages:pager action="../sys/listMsgTemplateAction.do"/></td>
							
		</tr>
	</table>
	<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="6%"  >编号</td>
            <td width="16%" >模版类型</td>
            <td width="18%" >名称</td>
            <td width="52%" >内容</td>
            <td width="8%" >是否启用</td>
          </tr>
          <logic:iterate id="w" name="wls" ><tr class="table-back-colorbar" onClick="CheckedObj(this,'${w.id}');"> 
            <td height="20">${w.id}</td>
            <td><windrp:getname key='TemplateType' value='${w.templatetype}' p='f'/></td>
            <td>${w.templatename}</td>
            <td title="${w.templatecontent}">${w.templatecontent}</td>
             <td><windrp:getname key='YesOrNo' value='${w.isuse}' p='f'/></td>
            </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <!--<td width="85"><a href="javascript:Detail();">详情</a></td>-->
        </tr>
      </table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
