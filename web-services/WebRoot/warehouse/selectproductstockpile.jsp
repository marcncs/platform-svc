<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
	var checkid=0;
	var warehouseid=0;
	var warehousename="";
	var batch="";
	var cost=0.00;
	function CheckedObj(obj,objid,objwid,objname,objbatch,objcost){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 warehouseid=objwid;
	 warehousename=objname;
	 batch=objbatch;
	 cost=objcost;
	}

	
	function Affirm(){
	if(checkid>0){		
		var wbc={warehouseid:warehouseid,warehousename:warehousename,batch:batch,cost:cost};
		window.returnValue=wbc;
		window.close();
		}else{
		alert("请选择你需要的产品!");
		}
	}
	
	function Check(){
		if(document.listform.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	

	
	

</script>
</head>

<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择产品</td>
	    
        </tr>
      </table>
      <form name="search" method="post" action="selectProductStockpileAction.do?pid=${pid}">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 		  	
		  <td width="9%" align="right">产品类别：</td>
            <td width="19%"><select name="KeyWordLeft" id="KeyWordLeft">
                <logic:iterate id="ps" name="uls" > 
				<option value="${ps.structcode}">
				<c:forEach var="i" begin="1" end="${fn:length(ps.structcode)/2}" step="1"><c:out value="--"/></c:forEach>${ps.sortname}</option> 
            </logic:iterate> </select></td>
			 <td width="6%"  align="right">品牌：</td>
            <td width="11%" >${brand}</td>
            <td width="13%"  align="right">关键字：</td>
            <td width="42%" ><input type="text" name="KeyWord" value="${KeyWord}">
            <input type="submit" name="Submit" value="查询"></td>
            
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
		<input type="hidden" name="pid" value="${pid}">
          <tr align="center" class="title-top"> 
	  	   <!-- <td width="3%" ><input type="checkbox" name="checkall" onClick="Check();" ></td>-->
            <td width="13%" >编号</td>
            <td width="26%">产品名称</td>
            <td width="28%">仓库名称</td>
            <td width="13%">批次</td>		
			<td width="13%">库存数量</td>			
          </tr>
          <logic:iterate id="p" name="sls" > 
		  <tr class="table-back" onClick="CheckedObj(this,${p.id},${p.warehouseid},'${p.warehourseidname}','${p.batch}',${p.cost});" onDblClick="Affirm();" > 
		  	<!--<td> <input type="checkbox" name="psid" value="${p.id}" ></td>-->
            <td >${p.id}</td>
            <td>${p.psproductname}</td>
            <td><input type="hidden" name="warehourseid" value="${p.warehouseid}">${p.warehourseidname}</td>   
			<td><input type="hidden" name="cost" value="${p.cost}"><input type="hidden" name="batch" value="${p.batch}">${p.batch}</td>   
			<td>${p.stockpile}</td>     
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Affirm();">确定</a></td>
                <td width="60"><a href="javascript:window.close();">取消</a></td>
              </tr>
            </table></td>
          <td width="70%"> <presentation:pagination target="/warehouse/selectProductStockpileAction.do?pid=${pid}"/> 
          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
