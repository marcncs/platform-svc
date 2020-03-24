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

	/**
	function SelectLoanObject(){
		showModalDialog("toSelectLoanObjectAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		document.addform.uid.value=getCookie("uid");
		document.addform.uidname.value=getCookie("uname");
	}
	**/
	
	function Chk(){
		var uid = document.addform.uid;
		var usefuldate = document.addform.usefuldate;
		//var target = document.addform.target;
		
		if(uid.value==""||uid.value==null){
			alert("主管对象不能为空！");
			return false;
		}
		if(usefuldate.value==""||usefuldate.value==null){
			alert("有效日期不能为空！");
			return false;
		}
		
		/**
		if(target.value==""||target.value==null){
			alert("目标不能为空！");
			return false;
		}
		**/
		
		
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
		var tid = document.addform.tid;
		var objcode = document.addform.objcode;
		var url = "";
		if(usefuldate.value==""){
			return;
		}
		//主管
		if(targettype=="" || targettype=="0" || targettype=="1"){
			url = '../users/chkUserTargetAction.do?uid=' + objcode.value + '&usefuldate=' 
					+ usefuldate.value + '&TargetType=' + targettype + "&tid=" + tid.value;
		}
		//机构
		else if(targettype=="2" || targettype=="3"){
			url = '../users/chkUserTargetAction.do?oid=' + objcode.value + '&usefuldate=' + usefuldate.value
					 + '&TargetType=' + targettype + "&tid=" + tid.value;
		}
		//办事处
		else if(targettype=="4"){
			url = '../users/chkUserTargetAction.do?bsrid=' + objcode.value + '&usefuldate=' + usefuldate.value 
				+ '&TargetType=' + targettype + "&tid=" + tid.value;
		}
		//大区
		else if(targettype=="5"){
			url = '../users/chkUserTargetAction.do?dqrid=' + objcode.value + '&usefuldate=' + usefuldate.value 
				+ '&TargetType=' + targettype + "&tid=" + tid.value;
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
			alert("该主管在当前月份已存在目标，请重新选择数据！");		
			document.addform.usefuldate.value=initusefuldate;
		}
    }
    
    //选择主管
    function SelectName(){
		var u=showModalDialog("../common/selectUsersAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		if(u==undefined){return;}
		document.addform.uid.value=u.uid;
		document.addform.uname.value=u.loginname;
	}
	
	var chkusefuldate;
	var initusefuldate;
	this.onload = function init(){
		chkusefuldate = document.addform.usefuldate.value;
		initusefuldate=chkusefuldate;
		setInterval("doChk()",500);
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
        <td width="772"> 修改对象指标</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="addform" method="post" action="updUserTargetAction.do" onSubmit="return Chk();">
	<input type="hidden" name="tid" value="${ut.id }">
	<input type="hidden" name=TargetType value="${ut.targettype }">
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
          <td width="20%"><input name="objcode" type="hidden" value="${ut.objcode }" id="uid">
            <input name="objname" type="text" id="objname" value="${ut.objname }" readonly></td>
          <td width="15%" align="right">有效日期：</td>
          <td width="20%"><input name="usefuldate" type="text" id="usefuldate" value="${ut.usefuldate }" onFocus="javascript:selectDate(this)"  require="ture" msg="请选择有效日期!" readonly></td>
	 	  <td>指标类型：</td>
          <td>
          	<input name="targettypename" type="text" id="targettypename" value="${ut.targettypename }" readonly>
          </td>
	  </tr>
	   <tr>
	  	 <td align="right">进口粉指标：</td>
	     <td><input name="importtarget" type="text" id="importtarget" value="${ut.importtarget }" size="15" dataType="Double" msg="金额只能是数字!" require="ture" onKeyPress="KeyPress(this)"></td>
	      <td align="right">国产成人粉指标：</td>
	     <td><input name="chmantarget" type="text" id="chmantarget" value="${ut.chmantarget }" size="15" dataType="Double" msg="金额只能是数字!" require="ture" onKeyPress="KeyPress(this)"></td>
	      <td align="right">国产婴儿粉指标：</td>
	     <td><input name="chbabytarget" type="text" id="chbabytarget" value="${ut.chbabytarget }" size="15" dataType="Double" msg="金额只能是数字!" require="ture" onKeyPress="KeyPress(this)"></td>
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
