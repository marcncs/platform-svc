<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%
String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
String tabName = request.getParameter("tab_name");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>云单页厂家管理平台</title>
<link href="<%=path%>/res/bracket/css/style.default.css" rel="stylesheet">
<link href="<%=path%>/res/bootstrap-fileinput/css/fileinput.min.css"
	rel="stylesheet" />

<link href="<%=path%>/res/datetime/bootstrap-datetimepicker.min.css"
	rel="stylesheet" />

<link href="<%=path%>/res/bracket/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/img-cropper/dist/cropper.css" rel="stylesheet">
<link href="<%=path%>/res/img-cropper/css/main.css" rel="stylesheet">
<link href="<%=path%>/res/bracket/css/jquery.tagsinput.css" rel="stylesheet" />
<script src="<%=path%>/res/bracket/js/jquery-1.11.1.min.js"></script>

<style type="text/css">
img {
	max-width: 100%;
}

/*图片自适应正方形*/
.pic-thumbnail {
	width: 130px;
	display: inline-block;
	float: left;
	padding-right: 10px;
}

.figure-thumbnail {
	position: relative;
	width: 100%;
	height: 0;
	overflow: hidden;
	margin: 0;
	padding-bottom: 100%; /* 关键就在这里 */
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover;
}
</style>

</head>

