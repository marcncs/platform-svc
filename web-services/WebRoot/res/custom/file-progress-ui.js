/*global plupload */
/*global qiniu */
function FileProgress(file, targetID, buttonName) {
    this.fileProgressID = file.id;
    this.file = file;
    this.buttonName = buttonName;

    this.opacity = 100;
    this.height = 0;
    this.fileProgressWrapper = jQuery('#' + this.fileProgressID);
    if (!this.fileProgressWrapper.length) {

        this.fileProgressWrapper = jQuery('<div></div>');
        var Wrappeer = this.fileProgressWrapper;
        Wrappeer.attr('id', this.fileProgressID).addClass('progress-container');

        var fileSize = plupload.formatSize(file.size).toUpperCase();
        var progressText = jQuery("<span/>");
        progressText.addClass('progress-file-name').text(file.name);

        var progressSize = jQuery("<span/>");
        progressSize.addClass("progress-file-size").text("（" + fileSize + "）");

        var progressBarBox = jQuery("<div/>");
        progressBarBox.addClass('info');
        var progressBarWrapper = jQuery("<div/>");
        progressBarWrapper.addClass("progress progress-striped");

        var progressBar = jQuery("<div/>");
        progressBar.addClass("progress-bar progress-bar-info")
            .attr('role', 'progressbar')
            .attr('aria-valuemax', 100)
            .attr('aria-valuenow', 0)
            .attr('aria-valuein', 0)
            .width('0%');

        var progressBarPercent = jQuery('<span class=sr-only />');
        progressBarPercent.text(fileSize);

        var progressCancel = jQuery('<a href=javascript:; />');
        progressCancel.show().addClass('progress-cancel').text('取消上传');

        progressBar.append(progressBarPercent);
        progressBarWrapper.append(progressBar);
        progressBarBox.append(progressBarWrapper);
        progressBarBox.append(progressCancel);

        var progressBarStatus = jQuery('<div class="status text-center"/>');
        progressBarBox.append(progressBarStatus);

        Wrappeer.append(progressText);
        Wrappeer.append(progressSize);
        Wrappeer.append(progressBarBox);

        jQuery('#' + targetID).append(Wrappeer);
    } else {
        this.reset();
    }

    this.height = this.fileProgressWrapper.offset().top;
    this.setTimer(null);
}

FileProgress.prototype.setTimer = function(timer) {
    this.fileProgressWrapper.FP_TIMER = timer;
};

FileProgress.prototype.getTimer = function(timer) {
    return this.fileProgressWrapper.FP_TIMER || null;
};

FileProgress.prototype.reset = function() {
    this.fileProgressWrapper.attr('class', "progress-container");
    this.fileProgressWrapper.find('.progress .progress-bar-info').attr('aria-valuenow', 0).width('0%').find('span').text('');
    this.appear();
};

FileProgress.prototype.setProgress = function(percentage, speed, chunk_size) {
    this.fileProgressWrapper.attr('class', "progress-container green");

    var file = this.file;
    var uploaded = file.loaded;

    var size = plupload.formatSize(uploaded).toUpperCase();
    var formatSpeed = plupload.formatSize(speed).toUpperCase();
    var progressbar = this.fileProgressWrapper.find('.progress').find('.progress-bar-info');
    if (this.fileProgressWrapper.find('.status').text() === '取消上传'){
        return;
    }
    this.fileProgressWrapper.find('.status').text("已上传: " + size + " 上传速度： " + formatSpeed + "/s");
    percentage = parseInt(percentage, 10);
    if (file.status !== plupload.DONE && percentage === 100) {
        percentage = 99;
    }

    progressbar.attr('aria-valuenow', percentage).css('width', percentage + '%');

    this.appear();
};

FileProgress.prototype.setComplete = function(up, info, isCloseProgress) {
	this.fileProgressWrapper.find('.status').text("文件上传成功");
	this.fileProgressWrapper.find('.progress-cancel').hide();
	if (isCloseProgress) {
		//关闭进度条
		this.fileProgressWrapper.remove();
	}
};

FileProgress.prototype.setError = function() {
	this.fileProgressWrapper.remove();
};

FileProgress.prototype.setCancelled = function(manual) {
//    var progressContainer = 'progress-container';
//    if (!manual) {
//        progressContainer += ' red';
//    }
//    this.fileProgressWrapper.attr('class', progressContainer);
//    this.fileProgressWrapper.find('.progress').remove();
//    this.fileProgressWrapper.find('.progress-cancel').hide();
    this.fileProgressWrapper.remove();
    if (this.buttonName) {
    	jQuery("#" + this.buttonName).show();
    }
};

FileProgress.prototype.setStatus = function(status, isUploading) {
    if (!isUploading) {
        this.fileProgressWrapper.find('.status').text(status).attr('class', 'status text-left');
    }
};

// 绑定取消上传事件
FileProgress.prototype.bindUploadCancel = function(up) {
    var self = this;
    if (up) {
        self.fileProgressWrapper.find('.progress-cancel').on('click', function(){
            up.removeFile(self.file);
            self.setCancelled(false);
//            self.setStatus("取消上传");
//            self.fileProgressWrapper.find('.status').css('left', '0');
        });
    }

};

FileProgress.prototype.appear = function() {
    if (this.getTimer() !== null) {
        clearTimeout(this.getTimer());
        this.setTimer(null);
    }

    if (this.fileProgressWrapper[0].filters) {
        try {
            this.fileProgressWrapper[0].filters.item("DXImageTransform.Microsoft.Alpha").opacity = 100;
        } catch (e) {
            // If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
            this.fileProgressWrapper.css('filter', "progid:DXImageTransform.Microsoft.Alpha(opacity=100)");
        }
    } else {
        this.fileProgressWrapper.css('opacity', 1);
    }

    this.fileProgressWrapper.css('height', '');

    this.height = this.fileProgressWrapper.offset().top;
    this.opacity = 100;
    this.fileProgressWrapper.show();

};
