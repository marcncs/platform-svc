<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>		 
<html>
<head>

<title>转仓机构</title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script type="text/javascript">
var checkid=0;
function CheckedObj(obj,objid){

 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;

}


function Check(){
	var pid = document.all("opid");
	var checkall = document.all("checkall");
	if (pid==undefined){return;}
	if (pid.length){
		for(i=0;i<pid.length;i++){
				pid[i].checked=checkall.checked;
		}
	}else{
		pid.checked=checkall.checked;
	}		
}

function Affirm(){
	var count=0;
	var isCheck=false;
	var pid = document.all("opid");
	if (pid.length){
		for(var i = 0; i < pid.length ; i ++){
			if(pid[i].checked){
				isCheck=true;
				break;
			}		
		}
	}else{
		if(pid.checked){
			isCheck=true;
		}
	}
	if(!isCheck){
		alert("请选择已有的机构!");

	}else{
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			showloading();
			listform.submit();
		}
	}
	
}

function ToSet(){
	popWin("../users/listMoveCanuseOrganForSelectAction.do?UID=${UID}",800,500);
}

function  SetDate(){
	var pid = document.all("opid");
	var arr = new Array();
	if (pid.length){
		for(var i = 0; i < pid.length ; i ++){
			if(pid[i].checked){
				arr.push(pid[i].value);
			}		
		}
		//alert(arr.length);
	}else{
		if(pid.checked){
			arr.push(pid.value);
		}
	}
	if(arr.length==0){
		alert("请选择要授权的机构!");
	}else{
		popWin("../users/toSetMoveOrganDateAction.do?arr="+arr,500,300);
	}
}

</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">转仓机构</td>
      </tr>
    </table>
    <form name="search" method="post" action="../users/listMoveCanuseOrganAction.do">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          
           <input type="hidden" name="UID" value="${UID}">
            <tr class="table-back">
              <td width="10%"  align="right">关键字：</td>
              <td width="25%" >
              <input type="text" name="KeyWord" value="${KeyWord}"></td>
              <td width="11%"  align="right">&nbsp;</td>
              <td width="24%" >&nbsp;</td>
              <td width="11%" align="right">&nbsp;</td>
              <td width="19%"  class="SeparatePage"><input name="Submit" type="image" id="Submit" 
							src="../images/CN/search.gif" border="0" title="查询"></td>
            </tr>
          
        </table>
        </form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr class="title-func-back">
            <td width="50" align="center">
					<a href="javascript:ToSet();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;设置</a></td>
<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
				<td width="50" align="center">
					<a href="javascript:Affirm();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
					<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
					<td width="100" align="center">
					<a href="javascript:SetDate();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;授权日期设置</a></td>
					<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
				<td width="50" align="center">
			  <td  class="SeparatePage"><pages:pager action="../users/listMoveCanuseOrganAction.do"/></td>
								
			</tr>
		</table>
		 <FORM METHOD="POST" name="listform" ACTION="../users/delMoveCanuseOrganAction.do">
        <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
         
            <tr align="center" class="title-top">
              <td width="2%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
              <td width="30%" >机构编号</td>
              <td width="30%">机构名称</td>
              <td width="20%">授权开始日期</td>
              <td width="20%">授权结束日期</td>
            </tr>
              <logic:iterate id="d" name="opls" >
                <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${d.id}');">
                  <td align="left"><input type="checkbox" name="opid" value="${d.id}" ></td>
                  <td  align="left">${d.oid}</td>
                  <td align="left">${d.oname}</td>
                  <td align="left"><fmt:formatDate value="${d.begindate}"/></td>
                  <td align="left"><fmt:formatDate value="${d.enddate}"/></td>
                </tr>
              </logic:iterate>
         
      </table>
       </form>
     </td>
  </tr>
</table>
</body>
</html>

