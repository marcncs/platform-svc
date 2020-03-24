<%@ page language="java" pageEncoding="UTF-8"%>
<ws:html>
<ws:head title="选择销售员" css="style" js="main,form,winsafe/pagination">
<script type="text/javascript">

	function CheckedObj(obj){
		setTrBgColor();
		obj.className = "event";
	}

	var choseOneRow = "<s:text name='common.info.chooseOneRow'/>";
	function preAdd(){
		var idarray = new Array();
		var idarray2 = new Array();
		$("#tbody :checkbox").each(function(){
			if($(this).attr("checked")){
				idarray.push($(this).val());
			}else{
				idarray2.push($(this).val());
			}
		});
		winsafe.getParentDialogWindow().winsafe.callwin.setSaleMan(idarray,idarray2);
		winsafe.close();
	}

	function doQuery(){
		document.form1.submit();
	}

	function resetText(){
		document.getElementById("kw").value = "";
	}
</script>
</ws:head>
<body>
	<s:form name="form1" method="post" action="organCommon!selectMoreSaleMan.action">
	<input type="hidden" id="oid" name="id" value="${id }"/>
	<s:token/>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  		<tr>
    		<td>
				<div id="bodydiv">
					<table width="100%"   border="0" cellpadding="0" cellspacing="0">					  
				  		<tr class="table-back">
				  			<td width="30%" align="left"><s:text name="msg.keyword"></s:text>：<s:textfield name="keyWords" id="kw"/></td>
				            <td width="70%"></td>						    							    							    
				        </tr>				 
				    </table>
            		<div class="button_area" >
              			<ws:button  onclick="doQuery();return false;" value="msg.search" fromProperties="true"></ws:button>
              			<ws:button id="btn_reset" name="btn_reset" value="msg.reset" fromProperties="true" onclick="resetText();"></ws:button>              
            		</div>
	 			</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
							<td align="left">
								<a href="javascript:preAdd();"><img src="${URL_IMAGEPATH}add.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a>
							</td>	
							<td align="right" id="pageMenu">
								<s:property value="%{pageMenu}" escape="false" />
							</td>
						</tr>
					</table> 
					<div id="listdiv" style="overflow-y: auto; height: 375px;" >
	       				<table class="sortable"  width="100%" border="0" cellpadding="0" cellspacing="1">
	           				<tr align="center" class="title-top-lock">
	           					<td width="32px"><input type="checkbox" name="checkall" class="checkall" group="listTableCheckBOX" /></td>
	             				<td width="150px"><ws:i18n key="msg.user.id" desc="用户ID"/></td>
			  					<td width="150px"><ws:i18n key="msg.username" desc="用户名"/></td>
			  					<td width="200px"><ws:i18n key="msg.sale.productstruct" desc="经营产品类别"/></td>
	             				<td style="width:auto">&nbsp;</td>
	           				</tr>
	           				<TBODY id="tbody">
	           				<s:iterator value="salesManList" id="s">
		  						<tr class="table-back-colorbar" onmousemove="CheckedObj(this);">
									<td align="center">
										<s:if test="%{#s.checked}">
											<input type="checkbox" name="ids" value="${s.id}" checked="checked" class="subcheck" group="listTableCheckBOX" onfocus="this.blur()"/>
										</s:if>
										<s:else>
											<input type="checkbox" name="ids" value="${s.id}" class="subcheck" group="listTableCheckBOX" onfocus="this.blur()"/>
										</s:else>
									</td>
									<td align="left">${s.userName}</td>
									<td align="left">${s.realName}</td>
									<td align="left">${s.salePSName}</td>
		 	 						<td>&nbsp;</td>
		 	 					</tr>
		  					</s:iterator>
		  					</TBODY>
	     				</table>
	     			</div>
	     		</div>
	  		</td>
  		</tr>
	</table>
	</s:form>
</body>
</ws:html>

