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
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";       
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"35\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"35\" readonly>";
    chebox=document.all("che");  

}

function SupperSelect(rowx){
	var product =showModalDialog("../warehouse/toSelectAllProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");

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

function Del(id){
		if(id!=""){
			if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../assistant/delRepositoryFileAction.do?id="+id,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	

function formChk(){
	var productname = document.repositoryForm.title;
	var rtid = document.repositoryForm.rtid;
	if(rtid.value==1){
		alert("请选择下级类别！");
		return false;
	}
	if(productname.value==""){
		alert("标题能为空！");
		return false;
	}
	

	return true;
 }

 var i=1;
 var chebox=null;
 
  function addRow(){
 
    var x = document.all("xq").insertRow(xq.rows.length);

        var a=x.insertCell(0);
        var b=x.insertCell(1);
 
        a.innerHTML="标题：<input type=\"text\" name=\"filetitle\" size=\"30\">";
        b.innerHTML="选择：<input type=\"file\" name=\"filename"+i+"\">";
		i++;
    chebox=document.all("che");  //計算total值
 
}


function deleteR(){
    var celltable = document.getElementById("xq");
	if ( celltable.rows.length > 1 ){
		document.getElementById("xq").deleteRow(-1)
	}
}

</script>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 修改知识库</td>
      </tr>
    </table>
        <form action="updRepositoryAction.do" method="post" enctype="multipart/form-data" name="repositoryForm" onSubmit="return formChk();">
		<input type="hidden" name="id" value="${r.id}">

         <fieldset align="center">  <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>基本信息</td>
            </tr>
          </table>
		  </legend>
		  
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td width="12%"  align="right">类别：</td>
              <td width="20%">
                <select name="rtid" id="rtid">
                  <logic:iterate id="ps" name="uls" >
                    <option value="${ps.structcode}" <c:if test="${ps.structcode==r.rtid}"><c:out value="selected"/></c:if>>
                      <c:forEach var="i" begin="1" end="${fn:length(ps.structcode)/2}" step="1">
                        <c:out value="--"/></c:forEach>${ps.sortname}</option>
                  </logic:iterate>
              </select></td>
              <td width="10%" align="right">标题：</td>
              <td width="23%"><input name="title" type="text" id="title" size="38" value="${r.title}">
              <span class="style1">*</span></td>
             
            </tr>
			 <tr>
			   <td  align="right">内容：</td>
			   <td colspan="3"><textarea name="content" id="content" style="width:620; height:400;">${r.content}</textarea></td>
			  
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
				<td width="4%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="36%">产品编号</td>
                <td width="31%" > 产品名称</td>
                <td width="29%">规格型号</td>               
              </tr>
			  <c:set var="count" value="2"/>
            <logic:iterate id="p" name="rplist" > 
            <tr class="table-back">
				<td><input type="checkbox" value="${count}" name="che"></td>
                <td><input name="productid" type="text" id="productid" value="${p.productid}" size="16" readonly></td>
                <td ><input name="productname" type="text" value="${p.productname}" id="productname" size="45" readonly></td>
				<td><input name="specmode" type="text" value="${p.specmode}" id="specmode" size="6" readonly></td>    
			</tr>
			<c:set var="count" value="${count+1}"/>
			</logic:iterate> 
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
			  
			  
			  <fieldset align="center">  <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>附件信息</td>
            </tr>
          </table>
		  </legend>
		  
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
			 <tr>
			   <td width="12%"  align="right" valign="top">附件：</td>
			   <td width="88%"><a href="javascript:addRow();"><img src="../images/nolines_plus.gif" width="16" height="18" border="0" title="新增"></a>
			     <a href="javascript:deleteR();"><img src="../images/nolines_minus.gif" width="16" height="18" border="0" title="删除"></a>
			     
				 <table width="500" border="0" id="xq" cellpadding="0" cellspacing="0">
                 <tr>
                   <td width="50%">标题：<input type="text" name="filetitle" size="30"></td>
                   <td width="50%">选择：
                   <input type="file" name="myfile0"></td>
                 </tr>
               </table></td>
		      </tr>
			  </table>
			  </fieldset>
			  
			<fieldset align="center"> <legend>
			  <table width="70" border="0" cellpadding="0" cellspacing="0">
				<tr>
				  <td>已上传附件</td>
				</tr>
			  </table>
			  </legend>
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
				<tr align="center" class="title-top">
				  <td width="12%" >序号</td> 
				  <td width="72%">标题(右击另存为下载)</td>
				  <td width="16%">操作</td>
				</tr>
				<c:forEach var="p" items="${rflist}" varStatus="aa" > 
				<tr class="table-back">
				  <td>${aa.index+1}</td> 
				  <td ><a href="${severpath}${p.filepath}" target="_blank">${p.title}</a></td>         	  <td align="center"><a href="javascript:void(0)" onClick="Del(${p.id})">删除</a></td>
				</tr>
				</c:forEach> 
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
</body>
</html>
