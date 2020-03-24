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
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
function Check(){
		if(document.getElementById("checkall").checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		var pid = document.listform.pid;		
		if(pid.length){
			for(i=0;i<pid.length;i++){
				pid[i].checked=true;
				//changeValue(pid[i]);
			}
		}else{
			pid.checked=true;
			//changeValue(pid);
		}		
	}

	function uncheckAll(){
		var pid = document.listform.pid;
		if(pid.length){
			for(i=0;i<pid.length;i++){
				pid[i].checked=false;
				//delObjValue(pid[i].value);
			}
		}else{
			pid.checked=false;
			//delObjValue(pid.value);
		}		
	}

function submit() {
	var pids = document.getElementsByName("pid");
	var count = 0;
	for(var i=0;i<pids.length;i++ ) {
		if(pids[i].checked) {
			count++;
		}
	}
	if( count<=0 ){
		alert("请选择行政区域!");
		return false;
	}
	var flag = checkRegionArea();
	if(flag) {
		document.getElementById("listform").action="../sys/addRegionAreaAction.do";
		document.getElementById("listform").submit();
		showloading();
	}
}

function submitCheckAll() {
	checkAll();
	submit();
}

function checkRegionArea(){
	var flag = false;
	// ajax同步检查库存 
		$.ajax({
		type:"POST",
		url:"../sys/ajaxCheckRegionArea.do",
		data:$("#listform").serialize(),
		dataType:"json",
		async: false,			
		success:function(msg){
			var popMsg = msg.returnMsg + "是否继续保存?";
			var code = msg.returnCode;
			if(code == 0){
				flag = true;
			}else{
				if(confirm(popMsg)){
					flag = true;
				}else{
					flag = false;
				}
			}
		}
	   	});
	   	return flag;
}
</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	 <form name="search" method="post" action="../sys/toAddRegionAreaAction.do">
	 <input name="PSID" type="hidden" id="PSID" value="${PSID}">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="16%" align="right">关键字：</td>
            <td width="16%"><input type="text" name="KeyWord" value="${KeyWord}"></td>         
            <td width="26%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
	  	  </tr>
       
      </table>
       </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
		  <td style="text-align:right"><pages:pager action="../sys/toAddRegionAreaAction.do"/></td>						
		</tr>
	</table>	
	 </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 300px;" >
		 <FORM id="listform" METHOD="POST" name="listform" ACTION=""  >    
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">  
	   	<input name="regioncodeid" type="hidden" id="regioncodeid" value="${PSID}">
          <tr align="center" class="title-top-lock"> 
	  	    <td width="4%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="20%" height="25">行政区域编号</td>
            <td width="30%">行政区域名称</td>
          </tr>
		  <c:set var="count" value="2"/>
          <logic:iterate id="p" name="ca" > 
		  <tr align="center" class="table-back-colorbar" > 
		  	<td> <input type="checkbox" name="pid" value="${p.id}"></td>
            <td height="20">${p.id}</td>
            <td>${p.areaname}</td>      
            </tr>
			<c:set var="count" value="${count+1}"/>
          </logic:iterate> 
		  
      </table>  
</form>
	  </div>   	
	</td>
  </tr>
</table>
<br/>	
<table width="100%" border="0" cellpadding="0" cellspacing="1">
		    
            <tr>
              <td><div align="center">
  				<input type="button" name="Submit" value="确定" onClick="submit()">&nbsp;&nbsp;<input type="button" name="cancel" value="取消" onClick="window.close();"><input type="hidden" name="speedstr">
  				&nbsp;&nbsp;<input type="button" name="button1" value="全部选中" onclick="submitCheckAll()">&nbsp;&nbsp;
              </div></td>
            </tr>
			 
          </table>

</body>
</html>
