package com.winsafe.drp.action.purchase;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppProductPicture;
import com.winsafe.drp.dao.ProductPicture;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.ValidateProductPicture;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UploadProductPictureAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ValidateProductPicture vpp = (ValidateProductPicture) form;

		try {

			UsersBean users = UserManager.getUser(request);
			int userid = users.getUserid();

			ProductPicture pp = new ProductPicture();
			pp.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"product_picture", 0, "")));
			pp.setProductid(request.getParameter("productid"));

			FormFile pictureFile = null;
			pictureFile = (FormFile) vpp.getPicture();
			String extName = null;
			String fileName = null;
			String contentType = null;
			String filePath = request.getRealPath("/");
			if (pictureFile != null && !pictureFile.equals("")) {
				fileName = pictureFile.getFileName().toLowerCase();
				extName = fileName.substring(fileName.indexOf("."), fileName
						.length());
				if (extName != null) {
					String firstName = fileName.substring(0, fileName
							.indexOf("."));
					InputStream fis = pictureFile.getInputStream();
					String sDateTime = DateUtil.getCurrentDateTimeString();
					String saveFileName = firstName + "_" + sDateTime + extName;

					OutputStream fos = new FileOutputStream(filePath
							+ "/picture/" + saveFileName);
					String fileAddress = "picture/" + saveFileName;
					byte[] buffer = new byte[2048];
					int bytesRead = 0;
					while ((bytesRead = fis.read(buffer, 0, 2048)) != -1) {

						fos.write(buffer, 0, bytesRead);
					}
					fos.close();
					fis.close();
					// 构造product
					pp.setPictureurl(fileAddress);

					// File ff = new File(filePath + "/picture/" +
					// "y_"+saveFileName);
					//
					// // 新加一张原图片大小一半的小图片===================
					// // Image src = javax.imageio.ImageIO.read(ff);
					// // //int wideth = src.getWidth(null); // 得到源图宽
					// // //int height = src.getHeight(null); // 得到源图长
					// // BufferedImage tag = new BufferedImage(104,
					// // 104, BufferedImage.TYPE_INT_RGB);
					// // tag.getGraphics().drawImage(src, 0, 0, 104,
					// // 104, null); // 绘制缩小后的图
					// // FileOutputStream out = new FileOutputStream(filePath
					// // + "/picture/" + "s_" + saveFileName); // 输出到文件流
					// // JPEGImageEncoder encoder =
					// JPEGCodec.createJPEGEncoder(out);
					// // encoder.encode(tag); // 近JPEG编码
					// // out.close();
					// getFixedBoundIcon(filePath + "/picture/",saveFileName,
					// 128,104);
					// // =================================================
					//					
					// //------------------加水印start-------------------
					// Image srci = javax.imageio.ImageIO.read(ff);
					// int wideth=srci.getWidth(null);
					// int height=srci.getHeight(null);
					// BufferedImage image=new
					// BufferedImage(wideth,height,BufferedImage.TYPE_INT_RGB);
					// Graphics g=image.createGraphics();
					// g.drawImage(srci,0,0,wideth,height,null);
					//					
					//					
					// File _filebiao = new File(filePath+"/images/logo.png");
					// Image src_biao = ImageIO.read(_filebiao);
					// int wideth_biao=src_biao.getWidth(null);
					// int height_biao=src_biao.getHeight(null);
					// g.drawImage(src_biao,wideth/2-100,height/2-height_biao,wideth_biao,height_biao,null);
					// g.dispose();
					// FileOutputStream fileout=new FileOutputStream(filePath +
					// "/picture/" + saveFileName);
					// JPEGImageEncoder jpegie =
					// JPEGCodec.createJPEGEncoder(fileout);
					// jpegie.encode(image);
					// fileout.close();
					// //------------------加水印end---------------------
				}
			}
			AppProductPicture app = new AppProductPicture();
			app.addProductPicture(pp);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 11, "商品资料>>上传产品图片,编号:" + pp.getId());

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

	public void getFixedBoundIcon(String filePath, String filename, int height,
			int width) throws Exception {
		double Ratio = 0.0;
		File F = new File(filePath + "y_" + filename);
		if (!F.isFile()) {
			throw new Exception(F
					+ " is not image file error in getFixedBoundIcon!");
		}
		BufferedImage Bi = ImageIO.read(F);
		if ((Bi.getHeight() > height) || (Bi.getWidth() > width)) {
			if (Bi.getHeight() > Bi.getWidth()) {
				Ratio = (new Integer(height)).doubleValue() / Bi.getHeight();
			} else {
				Ratio = (new Integer(width)).doubleValue() / Bi.getWidth();
			}
			File ThF = new File(filePath + "s_" + filename);
			Image Itemp = Bi.getScaledInstance(width, height, Bi.SCALE_SMOOTH);
			AffineTransformOp op = new AffineTransformOp(AffineTransform
					.getScaleInstance(Ratio, Ratio), null);
			Itemp = op.filter(Bi, null);
			try {
				ImageIO.write((BufferedImage) Itemp, "jpg", ThF);
			} catch (Exception ex) {
			}
		}
	}

}
