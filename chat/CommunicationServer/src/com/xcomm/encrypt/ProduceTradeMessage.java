package com.xcomm.encrypt;


import java.util.HashMap;

/**
 * ISO8583报文类
 * 作者：Leonidas
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：实现将数据封装成ISO8583报文格式
 * 以实现安全传输
 */
public class ProduceTradeMessage {
        
        //域号字长属性
        private HashMap fieldPro;
        
        //域号最大字长
        private HashMap fieldLen;
        
        //输入域值
        private HashMap inputField = new HashMap();
        
        private HashMap hexmap = new HashMap();
        
        //报文字长
        private String TradeLength;
        //报文类型长度
        private String TradeType;
        //64位域号
        private String Field64;
        //128位域号
        private String Field128;
        //域值
        private String FieldContent;
        //报文字符流
        private String TradeMessage;
        
        
        
        public ProduceTradeMessage(){
                inputField();
                hexmap();
        }
                
        /**
         * 对参数进行设置------------------------------------------------------------------------------------------------
         *
         */
        private void inputField(){
                
                inputField.put("2","22");     //  两位变长，最大长度22位
                
                inputField.put("3","aaaaaa");     //  6位定长
                
                inputField.put("4","aaaaaaaaaaaa");            //  12位定长
                
                inputField.put("7","aaaaaaaaaa");            //  10位定长
                
                inputField.put("11","aaaaaa");        //  6位定长
                
                inputField.put("14","aaaa");        //  4位定长
                
                inputField.put("18","aaaa");        //  4位定长
                
                inputField.put("22","aaa");        //  3位定长
                
                inputField.put("32","11");        //  两位变长，最大长度11位
                
                inputField.put("33","11");        //  两位变长，最大长度11位
                
                inputField.put("35","37");        //  两位变长，最大长度37位
                
                inputField.put("37","aaaaaaaaaaaa");        //  12位定长
                
                inputField.put("38","aaaaaa");        //  6位定长
                
                inputField.put("39","aa");        //  2位定长
                
                inputField.put("41","aaaaaaaa");        //  8位定长
                
                inputField.put("42","aaaaaaaaaaaaaaa");        //  15位定长
                
                inputField.put("43","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");        //  40位定长
                
                inputField.put("44","25");        //  两位变长，最大长度25位
                
                inputField.put("45","76");        //  两位变长，最大长度76位
                
                inputField.put("48","999");        //  三位变长，最大长度999位
                
                inputField.put("49","aaa");        //  3位定长
                
                inputField.put("51","aaa");        //  3位定长
                
                inputField.put("54","120");        //  三位变长，最大长度120位
                
                inputField.put("61","999");        //  三位变长，最大长度999位
                
                inputField.put("95","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");        //  42位定长
        }
        
        private String getTradeType(){
                TradeType = "0430";  //  交易类型，可选0110,0430(冲正交易),0130
                System.out.println("---------------------");
                System.out.println("报文类型："+TradeType);
                System.out.println("---------------------");
                return TradeType;
        }
        
