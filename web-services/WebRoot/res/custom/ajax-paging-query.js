/**
 * ajax分页
 * 
 * options={
 * 	url: "/demo/search" required,	 请求url
 * 	pageSize: 10 optional	每页显示数据条数||默认为20
 * 	container: "page_info_container" required	分页导航栏显示容器名称
 * 	data：{} optional	请求参数
 * 	successCallback: callback required;	//成功后回调函数 
 * }
 */

function ajaxQueryByPage(options) {
	this.url = options.url||null;	//请求url
	this.pageSize = options.pageSize||20;	//每页显示数据条数
	this.container = options.container;	//分页导航栏显示容器
	//this.textAlign = options.textAlign || "right";	//分页导航的信息显示位置
	this.data = options.data || {};
	var successCallback = options.successCallback || function(data){alert("请传入显示数据的回调函数");};	//回调函数
	
	var currentPageNo = 1;	//当前页，默认为第一页
	var totalPage = 0;	//记录总页数
	var containerObj = jQuery("." + this.container);
	
	//初始化方法
	this.init = function() {
		//调用第一页数据
		gotoPage(currentPageNo);
	}();
	
	//私有方法
	
	//显示分页导航栏
	function showPageNavigate(pageNo, totalRecords) {
		var startIndex = getStartIndex(pageNo);
		//结束位置
		var endIndex = getEndIndex(pageNo, totalRecords);
		//当前页
		currentPageNo = pageNo;
		
		containerObj.empty();
		if (totalRecords != 0)
			startIndex += 1;
		
		//containerObj.css("text-align", this.textAlign);
		//显示总数据条数和当前页的开始条数和结束条数
		//containerObj.append(jQuery("<span id='currentPageStartAndEndIndex' class='disabled'> " + startIndex + "-" + endIndex + "</span>, <span id='dataTotalRecords' class='disabled'>共" + totalRecords + "条</span>"));
		
		//定义页面之间间隔数
		var pageConfine = 3;
		
		//分页信息栏
		var pageInfoContainer = jQuery('<ul class="pagination nomargin pull-right"></ul>');
		//显示“上一页”的按钮，并判断是否可以点击
		var prevButton;
		if (currentPageNo == 1) {
			//当是第一页时，“上一页”不能再点击
			prevButton = jQuery("<li class='disabled'><a href='#'><i class='fa fa-angle-left'></i></a></li>");
		} else {
			prevButton = jQuery("<li><a href='#'><i class='fa fa-angle-left'></i></a></li>");
			//绑定“上一页”点击事件
			prevButton.click(function(e){
				var prevPageNo = currentPageNo-1;
				if (prevPageNo < 1) prevPageNo = 1;
			    if (prevPageNo > totalPage){
			    	prevPageNo = totalPage;
		        }
			    currentPageNo = prevPageNo;
				gotoPage(prevPageNo);
			});
		}
		pageInfoContainer.append(prevButton);
		
		//显示首页
		var firstButton;
		if (currentPageNo == 1) {
			firstButton = jQuery("<li class='active'><a href='#'>1</a></li>");
		} else {
			firstButton = jQuery("<li><a href='#'>1</a></li>");
			firstButton.on("click", null, {pageNo: 1}, function(e){
				gotoPage(e.data.pageNo);
			});
		}
		pageInfoContainer.append(firstButton);
		
		//然后计算当前页和首页距离，判断是否需要省略号
		if ((currentPageNo - 1 - 1) > pageConfine) {
			//当前页和首页超过pageConfine的间隔，需要加省略号
			pageInfoContainer.append(jQuery("<li><a href='#'>...</a></li>"));
			for (var i = currentPageNo - pageConfine; i < currentPageNo; i++) {
				var numberButton = jQuery("<li><a href='#'>" + i + "</a></li>");
				numberButton.on("click", null, {pageNo: i}, function(e){
					gotoPage(e.data.pageNo);
				});
				pageInfoContainer.append(numberButton);
			}
		} else {
			//当前页和首页不到pageConfine的间隔，直接写出首页到当前页（不包含当前页）的全部中间页码
			for (var i = 2; i < currentPageNo; i++) {
				var numberButton = jQuery("<li><a href='#'>" + i + "</a></li>");
				numberButton.on("click", null, {pageNo: i}, function(e){
					gotoPage(e.data.pageNo);
				});
				pageInfoContainer.append(numberButton);
			}
		}
		
		//如果当前页大于1，写出当前页
		if (currentPageNo > 1){
			pageInfoContainer.append(jQuery("<li class='active'><a href='#'>" + currentPageNo + "</a></li>"));
		}
		
		//判断尾页大于当前页，才有必要继续写出页码
		if (totalPage > currentPageNo) {
			//然后计算当前页和尾页距离，判断是否需要省略号
			if ((totalPage - currentPageNo - 1) > pageConfine) {
				//当前页和尾页超过pageConfine的间隔，需要加省略号
				for (var i = currentPageNo + 1; i < currentPageNo+pageConfine+1; i++) {
					var numberButton = jQuery("<li><a href='#'>" + i + "</a></li>");
					numberButton.on("click", null, {pageNo: i}, function(e){
						gotoPage(e.data.pageNo);
					});
					pageInfoContainer.append(numberButton);
				}
				pageInfoContainer.append(jQuery("<li><a href='#'>...</a></li>"));
				//显示最后一页
				var lastButton = jQuery("<li><a href='#'>" + totalPage + "</a></li>");
				lastButton.on("click", null, {pageNo: totalPage}, function(e){
					gotoPage(e.data.pageNo);
				});
				pageInfoContainer.append(lastButton);
			} else {
				//当前页和尾页不到pageConfine的间隔，直接写出当前页到尾页的全部中间页码
				for (var i = currentPageNo + 1; i <= totalPage; i++) {
					var numberButton = jQuery("<li><a href='#'>" + i + "</a></li>");
					numberButton.on("click", null, {pageNo: i}, function(e){
						gotoPage(e.data.pageNo);
					});
					pageInfoContainer.append(numberButton);
				}
			}
		}
		
		//显示“下一页”的按钮，并判断是否可以点击
		var nextButton;
		if (currentPageNo >= totalPage) {
			//最后一页不能再点击
			nextButton = jQuery("<li class='disabled'><a href='#'><i class='fa fa-angle-right'></i></a></li>");
		} else {
			nextButton = jQuery("<li><a href='#'><i class='fa fa-angle-right'></i></a></li>");
			//绑定“下一页”点击事件
			nextButton.click(function(e){
				var nextPageNo = currentPageNo+1;
				if (nextPageNo < 1) nextPageNo = 1;
			    if (nextPageNo > totalPage){
			    	nextPageNo = totalPage;
		        }
			    currentPageNo = nextPageNo;
				gotoPage(nextPageNo);
			});
		}
		pageInfoContainer.append(nextButton);
		
		containerObj.append(pageInfoContainer);
	}
	
	//翻页到一个指定的页面
	function gotoPage(pageNo){
		//获取起始位置
		var startIndex = getStartIndex(pageNo);
	    //var requestUrl = url + "&current_page =" + pageNo + "&page_size=" + this.pageSize;
	    this.data.current_page = pageNo;
	    this.data.page_size = this.pageSize;
	    
	    jQuery.ajax({type: 'GET', url: this.url, data: this.data, dataType: 'json', success: function(result) {
				var dataList = null;
				if (result) {
					var resultData = result.data;
					if (resultData) {
						//获取总记录数
						var totalRecords = resultData.total_rows;
						//计算总页数
						totalPage = getTotaoPage(totalRecords);
						//重绘分页导航栏
						showPageNavigate(pageNo, totalRecords);
						
						dataList = resultData.list;
					}
					//调用回调函数，显示页面
					successCallback(dataList);
				}
		  	}
		});
    }
	
	//获取总页数
	function getTotaoPage(totalRecords) {
		var count = totalRecords % this.pageSize;
		if (count > 0) {
			return parseInt(totalRecords/this.pageSize + 1, 10);
		} else {
			return parseInt(totalRecords/this.pageSize, 10);
		}
	}
	
	//某一页的开始位置
	function getStartIndex(pageNo) {
		return this.pageSize * (pageNo-1);
	}
	
	//某一页的结束位置
	function getEndIndex(pageNo, totalRecords) {
		var endIndex = this.pageSize * pageNo;
		if (endIndex <= totalRecords) {
			return endIndex;
		} else {
			return  totalRecords;
		}
	}
	
	//判断字符串是否为数字
	function isNumber(validatedStr) {
		if(validatedStr == null || validatedStr.length == 0){
			return false;
		}
		for(var i=0;i<validatedStr.length;i++){
			if(validatedStr.charAt(i) < '0' || validatedStr.charAt(i) > '9')
				return false;
		}
		return true;
	}
	
	//公共方法
}