<body>
	<!-- Preloader -->
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>

	<section>
		<div class="mainpanel">

			<!-- <div class="headerbar">

				<a class="menutoggle"><i class="fa fa-bars"></i></a>

			</div> -->
			<!-- headerbar -->

			<div class="contentpanel">
				<input type="hidden" id="page_current_product_id"
					value="${product.id}" />
				<div class="row">
					<div id="product_info_container" class="col-sm-3">
						<a href="javascript:displayChangeImage('${product.id}');">
						<img src="${product.picUrl}" class="thumbnail img-responsive" alt="" title="点击修改" /></a>
						<h3 class="mb5">${product.name}</h3>

						<div class="mb30"></div>

						<h4>${product.slogan}</h4>
						<ul id="ul_product_basic_info" class="profile-social-list">
							<li><span>有效成分：${product.component}</span></li>
							<li><span>登记证号：${product.certification}</span></li>
							<li><span>规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：${product.sku}</span></li>
							<li><span>创建时间：${product.createTime}</span></li>
							<li><span>修改时间：${product.lastModifyTime}</span></li>
						</ul>
						<div class="mb10"></div>
						<button class="btn btn-sm btn-warning"
							onClick="javascript:displayEditBasicInfo('${product.id}');">编辑基础信息</button>

						<div class="mb30"></div>
						<%-- <p>
							<a href="javascript:displayQRCodeDownload(${product.id});">下载更多尺寸
							</a>
						</p>
						<p>
							<a id="a_download_qrcode" href="javascript:displayQRCodeDownload(${product.id});">
								<img src="/api/product/${product.id}/qrcode?size=300&is_img=true" title="点击下载" />
							</a>
						</p>
						<p>建议您将此二维码印刷在产品包装上，每销售出一个产品包装就多一个传播机会，让产品为自己说话。</p> --%>

					</div>
					<!-- col-sm-3 -->

					<div class="col-sm-9">
						<!-- Nav tabs -->
						<ul id="tab_container"
							class="nav nav-tabs nav-justified nav-profile">
							<li id="tab_content" class="active"><a href="#content"
								data-toggle="tab"><strong>图文讲解</strong></a></li>
							<%-- <li id="tab_videos"><a href="#videos" data-toggle="tab"><strong>视频讲解</strong></a></li>
							<li id="tab_cases"><a href="#cases" data-toggle="tab"><strong>各地经验</strong></a></li>
							<li id="tab_label_detail"><a href="#label_detail"
								data-toggle="tab"><strong>标签详情</strong></a></li>
							<li id="tab_preview"><a href="#preview"
								onclick="window.open(document.all.preview_mobile.src,'preview_mobile','')"
								data-toggle="tab"><strong>手机预览</strong></a></li> --%>
						</ul>

						<!-- Tab panes -->
						<div class="tab-content">

							<div class="tab-pane" id="content">
								<div class="activity-list">
									<div class="media act-media">
										<!-- <button class="btn btn-sm btn-warning" onclick="javascript:displayEditContent(${product.id});">编 辑</button> -->
										<a href="<%=path%>/mfr_admin/product_content_edit.jsp?product_id=${product.id}"
											target="_blank" class="btn btn-sm btn-warning">编 辑</a>
									</div>
									<div class="media act-media">
										<%-- <div class="col-sm-12">
											<c:if test="${product.posterUrl != null}">
												<img src="${product.posterUrl}" />
											</c:if>
										</div> --%>
										<div id="product_content_container" class="col-sm-12">
											<div class="mb20"></div>
											${product.content}
										</div>
									</div>
								</div>
								<!-- activity-list -->
							</div>
							<!-- 图文内容 -->

							
							<!-- 视频 -->

							
							<!-- 案例 -->

							<!-- 产品标签 -->
							
							<!-- 产品标签 -->

							<!-- 手机预览标签 -->
							
							<!-- 手机标签 -->

						</div>
						<!-- tab-content -->

					</div>
					<!-- col-sm-9 -->
				</div>
				<!-- row -->
			</div>
			<!-- contentpanel -->
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
	<script src="<%=path%>/res/bracket/js/jquery.prettyPhoto.js"></script>

	<script src="<%=path%>/res/bracket/js/jquery-ui-1.10.3.min.js"></script>
	<script src="<%=path%>/res/bracket/js/select2.min.js"></script>
	<script src="<%=path%>/res/bracket/js/jquery.validate.min.js"></script>

	<script src="<%=path%>/res/bootstrap-fileinput/js/fileinput.min.js"></script>
	<script src="<%=path%>/res/bootstrap-fileinput/js/fileinput_locale_zh.js"></script>

	<script src="<%=path%>/res/bracket/js/custom.js"></script>

	<script src='<%=path%>/res/datetime/bootstrap-datetimepicker.min.js'></script>
	<script src='<%=path%>/res/datetime/locales/bootstrap-datetimepicker.zh-CN.js'></script>

	<!-- 配置文件 -->
	<script src="<%=path%>/res/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script src="<%=path%>/res/ueditor/ueditor.all.js"></script>
	<!-- ueditor添加自定义的标题目录按钮-->
	<script src="<%=path%>/res/custom/ueditor-customize-img-button.js"></script>

	<!-- 图片裁切所需文件 -->
	<script src="<%=path%>/res/img-cropper/dist/cropper.js"></script>
	<script src="<%=path%>/res/img-cropper/js/main.js"></script>
	<script src="<%=path%>/res/jquery.tagsinput.js"></script>
	<script src="<%=path%>/res/jquery.sliphover.min.js"></script>

	<!-- 七牛相关js -->
	<script src="<%=path%>/res/plupload/plupload.full.min.js"></script>
	<script src="<%=path%>/res/plupload/i18n/zh_CN.js"></script>
	<script src="<%=path%>/res/qiniu/qiniu.js"></script>
	<script src="<%=path%>/res/custom/file-progress-ui.js"></script>
	<script src="<%=path%>/res/custom/file-uploader.js?ver=201804251150"></script>
	<script src="<%=path%>/res/custom/ajax-paging-query.js"></script>
	<script src="<%=path%>/res/custom/wz-media.js"></script>
	<script src="<%=path%>/res/custom/api-product.js"></script>
	<script src="<%=path%>/res/custom/api-functions.js"></script>

	<script src="<%=path%>/res/datetime.js"></script>
	<script src="<%=path%>/res/input-len-counter.js"></script>
	<script src="<%=path%>/res/jquery.scrollTo.js"></script>

	<script>
	var productCoverPicUploader;
	
	jQuery(document).ready(function() {
    "use strict";
    // Basic Slider
    jQuery('#slider').slider({
      range: "min",
      max: 100,
      value: 50
    });
    jQuery(".select2").select2({
      width: '100%'
    });
    // Date Picker
    jQuery('#datepicker').datepicker();
    
	  // Tags Input
	  jQuery('#input_update_product_basic_sku').tagsInput({width:'auto', 
			defaultText:'新增规格' //默认文字
		});
	  
		//点击图片浏览图片
		//jQuery("a[data-rel^='prettyPhoto']").prettyPhoto();
		
		control_tab_display('<%=tabName%>');
		
		//鼠标从图片滑过时，添加覆盖物
		//$('#cases').sliphover();
		$('#product_info_container').sliphover();
		
		//初始化图片上传
		imgUploader("btn_product_label_native_upload", "progress_bar", 5, 
			function(data) {
				//加入到素材库中
				try {
					var title = data.fname;
					if (title === null || title === '') {
						title = data.key;
					}
					wzMedia.addImage(title, data.url, data.fsize, 0);
				} catch (e) {
					console.log(e);
				}
				
				$("#progress_bar").next('label').eq(0).remove();
				$("#img_product_label").attr("src", data.url);
				
				updateProductLabel(data.url);
			}, 
			function(err, errTip) {
				if (err.code == -600) {
					$("#progress_bar").next('label').eq(0).remove();
					$("#progress_bar").after('<label for="name" class="error">产品标签大小不能超过5M</label>');
				}
			}
		);
		
		productCoverPicUploader = imgUploader("btn_product_img_upload", "product_cover_pic_progress_bar", 5, 
				function(data) {
					console.log(data);
					try {
						//加入到素材库中
						var title = data.fname;
						if (title === null || title === '') {
							title = data.key;
						}
						wzMedia.addImage(title, data.url, data.fsize, 0);
						
						updateProductCoverPic('${product.id}', data.url);
						window.location.href="<%=path%>/qr/mfr_admin/product/${product.id}";
					} catch (e) {
						console.log(e);
					}
				}, 
				function(err, errTip) {
					if (err.code == -600) {
						$("#product_cover_pic_progress_bar").next('label').eq(0).remove();
						$("#product_cover_pic_progress_bar").after('<label for="name" class="error">产品封面图大小不能超过5M</label>');
					}
				}
			);
		
		<%-- /处理图片
		$("#product_content_container").find('img').each(function(i, n) {
			var imgUrl = $(n).attr("src");
			$(n).attr("src", imgUrl + "<%=GlobalConstant.IMG_PREVIEW_SIZE_W_1440 %>");
			$(n).attr('alt', '');
		}); --%>
		
  });
	
	function chooseImgFromMedias() {
		showMediaImageChooser(1, function(data) {
	 		if (data) {
	 			$("#progress_bar").next('label').eq(0).remove();
				$("#img_product_label").attr("src", data[0].url);
				
				updateProductLabel(data[0].url);
	 		}
		});
	}
	
	function updateProductLabel(labelUrl) {
		try {
			setProductLabel('${product.id}', labelUrl);
		} catch (e) {
			console.log(e);
		}
	}
	
	//标题和新闻概要最大字符数
	var title_max_len = 20;
	var summary_max_len = 40;
	
	//控制tab页的显示
	function control_tab_display(tabName) {
		if (!tabName || tabName === 'null' || tabName === 'undefined') {
			tabName = "content";
		}
		
		//删除所有li的active class
		$("#tab_container > li").removeClass("active");
		//添加为选中的tab添加class
		$("#tab_" + tabName).addClass("active");
		$("#" + tabName).addClass("active");
	}
    
	//显示基础信息编辑模态框
  function displayEditBasicInfo(productId) {
	  var product;
		//获取产品信息
		$.ajax({url: "<%=path%>/qr/api/product/" + productId, type: "GET", dataType: "json", async: false, 
			success: function(result) {
				product = result.data;
				if (product) {
					$("#form_update_product_basic").attr("action", "<%=path%>/qr/mfr_admin/product/update/" + product.id);
					$("#input_update_product_basic_name").val(product.name);
					//$("#input_update_product_basic_nameEng").val(product.name_eng);
					$("#input_update_product_basic_slogan").val(product.slogan);
					$("#input_update_product_basic_component").val(product.component);
					$("#input_update_product_basic_certification").val(product.certification);
					/* if (product.sku) {
						$("#input_update_product_basic_sku").importTags(product.sku);
					} */
				}
			}
		});
		$("#modal_update_productBasicInfo").modal('show'); 
	}
	
	//显示二维码下载页面
	function displayQRCodeDownload() {
		$("#alert_error_msg_modal_qrcode_download").hide();
		$("#modal_qrcode_download").modal('show');
	}
	
 	function displayEditLabel(productId) {
 		$("#txt_add_label_product_id").attr("value", productId);
 		$("#modal_add_product_label").modal('show');
 	}
	
	//显示新增视频模态框
 	function displayAddVideo(productId) {
 		$('#input_add_product_video_shoot_time').datetimepicker({
           format: 'yyyy-mm-dd',
           language: 'zh-CN',
           weekStart: 0,
           todayBtn:  1,
           autoclose: 1,
           todayHighlight: 1,
           startView: 2,
           minView: 2
       });
 		$("#input_add_video_product_id").attr("value", productId);
 		$("#modal_add_product_video").modal('show');
 	}
  
	//显示图文内容编辑模态框
	function displayEditContent(productId) {
	  var product;
    var ue = UE.getEditor('product_update_ueditor');
    ue.ready(function() {
    	//获取产品信息
			$.ajax({url: "/api/product/" + productId, type: "GET", dataType: "json", async: false, 
				success: function(result) {
					product = result.data;
					if (product) {
						//$("#form_update_product_content").attr("action", "/mfr_admin/product/update/content/" + product.id);
						$("#input_update_product_content_productid").attr("value", product.id);
// 						$("#input_update_product_content_poster_url").attr("value", product.poster_url);
						ue.setContent(product.content);
					}
				}
			});
    });
    $("#tip_update_product_content_poster").next('label').eq(0).remove();
    $("#product_update_ueditor").next('label').eq(0).remove();
		$("#modal_update_product_content").modal('show');
	}
	
	
	
	
	
	
	//显示产品图片更换模态框
  function displayChangeImage(productId) {
	  	<%-- var product;
		//获取产品信息
		$.ajax({url: "<%=path%>/qr/api/product/" + productId, type: "GET", dataType: "json", async: false, 
			success: function(result) {
				product = result.data;
				console.log(result);
				if (product) {
					$('#img2crop').attr("src", product.url);
					$("#form_update_product_basic").attr("action", "/mfr_admin/product/update/" + product.id);
					$("#input_update_product_basic_name").val(product.name);
					//$("#input_update_product_basic_nameEng").val(product.nameEng);
					$("#input_update_product_basic_slogan").val(product.slogan);
					$("#input_update_product_basic_component").val(product.component);
					$("#input_update_product_basic_certification").val(product.certification);
				}
			}
		});		 --%>
		$("#modal_product_img_crop").modal('show');
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
	    
		//产品基础信息编辑提交按钮
		$("#btn_submit_update_product_basic").click(function(e){
			var hasIllegalValue = false; //是否有非法值
			var name = $("#input_update_product_basic_name").val();
			var productGroup = $("#select_update_product_basic_group2").val();
			var slogan = $("#input_update_product_basic_slogan").val();
			var component = $("#input_update_product_basic_component").val();
			var certification = $("#input_update_product_basic_certification").val();
			var relatedProducts = $("#sel_update_product_basic_related_products").val();
			
			$("#input_update_product_basic_name").next('label').eq(0).remove();
			$("#select_update_product_basic_group2").next('label').eq(0).remove();
			$("#input_update_product_basic_slogan").next('label').eq(0).remove();
			$("#input_update_product_basic_component").next('label').eq(0).remove();
			$("#input_update_product_basic_certification").next('label').eq(0).remove();
			$("#sel_update_product_basic_related_products").next('label').eq(0).remove();
			if (name == '') {
				$("#input_update_product_basic_name").after('<label for="name" class="error">请输入产品名</label>');
				hasIllegalValue = true;
			}
			if (productGroup == '') {
				$("#select_update_product_basic_group2").after('<label for="name" class="error">请输选择类别</label>');
				hasIllegalValue = true;
			}
			/* if (slogan == '') {
				$("#input_update_product_basic_slogan").after('<label for="name" class="error">请输入宣传口号</label>');
				hasIllegalValue = true;
			} */
			if (component == '') {
				$("#input_update_product_basic_component").after('<label for="name" class="error">请输入有效成分</label>');
				hasIllegalValue = true;
			}
			if (certification == '' && parseInt(productGroup) != 103) {
				$("#input_update_product_basic_certification").after('<label for="name" class="error">请输入登记证号</label>');
				hasIllegalValue = true;
				return ;
			}
			
			if (relatedProducts && relatedProducts.length > 3) {
				$("#sel_update_product_basic_related_products").after('<label for="name" class="error">相关推荐不能大于3个产品</label>');
				hasIllegalValue = true;
			}
			
			if (hasIllegalValue) {
				return ;
			}
			
			$("#txt_update_product_basic_related_products").attr("value", relatedProducts);
			$("#form_update_product_basic").submit();
		});
		
		//产品图文信息编辑提交按钮
		$("#btn_submit_update_product_content").click(function(e){
			var file = $("#input_update_product_content_file").get(0).files[0];
			var imgUrl = $("#input_update_product_content_poster_url").val();
			if (file) {
				//如果图片存在，则上传到服务器
				imgUrl = uploadImg(file);
			}
			//图片路径为空则直接返回
			if (!imgUrl) {
				$("#tip_update_product_content_poster").next('label').eq(0).remove();
				$("#tip_update_product_content_poster").after('<label for="name" class="error">请选择产品海报</label>');
				return;
			}
			var content = UE.getEditor('product_update_ueditor').getContent();
			if (!content) {
				$("#product_update_ueditor").next('label').eq(0).remove();
				$("#product_update_ueditor").after('<label for="name" class="error">请输入图文内容</label>');
				
				return ;
			}
			//提交form表单
			var productId = $("#input_update_product_content_productid").val();
			var data = {poster_url:imgUrl, content:content};
			$.ajax({
				url: "/api/product/" + productId + "/content",
				type: "PUT",
				dataType:"json",
				data: data,
				success: function(d) {
					window.location.href="/mfr_admin/product/" + d.data.id;
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					var result = jQuery.parseJSON(XMLHttpRequest.responseText);
					if (result) {
						console.log(result);
					}
				}
			});
		});
		
				
	  //裁切图片上传按钮
		$("#btn_upload_crop_image").click(function(e){
			var $image = $('#img2crop');
			result = $image.cropper('getCroppedCanvas', {fillColor: 'white'});
			var canvas_data = result.toDataURL("image/jpeg");
			// dataURL 的格式为 “data:image/png;base64,****”,逗号之前都是一些说明性的文字，我们只需要逗号之后的就行了
			var canvas_data_base64 = canvas_data.split(',')[1];
			canvas_data = window.atob(canvas_data_base64);
			var ia = new Uint8Array(canvas_data.length);
			for (var i = 0; i < canvas_data.length; i++) {
			    ia[i] = canvas_data.charCodeAt(i);
			};
			var blob = new Blob([ia], {type:"image/jpg"}); // canvas.toDataURL 返回的默认格式就是 image/bmp
			
			productCoverPicUploader.addFile(blob, "ddd.jpg");
			productCoverPicUploader.start();
			
			return ;
			
			var fd = new FormData();
			fd.append("crop_file", blob);
			
			$.ajax({
				url: "/api/images",
				type: "POST",
				dataType:"json",
				processData: false,
				contentType: false,
				data: fd,
				success: function(response) {
// 					console.log(response.data.urlFilePath);
// 					console.log("hahahaha...........");
					//上传成功后更改数据库
					var productId = $("#page_current_product_id").val();
					var requestData = {"pic_url" : response.data.urlFilePath};
					$.ajax({
						url: "<%=path%>/qr/api/product/" + productId + "/pic",
						type: "POST",
						dataType:"json",
						data: requestData,
						success: function(d) {
							window.location.href="/mfr_admin/product/" + productId;
						}
					});
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("server response error");
					var responseText = jQuery.parseJSON(XMLHttpRequest.responseText);
					console.log(responseText.msg);
				}
			});
		});

		//产品标签提交按钮
		$("#btn_submit_add_product_label").click(function(e){
			var file = $("#txt_add_product_label").get(0).files[0];
			var imgPath = uploadImg(file);
			
			var productId = $("#txt_add_label_product_id").val();
			var data = {relative_img_path:imgPath};
			$.ajax({
				url: "/api/product/" + productId + "/label",
				type: "PUT",
				dataType:"json",
				data: data,
				success: function(d) {
					window.location.href="/mfr_admin/product/" + productId + "?tab_name=label_detail";
				}
			});
		});
		
		//进行二维码下载权限判断及处理
		var canDownloadQrcode = false;
		<%-- try {
			//获取厂家是否能够使用change_skin功能
			canDownloadQrcode = canUseFunction(<%=manufacturer.getId()%>, "DOWNLOAD_QRCODE");
		} catch (e) {
			console.log(e);
		} --%>
		canDownloadQrcode = true; //目前把二维码下载权限打开，即永远为true，以后需要时再注释此行即可
		if (canDownloadQrcode) {
			$("#a_download_qrcode").attr("href", "/api/product/${product.id}/qrcode?size=430&is_img=true");
		} else {
			$("#a_download_qrcode").attr("href", "javascript:displayQRCodeDownload('${product.id}');");
			//把二维码下载模态框中的所有下载链接都关闭
			$("#body_qrcode_download_links").find('a').each(function(){
				$(this).attr("href", "javascript:void(0);")
				$(this).attr("onclick", "$('#alert_error_msg_modal_qrcode_download').show();")
			});
		}
	
	}); //$().ready()函数结束
	/* function changeSource() {
		$("#selectImage").attr("src", "http://localhost:8080/BCS_RTCI/qr/img/88c692696dffc5226102e9907028bc83");
		console.log($("#selectImage").attr("src"));
		//imageSeleted($("#selectImage"));
	} */
