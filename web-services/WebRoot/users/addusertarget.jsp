<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/validator.js"></script>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript"><!--

var chkFalg = false;

	function Chk(){
		var targettype = document.addform.TargetType.value;
		var usefuldate = document.addform.usefuldate;
		var importtarget = document.addform.importtarget;
		
		
		//主管
		if(targettype=="" || targettype=="0" || targettype=="1"){
			var uid = document.addform.uid;
			if(uid.value==""||uid.value==null){
				alert("对象不能为空！");
				return false;
			}
		}
		//机构
		else if(targettype=="2" || targettype=="3"){
			var oid = document.addform.oid;
			if(oid.value==""||oid.value==null){
				alert("对象不能为空！");
				return false;
			}
		}
		
		if(usefuldate.value==""||usefuldate.value==null){
			alert("有效日期不能为空！");
			return false;
		}
		
		showloading();
		return true;
		
	}
	function doChk(){
		var usefuldate = document.addform.usefuldate.value;
		if(usefuldate.substring(0,7)!=chkusefuldate){
			chkUserTarget();
			chkusefuldate=usefuldate.substring(0,7);
		}
	}
	
	//验证
	function chkUserTarget(){
		var targettype = document.addform.TargetType.value;
		var usefuldate = document.addform.usefuldate;
		var url = "";
		if(usefuldate.value==""){
			return;
		}
		//主管
		if(targettype=="" || targettype=="0" || targettype=="1"){
			var uid = document.addform.uid;
			if(uid.value==""||uid.value==null){
				alert("请先选择对象信息！");
				document.addform.usefuldate.value="";
				return;
			}
			url = '../users/chkUserTargetAction.do?uid=' + uid.value + '&usefuldate=' + usefuldate.value + '&TargetType=' + targettype;
		}
		//机构
		else if(targettype=="2" || targettype=="3"){
			var oid = document.addform.oid
			if(oid.value==""||oid.value==null){
				alert("请先选择对象信息！");
				document.addform.usefuldate.value="";
				return;
			}
			url = '../users/chkUserTargetAction.do?oid=' + oid.value + '&usefuldate=' + usefuldate.value + '&TargetType=' + targettype;
		}
		//办事处
		else if(targettype=="4"){
			var bsrid = document.addform.bsrid;
			if(bsrid.value==""||bsrid.value==null){
				alert("请先选择对象信息！");
				document.addform.usefuldate.value="";
				return;
			}
			url = '../users/chkUserTargetAction.do?bsrid=' + bsrid.value + '&usefuldate=' + usefuldate.value + '&TargetType=' + targettype;
		}
		//大区
		else if(targettype=="5"){
			var dqrid = document.addform.dqrid;
			if(dqrid.value==""||dqrid.value==null){
				alert("请先选择对象信息！");
				document.addform.usefuldate.value="";
				return;
			}
			url = '../users/chkUserTargetAction.do?dqrid=' + dqrid.value + '&usefuldate=' + usefuldate.value + '&TargetType=' + targettype;
		}
        var myAjax = new Ajax.Request(
              url,
              {method: 'post', onComplete: showResponse}
       );
	}
	
	//处理验证结果
	function showResponse(originalRequest)
    {
		var data = eval('(' + originalRequest.responseText + ')');
		var result = data.result;
		if ( result == "0" ){
			chkFalg = true;
		}else{	
			chkFalg=false;	
			alert("该对象在当前月份已存在指标，请重新选择数据！");		
			document.addform.usefuldate.value="";
		}
    }
    
    //选择主管
    function SelectName(){
		var u=showModalDialog("../common/selectUsersAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		if(u==undefined){return;}
		document.addform.uid.value=u.uid;
		document.addform.uname.value=u.loginname;
	}
	
	//选择机构
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		document.addform.oid.value=p.id;
		document.addform.oname.value=p.organname;
	}
	
	var chkusefuldate;
	this.onload = function init(){
		chkusefuldate = document.addform.usefuldate.value;
		setInterval("doChk()",500);
	}
	
	//动态该表对象
	function changetarget(){
		var targettype = document.addform.TargetType.value;
		//主管模块
		var userspan = document.getElementById("userspan");
		//机构模块
		var organspan = document.getElementById("organspan");
		//办事处mok
		var bsregionspan = document.getElementById("bsregionspan");
		//大区模块
		var dqregionspan = document.getElementById("dqregionspan");
		
		//主管
		if(targettype=="" || targettype=="0" || targettype=="1"){
			userspan.style.display="";
			organspan.style.display="none";
			bsregionspan.style.display="none";
			dqregionspan.style.display="none";
		}
		//机构
		else if(targettype=="2" || targettype=="3"){
			userspan.style.display="none";
			organspan.style.display="";
			bsregionspan.style.display="none";
			dqregionspan.style.display="none";
		}
		//办事处
		else if(targettype=="4"){
			userspan.style.display="none";
			organspan.style.display="none";
			bsregionspan.style.display="";
			dqregionspan.style.display="none";
		}
		//大区
		else if(targettype=="5"){
			userspan.style.display="none";
			organspan.style.display="none";
			bsregionspan.style.display="none";
			dqregionspan.style.display="";
		}
		//清空有效日期
		document.addform.usefuldate.value="";
	}