        private String fieldCon2(){
                System.out.println("----------------------------2号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("2");
                int length = con.length();
                String len = new String();
                if(length>22){
                        System.out.println("错误:超过最大长度22位，请更正");
                }else{
                        if(length<10){
                                len = "0"+Integer.toString(length);
                        }else{
                                len = Integer.toString(length);
                        }
                }
                System.out.println("2位变长，长度："+len);
                content = len+con;
                System.out.println("值："+content);
                return content;
        }
                
                private String fieldCon32(){
                        System.out.println("----------------------------32号域----------------------------");
                        String content = new String();
                        String con = (String)inputField.get("32");
                        int length = con.length();
                        String len = new String();
                        if(length>11){
                                System.out.println("错误:超过最大长度11位，请更正");
                        }else{
                                if(length<10){
                                        len = "0"+Integer.toString(length);
                                }else{
                                        len = Integer.toString(length);
                                }
                        }
                
                System.out.println("2位变长，长度："+len);
                content = len+con;
                System.out.println("值："+content);
                return content;
        }
                
                private String fieldCon33(){
                        System.out.println("----------------------------33号域----------------------------");
                        String content = new String();
                        String con = (String)inputField.get("33");
                        int length = con.length();
                        String len = new String();
                        if(length>11){
                                System.out.println("错误:超过最大长度11位，请更正");
                        }else{
                                if(length<10){
                                        len = "0"+Integer.toString(length);
                                }else{
                                        len = Integer.toString(length);
                                }
                        }
                        System.out.println("2位变长，长度："+len);
                        content = len+con;
                        System.out.println("值："+content);
                        return content;
                }
                        
                        private String fieldCon35(){
                                System.out.println("----------------------------35号域----------------------------");
                                String content = new String();
                                String con = (String)inputField.get("35");
                                int length = con.length();
                                String len = new String();
                                if(length>37){
                                        System.out.println("错误:超过最大长度37位，请更正");
                                }else{
                                        if(length<10){
                                                len = "0"+Integer.toString(length);
                                        }else{
                                                len = Integer.toString(length);
                                        }
                                }
                                System.out.println("2位变长，长度："+len);
                                content = len+con;
                                System.out.println("值："+content);
                                return content;
                        }
        
        private String fieldCon3(){
                System.out.println("----------------------------3号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("3");
                int length = con.length();
                String len = new String();
                if(length!=6){
                        System.out.println("错误:不符合定长长度6位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("6位定长，长度："+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon4(){
                System.out.println("----------------------------4号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("4");
                int length = con.length();
                String len = new String();
                if(length!=12){
                        System.out.println("错误:不符合定长长度12位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("12位定长，长度："+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon7(){
                System.out.println("----------------------------7号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("7");
                int length = con.length();
                String len = new String();
                if(length!=10){
                        System.out.println("错误:不符合定长长度10位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("10位定长，长度："+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon11(){
                System.out.println("----------------------------11号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("11");
                int length = con.length();
                String len = new String();
                if(length!=6){
                        System.out.println("错误:不符合定长长度6位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("6位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon14(){
                System.out.println("----------------------------14号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("14");
                int length = con.length();
                String len = new String();
                if(length!=4){
                        System.out.println("错误:不符合定长长度4位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("4位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon18(){
                System.out.println("----------------------------18号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("18");
                int length = con.length();
                String len = new String();
                if(length!=4){
                        System.out.println("错误:不符合定长长度4位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("4位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon22(){
                System.out.println("----------------------------22号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("22");
                int length = con.length();
                String len = new String();
                if(length!=3){
                        System.out.println("错误:不符合定长长度3位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("3位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon37(){
                System.out.println("----------------------------37号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("37");
                int length = con.length();
                String len = new String();
                if(length!=12){
                        System.out.println("错误:不符合定长长度12位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("12位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon38(){
                System.out.println("----------------------------38号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("38");
                int length = con.length();
                String len = new String();
                if(length!=6){
                        System.out.println("错误:不符合定长长度6位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("6位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon39(){
                System.out.println("----------------------------39号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("39");
                int length = con.length();
                String len = new String();
                if(length!=2){
                        System.out.println("错误:不符合定长长度2位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("2位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon41(){
                System.out.println("----------------------------41号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("41");
                int length = con.length();
                String len = new String();
                if(length!=8){
                        System.out.println("错误:不符合定长长度8位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("8位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon42(){
                System.out.println("----------------------------42号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("42");
                int length = con.length();
                String len = new String();
                if(length!=15){
                        System.out.println("错误:不符合定长长度15位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("15位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon43(){
                System.out.println("----------------------------43号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("43");
                int length = con.length();
                String len = new String();
                if(length!=40){
                        System.out.println("错误:不符合定长长度40位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("40位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon49(){
                System.out.println("----------------------------49号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("49");
                int length = con.length();
                String len = new String();
                if(length!=3){
                        System.out.println("错误:不符合定长长度3位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("3位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon51(){
                System.out.println("----------------------------51号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("51");
                int length = con.length();
                String len = new String();
                if(length!=3){
                        System.out.println("错误:不符合定长长度3位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("3位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon95(){
                System.out.println("----------------------------95号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("95");
                int length = con.length();
                String len = new String();
                if(length!=42){
                        System.out.println("错误:不符合定长长度42位，请更正");
                }else{
                        len = Integer.toString(length);
                }
                System.out.println("42位定长，长度:"+len);
                content = con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon44(){
                System.out.println("----------------------------44号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("44");
                int length = con.length();
                String len = new String();
                if(length>25){
                        System.out.println("错误:超过最大长度25位，请更正");
                }else{
                        if(length<10){
                                len = "0"+Integer.toString(length);
                        }else{
                                len = Integer.toString(length);
                        }
                }
                System.out.println("2位变长，长度:"+len);
                content = len+con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon45(){
                System.out.println("----------------------------45号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("45");
                int length = con.length();
                String len = new String();
                if(length>76){
                        System.out.println("错误:超过最大长度76位，请更正");
                }else{
                        if(length<10){
                                len = "0"+Integer.toString(length);
                        }else{
                                len = Integer.toString(length);
                        }
                }
                System.out.println("2位变长，长度:"+len);
                content = len+con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon48(){
                System.out.println("----------------------------48号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("48");
                int length = con.length();
                String len = new String();
                if(length>999){
                        System.out.println("错误:超过最大长度999位，请更正");
                }else{
                        if(length<10){
                                len = "00"+Integer.toString(length);
                        }else if(length<100){
                                len = "0" + Integer.toString(length);
                        }else{
                                len = Integer.toString(length);
                        }
                }
                System.out.println("3位变长，长度:"+len);
                content = len+con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon54(){
                System.out.println("----------------------------54号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("54");
                int length = con.length();
                String len = new String();
                if(length>120){
                        System.out.println("错误:超过最大长度120位，请更正");
                }else{
                        if(length<10){
                                len = "00"+Integer.toString(length);
                        }else if(length<100){
                                len = "0" + Integer.toString(length);
                        }else{
                                len = Integer.toString(length);
                        }
                }
                System.out.println("3位变长，长度:"+len);
                content = len+con;
                System.out.println("值："+content);
                return content;
        }
        
        private String fieldCon61(){
                System.out.println("----------------------------61号域----------------------------");
                String content = new String();
                String con = (String)inputField.get("61");
                int length = con.length();
                String len = new String();
                if(length>999){
                        System.out.println("错误:超过最大长度999位，请更正");
                }else{
                        if(length<10){
                                len = "00"+Integer.toString(length);
                        }else if(length<100){
                                len = "0" + Integer.toString(length);
                        }else{
                                len = Integer.toString(length);
                        }
                }
                System.out.println("3位变长，长度:"+len);
                content = len+con;
                System.out.println("值："+content);
                return content;
        }
        
        public String tradeMessage64(){
                String tm = new String();
                tm =
                        getTradeType() +
                        FieldNum64() +
                //        FieldNum128() +
                        fieldCon2() +
                        fieldCon3() +
                        fieldCon4() +
                        fieldCon7() +
                        fieldCon11() +
                        fieldCon14() +
                        fieldCon18() +
                        fieldCon22() +
                        fieldCon32() +
                        fieldCon33() +
                        fieldCon35() +
                        fieldCon37() +
                        fieldCon38() +
                        fieldCon39() +
                        fieldCon41() +
                        fieldCon42() +
                        fieldCon43() +
                        fieldCon44() +
                        fieldCon45() +
                        fieldCon48() +
                        fieldCon49() +
                        fieldCon51() +
                        fieldCon54() +
                        fieldCon61() ;
                
                int length = tm.length()+4;
                String lengthall = new String();
                if(length<10){
                        lengthall = "000" + length;
                }else if(length<100){
                        lengthall = "00" + length;
                }else if(length<1000){
                        lengthall = "0" + length;
                }
                
                tm = lengthall + tm;
                return tm;
        }
        
        public String tradeMessage128(){
                String tm = new String();
                tm =
                        getTradeType() +
                        //FieldNum64() +
                        FieldNum128() +
                        fieldCon2() +
                        fieldCon3() +
                        fieldCon4() +
                        fieldCon7() +
                        fieldCon11() +
                        fieldCon14() +
                        fieldCon18() +
                        fieldCon22() +
                        fieldCon32() +
                        fieldCon33() +
                        fieldCon35() +
                        fieldCon37() +
                        fieldCon38() +
                        fieldCon39() +
                        fieldCon41() +
                        fieldCon42() +
                        fieldCon43() +
                        fieldCon44() +
                        fieldCon45() +
                        fieldCon48() +
                        fieldCon49() +
                        fieldCon51() +
                        fieldCon54() +
                        fieldCon61() +
                        fieldCon95() ;
                
                int length = tm.length()+4;
                String lengthall = new String();
                if(length<10){
                        lengthall = "000" + length;
                }else if(length<100){
                        lengthall = "00" + length;
                }else if(length<1000){
                        lengthall = "0" + length;
                }
                
                tm = lengthall + tm;
                return tm;
        }
        
        private void hexmap(){
                hexmap.put("0000","0");
                hexmap.put("0001","1");
                hexmap.put("0010","2");
                hexmap.put("0011","3");
                hexmap.put("0100","4");
                hexmap.put("0101","5");
                hexmap.put("0110","6");
                hexmap.put("0111","7");
                hexmap.put("1000","8");
                hexmap.put("1001","9");
                hexmap.put("1010","A");
                hexmap.put("1011","B");
                hexmap.put("1100","C");
                hexmap.put("1101","D");
                hexmap.put("1110","E");
                hexmap.put("1111","F");
        }
        
        public String FieldNum64(){
                /*
                 * 64位组合
                 */
                System.out.println("开始为 64 位报文域赋值二进制位");
                char field[] = new char[64];
                String num;
                String fieldnum = new String();
                field[0] = '0';
                System.out.println("赋值域号：1");
                for(int i = 1;i < 64;i ++){
                        num = String.valueOf(i+1);
                        System.out.println("赋值域号：" + num);
                        if(inputField.containsKey(num)){
                                field[i] = '1';
                        }else{
                                field[i] = '0';
                        }
                }
                System.out.println("赋值长度为： "+field.length+" 位");
                System.out.println("处理后64位域号值："+String.valueOf(field));
                fieldnum = bintohex64(field);
                return fieldnum;
        }
        
        public String FieldNum128(){
                System.out.println("开始为 128 位报文域赋值二进制位");
                String fieldnum = new String();
                char field[] = new char[128];
                String num;
                field[0] = '1';
                System.out.println("赋值域号： 1");
                for(int i = 1;i < 128; i ++){
                        num = String.valueOf(i+1);
                        System.out.println("赋值域号： "+num);
                        if(inputField.containsKey(num)){
                                field[i] = '1';
                        }else{
                                field[i] = '0';
                        }
                }
                System.out.println("赋值长度为： " + field.length + " 位");
                System.out.println("处理后128位域号值："+String.valueOf(field));
                fieldnum = bintohex128(field);
                return fieldnum;
        }
        
        private String bintohex64(char[] bin){
                String hex = new String();
                
                String field16 = new String();
                char field4[] = new char[4];
                char field64[] = bin;
                
                int i,j=0;
                
                for(i = 0;i < 64;i ++){
                        if(j<4){
                                field4[j] = field64[i];
                                System.out.println(field4[j]);
                                j++;
                        }else{
                                System.out.print("截取4位进行转换： ");
                                j = 0;
                                i--;
                                System.out.println(String.valueOf(field4));
                                hex = (String)hexmap.get(String.valueOf(field4));
                                System.out.println("转换16进制位： "+hex);
                                field16 = field16 + hex;
                        }
                }
                System.out.print("截取4位进行转换： ");
                System.out.println(String.valueOf(field4));
                hex = (String)hexmap.get(String.valueOf(field4));
                System.out.println("转换16进制位："+hex);
                field16 = field16 + hex;
                
                System.out.println("--------------------------------------------");
                System.out.println("域号16进制数："+field16);
                System.out.println("域长总位数："+field16.length());
                System.out.println("************************************************");
                System.out.println("************************************************");
                return field16;
        }
        
        private String bintohex128(char[] bin){
                String hex = new String();
                
                String field32 = new String();
                char field4[] = new char[4];
                char field128[] = bin;
                
                int i,j=0;
                
                for(i = 0;i < 128;i ++){
                        if(j<4){
                                field4[j] = field128[i];
                                System.out.println(field4[j]);
                                j++;
                        }else{
                                System.out.print("截取4位进行转换");
                                j = 0;
                                i--;
                                System.out.println(String.valueOf(field4));
                                hex = hexmap.get(String.valueOf(field4)).toString();
                                System.out.println("转换16进制位："+hex);
                                field32 = field32 + hex;
                        }
                }
                hex = hexmap.get(String.valueOf(field4)).toString();
                System.out.println(hex);
                field32 = field32 + hex;
                
                System.out.println("--------------------------------------------");
                System.out.println("域号16进制数："+field32);
                System.out.println("域长总位数："+field32.length());
                System.out.println("************************************************");
                System.out.println("************************************************");
                return field32;
        }
        
        public static void main(String[] args){
        	
                ProduceTradeMessage ptm = new ProduceTradeMessage();

                String tm;
                
                tm = ptm.tradeMessage64();
                System.out.println();
                System.out.println("报文总长度:"+tm.length());
                System.out.println("报文字符流:" + tm);
                System.out.println("报文结束");
                
                System.out.println("==============================================");
                
                tm = ptm.tradeMessage128();
                System.out.println();
                System.out.println("报文总长度:"+tm.length());
                System.out.println("报文字符流:" + tm);
                System.out.println("报文结束");
        }
        
}