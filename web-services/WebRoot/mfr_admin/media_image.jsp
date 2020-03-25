<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%
	String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
	PageBean<?> pageBean = (PageBean<?>)request.getAttribute("pageBean");
	List<?> images = pageBean == null ? null : pageBean.getList();
	MediaImageGroup group = (MediaImageGroup) request.getAttribute("media_images_group");
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
<link href="<%=path%>/res/bracket/css/jquery.tagsinput.css" rel="stylesheet" />

<style type="text/css">
	.title-display {
		display: block;
		white-space:nowrap;
		overflow:hidden;
		text-overflow:ellipsis;
	}
	.progress {
		margin-top:15px;
	}
	.progress-cancel {
		float:right;
	}
	.filemanager-options {
		margin-bottom:0 !important;
	} 
</style>

<script src="<%=path%>/res/bracket/js/jquery-1.11.1.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="/res/bracket/js/html5shiv.js"></script>
  <script src="/res/bracket/js/respond.min.js"></script>
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
		<div class="mainpanel">

			<div class="headerbar">

				<a class="menutoggle"><i class="fa fa-bars"></i></a>
				<!-- 
      <form class="searchform" action="index.html" method="post">
        <input type="text" class="form-control" name="keyword" placeholder="Search here..." />
      </form> -->

				<!-- header-right -->

			</div>
			<!-- headerbar -->

			<div class="pageheader">
				<h2>
					<i class="fa fa-edit"></i> 素材管理 <span>图片列表</span>
				</h2>
				<div class="breadcrumb-wrapper">
					<button class="btn btn-sm btn-info" id="btn_image_file_upload">本地上传</button>
				</div>
			</div>

			<div class="contentpanel">
      
	      <div class="row">
	      	<form id="myPageForm" method="get" action="<%=path%>/qr/mfr_admin/media/images">
	      		<input type="hidden" id="myPageForm_CurrentPageNo" name="currentPage" value="" />
						<input type="hidden" name="group_id" value='${group_id}' />
		        <div class="col-sm-4 col-md-3">
							<ul id="image_group_list" class="profile-social-list">
							</ul>
							
							<div class="mb10"></div>
							<a href="javascript:displayAddGroupModal()"><span class="fa fa-plus"></span> 新建分组</a>
		          
		        </div><!-- col-sm-3 -->
	        </form>
	        
	        <div class="col-sm-8 col-md-9">
            <div class="row filemanager">
                <div class="panel-heading">
                   <%
		               	if (group != null) {
		               %>
		               		<span class="panel-title"><%=group.getName() %></span>
		               <%
		               		if (group.getId() != 0) {
		               %>
		               		&nbsp;&nbsp;&nbsp;&nbsp;
		               		<a href="javascript:displayUpdateGroupModal(<%=group.getId() %>);">重命名</a>
		               		&nbsp;&nbsp;&nbsp;&nbsp;
		               		<a href="javascript:displayDeleteGroupModal(<%=group.getId() %>)">删除分组</a>
		               <%
		               		}
		               	}
		              
                   %>
                   
                   <p class="pull-right">建议宽度960-1080像素之间</p>
                   
						       <div id="progress_bar" style="padding-top: 20px;"></div>
                </div><!-- panel-heading -->
                
                <ul class="filemanager-options">
					        <li><div class="checkbox block"><label><input id="chb_select_all" type="checkbox"> 全选</label></div></li>
					        <li><button id="btn_batch_move" class="btn btn-default btn-xs" onclick="displayBatchMoveImageModal();" disabled>移动</button></li>
						      <li><button id="btn_batch_delete" class="btn btn-default btn-xs" onclick="dispalyBatchDelImageModal();" disabled>删除</button></li>
					        <li class="filter-type">
					          
					        </li>
					      </ul>
					      
                <div class="contentpanel banner-list">
                	<div id="images_container" class="row">
                	<%
                		if (images != null) {
                			for (Iterator<?> iterator = images.iterator(); iterator.hasNext();) {
                				MediaImage image = (MediaImage) iterator.next();
                	%>
                			<div class="col-xs-6 col-sm-4 col-md-3 image">
					              <div class="thmb">
					                <div class="thmb-prev">
					                  <a href="#" data-rel="prettyPhoto">
					                    <img src="<%=image.getUrl() %>" class="img-responsive" alt="">
					                  </a>
					                </div>
					                <div class="checkbox block">
					                	<label><input type="checkbox" name="chb_image" data-id="<%=image.getId() %>" onclick="setSelectAll();">
					                	<span id="image_title_<%=image.getId() %>" class="fm-title title-display"><%=image.getTitle() %></span></label>
					                </div>
					                <ul class="blog-meta">
						                <li><a href="javascript:displayUpdateImageModal(<%=image.getId() %>)">编辑</a></li>
						                <li><a href="javascript:displayMoveImageModal(<%=image.getId() %>)">移动</a></li>
						                <li><a href="javascript:displayDeleteImageModal(<%=image.getId() %>)">删除</a></li>
						              </ul>
					              </div><!-- thmb -->
					            </div>
                	<%	
                			}
                		}
                	%>
                	</div>
                	
                  <jsp:include page="/mfr_admin/include_pageinfo.jsp" flush="true" />
                    
                </div><!-- panel-body -->
            </div><!-- panel -->
        	</div><!-- col-sm-8 -->
	        
	      </div><!-- row -->
	      
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

	<script src="<%=path%>/res/bracket/js/custom.js"></script>
	
	<script type="text/javascript" src="<%=path%>/res/datetime.js"></script>
	<script type="text/javascript" src="<%=path%>/res/custom/ajax-paging-query.js"></script>
	<script src="<%=path%>/res/custom/wz-media.js"></script>
	
	<!-- 七牛相关js -->
	<script src="<%=path%>/res/plupload/plupload.full.min.js"></script>
	<script src="<%=path%>/res/plupload/i18n/zh_CN.js"></script>
	<script src="<%=path%>/res/qiniu/qiniu.js"></script>
	<script src="<%=path%>/res/custom/file-progress-ui.js"></script>
	<script src="<%=path%>/res/custom/file-uploader.js?ver=201804251150"></script>
	
	<script src="<%=path%>/res/custom/notifications_alert.js"></script>

	<script>
	
		function successCallback(data) {
			try {
				var title = data.fname;
				if (title === null || title === '') {
					title = data.key;
				}
				wzMedia.addImage(title, data.url, data.fsize, '<%=group.getId() %>');
			} catch (e) {
				notification(e.message);
			}
		}
		
		function failCallback(err, errTip) {
			if (err.code == -600) {
				notification("选择的图片大于5M");
			}
		}
	
		jQuery(document).ready(function() {
			"use strict";
			
			//初始化图片上传
			imgUploader("btn_image_file_upload", "progress_bar", 2, successCallback, failCallback, true, 
				function(){
					window.location.href = "<%=path%>/qr/mfr_admin/media/images?group_id=<%=group.getId() %>";
				}
			);
			
			//获取分组
			var groups = wzMedia.getImageGroups();
			if (groups != null) {
				var groupItems = [];
				for (var i=0; i < groups.length; i++) {
					groupItems.push('<li><a href="<%=path%>/qr/mfr_admin/media/images?group_id='  + groups[i].id + '">&nbsp;' + groups[i].name + '(' + groups[i].count + ')</a></li>');
				}
				$("#image_group_list").html(groupItems.join(""));
			}
			
			//全选按钮添加事件
			$("#chb_select_all").on("click", function(e) {
				if ($(this).attr("checked")) {
					$("input[name='chb_image']").attr("checked", true);
					$("#btn_batch_move").attr("disabled", false);
					$("#btn_batch_delete").attr("disabled", false);
				} else {
					$("input[name='chb_image']").attr("checked", false);
					$("#btn_batch_move").attr("disabled", true);
					$("#btn_batch_delete").attr("disabled", true);
				}
			});
			
			$("#btn_submit_add_image_group").on("click", function(e) {
				var groupName = $("#txt_add_image_group_name").val();
				try {
					var group = wzMedia.addImageGroup(groupName);
					window.location.href = "<%=path%>/qr/mfr_admin/media/images?group_id=<%=group.getId() %>";
				} catch (e) {
					console.log(e);
				}
				
			});
			
			$("#btn_submit_update_image_group").on("click", function() {
				var groupName = $("#txt_update_image_group_name").val();
				var groupId = $("#txt_update_image_group_id").val();
				
				try {
					var group = wzMedia.updateImageGroup(groupId, groupName);
					window.location.href = "<%=path%>/qr/mfr_admin/media/images?group_id=<%=group.getId() %>";
				} catch (e) {
					console.log(e);
				}
				
			});
			
			$("#btn_submit_delete_image_group").on("click", function() {
				var groupId = $("#txt_delete_image_group_id").val();
				
				try {
					var group = wzMedia.deleteImageGroup(groupId);
					window.location.href = "<%=path%>/qr/mfr_admin/media/images";
				} catch (e) {
					console.log(e);
				}
				
			});
			
			$("#btn_submit_update_image").on("click", function() {
				var imageId = $("#txt_update_image_id").val();
				var title = $("#txt_update_image_title").val();
				
				try {
					var image = wzMedia.updateImage(imageId, title);
					$("#image_title_" + imageId).html(title);
					$("#modal_update_image").modal("hide");
				} catch (e) {
					console.log(e);
				}
				
			});
			
			$("#btn_submit_move_images").on("click", function() {
				var ids = $("#txt_move_images_ids").val();
				var groupId = $("input[name='move_image_radio']:checked").attr("data-id");
				
				try {
					wzMedia.moveImages(ids, groupId);
					window.location.href = "<%=path%>/qr/mfr_admin/media/images?group_id=<%=group.getId() %>";
				} catch (e) {
					console.log(e);
				}
				
			});
			
			$("#btn_submit_delete_images").on("click", function() {
				var ids = $("#txt_delete_images_ids").val();
				
				try {
					wzMedia.deleteImages(ids);
					window.location.href = "<%=path%>/qr/mfr_admin/media/images?group_id=<%=group.getId() %>";
				} catch (e) {
					console.log(e);
				}
				
			});
			
		});
		
		function setSelectAll() {
			//当没有选中某个子复选框时，SelectAll取消选中  
			var selCount = $("input[name='chb_image']:checked").length;
	    if (selCount == 0) {
	    	$("#chb_select_all").attr("checked", false);
	    	$("#btn_batch_move").attr("disabled", true);
				$("#btn_batch_delete").attr("disabled", true);
	    } else {
	    	$("#btn_batch_move").attr("disabled", false);
				$("#btn_batch_delete").attr("disabled", false);
	    }
		}
		
		function displayAddGroupModal() {
			$("#txt_add_image_group_name").attr("value", "");
			$("#modal_add_image_group").modal("show");
		}
		
		function displayUpdateGroupModal(id) {
			var group = wzMedia.getImageGroup(id);
			if (group != null) {
				$("#txt_update_image_group_id").attr("value", id);
				$("#txt_update_image_group_name").attr("value", group.name);
				$("#modal_update_image_group").modal("show");
			}
		}
		
		function displayDeleteGroupModal(id) {
			$("#txt_delete_image_group_id").attr("value", id);
			$("#modal_delete_image_group").modal("show");
		}
		
		function displayUpdateImageModal(id) {
			var image = wzMedia.getImage(id);
			if (image != null) {
				$("#txt_update_image_id").attr("value", id);
				$("#txt_update_image_title").attr("value", image.title);
				$("#modal_update_image").modal("show");
			}
		}
		
		function batchMoveImageModal(ids) {
			//获取分组
			var groups = wzMedia.getImageGroups();
			if (groups != null) {
				var groupItems = [];
				for (var i=0; i<groups.length; i++) {
					if (groups[i].id != '<%=group.getId() %>') {
						groupItems.push('<label><input id="move_image_group_' + groups[i].id + '" name="move_image_radio" type="radio" data-id="' + groups[i].id + '"> ' + groups[i].name + '</label>');
					}
				}
				if (groupItems.length > 0) {
					$("#move_image_group_container").html(groupItems.join(""));
					$("#txt_move_images_ids").attr("value", ids);
					$("#modal_move_images").modal("show");
				} else {
					//TODO 提示没有可选的分组
				}
			}
		}
		
		function displayMoveImageModal(id) {
			batchMoveImageModal(id);
		}
		
		function displayBatchMoveImageModal() {
			var ids = [];
			$("input[name='chb_image']:checked").each(function(e) {
				ids.push($(this).attr("data-id"));
			});
		
			batchMoveImageModal(ids.join(";"));
		}
		
		function displayDeleteImageModal(id) {
			$("#txt_delete_images_ids").attr("value", id);
			$("#modal_delete_images").modal("show");
		}
		
		function dispalyBatchDelImageModal() {
			var ids = [];
			$("input[name='chb_image']:checked").each(function(e) {
				ids.push($(this).attr("data-id"));
			});
			$("#txt_delete_images_ids").attr("value", ids.join(";"));
			$("#modal_delete_images").modal("show");
		}
		
	</script>


	<!--新增图片分组模态框 -->
	<div class="modal fade" id="modal_add_image_group" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<h4>新建分组</h4>
					<input type="text" id="txt_add_image_group_name" class="form-control" >
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
					<button id="btn_submit_add_image_group" type="button" class="btn btn-primary">确 定</button>
				</div>
			</div>
		</div>
	</div><!-- modal -->
	
	<!--编辑图片分组模态框 -->
	<div class="modal fade" id="modal_update_image_group" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<h4>编辑名称</h4>
					<input id="txt_update_image_group_id" type="hidden">
					<input type="text" id="txt_update_image_group_name" class="form-control" >
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
					<button id="btn_submit_update_image_group" type="button" class="btn btn-primary">确 定</button>
				</div>
			</div>
		</div>
	</div><!-- modal -->
	
	<!--删除图片分组模态框 -->
	<div class="modal fade" id="modal_delete_image_group" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<br>
					<input id="txt_delete_image_group_id" type="hidden">
					<p>仅删除分组，不删除图片，组内图片将自动归入未分组</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
					<button id="btn_submit_delete_image_group" type="button" class="btn btn-primary">确 定</button>
				</div>
			</div>
		</div>
	</div><!-- modal -->
	
	<!--修改图片模态框 -->
	<div class="modal fade" id="modal_update_image" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<h4>编辑名称</h4>
					<input id="txt_update_image_id" type="hidden">
					<input type="text" id="txt_update_image_title" class="form-control" >
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
					<button id="btn_submit_update_image" type="button" class="btn btn-primary">确 定</button>
				</div>
			</div>
		</div>
	</div><!-- modal -->
	
	<!--移动图片模态框 -->
	<div class="modal fade" id="modal_move_images" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<input id="txt_move_images_ids" type="hidden">
					<div id="move_image_group_container" class="radio"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
					<button id="btn_submit_move_images" type="button" class="btn btn-primary">确 定</button>
				</div>
			</div>
		</div>
	</div><!-- modal -->
	
	<!--删除图片模态框 -->
	<div class="modal fade" id="modal_delete_images" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<br>
					<input id="txt_delete_images_ids" type="hidden">
					<p>确定要删除此图片吗？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
					<button id="btn_submit_delete_images" type="button" class="btn btn-primary">确 定</button>
				</div>
			</div>
		</div>
	</div><!-- modal -->

<jsp:include page="/mfr_admin/include_common_widget.jsp" flush="true"/>

</body>
</html>