</script>

	<!-- Modal -->
	<!-- 产品基础信息编辑模态框 -->
	<div class="modal fade" id="modal_update_productBasicInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>编辑产品基础信息</h4>
				</div>
				<div class="modal-body">
					<form id="form_update_product_basic" action="" method="post">
						<div class="form-group">
							<label class="col-sm-2 control-label">产品名称 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<input id="input_update_product_basic_name" type="text"
									name="name" class="form-control" disabled />
							</div>
						</div>
						<!-- 
						<div class="form-group">
							<label class="col-sm-2 control-label">产品类别 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10" id="div_update_product_basic_group2"></div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">英文名称 </label>
							<div class="col-sm-10">
								<input id="input_update_product_basic_nameEng" type="text"
									name="nameEng" class="form-control" />
							</div>
						</div> -->
						<div class="form-group">
							<label class="col-sm-2 control-label">宣传口号 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<input id="input_update_product_basic_slogan" type="text"
									name="slogan" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">有效成分 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<input readonly id="input_update_product_basic_component" type="text"
									name="component" class="form-control" required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">登记证号 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<input readonly id="input_update_product_basic_certification" type="text"
									name="certification" class="form-control" required />
							</div>
						</div>
						<!-- <div class="form-group">
							<label class="col-sm-2 control-label">产品规格</label>
							<div class="col-sm-10">
								<input name="sku" id="input_update_product_basic_sku"
									class="form-control" />
							</div>
						</div> 
						<div class="form-group">
							<label class="col-sm-2 control-label">相关推荐</label>
							<div class="col-sm-10">
								<input type="hidden"
									id="txt_update_product_basic_related_products"
									name="related_products"> <select
									id="sel_update_product_basic_related_products" class="select2"
									multiple data-placeholder="选择相关产品">
								</select>
							</div>
						</div>-->
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
					<button id="btn_submit_update_product_basic" type="button"
						class="btn btn-primary">提 交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<!-- 产品图文讲解编辑模态框 -->
	<div class="modal fade" id="modal_update_product_content" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document" style="width: 800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>编辑产品图文讲解内容</h4>
				</div>
				<div class="modal-body">
					<form id="form_update_product_content" action="" method="post">

						<div id="div_udpate_product_content_file" class="form-group">
							<label class="col-sm-2 control-label">产品海报 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<input id="input_update_product_content_file" class="file"
									name="file" type="file" data-min-file-count="0"
									data-max-file-count="3">
								<script>
		            	$("#input_update_product_content_file").fileinput({
		            		allowedFileTypes: ["image"],
		            		showUpload: false,
		            		maxFileSize : 2048,
							    	allowedFileExtensions: ["jpg", "jpeg", "gif", "png"]
									});
		            </script>
								<div id="tip_update_product_content_poster">
									<p>为节省用户流量，建议上传小于5M的图片。</p>
								</div>
							</div>
						</div>
						<input type="hidden" id="input_update_product_content_productid" />
						<input type="hidden" id="input_update_product_content_poster_url" />
						<div class="form-group">
							<!-- 加载编辑器的容器 -->
							<script id="product_update_ueditor" type="text/plain"></script>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
					<button id="btn_submit_update_product_content" type="button"
						class="btn btn-primary">提 交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->


	<!-- 产品视频新增模态框 -->
	<div class="modal fade" id="modal_add_product_video" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>新增视频</h4>
				</div>
				<div class="modal-body">
					<form id="form_add_product_video" method="post">
						<input type="hidden" id="input_add_video_product_id" />
						<div id="div_add_product_video_error_msg_container" class="form-group alert alert-danger"></div>
						<div id="div_add_product_video_file" class="form-group">
							<label class="col-sm-2 control-label">视 频 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<input id="input_add_product_video_file" class="file"
									name="file" type="file" data-min-file-count="0"
									data-max-file-count="3">
								<script>
		            	$("#input_add_product_video_file").fileinput({
		            		allowedFileTypes: ["video"],
		            		showUpload: false,
		            		maxFileSize : 20480,
							    	allowedFileExtensions: ["mp4"]
									});
		            </script>
								<div id="tip_add_product_video">
									<p>为节省用户流量，建议上传小于20M的视频。</p>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">视频标题 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<div id="div_add_product_video_title" class="input-group">
									<input id="input_add_product_video_title" type="text"
										name="title" class="form-control" data-max-len="20" required />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">拍摄时间</label>
							<div class="col-sm-10">
								<input id="input_add_product_video_shoot_time" type="text"
									name="shoot_time" class="form-control date" required />
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
					<button id="btn_submit_add_product_video" type="button"
						class="btn btn-primary">提 交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<!-- 产品视频编辑模态框 -->
	<div class="modal fade" id="modal_update_product_video" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>编辑视频</h4>
				</div>
				<div class="modal-body">
					<form id="form_update_product_video" action="">
						<input type="hidden" id="input_update_product_video_id" />
						<div class="form-group">
							<label class="col-sm-2 control-label">视频标题 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<div id="div_update_product_video_title" class="input-group">
									<input id="input_update_product_video_title" type="text"
										name="title" class="form-control" data-max-len="20" required />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">拍摄时间</label>
							<div class="col-sm-10">
								<input id="input_update_product_video_shoot_time" type="text"
									name="shoot_time" class="form-control date" required />
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
					<button id="btn_submit_update_product_video" type="button"
						class="btn btn-primary">提 交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<!-- 产品视频删除提示模态框 -->
	<div class="modal fade" id="modal_delete_product_video" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>删除提示</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="delete_product_video_id" /> 确定要删除该视频吗？
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
					<button id="btn_submit_delete_product_video" type="button"
						class="btn btn-primary">确 定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<!-- 各地经验新增模态框 -->
	<div class="modal fade" id="modal_add_product_cases" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document" style="width: 800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>新增各地经验</h4>
				</div>
				<div class="modal-body">
					<form id="form_add_product_cases" action="" method="post">
						<input type="hidden" id="input_add_cases_product_id" />
						<div id="div_add_product_cases_file" class="form-group">
							<label class="col-sm-2 control-label">封面 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<input id="input_add_product_cases_file" class="file"
									name="file" type="file" data-min-file-count="0"
									data-max-file-count="3">
								<script>
		            	$("#input_add_product_cases_file").fileinput({
		            		allowedFileTypes: ["image"],
		            		showUpload: false,
		            		maxFileSize : 2048,
							    	allowedFileExtensions: ["jpg", "jpeg", "gif", "png"]
									});
		            </script>
								<div id="tip_add_product_cases_file">
									<p>为节省用户流量，建议上传小于5M的图片。</p>
								</div>
								<div class="checkbox block">
									<label><input id="chk_add_product_case_pic_is_show"
										type="checkbox" checked> 封面图片显示在正文中</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">标题 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<div id="div_add_product_cases_title" class="input-group">
									<input id="input_add_product_cases_title" type="text"
										name="title" class="form-control len-counter"
										data-max-len="20" required />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">发布时间</label>
							<div class="col-sm-10">
								<input id="input_add_product_cases_publish_time" type="text"
									name="publish_time" class="form-control date" required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">地点<span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<div id="div_add_product_cases_address" class="row">
									<div class="col-sm-4">
										<select id="sel_add_product_cases_provinces" name="provinces"
											class="form-control">
											<option value="">请选择</option>
										</select>
									</div>
									<div class="col-sm-4">
										<select id="sel_add_product_cases_cities" name="cities"
											class="form-control">
											<option value="">请选择</option>
										</select>
									</div>
									<div class="col-sm-4">
										<select id="sel_add_product_cases_districts" name="districts"
											class="form-control">
											<option value="">请选择</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">摘要</label>
							<div id="div_add_product_cases_summary" class="col-sm-10">
								<textarea id="input_add_product_cases_summary"
									class="form-control len-counter" data-max-len="40" rows="3"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">内容<span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<!-- 加载编辑器的容器 -->
								<script id="ueditor_add_product_cases" type="text/plain"></script>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
					<button id="btn_submit_add_product_cases" type="button"
						class="btn btn-primary">提 交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<!-- 各地经验编辑模态框 -->
	<div class="modal fade" id="modal_update_product_case" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document" style="width: 800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>编辑各地经验</h4>
				</div>
				<div class="modal-body">
					<form id="form_update_product_case" action="" method="post">
						<input type="hidden" id="input_update_case_id" />
						<div id="div_update_product_case_title" class="form-group">
							<label class="col-sm-2 control-label">标题 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<div id="div_update_product_cases_title" class="input-group">
									<input id="input_update_product_case_title" type="text"
										name="title" class="form-control len-counter"
										data-max-len="20" required />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">发布时间</label>
							<div class="col-sm-10">
								<input id="input_update_product_case_publish_time" type="text"
									name="publish_time" class="form-control date" required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">地点<span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<div id="div_update_product_cases_address" class="row">
									<div class="col-sm-4">
										<select id="sel_update_product_cases_provinces"
											name="provinces" class="form-control"></select>
									</div>
									<div class="col-sm-4">
										<select id="sel_update_product_cases_cities" name="cities"
											class="form-control"></select>
									</div>
									<div class="col-sm-4">
										<select id="sel_update_product_cases_districts"
											name="districts" class="form-control"></select>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">摘要</label>
							<div id="div_update_product_cases_summary" class="col-sm-10">
								<textarea id="input_update_product_case_summary"
									class="form-control len-counter" data-max-len="40" rows="3"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">内容<span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<!-- 加载编辑器的容器 -->
								<script id="ueditor_update_product_case" type="text/plain"></script>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
					<button id="btn_submit_update_product_case" type="button"
						class="btn btn-primary">提 交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<!-- 编辑各地经验封面模态框 -->
	<div class="modal fade" id="modal_edit_product_case_pic" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>修改封面</h4>
				</div>
				<div class="modal-body">
					<form id="form_add_product_label" action="" method="post">
						<input type="hidden" id="txt_edit_product_case_id" />
						<div id="div_edit_product_case_pic" class="form-group">
							<div class="col-sm-10">
								<input id="txt_edit_product_case_pic" name="file" type="file"
									data-min-file-count="0" data-max-file-count="3">
								<div id="tip_edit_product_cases_pic">
									<p>为节省用户流量，建议上传小于5M的图片。</p>
								</div>
								<div class="checkbox block">
									<label><input id="chk_edit_product_case_pic_is_show"
										type="checkbox" checked> 封面图片显示在正文中</label>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
					<button id="btn_submit_edit_product_case_pic" type="button"
						class="btn btn-primary">提 交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<!-- 删除各地经验提示模态框 -->
	<div class="modal fade" id="modal_delete_product_cases" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>删除提示</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="delete_product_case_id" /> 确定要删除该案例吗？
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
					<button id="btn_submit_delete_product_case" type="button"
						class="btn btn-primary">确 定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<!-- 预览各地经验提示模态框 -->
	<div class="modal fade bs-example-modal-lg"
		id="modal_preview_case_content" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>预览</h4>
				</div>
				<div class="modal-body">
					<div id="div_preview_case_content"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
					<button id="btn_submit_preview_case_content" type="button"
						class="btn btn-primary">确 定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<!-- 产品图片修改裁切模态框 -->
	<div class="modal fade" id="modal_product_img_crop" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document" style="width: 800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>更换产品图片</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="">
								<div class="img-container">
									<img id="img2crop" src="" alt="请先选择要替换的图片">
									<div id="product_cover_pic_progress_bar"></div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 docs-buttons">
								<div class="btn-group" style="height: 30px;">
									<button class="btn btn-primary" data-method="zoom"
										data-option="0.1" type="button" title="Zoom In">
										<span class="docs-tooltip" data-toggle="tooltip"
											title="zoom.in"> <span class="icon icon-zoom-in"></span>
										</span>
									</button>
									<button class="btn btn-primary" data-method="zoom"
										data-option="-0.1" type="button" title="Zoom Out">
										<span class="docs-tooltip" data-toggle="tooltip"
											title="zoom.out"> <span class="icon icon-zoom-out"></span>
										</span>
									</button>
									<button class="btn btn-primary" data-method="rotate"
										data-option="-90" type="button" title="Rotate Left">
										<span class="docs-tooltip" data-toggle="tooltip"
											title="rotate.left.90"> <span
											class="icon icon-rotate-left"></span>
										</span>
									</button>
									<button class="btn btn-primary" data-method="rotate"
										data-option="90" type="button" title="Rotate Right">
										<span class="docs-tooltip" data-toggle="tooltip"
											title="rotate.right.90"> <span
											class="icon icon-rotate-right"></span>
										</span>
									</button>
									<button class="btn btn-primary" data-method="reset"
										type="button" title="Reset">
										<span class="docs-tooltip" data-toggle="tooltip" title="reset">
											<span class="icon icon-refresh"></span>
										</span>
									</button>
								</div>
								<div class="btn-group" style="height: 30px;">
									<label class="btn btn-primary btn-upload" for="inputImage"
										title="Upload image file"> <input class="sr-only"
										id="inputImage" name="file" type="file" accept="image/*">
										<span id="cropper_aspect_ratio" style="display:none" data-aspect-ratio-x="1" data-aspect-ratio-y="1"></span>
										<span class="docs-tooltip" data-toggle="tooltip"
										title="select image">选择本地图片</span>
									</label>
								</div>
								<%-- <div class="btn-group" style="height: 30px;" onclick="changeSource();">
									<label class="btn btn-primary btn-upload" for="selectImage"
										title="Upload image file"> <img class="sr-only"
										id="selectImage" name="selectfile">
										<span id="cropper_aspect_ratio_s" style="display:none" data-aspect-ratio-x="1" data-aspect-ratio-y="1"></span>
										<span class="docs-tooltip" data-toggle="tooltip"
										title="select image">从图库中选择</span>
									</label>
								</div> --%>
								<div class="btn-group btn-group-crop" style="height: 30px;">
									<div id="btn_product_img_upload"></div>
									<button id="btn_upload_crop_image" class="btn btn-primary"
										type="button">
										<span class="docs-tooltip" data-toggle="tooltip"
											title="upload">上传图片</span>
									</button>
								</div>
							</div>
							<!-- /.docs-buttons -->
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<!-- 添加标签模态框 -->
	<div class="modal fade" id="modal_add_product_label" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>添加标签</h4>
				</div>
				<div class="modal-body">
					<form id="form_add_product_label" action="" method="post">
						<input type="hidden" id="txt_add_label_product_id" />
						<div id="div_add_product_label" class="form-group">
							<label class="col-sm-2 control-label">标签 <span
								class="asterisk">*</span></label>
							<div class="col-sm-10">
								<input id="txt_add_product_label" class="file" name="file"
									type="file" data-min-file-count="0" data-max-file-count="3">
								<script>
		            	$("#txt_add_product_label").fileinput({
		            		allowedFileTypes: ["image"],
		            		showUpload: false,
		            		maxFileSize : 2048,
							    	allowedFileExtensions: ["jpg", "jpeg", "gif", "png"]
									});
		            </script>
								<div>
									<p>为节省用户流量，建议上传小于5M的图片。</p>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关
						闭</button>
					<button id="btn_submit_add_product_label" type="button"
						class="btn btn-primary">提 交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- modal -->

	<div class="modal fade bs-example-modal-photo" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog modal-photo-viewer">
			<img src="<%=path%>/res/bracket/images/loaders/loader6.gif" alt="">
		</div>
	</div>

	<!-- 下载模态框 -->
	
	<!-- modal -->

	<!-- 图片选择器 -->
	<jsp:include page="/mfr_admin/media_image_chooser.jsp" flush="true" />

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
