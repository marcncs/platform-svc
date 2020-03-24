<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>	
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script src="../js/prototype.js"></script>
<script src="../js/function.js"></script>
</head>
<script language="javascript">

function formcheck(){
	if ( $F('idcode').trim() ==""){
		alert("条码不能为空!");
		$('idcode').select();
		return;
	}
	/*else if($F('idcode').trim().length != 20){
		alert("条码格式不正确!");
		$('idcode').select();
		return;
	}
	*/
	addIdcode();
}

function showResponse2(originalRequest){
		var data = eval('(' + originalRequest.responseText + ')');	
		if ( data != undefined ){
			buildText(data.result);	
			document.submsg.document.forms[0].submit();		
		}
}

function buildText(str) {//赋值给消息提示框
	document.getElementById("result").style.display="";
	document.getElementById("result").innerHTML=str;
	setTimeout("document.getElementById('result').style.display='none'",2000);
}

function openpage(){//
	var billid=$F('billid');	
	var prid=$F('prid');	
	popWin4("multiAddIdcodeAction.do?billid="+billid+"&prid="+prid,800,600,"new");
	
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
		if(window.confirm("您确认要释放所选的记录吗？")){
			showloading();			
			popWin4("../erp/releaseCodeAction.do?ids="+rids,500,250, "del");
		}
	}else{
		alert("请选择你要操作的记录!");
	}
}

function Del2(){
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
		if(window.confirm("您确认要取消释放所选的记录吗？")){
			showloading();			
			popWin4("../erp/releaseCodeAction.do?type=cancel&ids="+rids,500,250, "del");
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
//下载暗码
	function downCovertCode(){
		popWin2("../erp/downloadCovertCodeAction.do?type=download&ID=${ID}");
	}	
</script>

<body>
<style>
#result {font-weight:bold; position:absolute;left:753px;top:20px; text-align:center; background-color:#ff0000;color:#fff;LEFT:expression(Math.abs(Math.round((document.body.clientWidth - result.offsetWidth)/2))); TOP:expression(Math.abs(Math.round((document.body.clientHeight)/2+document.body.scrollTop-180)))}
#result h1 {font-size:12px; margin:0px; padding:0px 5px 0px 5px};
</style>
<div id="result"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">释放条码</td>
      </tr>
    </table>
	<form name="addform" method="post" action="" >
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">

	<tr>
	  	  <td width="10%" align="right">PO单号：</td>
          <td width="19%">${plan.PONO}</td>
          <td width="10%" align="right">计划生产(箱)：</td>
	      <td width="12%">${plan.boxnum}</td>	
	      <td width="10%" align="right">已释放箱数：</td>
	      <td width="12%">${realnum}</td>	
          
	      
	  </tr>
	  	  <tr>
	   <td align="right">工厂名称：</td>
	      <td><windrp:getname key='organ' value='${plan.organId}' p='d'/></td>	
	      <td align="right">物料号：</td>
          <td width="19%">${product.mCode}</td>
	  	  <td align="right">物料名称：</td>
          <td width="19%">${product.productname}</td>
	    
	  </tr>
	  <tr>
          <td align="right">物料批次：</td>
	      <td width="19%">${plan.mbatch}</td>
	  	  <td align="right">生成打印任务：</td>
	  	  <td>
	  	  <windrp:getname key='YesOrNo' value='${printflag}' p='f'/>
<!--	      <windrp:getname key='YesOrNo' value='${plan.approvalFlag}' p='f'/>-->
	      </td>
          <td align="right">是否结束：</td>
	      <td>
	      <windrp:getname key='YesOrNo' value='${plan.closeFlag}' p='f'/>
	      </td>	
	    
	  </tr>
	  </table> 

	  </form>      
	  <br>
	 		<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
		  <tr>
		    <td>
			<div id="bodydiv">
			<form name="search" method="post" action="../erp/listProductPlanDetailAction.do">
		      <table width="100%" border="0" cellpadding="0" cellspacing="0">
		          <tr class="table-back">
		           
			      <td width="50%">
			      	 &nbsp; 条码(关键字)： &nbsp; 
			      <input type="text" name="KeyWord"  value="${KeyWord}"  maxlength="40" size="60" width="100%">
			       <input type="hidden" name="productPlanId" id="productPlanId" value="${plan.id}" >
			      </td>
			      
			      <td align="right">
									是否释放：
								</td>
								<td>
						<windrp:select key="YesOrNo" name="isRelease" value="${isRelease}" p="y|f"/>
					</td>
								
		            <td><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
			  	  </tr>
		        
		      </table>
		      </form>
			  <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="title-func-back">
											<ws:hasAuth operationName="/erp/downloadCovertCodeAction.do">
												<td width="1">
													<img src="../images/CN/hline.gif" width="2" height="14">
												</td>
												<td width="70" align="center">
													<a href="javascript:downCovertCode();"> <img
															src="../images/CN/outputExcel.gif" width="16" height="16"
															border="0" align="absmiddle">&nbsp;暗码下载</a>
												</td>
											</ws:hasAuth>
				  <td class="SeparatePage"><pages:pager action="../erp/listProductPlanDetailAction.do"/></td>						
				</tr>
			</table>	
			 </div>
			</td>
			</tr>
			<tr>
				<td>
				<div id="listdiv" style="overflow-y: auto; height: 480px;" >
				<FORM METHOD="POST" name="listform" ACTION=""  >      
		      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">  
			  
		          <tr align="center" class="title-top-lock"> 
		           <td width="1%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td>
				 	<td>条码</td>
					<td>单位</td>
					<td>暗码</td>
					<td>对应托码</td>
					<td>释放状态</td>
		          </tr>
		          <logic:iterate id="p" name="codelist" > 
				  <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${p.code}','${p.code}');"> 			
		          <td> <input type="checkbox" name="che" id="che" value="${p.code}"></td>
		         	 <td align="left">${p.code}</td> 
		         	 <td align="left">箱</td>
		         	 <td align="left">${p.covert_code}</td> 
		         	 <td align="left">${p.tcode}</td> 
		         	  <td align="left">
						<c:if test="${empty p.isrelease or p.isrelease == 0 }"></c:if>
		         	  	<c:if test="${p.isrelease == 1}">已释放</c:if>
		         	  </td>  
		            </tr>
					<c:set var="count" value="${count+1}"/>
		          </logic:iterate> 
				  
		      </table>  
		      </form> 
			  </div>   	
			</td>
		  </tr>
		</table>	
 </td>
</tr>
</table>
</body>
</html>
