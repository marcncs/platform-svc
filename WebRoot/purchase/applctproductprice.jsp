<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">

function addin()
		{
			var i = 0;
			
			for (i; i < f1.selectallorgans.length; i++)
			{
				if (f1.selectallorgans[i].selected == true)
				{		
					if (!isinlist(f1.selectallorgans[i].value))
					{
						var oOption = document.createElement("OPTION");
						oOption.value= f1.selectallorgans[i].value;
						oOption.text = f1.selectallorgans[i].text;
						f1.selectorgans.add(oOption);	
						f1.selectallorgans.remove(i);
						i--;
					}else{
						f1.selectallorgans.remove(i);
						i--;
					}
				}
			}
		}
		
		function addAll(){
			for (var i=0; i < f1.selectallorgans.length; i++)
			{
				if (!isinlist(f1.selectallorgans[i].value))
				{
					var oOption = document.createElement("OPTION");
					oOption.value= f1.selectallorgans[i].value;
					oOption.text = f1.selectallorgans[i].text;
					f1.selectorgans.add(oOption);
					f1.selectallorgans.remove(i);
					i--;
				}else{
					f1.selectallorgans.remove(i);
					i--;
				}

			}
		}
	
		function isinlist(value)
		{
			var i = 0;
			for (i; i < f1.selectorgans.length; i++)
			{
				if (f1.selectorgans[i].value == value)
				{
					return true;
				}
			}
		
			return false;
		}
		
		
	
	
		function delout()
		{
			var i = 0;
			for (i; i < f1.selectorgans.length; i++)
			{
				if (f1.selectorgans[i].selected == true)
				{
					if (!isinlisttwo(f1.selectorgans[i].value))
					{
						var oOption = document.createElement("OPTION");
						oOption.value= f1.selectorgans[i].value;
						oOption.text = f1.selectorgans[i].text;
						f1.selectallorgans.add(oOption);
						f1.selectorgans.remove(i);
						i--;
					}else{
						f1.selectorgans.remove(i);
						i--;
					}					
				}
			}
		}
		
		function isinlisttwo(value)
		{
			var i = 0;
			for (i; i < f1.selectallorgans.length; i++)
			{
				if (f1.selectallorgans[i].value == value)
				{
					return true;
				}
			}
		
			return false;
		}
		
		function deloutAll(){
		
			var i = 0;
			for (i; i < f1.selectorgans.length; i++)
			{
				if (!isinlisttwo(f1.selectorgans[i].value))
				{
					var oOption = document.createElement("OPTION");
					oOption.value= f1.selectorgans[i].value;
					oOption.text = f1.selectorgans[i].text;
					f1.selectallorgans.add(oOption);
					f1.selectorgans.remove(i);
					i--;
				}else{
					f1.selectorgans.remove(i);
					i--;
				}
			}
		
		}
	
	
	function savespeed(){
		var str = "";
		var i = 0;
		var count = f1.selectorgans.length;
		
		if(count <=0 ){
			alert("请选择机构!");
			return false;
		}
		for (i; i < count; i++)
		{
			str = str + f1.selectorgans[i].value + ",";
		}
		
		f1.speedstr.value = str;
		f1.uscount.value= count;
		f1.action = "appLctProductPriceAction.do"; 
		showloading();
		f1.submit();
	}

</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <form name="f1" method="post" action="">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
                  <td width="772"> 应用到机构 
                    
					<INPUT NAME="speedstr" TYPE="hidden">
					<INPUT NAME="uscount" TYPE="hidden">
				  </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td><table width="100%" height="45" border="0" cellpadding="0" cellspacing="0">
			<tr align="center"> 
                  <td width="44%"> 
					<select name="selectallorgans" size="30" style="width: 200px;" multiple LANGUAGE="javascript" ondblclick="return addin();">
					  <logic:iterate id="o" name="aols" > 
					  <option value="${o.id}">${o.organname}</option>
					  </logic:iterate>
					</select>
				</td>
                  <td width="8%"> 
                    <input style="width: 75px;" type="button" value="添加>" language=javascript onClick="addin();">
        		<p>
                  <input style="width: 75px;" type="button" value="全部添加>>"
								language=javascript onClick="addAll();">
							<p>
       			 <input style="width: 75px;" type="button" value="<删除" language=javascript onClick="delout();">
							<p>
        		<input style="width: 75px;" type="button" value="<<全部删除"
									language=javascript onClick="deloutAll();">
        		<br>
		</td>
                  <td width="48%"> 
                    <select name="selectorgans" size="30" multiple style="width: 200px;" LANGUAGE=javascript ondblclick="return delout();">
                      <logic:iterate id="o" name="pols" > 
					  <option value="${o.organid}">${o.organidname}</option>
					  </logic:iterate>
				    </select>
				</td>
              </tr>
            </table></td>
  </tr>
  <tr>
          <td align="center"><input type="Button" value="确定" onClick="savespeed();">&nbsp;&nbsp;
            <input type="button" name="Button" value="取消" onClick="window.close();"> </td>
  </tr>

</table>
  </form>
	</td>
  </tr>
</table>
</body>
</html>
