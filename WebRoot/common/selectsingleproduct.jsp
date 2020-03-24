<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="tag.jsp"%>
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="javascript">
	var checkid=0;
	var checkname="";
	var checkunit="";
	var checkunitid="";
	var checkspec="";
	var checknccode="";
	var checksunit="";
	var checksunitid="";
	var checkboxquantity="";
	var checkstockpile="";
	var checkpacksize="";
	var checkduqty="";
	var checkdu="";
	var checkduname="";
	var checkabc="";
	var checkncc="";
	var checkcodeseq="";
	
	var checkwise=""; //add by tommy 
	
	var checkcopys=""; //add by yufeng.wang
	
	function CheckedObj(obj,objid,objname,objunitidname,objunitid,objspecmode, objnccode, objsunitname,objsunit,objboxquantity,objstockpile,objpacksize,objduqty,objdu,objduname,objabc,objncc,objcodeseq,objwise,objcopys){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 } 
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	 checkunit = objunitidname;
	 checkunitid = objunitid;
	 checkspec = objspecmode;
	 checknccode = objnccode;
	 checksunit = objsunitname;
	 checksunitid = objsunit;
	 checkboxquantity = objboxquantity;
	 checkstockpile = objstockpile;
	 checkpacksize = objpacksize;
	 checkduqty = objduqty;
	 checkdu = objdu;
	 checkduname = objduname;
	 checkabc = objabc;
	 checkncc = objncc;
	 checkcodeseq=objcodeseq;
	 checkwise=objwise;
	 checkcopys = objcopys;
	}

	function Affirm(){
	if(checkid!=""){
		var p={id:checkid,productname:checkname,unitid:checkunitid,unitidname:checkunit,specmode:checkspec,nccode:checknccode,sunitname:checksunit,sunit:checksunitid,boxquantity:checkboxquantity,stockpile:checkstockpile,packsizename:checkpacksize,duqty:checkduqty,du:checkdu,duname:checkduname,abc:checkabc,ncc:checkncc,codeseq:checkcodeseq,wise:checkwise,copys:checkcopys};
		window.returnValue=p;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}
	}

this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
	}	
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="bodydiv" >
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择产品</td>
        </tr>
      </table>
       <form name="search" method="post" action="../common/selectSingleProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       		<input type="hidden" name="proId" value="${proId}" />
       		<input type="hidden" name="wid" value="${wid}" />
       		<input type="hidden" name="oid" value="${oid}" />
       		<input type="hidden" name="unit" value="${unit}" />
          <tr class="table-back"> 
            <td width="18%" height="22" align="right">产品类别：</td>
            <td width="20%"><input type="hidden" name="KeyWordLeft" value="${KeyWordLeft}"><windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}" /></td> 
            <td width="29%" align="right">关键字：</td>
            <td width="33%"><input type="text" name="KeyWord" value="${KeyWord}"></td>
             <td class="SeparatePage">
            <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"/></td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td style="text-align: right;"><pages:pager action="../common/selectSingleProductAction.do"/></td>
        </tr>
      </table>
       </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        
          <tr align="center" class="title-top-lock"> 
            <td>编号</td>
            <td>产品名称</td>
            <td>物料号</td>
            <td>规格</td>
            <td>规格明细</td>
            <td>单位</td>
            <td>标签份数</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}','${p.productname}','${p.countunitname}','${p.countunit}','${p.specmode}', '${p.nccode}', '${p.sunitname}','${p.sunit}', '${p.boxquantity}','${p.stockpile}','${p.packSizeName}','${p.defaultUnitQuantity}','${p.defaultUnit}','${p.defaultUnitName}','${p.abcsort}','${p.needCovertCode}','${p.codeSeq}','${p.wise}','${p.copys}');" onDblClick="Affirm();"> 
            <td>${p.id}</td>
            <td>${p.productname}</td>
            <td>${p.nccode}</td>
            <td>${p.specmode}</td>
            <td>${p.packSizeName}</td>
            <td><windrp:getname key='CountUnit' value='${p.countunit}' p='d'/></td>
            <td>${p.copys}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
