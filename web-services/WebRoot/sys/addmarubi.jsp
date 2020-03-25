<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>物流码申请</title>
<script language="javascript" src="../js/prototype.js"></script>
<script language="javascript" src="../js/function.js"></script>
<script language="javascript" src="../js/selectDate.js"></script>
<script type="text/javascript" src="../js/validator.js"></script>
<script language="javascript" type="text/javascript" src="../js/datePicker/WdatePicker.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	var xmlhttp;
	function createXMLHTTP(){
		try{   
      		 xmlhttp = new ActiveXObject('Msxm12.XMLHTTP');   
  		}catch(e){   
       		try{   
           		xmlhttp = new ActiveXObject('Microsoft.XMLHTTP');   
       		}catch(e){   
          		try{   
               		xmlhttp = new XMLHttpRequest();   
           		}catch(e){}   
       		}   
		}
	}
	function selectproductcode() {
			createXMLHTTP();
			var v = document.getElementById("productname").value;
			if(v != "请选择产品名称"){
				xmlhttp.open("post","../sys/getMarubiProductCodeAction.do?productid="+v+"&type=1",true); 
	   	 		xmlhttp.send(null);
	    		xmlhttp.onreadystatechange = getselectproductcode;
			}else{
				document.getElementById("productcode").innerHTML="";
				var opt = new Option("请选择产品编码","请选择产品编码");
  				document.getElementById("productcode").add(opt);
  				document.getElementById("productcode").disabled = true;
			}
	    }
	    
	     //创建请求产品信息结果处理程序   
	    function getselectproductcode(){   
	        if(4==xmlhttp.readyState){ 
	        	var str = xmlhttp.responseXML; 
				var item = str.getElementsByTagName("str0");
				var inStr=""; 
				document.getElementById("productcode").disabled = false;
				document.getElementById("productcode").innerHTML="";
				for (var i = 0; i < item.length; i++){
					var name = item[i].childNodes[0].nodeValue;
					if(name == '0000'){
						document.getElementById("productcode").innerHTML="";
						var opt = new Option("请选择产品编码","请选择产品编码");
		  				document.getElementById("productcode").add(opt);
		  				document.getElementById("productcode").disabled = true;
					}else{
						var opt = new Option(name,name);
  						document.getElementById("productcode").add(opt);
					}
  				}
	        }
	    }
	    
	    function check(){
	    	var productname = document.getElementById("productname").value;
			var productcode = document.getElementById("productcode").value;
			var fwmnum = document.getElementById("fwmnum").value;
			var daoformat = document.getElementById("daoformat").value;
			var date = document.getElementById("date").value;
			if(productname != "" && productcode != "" && daoformat != "" && fwmnum != "" && date != ""){
				if(productname == "请选择产品名称"){
					alert('产品名称无值,请重新选择产品名称 !', '提示');
					document.getElementById("productname").focus(); 
					return false;
				}else if(productcode == "请选择产品编码"){
					alert('产品编码无值,请重新选择产品编码 !', '提示');
					document.getElementById("productname").focus(); 
					return false;
				}else if(daoformat == "请选择"){
					alert('生成格式无值,请重新选择生成格式 !', '提示');
					document.getElementById("daoformat").focus(); 
					return false;
				}else if(fwmnum == ""){
					alert('防伪码数量不能为空 !', '提示');
					document.getElementById("daoformat").focus(); 
					return false;
				}else{
					if(confirm("请确定你要申请生成产品为："+productcode+"，数量为："+fwmnum+"的防伪码吗？")){
 						search.target="";
 						search.action="../sys/addMarubiBarcodeLogging.do";
						search.submit();
					}else{
						return false;
					}
				}
			}else{
				alert('防伪物流信息不能为空,请检查后输入!', '提示');
				return false;
			}			
	    }
</script>
</head>
<body style="overflow-y:auto">
<form name="search" method="post" action="../sys/addMarubiBarcodeLogging.do">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">填写物流码相关资料</td>
      </tr>
    </table>
	</td></tr>
	<tr>
	<td>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr> 
		<td>
		<fieldset align="center"> <legend>
	      <table width="50" border="0" cellpadding="0" cellspacing="0">
	        <tr>
	          <td>基本信息</td>
	        </tr>
	      </table>
		  </legend>
		  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
		  	  <tr>
			  	<td align="right">企业名称：</td>
			  	<td>
			  		<input type="text" id="enterpriseName" name="enterpriseName" value="广州丸美" readonly style="width:170px;"/><span class="STYLE1">*</span>
			  		<input type="hidden" id="enterpriseId" name="enterpriseId" value="10000083"/>
			  	</td>
			  	<td align="right">产品名称：</td>
			     <td>
			      	<select id="productname" name="productname" onchange="selectproductcode()" style="width:200px;">
						<option selected="selected" value="请选择产品名称">请选择产品名称</option>
						<c:forEach var="entry" items="${marubiProductMap}">
							<option value="${entry.key}">${entry.value}</option>
						</c:forEach>
					</select><span class="STYLE1">*</span>
			     </td>
			  	<td align="right">产品编码：</td>
			      <td>
			      	<select id="productcode" name="productcode" disabled="disabled" style="width:200px;">
										<option selected="selected">请选择产品编码</option>
									</select><span class="STYLE1">*</span>
			      </td>
			  </tr>
			  <tr>
		      	<td>&nbsp;</td>
		      </tr>
			  <tr>
			  	<td  align="right">生成格式：</td>
			    <td>
			    	<select id="daoformat" name="daoformat" style="width:170px;">
		            	<option value="txt">TXT</option>
					</select><span class="STYLE1">*</span>
			    </td>
			    <td align="right">生成数量(个)：</td>
			    <td><input type="text" id="fwmnum" name="fwmnum" style="width:200px;"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/><span class="STYLE1">*</span></td>
			  	<td  align="right">生成日期：</td>
			    <td><input name="date" type="text" value="${date}" style="width:150px;" class="Wdate" id="date" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: pointer;" )"><span class="STYLE1">*</span></td>
			  </tr>
			    <tr>
		      	<td>&nbsp;</td>
		      </tr>
		  </table>
		</fieldset>
        <table width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr align="center">
              <td><input type="button" value="申请" onClick="check();">&nbsp;&nbsp;
              <input type="button" value="取消" onClick="window.close()"></td>
            </tr>
      </table>
	</td>
  </tr>
</table>
</td>
</tr>
</table>
</form>
</body>
</html>
