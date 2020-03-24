
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

function uploadImage(target, callback) {
	var file = target.files[0];
	
	if (!file || file.length == 0) {
		return ;
	}
	
	if (file.size > 2 * 1024 * 1024) {
		$(target).after('<label class="error">图片大小不能超过2M，请重新选择</label>');
		return;
	}
	
	var img_url = uploadImg(file);
	callback && callback(img_url);
}