<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function getResult(areaVal) {
	//alert(a);
		var url = "ajaxGetRankAction.do?rank="+areaVal; 
		if (window.XMLHttpRequest) { 
				req = new XMLHttpRequest(); 
		}else if (window.ActiveXObject) { 
				req = new ActiveXObject("Microsoft.XMLHTTP"); 
		} 
		if(req){ 
				req.open("GET",url, true); 
				//alert(a=="p");
				req.onreadystatechange = completeProvince;
				req.send(null); 
		} 
} 
function completeProvince(){//鑾峰緱鐪佷唤鏁版嵁
		if (req.readyState == 4) { 
				if (req.status == 200) { 
						var city = req.responseXML.getElementsByTagName("area"); 
						//alert(city.length);
						var strid = new Array();
						var str = new Array();
						for(var i=0;i<city.length;i++){
							var e = city[i];
							str[i] =new Array(e.getElementsByTagName("areaid")[0].firstChild.data , e.getElementsByTagName("areaname")[0].firstChild.data);
							//alert(str);

						}

						buildSelect(str,document.getElementById("parentid"));//璧嬪€肩粰province閫夋嫨妗?


				}
		}
}

function buildSelect(str,sel) {//璧嬪€肩粰閫夋嫨妗?


	//alert(str.length);
		sel.options.length=0;
		for(var i=0;i<str.length;i++) {
			//alert(str[i]);	
				sel.options[sel.options.length]=new Option(str[i][1],str[i][0])
		}
}


function abc(){
	alert("aa"+document.areaform.city.value);
}
</script>
<script language="javascript">
function chkArea(){
	  var areaname = addarea.areaname.value;
	  if(areaname.length<=0){
		document.getElementById("area").innerHTML="<font color='red'><bean:message key='sys.addcountryarea.nameempty'/></font>";
		return false;
	  }
	  if(areaname.length>128){
		document.getElementById("area").innerHTML="<font color='red'><bean:message key='sys.addcountryarea.namelength'/></font>";
		return false;
	  }else{
		document.getElementById("area").innerHTML="<font color='green'><bean:message key='sys.addcountryarea.namecanuse'/></font>";
		return true;
	  }
  }
function closeWindow(){
	alert("run the close()");
	window.param=null;
	window.close();
	return true;
}
</script>

</head>

<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> <bean:message key="sys.addcountryarea.addarea"/></td>
        </tr>
      </table>
       <form name="addarea" method="post" action="AddCountryAreaAction.do" onSubmit="return chkArea();">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr class="table-back"> 
            <td width="14%" height="20" align="right"> <bean:message key="sys.addcountryarea.areaname"/> ：</td>
            <td width="33%"> 
              <input name="areaname" type="text" id="areaname" onKeyUp="return chkArea();"> 
              <font color="#FF0000">*</font> <span id="area" style="font-weight:bold;"></span>
            </td>
            <td width="12%" align="right"> <bean:message key="sys.addcountryarea.level"/> ：</td>
            <td width="41%"><select name="rank" onChange="getResult(this.value,'p')">
			 <option value="-1"><bean:message key="sys.addcountryarea.province"/></option>
			 <option value="0"><bean:message key="sys.addcountryarea.city"/></option>
			 <option value="1"><bean:message key="sys.addcountryarea.areas"/></option>
		</select></td>
          </tr>
          <tr class="table-back"> 
            <td height="20" align="right"> <bean:message key="sys.addcountryarea.prevareas"/> ：</td>
            <td><select name="parentid" id="parentid">
            </select>
            </td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr class="table-back"> 
            <td height="20" align="right">&nbsp;</td>
            <td> <input type="submit" name="Submit" value="<bean:message key='sys.affirm'/>"> <input name="cancel" type="button" id="cancel" value="<bean:message key='sys.cancel'/>" onClick="javascript:history.back();"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
       
	  </table>
	   </form>
    </td>
  </tr>
</table>
</body>
</html>
