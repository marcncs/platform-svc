package com.winsafe.drp.util;

/**
 * @author Fox
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class CheckImgGenerator {
        private int width=60;
        private int height=20;

        Color getRandColor(int fc, int bc) {
                Random random = new Random();
                if (fc > 255)
                        fc = 255;
                if (bc > 255)
                        bc = 255;
                int r = fc + random.nextInt(bc - fc);
                int g = fc + random.nextInt(bc - fc);
                int b = fc + random.nextInt(bc - fc);
                return new Color(r, g, b);
        }

        public String generateCheckImg(OutputStream pOut)throws IOException{
                StringBuffer tmpCheckNum=new StringBuffer();
                BufferedImage tmpImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics tmpGraphics = tmpImg.getGraphics();
                Random tmpRandom = new Random();
                tmpGraphics.setColor(getRandColor(200,250));
                tmpGraphics.fillRect(0, 0, width, height);
                tmpGraphics.setFont(new Font("Times New Roman",Font.PLAIN,18));

                tmpGraphics.setColor(getRandColor(160,200));
                for (int i=0;i<155;i++)
                {
                 int x = tmpRandom.nextInt(width);
                 int y = tmpRandom.nextInt(height);
                        int xl = tmpRandom.nextInt(12);
                        int yl = tmpRandom.nextInt(12);
                 tmpGraphics.drawLine(x,y,x+xl,y+yl);
                }

                for (int i=0;i<4;i++){
                    String rand=String.valueOf(tmpRandom.nextInt(10));
                    tmpCheckNum.append(rand);
                    tmpGraphics.setColor(new Color(20+tmpRandom.nextInt(110),20+tmpRandom.nextInt(110),20+tmpRandom.nextInt(110)));
                    tmpGraphics.drawString(rand,13*i+6,16);
                }


                tmpGraphics.setColor(Color.black);
                tmpGraphics.drawRect(0,0,width-1,height-1);

                tmpGraphics.dispose();

                ImageIO.write(tmpImg, "JPEG", pOut);

                return tmpCheckNum.toString();
        }


        public void generateCheckImg(OutputStream pOut,String outStr)throws IOException{
                StringBuffer tmpCheckNum=new StringBuffer();
                if(outStr == null)
                {
                    return;
                }
                if(outStr.length()> 4)
                {
                    int multiple = outStr.length() / 4;
                    int model = outStr.length() % 4;
                    if(multiple > 1)
                    {
                        switch(model)
                        {
                                case 0:
                                    width = width * multiple - 29 - multiple / 3 * 19 - multiple / 4 * 19;
                                    break;
                                case 1:
                                    width = width * multiple - 13 - multiple / 3 * 20 - multiple / 4 * 14;
                                    break;
                                case 2:
                                    width = width * multiple  -multiple / 2 * 2 - multiple / 3 * 20 - multiple /4 * 8;
                                    break;
                                case 3:
                                    width = width * multiple + 12 - multiple / 3 * 20 - multiple / 4 * 18;
                                    break;
                        }
                    }
                    else
                    {
                        switch(model)
                        {
                                case 1:
                                    width = width * multiple + 6;
                                    break;
                                case 2:
                                    width = width * multiple + 18;
                                    break;
                                case 3:
                                    width = width * multiple + 26;
                                    break;
                        }
                    }
                }

                BufferedImage tmpImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics tmpGraphics = tmpImg.getGraphics();
                Random tmpRandom = new Random();
                tmpGraphics.setColor(getRandColor(200,250));
                tmpGraphics.fillRect(0, 0, width, height);
                tmpGraphics.setFont(new Font("Times New Roman",Font.PLAIN,18));

                tmpGraphics.setColor(getRandColor(160,200));
                for (int i=0;i<155;i++)
                {
                 int x = tmpRandom.nextInt(width);
                 int y = tmpRandom.nextInt(height);
                        int xl = tmpRandom.nextInt(12);
                        int yl = tmpRandom.nextInt(12);
                 tmpGraphics.drawLine(x,y,x+xl,y+yl);
                }

                for (int i=0;i<outStr.length();i++){
                    String rand=String.valueOf(outStr.charAt(i));
                    tmpCheckNum.append(rand);
                    tmpGraphics.setColor(new Color(20+tmpRandom.nextInt(110),20+tmpRandom.nextInt(110),20+tmpRandom.nextInt(110)));
                    tmpGraphics.drawString(rand,13*i+6,16);
                }


                tmpGraphics.setColor(Color.black);
                tmpGraphics.drawRect(0,0,width-1,height-1);

                tmpGraphics.dispose();

                ImageIO.write(tmpImg, "JPEG", pOut);
        }

        public static void main(String[] pArgs)throws Exception{
                String tmpOutFile="outImg.jpg";
                if(pArgs.length>=1){
                        tmpOutFile=pArgs[0];
                }
                System.out.println("ok");
                FileOutputStream tmpFileOut=new FileOutputStream(tmpOutFile);
                BufferedOutputStream tmpBufOut=new BufferedOutputStream(tmpFileOut);
                CheckImgGenerator tmpCheckImgG=new CheckImgGenerator();
                tmpCheckImgG.generateCheckImg(tmpBufOut,"abcf1111222233334444");
                //System.out.println("the check number is : "+tmpCheckNum);
                tmpBufOut.flush();
                tmpBufOut.close();
        }

}
