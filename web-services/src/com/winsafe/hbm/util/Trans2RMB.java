package com.winsafe.hbm.util;

public class Trans2RMB {   
  
       

    private String getNum() {   
        String s = null;   
        java.util.Scanner scanner = new java.util.Scanner(System.in);   
        s = scanner.next();   
        scanner.close();   
        if(this.checkNum(s)) {   
            return s;   
        } else {   
            return "";   
        }   
    }   

    private boolean checkNum(String s) {   
        try {   
            float f = Float.valueOf(s);   
            if(f < 0) {   
                System.out.println("非法数据，请检查！");   
                return false;   
            }else {   
                return true;   
            }   
        } catch (NumberFormatException e) {   
            System.out.println("非法数据，请检查！");   
            return false;   
        }      
    }   
       

    private String splitNum(String s) {   
        if("".equals(s)) {   
            return "";   
        }   
        int index = s.indexOf(".");   
        String intOnly = s.substring(0, index);   
        String part1 = this.numFormat(1, intOnly);   
        String smallOnly = s.substring(index + 1);   
        String part2 = this.numFormat(2, smallOnly);   
        String newS = part1 + part2;   
        return newS;   
    }   
           

    private String roundString(String s) {   
        if("".equals(s)) {   
            return "";   
        }   
        double d = Double.parseDouble(s);   
        d = (d * 100 + 0.5) / 100;   
        s = new java.text.DecimalFormat("##0.000").format(d);   
        int index = s.indexOf(".");   
        String intOnly = s.substring(0, index);   
        if(intOnly.length() > 13) {   
            System.out.println("输入数据过大！（整数部分最多13位！）");   
            return "";   
        }   
        String smallOnly = s.substring(index + 1);   
        if(smallOnly.length() > 2) {   
            String roundSmall = smallOnly.substring(0, 2);   
            s = intOnly + "." + roundSmall;   
        }   
        return s;   
    }   
       

    private String numFormat(int flag, String s) {   
        int sLength = s.length();   
        String bigLetter[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};   
        String unit[] = {"元", "拾", "佰", "仟", "万",    
                "拾", "佰", "仟",   
                "亿", "拾", "佰", "仟", "万"};   
        String small[] = {"分", "角"};   
        String newS = "";   
        for(int i = 0; i < sLength; i ++) {   
            if(flag == 1) {   
                newS = newS + bigLetter[s.charAt(i) - 48] + unit[sLength - i - 1];   
            } else if(flag == 2) {   
                newS = newS + bigLetter[s.charAt(i) - 48] + small[sLength - i - 1];   
            }   
        }   
        return newS;   
    }   
       

    private String cleanZero(String s) {   
        if("".equals(s)) {   
            return "";   
        }   
        while(s.charAt(0) == '零') {   
            s = s.substring(2);   
            if(s.length() == 0) {   
                return "零";   
            }   
        }   
        String regex1[] = {"零仟", "零佰", "零拾"};   
        String regex2[] = {"零亿", "零万", "零元"};   
        String regex3[] = {"亿", "万", "元"};   
        String regex4[] = {"零角", "零分"};   
        for(int i = 0; i < 3; i ++) {   
            s = s.replaceAll(regex1[i], "零");   
        }   
        for(int i = 0; i < 3; i ++) {   
            s = s.replaceAll("零零零", "零");   
            s = s.replaceAll("零零", "零");   
            s = s.replaceAll(regex2[i], regex3[i]);   
        }   
        for(int i = 0; i < 2; i ++) {   
            s = s.replaceAll(regex4[i], "");   
        }   
        s = s.replaceAll("亿万", "亿");   
        return s;   
    }   
}
