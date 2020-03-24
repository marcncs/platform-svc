<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>


<!DOCTYPE HTML>
<head>
<!-- 引入通用的css、js等 -->
<%@ include file="include/meta.jsp"%>

<link href="<%=path%>/res/bracket/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=path%>/res/star-rating.min.css" media="all" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="<%=path%>/res/am-swipe/css/amazeui.css">

<style>
	.upload_preview {
  	border-top: 1px solid #D2D2D2;
    border-bottom: 1px solid #bbb;
    background-color: #fff;
    overflow: hidden;
	}
	
	.add_upload {
    height: 100%;
    margin: 1em;
    float: left;
    position: relative;
	}
	
	.add_imgBox {
    border: 1px solid #DFDFDF;
    display: table-cell;
    text-align: center;
    vertical-align: middle;
    transition: border 0.2s;
    -moz-transition: border 0.2s;
    -webkit-transition: border 0.2s;
    -o-transition: border 0.2s;
	}
	/**.add_upload a {
    color: #34538b;
    text-decoration: none;
	}*/
	.uploadImg {
    margin: auto;
	}
	
	.uploadImg img {
    border: none;
    max-width: 100%!important;
    height: auto!important;
    margin: auto;
	}
	.upload_image {
    padding: 0px;
	}
</style>

<title>云单页</title>

</head>
<body>

	<div class="all-elements">
		<div class="snap-drawers"></div>

		<!-- 页面头部 -->
		<jsp:include page="include/header.jsp" flush="true" />

		<!-- Page Content-->
		<div id="content" class="snap-content">

			<div class="content">

				<div id="ranking" class="one-half-responsive">
				<br><br>
<!-- 					<input id="rating-input" value="5" type="number"/> -->
					<textarea id="contactMessageTextarea" class="contactTextarea requiredField" placeholder="评论将由厂家筛选后显示，对所有人可见" data-max-len="500"></textarea>
					<a style="height: 80px; width: 100px;" title="点击添加文件"
						id="btn_upload_img" class="add_imgBox" href="javascript:void(0)">
						<div class="uploadImg" style="width: 105px">
							<img class="upload_image" src="<%=path%>/images/elabel/add_img.png"
								style="width: expression(this.width &gt; 105 ? 105px : this.width)">
								<input id="txt_image_url" type="hidden" />
						</div>
					</a>
					<a id="btn_delete_image" style="display:none" href="javascript:void(0);">删除</a>
					<button id="btn_choose_pic"></button>
					<div id="progress_bar"></div>
					<input id="file_image" style="display:none" type="file" accept="image/*" class="input">
					
					<br/>
					<a id="btn_submit_evaluation" href="#" class="button button-green full-button">提交评论</a>
				</div>

			</div>

			<!-- Page Footer-->
		</div>
	</div>
	
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
	  <div class="am-modal-dialog">
	    <div class="am-modal-hd">感谢您的评论！</div>
	    <div class="am-modal-bd">
	     	系统正在处理中...
	    </div>
	    <div class="am-modal-footer">
	      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
	    </div>
	  </div>
	</div>
	
	<script src="<%=path%>/js/elabel/login-info-cookie.js"></script>
	<script src="<%=path%>/res/star-rating.min.js" type="text/javascript"></script>
	<script src="<%=path%>/res/input-len-counter.js"></script>
	<script src="<%=path%>/res/am-swipe/js/amazeui.min.js"></script>
	
	<!-- 七牛相关js -->
	<script src="<%=path%>/res/plupload/plupload.full.min.js"></script>
	<script src="<%=path%>/res/plupload/i18n/zh_CN.js"></script>
	<script src="<%=path%>/res/qiniu/qiniu.js"></script>
	<script src="<%=path%>/res/custom/file-progress-ui.js"></script>
	<script src="<%=path%>/res/custom/file-uploader.js?ver=201804251150"></script>

	<script>
		(function ($) {
			$().ready(function() {
				set_elabel_title('发表评论');
				show_elabel_back('<%=path%>/qr/elabel/${product_id}/feedbacks');
				hide_menu();
				set_html_title("发表评论", "发表评论");
				set_custom_style(1); //根据不同厂家 应用不同的样式
				
				/* $('#rating-input').rating({
	        min: 0,
	        max: 5,
	        step: 1,
	        size: 'lg',
	        showClear: false,
	        showCaption: false
	      }); */
				
				//初始化图片上传
				var uploader = imgUploader("btn_choose_pic", "progress_bar", 5, 
					function(data) {
						$(".upload_image").attr("src", data.url);
						$("#txt_image_url").attr("value", data.url);
						$("#btn_delete_image").show();
					}, 
					function(err, errTip) {
						console.log(err);
						console.log(errTip);
					}
				);
				
				$("#contactMessageTextarea").focus(function(){
					$("#contactMessageTextarea").next('label').eq(0).remove();
				});
				
				//提交评论
        $("#btn_submit_evaluation").on("click", function() {
        			//var score = $('#rating-input').val();
					var content = $("#contactMessageTextarea").val();
					/* if (!score) {
						$("#starBg").next('label').eq(0).remove();
						$("#starBg").after('<label class="control-label error">请评分</label>');
					} */
					if (!content || content === '' || content.length > 500) {
						$("#contactMessageTextarea").next('label').eq(0).remove();
						$("#contactMessageTextarea").after('<label class="control-label error">评论内容不能为空且不能大于500字</label>');
					}
					if (!content) {
						return ;
					}
					
					var picUrl = $("#txt_image_url").val();
					if (!picUrl || picUrl === 'undefined') {
						picUrl = null ;
					}
					
					//var data = {score:score, content:content, pic_url:picUrl};
					var data = {content:content, pic_url:picUrl};
					ajaxJson("<%=path%>/qr/api/product/${product_id}/feedbacks", "POST", data, false, successCallback, errorCallback);
					
				});
				
				$("#btn_upload_img").bind("click", function(e){
					$("#file_image").click();
	      });
				
				$("#file_image").on("change", function(e) {
					var file = e.target.files[0];
					
					if (!file || file.length == 0) {
						return ;
					}
					
					uploader.addFile(file, file.name);
					uploader.start();
				});
				
				$("#btn_delete_image").click(function(){
					$(".upload_image").attr("src", "<%=path%>/images/elabel/add_img.png");
					$("#txt_image_url").attr("value", "");
					$("#btn_delete_image").hide();
				});
			});
			
			function successCallback(result) {
			      $('#my-confirm').modal({
			        relatedTarget: this,
			        onConfirm: function(options) {
			          window.location.href = "<%=path%>/qr/elabel/${product_id}/feedbacks";
			        },
			        closeViaDimmer: false,
			      });
				}

			
			function errorCallback(result) {
				if (result.code == 'WZ110003') {
					$("#contactMessageTextarea").next('label').eq(0).remove();
					$("#contactMessageTextarea").after('<label class="control-label error">请输入评论内容</label>');
				} else if (result.code == 'WZ130001') {
					window.location.href = "/elabel/signin.jsp";
				} else if (result.code == 'WZ140001') {
					$("#contactSubmitButton").next('label').eq(0).remove();
					$("#contactSubmitButton").after('<label class="control-label error">您已经评论过，不能进行多次评论</label>');
				}
			}
		}(jQuery));
	</script>
</body>
