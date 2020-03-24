import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MailService;

public class TestMail {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String title = new String("测试标题 subject test");
		String content = new String((DateUtil.formatDateTime(new Date())+" 测试内容 content test"));
		MailService mailService = new MailService();
		mailService.addMailTo("yu.xi.ext@bayer.com",",","TO");
		//mailService.addMailTo("xingkui.zhu@bayer.com",",","TO");
		mailService.setSubject(title);
		mailService.setContent(content);
//		mailService.send2();
	}
}
