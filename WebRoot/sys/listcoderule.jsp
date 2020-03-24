<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
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
	popWin("toAddCodeRuleAction.do",700,400);
	}
	
	function Update(){
		if(checkid!=""){
			popWin("toUpdCodeRuleAction.do?ucode="+checkid,700,400);
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
		popWin("toVisitMemberGradeAction.do?ID="+checkid,500,250);
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
          <td width="772">条形码规则列表</td>
        </tr>
      </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50" align="center">
									<a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
								<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
								<td width="50" align="center">
									<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
						      <td class="SeparatePage"><pages:pager action="../sys/listCodeRuleAction.do"/></td>
												
							</tr>
						</table>
		</div>
	</td>
				</tr>
				<tr>
					<td>
	<div id="listdiv" style="overflow-y: auto; height: 600px;" >
	<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="10%"  >条形码名称</td>            
            <td width="15%" >标识号</td>
          </tr>
          <logic:iterate id="w" name="culist" ><tr class="table-back-colorbar" onClick="CheckedObj(this,'${w.ucode}');"> 
            <td height="20">${w.uname}</td>            
            <td>${w.ucode}</td>
            </tr>
          </logic:iterate> 
       
      </table>   
       </form>   
     <!-- <table  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="85"><a href="javascript:Detail();">会员级别详情</a></td>
        </tr>
      </table>
	  <IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this)"></IFRAME>-->
	  </div>
	  </td>
  </tr>
</table>

</body>
</html>
