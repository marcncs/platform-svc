<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
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

	function Update(){
		if(checkid!=""){
			popWin("../sys/toUpdMakeConfAction.do?tablename="+checkid,700,400);
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
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 编号设置 </td>
        </tr>
      </table>	  
       <form name="search" method="post" action="../sys/listMakeConfAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="11%"  align="right">关键字：</td>
            <td width="37%" > <input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}">
            </td>
            <td width="11%"  align="right">&nbsp;</td>
            <td width="41%" class="SeparatePage">
            	<input name="Submit" type="image" id="Submit" 
								src="../images/CN/search.gif" border="0" title="查询">
            	</td>
          </tr>
       
      </table>
       </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">			
			<td width="50" align="center">
				<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
				<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		  <td class="SeparatePage"><pages:pager action="../sys/listMakeConfAction.do"/></td>
							
		</tr>
	</table>
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">

          <tr align="center" class="title-top"> 
            <td width="12%">表名</td>
            <td width="19%" >表中文名</td>
            <td width="11%">是否自动编号</td>
            <td width="30%">前缀</td>
            <td width="28%">长度</td>
          </tr>
          <logic:iterate id="c" name="mcls" ><tr class="table-back-colorbar" onClick="CheckedObj(this,'${c.tablename}');"> 
            <td height="20">${c.tablename}</td>
            <td>${c.chname}</td>
            <td><windrp:getname key='YesOrNo' value='${c.runmode}' p='f'/></td>
            <td>${c.profix}</td>
            <td>${c.extent}</td>
          </tr>
          </logic:iterate> 
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
