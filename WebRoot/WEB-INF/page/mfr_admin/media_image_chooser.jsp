<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+path;
	String cssPath = path + "/css";
	String scriptPath = path + "/js";
	String imgPath = path + "/images";
%>
<style type="text/css">
	.title-display {
		display: block;
		white-space:nowrap;
		overflow:hidden;
		text-overflow:ellipsis;
	}
	.dialog_choose_desc {
    position: absolute;
    bottom: 23px;
    left: 20px;
	}
	.progress_bar {
		padding: 10px;
		width: 90%;
    position: absolute;
    top: 70px;
    left: 20px;
    z-index: 1;
    background: white;
    border: 1px solid #e5e5e5;
	}
	.img-responsive, .thumbnail>img, .thumbnail a>img, .carousel-inner>.item>img, .carousel-inner>.item>a>img {
    display: block;
    max-width: 130px;
    height: 100px;
</style>

  <!-- 图片选择器模态框 -->
	<div class="modal fade" id="modal_media_image_chooser"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="panel panel-default">
	        <div class="panel-heading">
	          <div class="panel-btns">
	          	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
	          </div>
	          <h4 class="panel-title">选择图片</h4>
	        </div>
	        <div class="panel-body panel-body-nopadding">
            <div class="col-md-3 nopadding" style="height:600px; border-right:1px solid #e5e5e5;">
            	<div class="panel-body">
	              <ul id="image_chooser_group_container" class="profile-social-list">
	              </ul>
			        </div>
            </div>
            
            <div class="col-md-9 nopadding" style="height:600px">
            	<div class="panel-body" style="border-bottom:1px solid #e5e5e5;">
	              <a id="btn_image_chooser_upload" class="btn btn-info">本地上传</a>
	              <p class="pull-right">建议宽度960-1080像素之间 </p>
	              <div id="image_chooser_progress_bar" class="progress_bar"></div>
            	</div>
            	<div class="panel-body">
	              <div id="image_chooser_images_container" class="row">
               	
               	</div>
               	
               	<div class="image_chooser_page_container"></div>
               	
            	</div>
            </div>
	            
		        <p class="dialog_choose_desc">已选<span id="choose_image_number">0</span>个，可选<span id="max_choose_image_number">100</span>个</p>
		        
	        </div><!-- panel-body -->
	        <div class="modal-footer">
	        	<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
						<button id="btn_media_image_chooser" type="button" class="btn btn-primary" onclick="chooseMediaImages()">确 定</button>
				  </div>
	      </div>
			</div>
		</div>
	</div><!-- modal -->
	
	<script>
		var chooseNumber = 0;
		//{id:id, url:url}
		var chooseImages;
		var maxChooseImageNumber;
		var imageChooserGroupId;
		var imageChooserCallback;
		
		//初始化图片上传
		imgUploader("btn_image_chooser_upload", "image_chooser_progress_bar", 5, function(data) {
			try {
				var title = data.fname;
				if (title === null || title === '') {
					title = data.key;
				}
				wzMedia.addImage(title, data.url, data.fsize, imageChooserGroupId);
			} catch (e) {
				console.log(e);
			}
		}, function(err, errTip) {
			if (err.code == -600) {
				$("#btn_image_chooser_upload").next('label').eq(0).remove();
				$("#btn_image_chooser_upload").after('<label for="name" class="error">素材图片大小不能超过5M</label>');
			}
		}, true, function() {
			getImages(imageChooserGroupId);
		});
		
		function showMediaImageChooser(maxChooseNumber, callback) {
			//初始化
			chooseNumber = 0;
			imageChooserGroupId = 0;
			chooseImages = new Array();
			maxChooseImageNumber = maxChooseNumber || 1;
			imageChooserCallback = callback;
			$("#choose_image_number").html(chooseNumber);
			$("#max_choose_image_number").html(maxChooseImageNumber);
			
			showGroup();
			getImages(0);
			
			$("#image_chooser_progress_bar").hide();
			$("#btn_image_chooser_upload").next('label').eq(0).remove();
			
			$("#modal_media_image_chooser").modal("show");
			
		}
		
		function showGroup() {
			//获取分组
			var groups = wzMedia.getImageGroups();
			if (groups != null) {
				var groupItems = [];
				for (var i=0; i < groups.length; i++) {
					groupItems.push('<li><a href="javascript:getImages('  + groups[i].id + ');">&nbsp;' + groups[i].name + '(' + groups[i].count + ')</a></li>');
				}
				$("#image_chooser_group_container").html(groupItems.join(""));
			}
		}
		
		function createImageItem(image) {
			var imageItem = [];
			if (image) {
				imageItem.push('<div class="col-md-3 image">');
					imageItem.push('<div class="thmb">');
						imageItem.push('<div class="thmb-prev" style="width:130px;height:100px;">');
							imageItem.push('<a href="javascript:chooseImage(' + image.id + ', 1);" data-rel="prettyPhoto">');
								imageItem.push('<img width="130" height="100" src="' + image.url + '" class="img-responsive" alt="">');
							imageItem.push('</a>');
						imageItem.push('</div>');
						imageItem.push('<div class="checkbox block">');
							var index = getIndex(image.id);
							if (index >= 0 ) {
								imageItem.push('<label><input type="checkbox" name="chb_choose_images" id="chb_choose_image_' + image.id + '" data-url="' + image.url + '" onclick="chooseImage(' + image.id + ');" checked>');
							} else {
								imageItem.push('<label><input type="checkbox" name="chb_choose_images" id="chb_choose_image_' + image.id + '" data-url="' + image.url + '" onclick="chooseImage(' + image.id + ');">');
							}
							imageItem.push('<span class="fm-title title-display">' + image.title + '</span></label>');
						imageItem.push('</div>');
					imageItem.push('</div>');
				imageItem.push('</div>');
			}
			
			return imageItem.join("");
		}
		
		function getImages(groupId) {
			imageChooserGroupId = groupId;
			wzMedia.getImagesByPage('image_chooser_page_container', function(images){
				if (images != null) {
					var elements = [];
					$("#image_chooser_images_container").html("");
					for (var i=0; i<images.length; i++) {
						$("#image_chooser_images_container").append(createImageItem(images[i]));
					}
				} else {
					$("#image_chooser_images_container").html("无数据");
				}
			}, groupId);
		}
		
		function chooseImage(imageId, mode) {
			if (maxChooseImageNumber == 1) {
				chooseImages = new Array();
				chooseNumber = 0;
				$("input[name='chb_choose_images']").attr("checked", false);
				$("#chb_choose_image_" + imageId).attr("checked", true);
				chooseNumber = 1;
				var url = $("#chb_choose_image_" + imageId).attr("data-url");
				chooseImages = new Array({id: imageId, url : url});
			} else {
				if (mode == 1) {
					if ($("#chb_choose_image_" + imageId).attr("checked")) {
						$("#chb_choose_image_" + imageId).attr("checked", false);
					} else {
						$("#chb_choose_image_" + imageId).attr("checked", true);
					}
				}
				if ($("#chb_choose_image_" + imageId).attr("checked")) {
					if (chooseNumber >= maxChooseImageNumber) {
						//如果已经选择数量不小于maxChooseMsgNumber，则去掉选中状态
						$("#chb_choose_image_" + imageId).attr("checked", false)
						return ;
					}
					chooseNumber ++;
					var url = $("#chb_choose_image_" + imageId).attr("data-url");
					chooseImages.push({id: imageId, url : url});
				} else {
					chooseNumber --;
					var index = getIndex(imageId);
					chooseImages[0];
					if (index >= 0) chooseImages.splice(index, 1);
				}
			}
			
			$("#choose_image_number").html(chooseNumber);
		}
		
		function getIndex(id) {
			for (var i=0; i<chooseImages.length; i++) {
				if (id === chooseImages[i].id) {
					//指针移动第一元素，不然删除会有问题
					return i;
				}
			}
			return -1;
		}
		
		function chooseMediaImages() {
			if (chooseImages) {
				$("#modal_media_image_chooser").modal("hide");
				imageChooserCallback && imageChooserCallback(chooseImages);
			}
		}
	</script>