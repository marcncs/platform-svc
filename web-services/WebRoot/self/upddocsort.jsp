<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
function Update(){
	var value = CheckboxOnlyOne();
	if( value ==0 ){
		return;
	}
	location.href("../self/listDocSortDetailAction.do?ID="+value);
}
function CheckboxOnlyOne(){
		var onlyOneObj = document.listform.PbsID;
		var count = 0;
		if(onlyOneObj.length){
            for (i=0; i<onlyOneObj.length; i++) {
				if (onlyOneObj[i].checked) {
                    count += 1;
				}
			}
			if(count !=1 )
			{
        		alert("您必须且只能选择一条记录！");
				return 0;
			}
			else{
				for (i=0; i<onlyOneObj.length; i++) {
					if (onlyOneObj[i].checked) {
                    return onlyOneObj[i].value;
					}
				}
			}
		}else{
            if (!onlyOneObj.checked) {
				alert("请选择记录!");
				return 0;
			}else{
				return  onlyOneObj.value;
			}
		}
	}
	
	function Check(){
		if(document.listform.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.listform.length;i++){

			if (!document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.listform.length;i++){
			if (document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=false;
				}
		}
	}
	
	function delDocSort(){

		if(confirm("你确定要删除文件柜！删除文件柜时相映的文件也将删除！")){
			var checks = document.getElementsByName("PbsID");
			var value="";
			for(var i = 0; i < checks.length; i++){
				if(checks[i].checked){
					if(value==""){	
						value=checks[i].value;
					}else{
						value+=","+checks[i].value;	
					}
				}
			}
			popWin2("../self/delDocSortAction.do?value="+value);
		}
	}
</script>
</head>

<body style="overflow: auto;">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="table-back">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 组列表</td>
        </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
        <tr align="center" class="title-top"> 
          <td width="4%" > 
		<input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
          <td width="36%">编号</td>
          <td width="60%">文件柜名</td>
        </tr>
		<logic:iterate id="p" name="pbs" >
        <tr align="center" class="table-back"> 
		<td > 
		<input type="checkbox" name="PbsID" value="${p.id}"></td>
          <td>${p.id}</td>
          <td>${p.sortname}</td>
        </tr>
		</logic:iterate>
		
      </table>
      </form>
      <table  border="0" cellpadding="0" cellspacing="0">
        <tr align="center"> 
          <td width="60"><a href="javascript:Update();">修改</a></td>
          <td width="60"><a href="javascript:delDocSort();">删除</a></td>
        </tr>
      </table>
     <div style="width: 100%; text-align: center;">
     	<button onclick="javascript:window.close();">关闭</button>
     </div>
      </td>
  </tr>
</table>
</body>
</html>
