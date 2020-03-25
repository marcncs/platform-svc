<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>	
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
var checkid="";
var idcode="";
function CheckedObj(obj,objid,objidcode){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
 idcode=objidcode;
}

function Update(){
		if(checkid!=""){
		window.open("../warehouse/toUpdPurchaseIncomeAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}

function Del(){
	var flag=false;
	var rid=document.all("che");
	var rids="";
	if ( rid.length >1){
		for(var i=0; i<rid.length; i++){
			if (rid[i].checked){
				flag=true;
				rids=rid[i].value+","+rids;
			}
		}
	}else{
		if(rid.checked){
			flag=true;
			rids=rid.value+",";
		}
	}
	if(flag){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			showloading();			
			popWin4("../aftersale/delSaleTradesIdcodeAction.do?ids="+rids,500,250, "del");
		}
	}else{
		alert("请选择你要操作的记录!");
	}
}

function Check(){
	var pid = document.all("che");
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


</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	 <form name="search" method="post" action="../aftersale/listSaleTradesIdcode.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="11%" align="right">关键字：</td>
            <td width="19%"><input type="text" name="KeyWord" maxlength="40"></td>
            <td width="9%" height="25" align="right">&nbsp;</td>
            <td class="SeparatePage"><input name="Submit2" type="image" id="Submit" 
					src="../images/CN/search.gif"  title="查询"></td>
	  	  </tr>
        
      </table>
      </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
		 <!-- <td width="50" >
			<a href="#" onClick="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>-->
			<td width="50" >
			<c:choose>
			  <c:when test="${isaudit==0}">
			  	<a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
			  </c:when>
			  <c:otherwise>
			  	<a href="#" disabled><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
			  </c:otherwise>
          	</c:choose>			
			</td>	
		  <td class="SeparatePage"><pages:pager action="../aftersale/listSaleTradesIdcode.do"/></td>						
		</tr>
	</table>	
	 </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 300px;" >
		 <FORM METHOD="POST" name="listform" ACTION=""  >      
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">  
	 
          <tr align="center" class="title-top-lock"> 
           <td width="3%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
		 	<td width="24%">条码</td>
			<td width="7%" >单位</td>
		  	<td width="12%" >起始物流码</td>
			<td width="12%" >结束物流码</td>
            <td width="8%" >仓位</td>
			<td width="15%">批次</td>
			<td width="14%">生产日期</td>
			<td width="8%">包装数量</td>
          </tr>
		  <c:set var="count" value="2"/>
          <logic:iterate id="p" name="vulist" > 
		  <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}','${p.idcode}');"> 	
          	 <td> <input type="checkbox" name="che" id="che" value="${p.id}"></td>
          	<td>${p.idcode}</td> 
		  	<td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td> 
			<td>${p.startno}</td> 
			<td>${p.endno}</td> 
            <td height="20">${p.warehousebit}</td>
			<td>${p.batch}</td>     
			<td>${p.producedate}</td>  
			<td>${p.packquantity}</td>           
            </tr>
			<c:set var="count" value="${count+1}"/>
          </logic:iterate> 
		  
      </table>   
      </form>
	  </div>   	
	</td>
  </tr>
</table>	

</body>
</html>
