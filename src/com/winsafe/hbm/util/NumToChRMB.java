package com.winsafe.hbm.util;
/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NumToChRMB {
    private static final String chs[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾" };


    public static String numToRMB(String money) {
        StringBuilder chRMB = new StringBuilder();
        money=money.replaceAll("[,，]", "");
        
        try {
            double t = Double.parseDouble(money);
            if (t < 0) {
                System.out.println("金额不能是负的！");
                throw new IllegalArgumentException("金额不能是负的！");
                //return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("输入数据有误，请检查！");
            throw new IllegalArgumentException("输入数据有误，请检查！");
            //return null;
        }
        int dotPos = money.indexOf('.');
        String strz;
        if(dotPos==-1){
            strz=money;
        }
        else{
            strz=money.substring(0, dotPos);
        }
        StringBuilder sb=new StringBuilder(strz);
        while(sb.length()>=1&&sb.charAt(0)=='0'){
            sb.deleteCharAt(0);
        }
        if(sb.length()==0){
            return "零";
        }
        strz=sb.toString();
        String cur="";
        boolean zero=false;
        String temp;
        String digits;
        while(strz.length()>0){
            
            if(strz.length()>=4){
                digits=strz.substring(strz.length()-4);
                strz=strz.substring(0, strz.length()-4);
            }
            else{
                digits=strz;
                strz="";

            }
                temp=fourMaxChange(digits);
                if(temp.equals("零")){
                    if(cur.equals("亿")){
                        chRMB.insert(0, "亿");
                    }
                    if (zero == true) {
                        if (!chRMB.substring(0, 1).equals("零"))
                            chRMB.insert(0, "零");
                        zero = false;
                    }
                }
                else {
                temp += cur;
                chRMB.insert(0, temp);
                if (digits.charAt(0) == '0')
                    if (chRMB.length() >=1
                            && !chRMB.substring(0, 1).equals("零"))
                        chRMB.insert(0, "零");
                zero = true;

            }
                if(cur.equals("")){
                    cur="万";
                }
                else if(cur.equals("亿")){
                    cur="万";
                }
                else cur="亿";
                
            }
        String strx;
        if(dotPos!=-1){
        strx = money.substring(dotPos+1);
        if (strx.length() > 2) {
            System.out.println("金额只允许两位小数！");
            throw new IllegalArgumentException("金额只允许两位小数！");
            //return null;
        }
        int xiaoshu = Integer.parseInt(strx);
        if(xiaoshu==0||strx.length()==0){
            chRMB.append("元整");
        }
        else
        chRMB.append("元");
        if(strx.length()==1){
            chRMB.append(chs[xiaoshu]).append("分");
        }
        else{
            if(xiaoshu/10!=1){
                chRMB.append(chs[xiaoshu/10]).append("角");
            }
            else{
                chRMB.append("零");
            }
            if(xiaoshu%10!=0){
                chRMB.append(chs[xiaoshu%10]).append("分");
            }
        }
        }
        else{
            chRMB.append("元整");
        }
        String rmb = chRMB.toString();
        rmb= rmb.replace("零分", "");
        
        return rmb;
    }

    public static String fourMaxChange(String number)
            throws NumberFormatException {
        StringBuilder rs = new StringBuilder();
        int money;
        money = Integer.parseInt(number);
        boolean zero = false;
        if (money / 1000 != 0) {
            zero = true;
            rs.append(chs[money / 1000]).append("仟");
            money %= 1000;
        }

        if (money / 100 != 0) {
            zero = true;
            rs.append(chs[money / 100]).append("佰");
            money %= 100;
        } else if (zero == true) {
            rs.append(chs[0]);
            money %= 100;
        }

        if (money / 10 != 0) {
            zero = true;
            rs.append(chs[money / 10]).append("拾");
            money %= 10;
        } else if (zero == true && !rs.substring(rs.length() - 1).equals("零")) {
            rs.append(chs[0]);
            money %= 10;
        }
        if (money != 0) {
            zero = true;
            rs.append(chs[money]);
        } else if (zero == true && rs.substring(rs.length() - 1).equals("零")) {
            rs.delete(rs.length() - 1, rs.length());
        }
        if (zero == true)
            return rs.toString();
        else
            return chs[0];
    }

}

