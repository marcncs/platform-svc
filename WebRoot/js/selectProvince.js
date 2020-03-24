/**
 * 获取省市区列表。查询省份时currentObj为null。childObj为需要显示列表的对象。
 * @param currentObj
 * @param childObj
 * @param selectedVal
 */
function ajaxSelectChildren(currentObj, childObj, selectedVal){
	var parentCode = 0;
	if(currentObj != null){
		parentCode = $(currentObj).val();
	} 
	$.ajax({
		type: 'POST',
		async: false,
		url: "../sales/listAreasAction.do?type=1" ,
		data: {'code':parentCode} ,
		dataType:'json',
		success: function(results){
			if (!results.legal){
				$.messager.show({
					title: '错误',
					msg: results.message
				});
			} else {
				buidSelect(childObj, results.result, selectedVal);
			}
		},  
	    error : function() {  
	     	alert("异常！");
	    }
	});
}

function buidSelect(obj, list, selectedVal){
	$(obj).empty();
	$(obj).append("<option value=''>-请选择-</option>"); 
	$.each(list, function(){     
		if(this.id == selectedVal){
			$(obj).append("<option value='"+this.id+"' selected>"+this.areaname+"</option>"); 
		}
		else{
			$(obj).append("<option value='"+this.id+"'>"+this.areaname+"</option>"); 
		}
	});
}