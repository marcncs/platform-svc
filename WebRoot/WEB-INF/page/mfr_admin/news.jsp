<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.*"%> 
<%@page import="com.winsafe.drp.base.dao.db.PageBean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%
String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
PageBean<?> pageBean = (PageBean<?>)request.getAttribute("pageBean");
List<?> newsList = pageBean == null ? null : pageBean.getList();
request.setAttribute("newsList",newsList);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>云单页厂家管理平台</title>

<link href="<%=path%>/res/bracket/css/style.default.css" rel="stylesheet">
<link href="<%=path%>/res/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet" />

<style type="text/css">
	p {
	    overflow: hidden;
	    text-overflow: ellipsis;
	    display: -webkit-box;
	    -webkit-line-clamp: 3;
	    -webkit-box-orient: vertical;
	}
	.title-display {
		white-space:nowrap;
		overflow:hidden;
		text-overflow:ellipsis;
	}
</style>

<script src="<%=path%>/res/bracket/js/jquery-1.11.1.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="<%=path%>/res/bracket/js/html5shiv.js"></script>
  <script src="<%=path%>/res/bracket/js/respond.min.js"></script>
  <![endif]-->
</head>

<body>
	<!-- Preloader -->
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>

	<section>
		<!-- leftpanel -->
<%-- 		<jsp:include page="/mfr_admin/include_left.jsp" flush="true" /> --%>

		<div class="mainpanel">

			<!-- headerbar -->

			<div class="pageheader">
				<h2>
					<i class="fa fa-edit"></i> 成功故事<span>故事列表</span>
				</h2>
				<div class="breadcrumb-wrapper">
					<a href="<%=path%>/mfr_admin/news_add.jsp" target="_blank" class="btn btn-sm btn-info">新增故事</a>
					<!-- <button class="btn btn-sm btn-info" onclick="javascript:dispalyAddNews();">新增新闻</button> -->
				</div>
			</div>

			<div class="contentpanel">
      
		      <div id="bloglist" class="row">
		      	<c:if test="${newsList != null}">
		      		<logic:iterate id="manuNews" name="newsList" > 
		      			<div class="col-xs-6 col-sm-4 col-md-3">
				          <div class="blog-item">
				            <a title="点击图片修改" href="#" class="blog-img">
				            	<img id="img_manufacturer_news_pic_${manuNews.id}" style="width:390.75px; height:206px;" 
				            			src="${manuNews.picUrl}" class="img-responsive" title="点击修改" />
				            </a>
				            <div class="blog-details">
				              <h4 class="blog-title title-display"><a href="#">${manuNews.title}</a></h4>
				              <ul class="blog-meta">
				                <li>${manuNews.publishTime}</li>
				              </ul>
				              <div style="height:137px;" class="blog-summary">
				                <p>${manuNews.summary}</p>
				                <a class="btn btn-sm btn-warning" href="<%=path%>/mfr_admin/news_update.jsp?news_id=${manuNews.id}" target="_blank">编 辑</a>
				                <button class="btn btn-sm btn-danger" onclick="displayDeleteNews(${manuNews.id})">删 除</button>
				              </div>
				            </div>
				          </div><!-- blog-item -->
				        </div><!-- col-xs-6 -->
		      		</logic:iterate>
		      	</c:if>
		        <%-- <%
		        	if (newsList != null) {
								for (Iterator<?> iterator = newsList.iterator(); iterator.hasNext();) {
									ManufacturerNews manuNews = (ManufacturerNews) iterator.next();
		        %>
		        	<div class="col-xs-6 col-sm-4 col-md-3">
			          <div class="blog-item">
			            <a title="点击图片修改" href="#" class="blog-img">
			            	<img id="img_manufacturer_news_pic_<%=manuNews.getId() %>" style="width:390.75px; height:206px;" 
			            			src="<%=manuNews.getPicUrl() %>" class="img-responsive" title="点击修改" />
			            </a>
			            <div class="blog-details">
			              <h4 class="blog-title title-display"><a href=""><%=manuNews.getTitle() %></a></h4>
			              <ul class="blog-meta">
			                <li><%=DateFormatUtils.format(manuNews.getPublishTime(), "yyyy-MM-dd") %></li>
			              </ul>
			              <div style="height:137px;" class="blog-summary">
			                <p><%=manuNews.getSummary()%></p>
			                <a class="btn btn-sm btn-warning" href="/mfr_admin/news_update.jsp?news_id=<%=manuNews.getId() %>" target="_blank">编 辑</a>
			                <!-- <button class="btn btn-sm btn-warning" onclick="displayEditNews(<%=manuNews.getId() %>)">编 辑</button> -->
			                <button class="btn btn-sm btn-danger" onclick="displayDeleteNews(<%=manuNews.getId() %>)">删 除</button>
			              </div>
			            </div>
			          </div><!-- blog-item -->
			        </div><!-- col-xs-6 -->
		        <%
		        		}
		        	}
		        %> --%>
		        <form id="myPageForm" method="get" action="<%=path%>/qr/mfr_admin/news">
	      		<input type="hidden" id="myPageForm_CurrentPageNo" name="currentPage" value="" />
	       		 </form>
		      </div><!-- row -->
		      <jsp:include page="../mfr_admin/include_pageinfo.jsp" flush="true" />
		    </div><!-- contentpanel -->

		</div>
		<!-- mainpanel -->
		

	</section>

	<script src="<%=path%>/res/bracket/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path%>/res/bracket/js/bootstrap.min.js"></script>
	<script src="<%=path%>/res/bracket/js/modernizr.min.js"></script>
	<script src="<%=path%>/res/bracket/js/jquery.sparkline.min.js"></script>
	<script src="<%=path%>/res/bracket/js/toggles.min.js"></script>
	<script src="<%=path%>/res/bracket/js/retina.min.js"></script>
	<script src="<%=path%>/res/bracket/js/jquery.cookies.js"></script>

	<script src="<%=path%>/res/bracket/js/jquery-ui-1.10.3.min.js"></script>
	<script src="<%=path%>/res/bracket/js/select2.min.js"></script>
	<script src="<%=path%>/res/bracket/js/jquery.validate.min.js"></script>

	<script src="<%=path%>/res/bootstrap-fileinput/js/fileinput.min.js"></script>
	<script src="<%=path%>/res/bootstrap-fileinput/js/fileinput_locale_zh.js"></script>
	
	<script src="<%=path%>/res/jquery.sliphover.min.js"></script>
	
	<!-- 配置文件 -->
    <script type="text/javascript" src="<%=path%>/res/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="<%=path%>/res/ueditor/ueditor.all.js"></script>

	<script src="<%=path%>/res/bracket/js/custom.js"></script>
	
	<script src="<%=path%>/res/input-len-counter.js"></script>
	<script src="<%=path%>/res/jquery.scrollTo.js"></script>

	<script>
	
		jQuery(document).ready(function() {
	    "use strict";
			
			//鼠标从图片滑过时，添加覆盖物
			//$('#bloglist').sliphover();
	  });
		
		//新闻标题和新闻概要最大字符数
		var news_title_max_len = 20;
		var news_summary_max_len = 40;
	
		function dispalyAddNews() {
			var ue = UE.getEditor('ueditor_add_news_content');
			//为编辑器添加获取焦点事件
			ue.addListener("focus", function (type, event) {
				$("#ueditor_add_news_content").next('label').eq(0).remove();
	    });
			$("#modal_add_manufacturer_news").modal("show");
		}

		function displayEditNews(newsId) {
			var ue = UE.getEditor('ueditor_update_news_content');
			//为编辑器添加获取焦点事件
			ue.addListener("focus", function (type, event) {
				$("#ueditor_update_news_content").next('label').eq(0).remove();
	    });
			ue.ready(function() {
	      //获取产品信息
    		$.ajax({url: "/mfr_admin/news/" + newsId, type: "GET", dataType: "json", async: false, 
    			success: function(result) {
    				var manufacturernews = result.data;
    				if (manufacturernews) {
    					$("#txt_update_news_title").attr("value", manufacturernews.title);
    					characterLenCounter($("#txt_update_news_title"));
    					$("#txt_update_news_summary").html(manufacturernews.summary);
    					characterLenCounter($("#txt_update_news_summary"));
    					ue.setContent(manufacturernews.content);
    				}
    			}
    		});
	    });
			$("#txt_update_news_id").attr("value", newsId);
			$("#modal_update_manufacturer_news").modal("show");
		}
		
		//显示企业新闻封面图
		function displayEditNewsPic(newsId) {
			$("#tip_edit_manufacturer_news_pic").next('label').eq(0).remove();
			$("#txt_edit_manufacturer_news_id").attr("value", newsId);
			//获取产品信息
   		$.ajax({url: "/mfr_admin/news/" + newsId, type: "GET", dataType: "json", async: false, 
   			success: function(result) {
   				var manufacturernews = result.data;
   				if (manufacturernews) {
   					$("#chk_edit_manufacturer_news_pic_is_show").attr("checked", manufacturernews.is_show_pic);
   					$("#txt_edit_manufacturer_news_pic").fileinput('clear');
   					$("#txt_edit_manufacturer_news_pic").fileinput('refresh', {
	            		allowedFileTypes: ["image"],
	            		showUpload: false,
	            		maxFileSize : 2048,
						    	allowedFileExtensions: ["jpg", "jpeg", "gif", "png"],
						    	initialPreview: [],
								});
   				}
   			}
   		});
			$("#modal_edit_manufacturer_news_pic").modal("show");
		}
		
		function displayDeleteNews(newsId) {
			$("#txt_delete_news_id").attr("value", newsId);
			$("#modal_delete_manufacturer_news").modal("show");
		}
		
		function uploadImg(file) {
			var fd = new FormData();
			fd.append("crop_file", file);
			
			var imgUrl = null;
			$.ajax({
				url: "/api/images",
				type: "POST",
				dataType:"json",
				processData: false,
				contentType: false,
				async: false,
				data: fd,
				success: function(d) {
					imgUrl = d.data.urlFilePath;
				}
			});
			
			return imgUrl;
		}

		$().ready(function() {
			UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
      UE.Editor.prototype.getActionUrl = function(action) {
          if (action == 'uploadimage') {
              return '/api/images/ueditor';
          } else {
              return this._bkGetActionUrl.call(this, action);
          }
      }
	        
      $("#txt_add_news_file").change(function(){
				$("#tip_add_manufacturer_news_file").next('label').eq(0).remove();
			});
	    $("#txt_add_news_title").focus(function(){
				$("#div_add_news_title").next('label').eq(0).remove();
			});
			$("#txt_add_news_summary").focus(function(){
				$("#div_add_news_summary > label:last").remove();
			});
			
			$("#txt_update_news_title").focus(function(){
				$("#div_update_news_title").next('label').eq(0).remove();
			});
			$("#txt_update_news_summary").focus(function(){
				$("#div_update_news_summary > label:last").remove();
			});
	        
			//新增新闻
      $("#btn_submit_add_news").click(function(e) {
      	var title = $("#txt_add_news_title").val();
      	var summary = $("#txt_add_news_summary").val();
      	var content = UE.getEditor('ueditor_add_news_content').getContent();
      	var file = $("#txt_add_news_file").get(0).files[0];
    		var isShowPic = $("#chk_add_news_pic_is_show").is(':checked');
      	
      	if (!file) {
      		$("#tip_add_manufacturer_news_file").next('label').eq(0).remove();
					$("#tip_add_manufacturer_news_file").after('<label for="name" class="error">请选择封面图片</label>');
					$("#modal_add_manufacturer_news").scrollTo("#form_add_news", 500);
					return ;
      	}
      	if (title == null || $.trim(title) == '' || $.trim(title).length > news_title_max_len) {
      		$("#div_add_news_title").next('label').eq(0).remove();
      		$("#div_add_news_title").after('<label for="name" class="error">新闻标题不能为空且长度不能超过' + news_title_max_len + '字</label>');
      		$("#modal_add_manufacturer_news").scrollTo("#tip_add_manufacturer_news_file", 500);
      		return ;
      	}
      	if (summary == null || $.trim(summary) == '' || $.trim(summary).length > news_summary_max_len) {
      		$("#div_add_news_summary > label:last").remove();
      		$("#div_add_news_summary").append('<label for="name" class="error">摘要不能为空且长度不能超过' + news_summary_max_len + '字</label>');
      		$("#modal_add_manufacturer_news").scrollTo("#div_add_news_title", 500);
      		return ;
      	}
      	if (content == null || $.trim(content) == '') {
      		$("#ueditor_add_news_content").next('label').eq(0).remove();
      		$("#ueditor_add_news_content").after('<label for="name" class="error">请输入新闻内容</label>');
      		return ;
      	}
      	
      	var fd = new FormData();
      	fd.append("title", title);
      	fd.append("summary", summary);
      	fd.append("content", content);
      	fd.append("file", file);
      	fd.append("is_show_pic", isShowPic);
      	
      	$.ajax({
				url: "/mfr_admin/news",
				type: "POST",
				dataType:"json",
				processData: false,
				contentType: false,
				data: fd,
				success: function(d) {
					window.location.href="/mfr_admin/news";
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					var result = jQuery.parseJSON(XMLHttpRequest.responseText);
					if (result) {
						if (result.code == '1') {
							$("#div_add_product_cases_title").addClass("has-error");
						} else if (result.code == '2') {
							console.log(result.msg);
						}
					}
				}
			});
      });
	        
     	//修改新闻
      $("#btn_submit_update_news").click(function(e) {
       	var newsId = $("#txt_update_news_id").val();
       	var title = $("#txt_update_news_title").val();
       	var summary = $("#txt_update_news_summary").val();
       	var content = UE.getEditor('ueditor_update_news_content').getContent();
       	//var file = $("#txt_update_news_file").get(0).files[0];
       	//if (!file || file == 'undefined') {
       	//	file =  new File([], {type:"image/jpg"});
       	//}
       	
       	if (title == null || $.trim(title) == '' || $.trim(title).length > news_title_max_len) {
       		$("#div_update_news_title").next('label').eq(0).remove();
       		$("#div_update_news_title").after('<label for="name" class="error">新闻标题不能为空且长度不能超过' + news_title_max_len + '字</label>');
       		$("#modal_update_manufacturer_news").scrollTo("#txt_update_news_title", 500);
      		return ;
       	}
       	if (summary == null || $.trim(summary) == '' || $.trim(summary).length > news_summary_max_len) {
       		$("#div_update_news_summary > label:last").remove();
       		$("#div_update_news_summary").append('<label for="name" class="error">摘要不能为空且长度不能超过' + news_summary_max_len + '字</label>');
       		$("#modal_update_manufacturer_news").scrollTo("#txt_update_news_title", 500);
      		return ;
       	}
       	if (content == null || $.trim(content) == '') {
       		$("#ueditor_update_news_content").next('label').eq(0).remove();
       		$("#ueditor_update_news_content").after('<label for="name" class="error">请输入新闻内容</label>');
       		return ;
       	}
       	
       	var fd = new FormData();
       	fd.append("title", title);
       	fd.append("summary", summary);
       	fd.append("content", content);
       	//fd.append("file", file);
       	
       	$.ajax({
					url: "/mfr_admin/news/" + newsId,
					type: "POST",
					dataType:"json",
					processData: false,
					contentType: false,
					data: fd,
					success: function(d) {
						window.location.href="/mfr_admin/news";
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						var result = jQuery.parseJSON(XMLHttpRequest.responseText);
						if (result) {
							if (result.code == '1') {
								$("#div_add_product_cases_title").addClass("has-error");
							} else if (result.code == '2') {
								console.log(result.msg);
							}
						}
					}
				});
    	});
     	
	    //修改企业新闻封面图
			$("#btn_submit_edit_manufacturer_news_pic").click(function(e){
				var file = $("#txt_edit_manufacturer_news_pic").get(0).files[0];
				if (!file) {
	     		$("#tip_edit_manufacturer_news_pic").next('label').eq(0).remove();
					$("#tip_edit_manufacturer_news_pic").after('<label for="name" class="error">请选择封面图片</label>');
					return ;
	     	}
				
				var imgPath = uploadImg(file);
				if (!imgPath) return ;
				var newsId = $("#txt_edit_manufacturer_news_id").val();
				var checked = $("#chk_edit_manufacturer_news_pic_is_show").is(':checked');
				var data = {pic_url:imgPath, is_show_pic:checked};
				$.ajax({
					url: "/api/manufacturer/news/" + newsId + "/pic",
					type: "PUT",
					dataType:"json",
					data: data,
					success: function(d) {
						$("#img_manufacturer_news_pic_" + newsId).attr("src", imgPath);
						$("#modal_edit_manufacturer_news_pic").modal("hide");
					}
				});
			});
	        
	    //删除新闻提交按钮
			$("#btn_submit_delete_news").click(function(e){
				var newsId = $("#txt_delete_news_id").val();
				$.ajax({
					url: "<%=path%>/qr/mfr_admin/news/" + newsId+"/d",
					type: "POST",
					dataType:"json",
					success: function(d) {
						window.location.href="<%=path%>/qr/mfr_admin/news";
					}
				});
			});
		});
	</script>

	<!-- Modal -->
	<!-- 企业新闻新增模态框 -->
	<div class="modal fade" id="modal_add_manufacturer_news" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>新增企业新闻</h4>
				</div>
				<div class="modal-body">
					<form id="form_add_news" method="post" enctype="multipart/form-data">
						<div class="form-group">
							<label class="col-sm-2 control-label">封面图片 <span class="asterisk">*</span></label>
							<div class="col-sm-10">
								<input id="txt_add_news_file" class="file" name="file" type="file" data-min-file-count="1" data-max-file-count="3">
								<script>
									$("#txt_add_news_file").fileinput({
										allowedFileTypes: ["image"],
										maxFileSize : 2000,
										showUpload: false,
										allowedFileExtensions : [ "jpg", "jpeg", "gif", "png" ]
									});
								</script>
								<div id="tip_add_manufacturer_news_file">
	                <p>为节省用户流量，建议上传小于5M的图片。</p>
	              </div>
	              <div class="checkbox block"><label><input id="chk_add_news_pic_is_show" type="checkbox" checked> 封面图片显示在正文中</label></div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">新闻标题 <span class="asterisk">*</span></label>
							<div class="col-sm-10">
								<div id="div_add_news_title" class="input-group">
                  <input id="txt_add_news_title" type="text" class="form-control" data-max-len="20" required>
                </div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">新闻摘要 <span class="asterisk">*</span></label>
							<div id="div_add_news_summary" class="col-sm-10">
								<textarea id="txt_add_news_summary" class="form-control" rows="2" data-max-len="40" required></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">内容 <span class="asterisk">*</span></label>
							<div class="col-sm-10">
								<!-- 加载编辑器的容器 -->
								<script id="ueditor_add_news_content" type="text/plain"></script>
							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
					<button id="btn_submit_add_news" type="button" class="btn btn-primary">提 交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->
	
	<!-- 企业新闻编辑模态框 -->
	<div class="modal fade" id="modal_update_manufacturer_news" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>编辑企业新闻</h4>
				</div>
				<div class="modal-body">
					<form id="form_update_news" method="post" enctype="multipart/form-data">
						<input type="hidden" id="txt_update_news_id" value=""/>
						<div style="display:none" class="form-group">
							<label class="col-sm-2 control-label">封面图片 </label>
							<div class="col-sm-10">
								<input id="txt_update_news_file" class="file" name="file" type="file" data-min-file-count="1" data-max-file-count="3">
								<script>
									$("#txt_update_news_file").fileinput({
										allowedFileTypes: ["image"],
										maxFileSize : 2000,
										showUpload: false,
										allowedFileExtensions : [ "jpg", "jpeg", "gif", "png" ]
									});
								</script>
								<div>
	                <p>为节省用户流量，建议上传小于5M的图片。</p>
	              </div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">新闻标题 <span class="asterisk">*</span></label>
							<div class="col-sm-10">
								<div id="div_update_news_title" class="input-group">
                  <input id="txt_update_news_title" type="text" class="form-control" data-max-len="20" required/>
                </div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">新闻摘要 <span class="asterisk">*</span></label>
							<div id="div_update_news_summary" class="col-sm-10">
								<textarea id="txt_update_news_summary" class="form-control" rows="2" data-max-len="40" required></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">内容 <span class="asterisk">*</span></label>
							<div class="col-sm-10">
								<!-- 加载编辑器的容器 -->
								<script id="ueditor_update_news_content" type="text/plain"></script>
							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
					<button id="btn_submit_update_news" type="button" class="btn btn-primary">提 交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<!-- 删除新闻提示模态框 -->
	<div class="modal fade" id="modal_delete_manufacturer_news" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>删除提示</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="txt_delete_news_id"/>
					确定要删除该新闻吗？
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
					<button id="btn_submit_delete_news" type="button" class="btn btn-primary">确 定</button>
				</div>
			</div>
		</div>
	</div><!-- modal -->
	
	<!-- 编辑各地经验封面模态框 -->
	<div class="modal fade" id="modal_edit_manufacturer_news_pic" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>修改封面</h4>
				</div>
				<div class="modal-body">
					<form id="form_add_product_label" action="" method="post">
						<input type="hidden" id="txt_edit_manufacturer_news_id"/>
						<div id="div_edit_manufacturer_news_pic" class="form-group">
							<div class="col-sm-10">
								<input id="txt_edit_manufacturer_news_pic" name="file" type="file" data-min-file-count="0" data-max-file-count="3">
		            <div id="tip_edit_manufacturer_news_pic">
	                <p>为节省用户流量，建议上传小于5M的图片。</p>
	              </div>
	              <div class="checkbox block"><label><input id="chk_edit_manufacturer_news_pic_is_show" type="checkbox" checked> 封面图片显示在正文中</label></div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
					<button id="btn_submit_edit_manufacturer_news_pic" type="button" class="btn btn-primary">提 交</button>
				</div>
			</div>
		</div>
	</div><!-- modal -->

<%-- <jsp:include page="/mfr_admin/include_common_widget.jsp" flush="true"/> --%>

<script type="text/javascript" src="http://js.users.51.la/18674045.js" async="async"></script>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?84f2ccbc987ef138709ef25bd96daf46";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
</body>
</html>
