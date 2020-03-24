<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>新增角色</title>
<script language="JavaScript">
	function addin()
	{
		var i = 0;
		
		for (i; i < f1.selectalldept.length; i++)
		{
			if (f1.selectalldept[i].selected == true)
			{
				
				if (isinlist(f1.selectalldept[i].text) == false)
				{
					var oOption = document.createElement("OPTION");
					oOption.value= f1.selectalldept[i].value;
					oOption.text = f1.selectalldept[i].text;
					//alert(oOption.value+"---"+oOption.text);
					//oOption.selected = true;
					f1.selectdept.add(oOption);
					//f1.selectalldept.remove(i);
					
				}
			}
		}
	}
	
	function isinlist(name)
	{
		var i = 0;
		for (i; i < f1.selectdept.length; i++)
		{
			if (f1.selectdept[i].text == name)
			{
				return true;
			}
		}
	
		return false;
	}
	
	
	function delout()
	{
		var i = 0;
		for (i; i < f1.selectdept.length; i++)
		{
			if (f1.selectdept[i].selected == true)
			{
				var oOption = document.createElement("OPTION");
				oOption.value = f1.selectdept[i].value;
				oOption.text = f1.selectdept[i].text;
				f1.selectdept.remove(i);
				//oOption.selected = true;
				//f1.selectalldept.add(oOption);
				i--;
			}
		}
	}
	
	function savespeed(){
		var str = "";
		var i = 0;
		var count = f1.selectdept.length;
		for (i; i < count; i++)
		{
			str = str + f1.selectdept[i].value + ",";
		}
		
		f1.speedstr.value = str;
		f1.uscount.value= count;
		f1.action = "setDeptVisitAction.do"; 
			//alert(str);
		f1.submit();
	}

</script>
</head>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 部门列表</td>
      </tr>
    </table>
	<form name="f1" method="post" action="" onSubmit="return check();">
	 <input name="uvid" type="hidden" id="uvid" value="">
	 <INPUT NAME="speedstr" TYPE="hidden">
	 <INPUT NAME="uscount" TYPE="hidden">
        <table width="100%" border="0" cellpadding="0" cellspacing="1">

            <tr align="center">
              <td><table width="100%" height="45" border="0" cellpadding="0" cellspacing="0">
                <tr align="center">
                  <td width="44%"><select name="selectalldept" size="30" style="width: 170px;" multiple LANGUAGE="javascript" ondblclick="return addin();">
                      <logic:iterate id="d" name="auls" >
                        <option value="${d.id}">${d.deptname}</option>
                      </logic:iterate>
                    </select>
                  </td>
                  <td width="8%"><input name="button" type="button" style="width: 75px;" onClick="addin();" value='添加>>' language=javascript/>
                      <p>
                        <input name="button" type="button" style="width: 75px;" onClick="delout();" value='<<删除' language=javascript/>
                        <br>
                                  </td>
                  <td width="48%"><select name="selectdept" size="30" multiple style="width: 170px;" LANGUAGE=javascript ondblclick="return delout();">
                      <logic:iterate id="ad" name="alls">
                        <option value="${ad.id}">${ad.deptname}</option>
                      </logic:iterate>
                    </select>
                  </td>
                </tr>
              </table></td>
            </tr>
            <tr align="center">
              <td><input type="Button" value="确定" onClick="savespeed();">&nbsp;&nbsp;
              <input type="reset" name="Submit2" value="重置"></td>
            </tr>
      </table>
	</form>
</body>
</html>
