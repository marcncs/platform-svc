<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.winsafe.drp.base.dao.db.PageBean"%>
<script>

	var pageFormObj = document.getElementById("myPageForm");
	if (!pageFormObj){
		alert("翻页所需pageForm对象不存在，请检查！");
	}

	function gotoPage(i){
		if (i < 1) i = 1;
		var currentPageNoObj = document.getElementById("myPageForm_CurrentPageNo");
		if (! currentPageNoObj){
			alert("当前页对象不存在，请检查！");
		}
		currentPageNoObj.value = i;
		pageFormObj.submit();
	}
</script>
<%
PageBean<?> include_pageBean = null;
Object attribute_pageBean = request.getAttribute("pageBean");
if (attribute_pageBean != null && attribute_pageBean instanceof PageBean<?>) {
	include_pageBean = (PageBean<?>)attribute_pageBean;
}
if (include_pageBean != null) {
%>	

<div class="panel-heading">
    <ul class="pagination nomargin pull-right">
    
<%
	//定义页面之间间隔数
	int pageConfine = 3;
	//总页数
	Integer totalPage = include_pageBean.getTotalPage();
	//当前页码
	int currentPageNo = include_pageBean.getCurrentPage();

	//首先显示“上一页”的按钮，并判断是否可以点击
	if (currentPageNo == 1) {
		out.print("<li class='disabled'><a href='#'><i class='fa fa-angle-left'></i></a></li>");
	} else {
		out.print("<li><a href='javascript:gotoPage(" + (currentPageNo-1) +");'><i class='fa fa-angle-left'></i></a></li>");
	}

	//显示首页
	if (currentPageNo == 1) {
		out.print("<li class='active'><a href='#'>1</a></li>");
	} else {
		out.print("<li><a href='javascript:gotoPage(1);'>1</a></li>");
	}

	//然后计算当前页和首页距离，判断是否需要省略号
	if ((currentPageNo - 1 - 1) > pageConfine) {
		//当前页和首页超过pageConfine的间隔，需要加省略号
		out.print("<li><a href='#'>...</a></li>");
		for (int i = currentPageNo - pageConfine; i < currentPageNo; i++) {
			out.print("<li><a href='javascript:gotoPage(" + i + ");'>" + i + "</a></li>");
		}
	} else {
		//当前页和首页不到pageConfine的间隔，直接写出首页到当前页（不包含当前页）的全部中间页码
		for (int i = 2; i < currentPageNo; i++) {
			out.print("<li><a href='javascript:gotoPage(" + i + ");'>" + i + "</a></li>");
		}
	}

	//如果当前页大于1，写出当前页
	if (currentPageNo > 1){
		out.print("<li class='active'><a href='#'>" + currentPageNo + "</a></li>");
	}

	//判断尾页大于当前页，才有必要继续写出页码
	if (totalPage > currentPageNo) {
		//然后计算当前页和尾页距离，判断是否需要省略号
		if ((totalPage - currentPageNo - 1) > pageConfine) {
			//当前页和尾页超过pageConfine的间隔，需要加省略号
			for (int i = currentPageNo + 1; i < currentPageNo+pageConfine+1; i++) {
				out.print("<li><a href='javascript:gotoPage(" + i + ");'>" + i + "</a></li>");
			}
			out.print("<li><a href='#'>...</a></li>");
			out.print("<li><a href='javascript:gotoPage(" + totalPage + ");'>" + totalPage + "</a></li>");
		} else {
			//当前页和尾页不到pageConfine的间隔，直接写出当前页到尾页的全部中间页码
			for (int i = currentPageNo + 1; i < totalPage; i++) {
				out.print("<li><a href='javascript:gotoPage(" + i + ");'>" + i + "</a></li>");
			}
			out.print("<li><a href='javascript:gotoPage(" + totalPage + ");'>" + totalPage + "</a></li>");
		}
	}
	
	//最后显示“下一页”的按钮，并判断是否可以点击
	if (currentPageNo >= totalPage) {
		out.print("<li class='disabled'><a href='#'><i class='fa fa-angle-right'></i></a></li>");
	} else {
		out.print("<li><a href='javascript:gotoPage(" + (currentPageNo+1) +");'><i class='fa fa-angle-right'></i></a></li>");
	}
%>
    </ul>
    <h4 class="panel-title">&nbsp;</h4>
</div>
<%
}
%>