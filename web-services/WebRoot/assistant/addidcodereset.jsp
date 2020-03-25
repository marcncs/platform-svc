<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script src="../js/prototype.js"></script>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<script language="javascript">
var chebox=null;
  function addProductRow(){ 
    var x = document.all("dbtable").insertRow(dbtable.rows.length);

        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);
        var d=x.insertCell(3);   
		var e=x.insertCell(4);       
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";  
		e.className = "table-back";         
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"35\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"35\" readonly>";		
		e.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" size=\"10\" value='1' >";
    chebox=document.all("che");  

}

function SupperSelect(rowx){
	var product =showModalDialog("../assistant/selectIdcodeProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");

	var arrid=product.productid;
	var arrpordocutname=product.productname;
	var arrspecmode=product.specmode;	
	
	if((dbtable.rows.length-1) <1){
		for(var i=0;i<arrid.length;i++){
				addProductRow();
				if(i==0){
					document.repositoryForm.item("productid").value =arrid[0];
					document.repositoryForm.item("productname").value =arrpordocutname[0];
					document.repositoryForm.item("specmode").value=arrspecmode[0];					
					}else{
						document.repositoryForm.item("productid")[i].value =arrid[i];
						document.repositoryForm.item("productname")[i].value =arrpordocutname[i];
						document.repositoryForm.item("specmode")[i].value=arrspecmode[i];						
					}
			}	
	}else{
		for(var i=0;i<arrid.length;i++){
			addProductRow();
			document.repositoryForm.item("productid")[rowx-1+i].value =arrid[i];
			document.repositoryForm.item("productname")[rowx-1+i].value =arrpordocutname[i];
			document.repositoryForm.item("specmode")[rowx-1+i].value=arrspecmode[i];
			}
	}
	
}	

function deleteProductR(){
	chebox=document.all("che");
	if(chebox!=null){	
		if (chebox.length >1){
			for(var i=1;i<=chebox.length;i++){
			  if (chebox[i-1].checked) {
				document.getElementById('dbtable').deleteRow ( i);
				i=i-1;
			  }
			}
		}else{
			document.all('dbtable').deleteRow(1)
		}
 	 }
}



function formChk(){
/*	var productname = document.repositoryForm.title;
	var rtid = document.repositoryForm.rtid;
	if(rtid.value==1){
		alert("请选择下级类别！");
		return false;
	}
	if(productname.value==""){
		alert("标题能为空！");
		return false;
	}
	*/

	return true;
 }

function Check(){
			//alert("document.activeform.checkall.values");
		if(document.repositoryForm.checkall.checked){
			//alert(document.activeform.checkall.values);
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.repositoryForm.length;i++){

			if (!document.repositoryForm.elements[i].checked)
				if(repositoryForm.elements[i].name != "checkall"){
				document.repositoryForm.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.repositoryForm.length;i++){
			if (document.repositoryForm.elements[i].checked)
				if(repositoryForm.elements[i].name != "checkall"){
				document.repositoryForm.elements[i].checked=false;
				}
		}
	}

</script>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 新增序号重置</td>
      </tr>
    </table>
        <form action="addIdcodeResetAction.do" method="post"  name="repositoryForm" onSubmit="return formChk();">

         <fieldset align="center">  <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>基本信息</td>
            </tr>
          </table>
		  </legend>
		  
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td width="7%"  align="right">备注：</td>
              <td width="93%"><textarea name="memo" cols="150" rows="4" id="memo"></textarea></td>
              </tr>
			  </table>
		  </fieldset>
			  
			   <fieldset align="center">  <legend> 
            <table width="80" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>相关产品信息</td>
            </tr>
          </table>
		  </legend>
		  
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr > 
                  <td width="100%" ><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td id="elect" >选择产品： <img src="../images/CN/find.gif" width="18" height="18" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                    </tr>
                  </table></td>
                </tr>
              </table>          
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<td width="3%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="22%">产品编号</td>
                <td width="32%" > 产品名称</td>
                <td width="27%">规格型号</td>   
				<td width="16%">数量</td>                  
              </tr>
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
			<tr align="center" class="table-back">
			  <td width="4%" ><a href="javascript:deleteProductR();"><img src="../images/CN/del.gif"   border="0"></a></td>
			  <td width="36%">&nbsp;</td>
			  <td width="31%">&nbsp;</td>   
			  <td width="29%">&nbsp;</td>         
			</tr>
		  </table>
			  </fieldset>

          <table width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td><div align="center">
  				<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;<input type="button" name="cancel" value="取消" onClick="history.back();">
              </div></td>
            </tr>
          </table>
        </form>
 </td>
</tr>
</table>
`
</body>
</html>
