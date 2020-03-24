<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<base target="_self">
  <head>
    <html:base />
    <title>WINDRP-分销系统</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
	<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
	<SCRIPT language="javascript" src="../js/selectunit.js"> </SCRIPT>
	<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
	<link href="../css/ss.css" rel="stylesheet" type="text/css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<script language="JavaScript">

    var billno="";
    var billsort="";
    var index = "";
    var truebillno="";
    var object;
	function CheckedObj(obj,billid,iindex){	
	  object = obj;
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }	 
	 obj.className="event";
	 billno = billid; 
	 billsort =document.validateProvide.billsort.value;
	 index = iindex;
	}
	
	function Del(obj){
		if(billno!=""){
			if(window.confirm("您确认要删除编号为："+billno+" 的单据吗？如果删除将永远不能恢复！")){
				popWin("../common/delUploadBillNoAction.do?billsort="+billsort+"&billno="+billno,500,250);				
				deleteR(obj)
			}
		}else{
			alert("请选择你要操作的单据！");
		}
	}
	
	function Modify(obj){
	    var truebills = document.getElementsByName("trueBillNo");
	    var oRow = obj.parentElement.parentElement;			
		truebillno =truebills[oRow.rowIndex-1].value;
				
	    if(billno!=""&&truebillno != ""){
			if(window.confirm("您确认要修改编号为："+billno+" 的单据\n改为："+truebillno+"吗？")){
				popWin("../common/updUploadBillNoAction.do?billsort="+billsort+"&billno="+billno+"&truebillno="+truebillno,500,250);				
				//deleteR(obj)
			}
		}else{
			alert("请选择你要操作的单据！");
		}
	}
	
	function deleteR(src){
	
       var oRow = src.parentElement.parentElement;		
	       
				//once the row reference is obtained, delete it passing in its rowIndex			
		document.all("dbtable").deleteRow(oRow.rowIndex);		

	
		//document.getElementById('dbtable').deleteRow (i);				
      }
      
      function ChkValue(){
	   var trueBillNo = document.validateProvide.trueBillNo;	
	   if(trueBillNo.value==null||trueBillNo.value==""){
		alert("新单据编码不能为空!");
		return false;
	   }
	   if ( !Validator.Validate(document.validateProvide,2) ){
		      return false;
	         }

	showloading();
	return true;
    }
	
	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
   </script>
  </head>  
  <body>
    <form name="validateProvide" method="post" action="updUploadBillNoAction.do">
    <table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
 
  <tr>
    <td>
    <div id="div1">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改或删除单据上传的错误单号</td>
        </tr>
      </table>      
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr class="title-func-back">
				<td width="50">
				<!-- <a href="javascript:Del();"><img
					src="../images/CN/delete.gif" width="16" height="16"
					border="0" align="absmiddle">&nbsp;删除</a> -->
				</td>
				<td width="1">
				<img src="../images/CN/hline.gif" width="2" height="14">
				</td>
				<td class="SeparatePage">
				</td>
				</tr>
		</table>     
      </div>
      <input type="hidden" name="billsort" value="${billsort}">
      	<div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr class="title-top">
          <td width="10%" align="left">序号</td>         
          <td width="20%" align="left">上传单据编号</td>
          <td width="20%" align="left" >输入正确单据编号 </td>  
          <td width="30%" align="left" colspan="2">提交操作 </td>    
          </tr>
           <c:set var="count" value="2"/>
          <logic:iterate id="p" name="els" >
         	<tr class="table-back-colorbar"
				onClick="CheckedObj(this,'${p}','${count}');">
            <td>${count-1}</td>            
            <td><input name="billno" type="text" value="${p}" id="billno" size="30" readonly></td>
            <td><input name="trueBillNo" type="text" value="${p}" id="trueBillNo" size="35">
              </td> 
              <td align="center"><input type="button" name="Submit" onClick="Modify(this);" value="确定修改"></input></td> 
              <td align="center"><input type="button" name="Submit" onClick="Del(this);" value="删除上传"></input></td>                  
            </tr>     
             <c:set var="count" value="${count+1}"/>    
        </logic:iterate>
       </table>  
      </div>    
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
          <td align="center" height="20">     
          </td> 
        </tr>
        <tr>           
       &nbsp;&nbsp;
          <!-- <input type="button" name="button" value="取消" onClick="window.close();"> 
          -->
  </tr>

</table>
</td>
</tr>
</table>
</form>
  </body>
</html:html>
