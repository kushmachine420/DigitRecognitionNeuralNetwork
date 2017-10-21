
package javaapplication7;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static javaapplication7.Noise.meanFilter_RGB;
import javax.imageio.ImageIO;


public class JavaApplication7 {
    private static BufferedImage img=null;
    private static File f=null;
    
    private static BufferedImage imag=null;
    private static File g=null;
    
    
    public static void main(String[] args) {
        try{
        
            f=new File("D:\\Images\\h.jpg");
            img=ImageIO.read(f);
        
        }catch(IOException e){
        System.out.println("Error"+e);
        }
        
      autoThreshold(img);  
        
     
      
    
      
    }
    
    
     public static void autoThreshold(BufferedImage img){
         
        
        
        int thresholdValue = 255, iThreshold;
        
        
        int sum1, sum2, count1, count2;
        
        
        int mean1, mean2;
        int black=0;
        int white=255;
         
        /** calculating thresholdValue */
        while(true){
            sum1 = sum2 = count1 = count2 = 0;
            for(int y = 0; y < img.getHeight(); y++){
                for(int x = 0; x < img.getWidth(); x++){
                    int rgb =img.getRGB(x,y);
                    
                    int a = (rgb>>24)&0xff;
                    
                    int r = (rgb >> 16 ) & 0xff;
                    int g = (rgb >> 8 )& 0xff;
                    int b = (rgb >> 0) & 0xff;
                    
                    int avgOfRGB = (r+g+b)/3;
                    
                    if(avgOfRGB < thresholdValue){
                        sum1 += avgOfRGB;
                        count1++;
                    }else{
                        sum2 += avgOfRGB;
                        count2++;
                    }
                    
                }
            }
            /** calculating mean */
            mean1 = (count1 > 0)?(int)(sum1/count1):0;
            mean2 = (count2 > 0)?(int)(sum2/count2):0;
            
           
            /** calculating intermediate threshold */
            
            iThreshold = (mean1 + mean2)/2;
            
            if(thresholdValue > iThreshold){
                thresholdValue = iThreshold;
            }else{
                break;
            }
            System.out.println(thresholdValue);
        }
        
        /** performing thresholding on the image pixels */
        for(int y = 0; y < img.getHeight(); y++){
            for(int x = 0; x < img.getWidth(); x++){
                int rgb =img.getRGB(x,y);
                
                int a = (rgb>>24)&0xff;
                int r = (rgb >> 16 ) & 0xff;
                int g = (rgb >> 8 )& 0xff;
                int b = (rgb >> 0) & 0xff;
                int avgOfRGB = (r+g+b)/3;
                
                if(avgOfRGB >= thresholdValue){
                      rgb=(white<<24)| (white<<16) | (white<<8) | white;
                      img.setRGB(x, y, rgb);
                    //img.setPixel(x,y,255,255,255,255);  //set WHITE
                }else{
                     rgb=(black<<24)| (black<<16) | (black<<8) | black;
                     img.setRGB(x, y,rgb);
                    //img.setPixel(x,y,255,0,0,0);  //set BLACK                    
                }
                
                
                
            }
        }
        
                    
                    
                    
                    
                    try{
                        f=new File("D:\\Images\\Output2.jpg");
                        ImageIO.write(img, "jpg", f);
             
                    }catch(IOException e){
                        System.out.println("Error"+e);
                    }
                    
                    try{
                        g=new File("D:\\Images\\Output2.jpg");
                        imag=ImageIO.read(g);
             
                    }catch(IOException e){
                        System.out.println("Error"+e);
                    }
                    
                    meanFilter_RGB(imag, 3);
    }
    
}
