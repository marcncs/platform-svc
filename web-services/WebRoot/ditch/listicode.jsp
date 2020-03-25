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
	popWin("toAddICodeAction.do",700,400);
	}
	
	function Update(){
		if(checkid!=""){
			popWin("toUpdCodeUnitAction.do?ucode="+checkid,700,400);
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
          <td width="772">物流码前缀列表</td>
        </tr>
      </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50" align="center">
												
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
            <td width="41%"  >物流码前缀</td>            
            <td width="59%" >产品编号</td>
          </tr>
          <logic:iterate id="w" name="iclist" > 
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${w.lcode}');"> 
            <td height="20">${w.lcode}</td>            
            <td>${w.productid}</td>
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
