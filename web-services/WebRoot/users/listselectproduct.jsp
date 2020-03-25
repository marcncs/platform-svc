<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>

<title>产品列表</title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script type="text/javascript">
var checkid=0;
function CheckedObj(obj,objid){

 for(i=1; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;

}

function Check(){
	var pid = document.all("pid");
	var checkall = document.all("checkall");
	if (pid==undefined){return;}
	if (pid.length){
		for(i=0;i<pid.length;i++){
				pid[i].checked=checkall.checked;
		}
	}else{
		pid.checked=checkall.checked;
	}		
}	


function Affirm(){
	var count=0;
	var pid = document.all("pid");
	if (pid.length){
		for(var i = 0; i < pid.length ; i ++){
			if(pid[i].checked){
				count++;
			}		
		}
	}else{
		if(pid.checked){
			count++;
		}
	}
	if(count<=0){
		alert("请选择上架的产品!");
	}else{
		showloading();
		popWin4("about:blank",500,200, "winform"); 
		listform.submit();
	}
}


</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">选择经营产品</td>
      </tr>
    </table>
     <form name="search" method="post" action="../users/listOrganProductForSelectAction.do">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
         
            <tr class="table-back">
              <td width="10%"  align="right">产品类别：</td>
              <td width="25%" ><input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">			
			<windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}"/></td>
              <td width="11%"  align="right">关键字：</td>
              <td width="24%" ><input type="text" name="KeyWord" value="${KeyWord}">
                </td>
              <td width="11%" align="right">&nbsp;</td>
              <td width="19%" class="SeparatePage"><input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif" border="0" title="查询"></td>
            </tr>
         
        </table>
         </form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50" align="center">
									<a href="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上架</a></td>
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
						      <td class="SeparatePage"><pages:pager action="../users/listOrganProductForSelectAction.do"/></td>
												
							</tr>
						</table>
						 <FORM METHOD="POST" name="listform" ACTION="../users/setOrganProductAction.do" target="winform">
        <table class="sortable"  width="100%" border="0" cellpadding="0" cellspacing="1">
         
            <tr align="center" class="title-top">
              <td width="3%" class="sorttable_nosort"><input type="checkbox" name="checkall" id="checkall" onClick="Check();" ></td>
              <td>产品编号</td>
              <td>物料号</td> 
              <td>产品名称</td>
              <td>规格</td>
              <td>单位</td>
            </tr>
              <logic:iterate id="d" name="opls" >
                <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${d.id}');">
                  <td align="left"><input type="checkbox" name="pid" value="${d.id}" ></td>
                  <td  align="left">${d.id}</td>
                  <td  align="left">${d.mCode}</td>
                  <td align="left">${d.productname}</td>
                  <td align="left">${d.specmode}</td>
                  <td align="left"><windrp:getname key='CountUnit' value='${d.countunit}' p='d'/></td>
                </tr>
              </logic:iterate>
         
      </table>
       </form>
     </td>
          </tr>
      </table>
</body>
</html>