--></script>

</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 新增对象指标</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="addform" method="post" action="addUserTargetAction.do" onSubmit="return Chk();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="15%"  align="right">对象名称：</td>
          <td width="20%">
          	<span id="userspan">
	          	<input name="uid" type="hidden" id="uid">
	            <input name="uname" type="text" id="uname" readonly><a href="javascript:SelectName();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
            </span>
            <span id="organspan" style="display: none;">
          		<input name="oid" type="hidden" id="oid">
            	<input name="oname" type="text" id="oname" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
            </span>
            
            <span id="bsregionspan" style="display: none;">
            	<select name="bsrid">
            		<logic:iterate id="bs" name="bslist">
            		<option value="${bs.id }">${bs.sortname }</option>
            		</logic:iterate>
            	</select>
            </span>
            
            <span id="dqregionspan" style="display: none;">
          		<select name="dqrid">
            		<logic:iterate id="dq" name="dqlist">
            		<option value="${dq.id }">${dq.sortname }</option>
            		</logic:iterate>
            	</select>
            </span>
            </td>

          <td width="15%" align="right">有效日期：</td>
          <td width="20%"><input name="usefuldate" type="text" id="usefuldate" onFocus="javascript:selectDate(this)" require="ture" msg="请选择有效日期!" readonly></td>
          <td>指标类型：</td>
          <td>
          	<select name="TargetType" onchange="changetarget();">
          		<logic:iterate id="type" name="typelist">
          			<option value="${type.tagsubkey }">${type.tagsubvalue }</option>
          		</logic:iterate>
          	</select>
          </td>
	  </tr>
	  <tr>
	  	 <td align="right">进口粉指标：</td>
	     <td><input name="importtarget" type="text" id="importtarget" value="0" size="15" dataType="Double" msg="金额只能是数字!" require="ture" onKeyPress="KeyPress(this)"></td>
	      <td align="right">国产成人粉指标：</td>
	     <td><input name="chmantarget" type="text" id="chmantarget" value="0" size="15" dataType="Double" msg="金额只能是数字!" require="ture" onKeyPress="KeyPress(this)"></td>
	      <td align="right">国产婴儿粉指标：</td>
	     <td><input name="chbabytarget" type="text" id="chbabytarget" value="0" size="15" dataType="Double" msg="金额只能是数字!" require="ture" onKeyPress="KeyPress(this)"></td>
	  </tr>
	  </table>
	</fieldset>
	
	<table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center">
          <td><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
              <input type="reset" name="cancel" onClick="javascript:window.close()" value="取消"></td>
        </tr>

    </table>
	 </form>
	 </td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
