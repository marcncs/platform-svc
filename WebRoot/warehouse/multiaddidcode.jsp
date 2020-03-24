<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>

	function winClose() 
	{ 
		
		if (window.opener){
			window.opener.location.href=window.opener.location.href;
		}
		window.close(); 
	} 
	function ChkValue(){
		var val = $("#result").val();
		 if(val==""){
	    	 alert("当前没有任何条码可供添加");
	    	 return false;
	     }
		 $("#SubmitButton").attr("disabled","disabled");
		// alert($("#SubmitButton").attr("disabled"));
		 
		 //var billid = $("#billid").val();
		 //var productid = $("#productid").val();

		 //alert(billid+productid);

		//showloading();
		//validateProvide.submit();
		
		//var url = "../warehouse/multiInsertIdcodeAction.do?billid="+billid+"&productid="+productid;
		//$.post(url,function(str){
		//	var json = eval('(' + str + ')');
		//	if(json.result == 0){
		//		alert("批量增加成功");
		//	}else{
		//		var ran = Math.random();
				//alert(ran);
		//		showModalDialog("toShowMsgAction.do?r="+ran,"newwindow","dialogWidth:650px;dialogHeight:550px;center:yes;status:no;");
		//	}
	//		$("#SubmitButton").attr("disabled","");
	//	},"JSON");

		$.ajax( {
			type : "POST",
			url : "../warehouse/multiInsertIdcodeAction.do",
			data : $("#validateProvide").serializeArray(),
			dataType: "json",
			success : function(data) {
				if(data.result == 0){
					alert("批量增加成功");
			    }else{ 
			    	var ran = Math.random();
					showModalDialog("toShowMsgAction.do?r="+ran,"newwindow","dialogWidth:650px;dialogHeight:550px;center:yes;status:no;");
			    }
				$("#SubmitButton").attr("disabled","");
			}
		});
	}


	function confirmcheck(){
		var startno = $("#startno").val();
		var endno = $("#endno").val();
		$("#result").val("");
		if(startno==null || startno==""){
			alert("起始码不能为空！");
			return;
		}
		if(startno.length!=13 && startno.length!=16){
			alert("起始码格式错误！");
			return;
		}
		if(endno==null || endno==""){
			alert("结束码不能为空！");
			return;
		}
		if(endno.length!=13 && endno.length!=16){
			alert("结束码格式错误！");
			return;
		}

	 	var url = "../warehouse/checkAndGenerateIdcode.do?startno="+startno+"&endno="+endno;
		$.post(url,function(str){
			var json = eval('(' + str + ')');
			if(json.result == 0){
				alert("起始码或结束码格式错误，请检查!");
			}else if(json.result == 1){
				alert("起始码与结束码必须为同一类型的条码(同为国内粉或同为国外粉)!");
			}else if(json.result == 2){
				alert("批量新增只针对箱码!");
			}else if(json.result == 3){
				alert("起始码与结束码不是同一种产品!");
			}else{
				$("#result").val(json.resultVal);
			}
		},"JSON");
	}
	
	
</script>
</head>

<body style="overflow:auto" onunload="winClose()">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 批量新增条码</td>
        </tr>
      </table>
      <form name="validateProvide" id="validateProvide" method="post" action="../warehouse/multiInsertIdcodeAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr> 
            <td >
			
	<fieldset align="center">
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">起始码：</td>
          <td width="21%"><input name="startno" type="text" id="startno"></td>
          <td width="13%" align="right">结束码：</td>
          <td width="23%"><input name="endno" type="text" id="endno"></td>
           <td width="14%" >
	          <input type="button" value="生成" onClick="confirmcheck();" >
	      </td>
	          <input type="hidden" name="billid" id="billid" value="<%=request.getParameter("billid") %>"/>
	           <input type="hidden" name="productid" id="productid" value="<%=request.getParameter("prid") %>"/>
	  </tr>
	  </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr> 
                  <td width="80%"><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td id="elect"></td>
                      <td width="80%">生成的所有条码如下：</td>
                      </tr>
                  </table>
                  </td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="center"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td><textarea name="result" cols="155" rows="1" id="result"  style="height: 360px"></textarea><br><span class="td-blankout">(提示：各个条码之间用回车换行进行分隔)</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center"><input type="button" name="Submit" onClick="ChkValue();" value="新增" id="SubmitButton">              &nbsp;&nbsp;
            <input type="button" name="Submit2" value="关闭" onClick="winClose();"></td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
