package com.sanshengshui.netty.utils;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

/**
 * @author fanghuaizheng
 * @Description:
 * @date 2020-02-10 14:25
 */
@UtilityClass
public class DigitalUtils {
    public static final byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }


    public static final String byte2hex(byte b[]) {
        if (b == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
    
    /**
     	* 对16进制 字符串两两异或校验
     * @param data
     * @return
     */
    public static String getXor(String data) {
		String x = data.substring(0,2);
				
		for (int i = 2; i < data.length()-1; i++) {
			x = xor(x, data.substring(i,i+2));
			i++;
		}
		if (x.length()==1) {
			x = "0"+x;
		}
		return x;
	}
	
	/**
	 * 两个16进制数进行异或计算
	 * @param strHex_X
	 * @param strHex_Y
	 * @return
	 */
    public static String xor(String strHex_X,String strHex_Y){ 
			//将x、y转成二进制形式 
			String anotherBinary=Integer.toBinaryString(Integer.valueOf(strHex_X,16)); 
			String thisBinary=Integer.toBinaryString(Integer.valueOf(strHex_Y,16)); 
			String result = ""; 
			//判断是否为8位二进制，否则左补零 
			if(anotherBinary.length() != 8){ 
			for (int i = anotherBinary.length(); i <8; i++) { 
					anotherBinary = "0"+anotherBinary; 
				} 
			} 
			if(thisBinary.length() != 8){ 
			for (int i = thisBinary.length(); i <8; i++) { 
					thisBinary = "0"+thisBinary; 
				} 
			} 
			//异或运算 
			for(int i=0;i<anotherBinary.length();i++){ 
			//如果相同位置数相同，则补0，否则补1 
					if(thisBinary.charAt(i)==anotherBinary.charAt(i)) 
						result+="0"; 
					else{ 
						result+="1"; 
					} 
				}
//			log.info("code",result);
			return Integer.toHexString(Integer.parseInt(result, 2)); 
		}
    
    
    
  //对象转16进制
  	public String objectToHex(Object a) {
  		return objectToHex(a,1);
  		
  	}
  	public String objectToHex(Object a,Boolean hasFailer) {
  		return objectToHex(a,1,hasFailer);
  		
  	}
  	
  	public String objectToHex(Object a,Integer size) {
  		return objectToHex(a,size,1,0,true);
  	}
  	
  	/**
  	 * 
  	 * @param a 原数值
  	 * @param size 所占字节
  	 * @param offset 偏移量
  	 * @return
  	 */
  	public String objectToHex(Object a,Integer size,Integer offset) {
  		return objectToHex(a,size,1,offset,true);
  	}
  	
  	public String objectToHex(Object a,Integer size,Boolean hasFailer) {
  		return objectToHex(a,size,1,0,hasFailer);
  	}
  	

  	/**
  	 * 
  	 * @param a 十进制数
  	 * @param size 所占字节
  	 * @param multiple 所乘倍数
  	 * @param offset 偏移量
  	 * 注意 如果数据是 256 255 直接返回 FF FE
  	 * @return
  	 */
  	public String objectToHex(Object a,Integer size,Integer multiple,Integer offset) {
  		return objectToHex(a,size,multiple,offset,true);
  	}
  	
  	/**
  	 * 
  	 * @param a 十进制数
  	 * @param size 所占字节
  	 * @param multiple 所乘倍数
  	 * @param failer 是否进行无效判断 默认进行
  	 * @param offset 偏移量
  	 * 注意 如果数据是 256 255 直接返回 FF FE
  	 * @return
  	 */
  	public String objectToHex(Object a,Integer size,Integer multiple,Integer offset,Boolean hasFailer) {
  		
  		
  		
  		if (a==null) {
  			return "";
  		}else if ((a instanceof Double)||a.toString().indexOf(".")>-1) {
  			Double b = 0D;
  			if (hasFailer) {//进行无效字符判断
  				//算出无效数字 根据数据所占位数来算
  				Long re1 = 0l;
  				Long re2 = 0l;
  				for (int i = 0; i < size*2; i++) {
  					re1 += 15*power(16, i);
  				}
  				re2 = re1-1;
  				b  = Double.valueOf(a.toString());
  				if (Long.compare(re1, b.longValue())!=0&&Long.compare(re2, b.longValue())!=0) {//有一个满足，不进行偏移计算
  					b  = (Double.valueOf(a.toString())+offset)*multiple;
  				}
  			}else {//不进行无效字符判断
  				b  = (Double.valueOf(a.toString())+offset)*multiple;
  			}
  			
  			Long c = b.longValue();
  			return StringUtils.leftPad(Long.toHexString(c), 2*size, '0');
  		}else {
  			Long c = 0l;
  			if (hasFailer) {//进行无效字符判断
  				//算出无效数字 根据数据所占位数来算
  				Long re1 = 0l;
  				Long re2 = 0l;
  				for (int i = 0; i < size*2; i++) {
  					re1 += 15*power(16, i);
  				}
  				re2 = re1-1;
  				c = Long.valueOf(a.toString());
  				if (Long.compare(re1, c)!=0&&Long.compare(re2, c)!=0) {
  					c = (Long.valueOf(a.toString())+offset)*multiple;
  				}

  			}else {
  				 c = (Long.valueOf(a.toString())+offset)*multiple;
  			}
  			return StringUtils.leftPad(Long.toHexString(c), 2*size, '0');
  		}	
  		
  	}
  	
  	/**
  	 * 幂运算
  	 * @param a
  	 * @param b
  	 * @return
  	 */
  	private  Long power(int a , int b) {
  	    Long power = 1L;
  	    for (int c = 0; c < b; c++)
  	      power *= a;
  	    return power;
  	  }
   
  	
  	public static void main(String[] args) {
  		
//  		objectToHex("0.0");
  		
//  		String s1 = getXor("232301fe4c4a3145454b5250314a4e4557343636330400402873C1DCF32C7C562115BC99131CBF9FB1F4A1224E82AEBE2374553275827A2F534E9D7B8762B004A3C2F758D9C3EB7CFFC29136682DA4A3E21753CEC52391C2");
  		
  		
//  		System.out.println(s1);
  		
  		String re = objectToHex(0, 2, 1, 20000, true);
  		
  		System.out.println(re);
  		
  		
  	}
  	
  	
  	
}
