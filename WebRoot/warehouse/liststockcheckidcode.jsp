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

function CheckedObj(obj){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 obj.className="event";
}

function Check(){
		var pid = document.all("pid");
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


function Del(){

	var checkid="";
	var pid = document.getElementsByName("pid");
	for(i=0;i<pid.length;i++){
		if(pid[i].checked==true){
			if(checkid==""){
			
				checkid=pid[i].value;
			}else{
				checkid+=","+pid[i].value;
			}
		}
	}
	if(checkid!=""){
		if ( confirm("你确认要删除选择的记录吗?如果删除将不能恢复!") ){
			window.open("../warehouse/delStockCheckIdcodeiiAction.do?billno=${SCID}&ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	}else{
		alert("请选择你要操作的记录!");
	}
}

function AddNew(){
	popWin("toAddStockCheckIdcodeiiAction.do?billno=${SCID}",800,500);	
}

</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">条码表详情</td>
        </tr>
      </table>
       <form name="search" method="post" action="../warehouse/listStockCheckIdcodeAction.do?SCID=${SCID}">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="11%" align="right">关键字：</td>
            <td width="19%"><input type="text" name="KeyWord" value="${KeyWord}" maxlength="40"></td>
            <td width="9%" height="25" align="right">&nbsp;</td>
             <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
	  	  </tr>
        
      </table>
      </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
        	<td width="50" >
			<a href="javascript:AddNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" >
			<a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>	
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>			
		  <td class="SeparatePage"><pages:pager action="../warehouse/listStockCheckIdcodeAction.do"/></td>						
		</tr>
	</table>		
	<FORM METHOD="POST" name="listform" ACTION=""  >   
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">  
	  	   
          <tr align="center" class="title-top-lock"> 
           <td width="2%" align="left" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
		 	<td width="24%">库存条码</td>
			<td width="23%" >采集器条码</td>
		  	<td width="10%" >起始物流码</td>
			<td width="10%" >结束物流码</td>
            <td width="6%" >仓位</td>
			<td width="13%">批次</td>
			<td width="7%">帐面数量</td>
			<td width="7%">盘点数量</td>
          </tr>
		  <c:set var="count" value="2"/>
          <logic:iterate id="p" name="als" > 
		  <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this);">
		   <td align="left"><input type="checkbox" name="pid" value="${p.id}"></td> 			
          	<td>${p.idcode}</td> 
		  	<td>${p.cidcode}</td> 
			<td>${p.startno}</td> 
			<td>${p.endno}</td> 
            <td>${p.warehousebit}</td>
			<td>${p.batch}</td>     
			<td>${p.quantity}</td>  
			<td>${p.cquantity}</td>           
            </tr>
			<c:set var="count" value="${count+1}"/>
          </logic:iterate> 
		 
      </table>   
       </form>
	</td>
  </tr>
</table>	

</body>
</html>
