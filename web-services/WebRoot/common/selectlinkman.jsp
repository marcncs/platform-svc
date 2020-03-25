<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>  
<script language="javascript">
	var checkid=0;
	var checkname="";
	var checktel="";
	var transit=0;
	var checktransportaddr="";
	var mobile="";
	function CheckedObj(obj,objid,objname,objtel,objtransportaddr,objmobile){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	 checktel = objtel;
	 checktransportaddr=objtransportaddr;
	 mobile=objmobile;
	// transit=objtransit;
	}

	function Affirm(){
	if(checkid!=""){		
		var lk={lid:checkid,lname:checkname,ltel:checktel,ltransportaddr:checktransportaddr,transit:transit,mobile:mobile};
		window.returnValue=lk;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}
	}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}
	
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择联系人</td>
        </tr>
      </table>
      <form name="search" method="post" action="selectLinkmanAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="16%"  align="right">名称关键字：</td>
            <td width="84%" ><input type="text" name="KeyWord" value="${KeyWord}"></td>
             <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td class="SeparatePage"><pages:pager action="../common/selectLinkmanAction.do"/>
          </td>
        </tr>
      </table>
	  </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		 <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
       
          <tr align="center" class="title-top"> 
            <!--<td width="13%" >联系人编号</td>-->
            <td width="17%" >联系人姓名</td>
            <td width="10%">性别</td>
            <td width="18%">电话</td>
			<td width="16%">手机</td>
            <td width="17%">部门</td>
            <td width="22%">职务</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${p.id},'${p.name}','${p.officetel}','${p.addr}','${p.mobile}');" onDblClick="Affirm();"> 
            <!--<td >${p.id}</td>-->
            <td >${p.name}</td>
            <td>${p.sexname}</td>
            <td>${p.officetel}</td>
			<td>${p.mobile}</td>
            <td>${p.department}</td>
            <td>${p.duty}</td>
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
