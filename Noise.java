/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication7;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Noise {
    private static BufferedImage img=null;
    private static File f=null;
    
    /**
     * This method is used to perform mean filtering on the image object passed.
     * 
     * @param img The image object passed on which mean filtering is performed.
     * @param maskSize - The size of the mask is an odd integer like 3, 5, 7 … etc.
     */
    public static void meanFilter_RGB(BufferedImage img, int maskSize){
        
        /** 
         * This array will store the output of the mean filter operation which will
         * be later written back to the original image pixels.
         */
        
        int outputPixels[] = new int[img.getWidth()*img.getHeight()];
        
        //image dimension
        int width = img.getWidth();
        int height = img.getHeight();
        
        //ARGB variable
        int alpha, red, green, blue;
        System.out.println("a");
        
        /** Mean Filter operation */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                alpha = 0;
                red = 0;
                green = 0;
                blue = 0;
                int count = 0;
                for(int r = y - (maskSize / 2); r <= y + (maskSize / 2); r++){
                    for(int c = x - (maskSize / 2); c <= x + (maskSize / 2); c++){
                        if(r < 0 || r >= height || c < 0 || c >= width){
                            /** Some portion of the mask is outside the image. */
                            continue;
                        }else{
                            int rgb =img.getRGB(c,r);
                
                            alpha += (rgb>>24)&0xff;
                            red += (rgb >> 16 ) & 0xff;
                            green += (rgb >> 8 )& 0xff;
                            blue += (rgb >> 0) & 0xff;
                            count++;
                        }
                    }
                }
                
                /** save mean value in outputPixels array */
                int p = getPixelValueFromARGBValue(alpha/count, red/count, green/count, blue/count);
                outputPixels[x+y*width] = p;
            }
        }
        
        /** Write the output pixels to the image pixels */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                img.setRGB(x, y, outputPixels[x+y*width]);
            }
        }
        
         try{
                        f=new File("D:\\Images\\Output3.jpg");
                        ImageIO.write(img, "jpg", f);
             
                    }catch(IOException e){
                        System.out.println("Error"+e);
                    }
        
       
    }
    
    /**
     * Common names: Mean Filtering, Smoothing, Averaging, Box Filtering.
     * This method is used to remove noise from image by averaging the neighboring
     * pixels under the mask. Note! details from the image is also removed in this
     * process. Hence bigger the mask size more the detail lost.
     * This method will fill the pixels outside the mask with 0 value.
     *
     * Mask 3x3
     *  |
     *  V         Image Pixels 3x5
     * +-+-+-+    |
     * |0|0|0|    V
     * +-+-+-+-+-+-+
     * |0|1|2|3|2|1|
     * +-+-+-+-+-+-+
     * |0|4|5|6|5|4|
     * +-+-+-+-+-+-+
     *   |7|8|9|8|7|
     *   +-+-+-+-+-+
     * 
     * @param img The image object passed on which mean filtering is performed.
     * @param maskSize - The size of the mask is an odd integer like 3, 5, 7 … etc.
     */
    public static void meanFilter_ZeroFill(BufferedImage img, int maskSize){
        
        /** 
         * This array will store the output of the mean filter operation which will
         * be later written back to the original image pixels.
         */
        int outputPixels[] = new int[img.getHeight()*img.getWidth()];
        
        //image dimension
        int width = img.getWidth();
        int height = img.getHeight();

        /**
         * Buff is a 2D square of odd size like 3x3, 5x5, 7x7, ...
         * For simplicity storing it into 1D array.
         */
        int buff[];
        
        /** Mean Filter operation */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                buff = new int[maskSize * maskSize];
                int i = 0;
                for(int r = y - (maskSize / 2); r <= y + (maskSize / 2); r++){
                    for(int c = x - (maskSize / 2); c <= x + (maskSize / 2); c++){
                        if(r < 0 || r >= height || c < 0 || c >= width){
                            /** Some portion of the mask is outside the image. */
                            buff[i] = 0;
                        }else{
                            buff[i] = img.getRGB(c,r);
                        }
                        i++;
                    }
                }
                
                /** Find mean of the buff array value */
                int sa=0, sr=0, sg=0, sb=0;
                for(i = 0; i < 9; i++){
                    sa += getAlphaValueFromPixelValue(buff[i]);
                    sr += getRedValueFromPixelValue(buff[i]);
                    sg += getGreenValueFromPixelValue(buff[i]);
                    sb += getBlueValueFromPixelValue(buff[i]);
                }
                
                /** Save pixel value to outputPixels array */
                int p = getPixelValueFromARGBValue(sa/9, sr/9, sg/9, sb/9);
                outputPixels[x+y*width] = p;
            }
        }
        /** Write the output pixels to the image pixels */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                img.setRGB(x, y, outputPixels[x+y*width]);
            }
        }
    }
    
    /**
     * Common names: Mean Filtering, Smoothing, Averaging, Box Filtering.
     * This method is used to remove noise from image by averaging the neighbouring
     * pixels under the mask. Note! details from the image is also removed in this
     * process. Hence bigger the mask size more the detail lost.
     * This method fill the pixels outside the mask with values near to the pixels.
     *
     * Mask 3x3
     *  |
     *  V         Image Pixels 3x5
     * +-+-+-+    |
     * |1|1|2|    V
     * +-+-+-+-+-+-+
     * |1|1|2|3|2|1|
     * +-+-+-+-+-+-+
     * |4|4|5|6|5|4|
     * +-+-+-+-+-+-+
     *   |7|8|9|8|7|
     *   +-+-+-+-+-+
     * 
     * @param img The image object passed on which mean filtering is performed.
     * @param maskSize - The size of the mask is an odd integer like 3, 5, 7 … etc.
     */
    public static void meanFilter_ValueFill(BufferedImage img, int maskSize){
        
        /** 
         * This array will store the output of the mean filter operation which will
         * be later written back to the original image pixels.
         */
        int outputPixels[] = new int[img.getWidth()*img.getHeight()];
        
        //image dimension
        int width = img.getWidth();
        int height = img.getHeight();

        /**
         * Buff is a 2D square of odd size like 3x3, 5x5, 7x7, ...
         * For simplicity storing it into 1D array.
         */
        int buff[];
        
        /** Mean Filter operation */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                buff = new int[maskSize * maskSize];
                int i = 0;
                for(int r = y - (maskSize / 2); r <= y + (maskSize / 2); r++){
                    for(int c = x - (maskSize / 2); c <= x + (maskSize / 2); c++){
                        if(r < 0 || r >= height || c < 0 || c >= width){
                            /** Some portion of the mask is outside the image. */
                            int tr = r, tc = c;
                            if(r < 0){
                                tr = r+1;
                            }else if(r == height){
                                tr = r-1;
                            }
                            if(c < 0){
                                tc = c+1;
                            }else if(c == width){
                                tc = c-1;
                            }
                            buff[i] = img.getRGB(tc, tr);
                        }else{
                            buff[i] = img.getRGB(c, r);
                        }
                        i++;
                    }
                }
                
                /** Find mean of the buff array value */
                int sa=0, sr=0, sg=0, sb=0;
                for(i = 0; i < 9; i++){
                    sa += getAlphaValueFromPixelValue(buff[i]);
                    sr += getRedValueFromPixelValue(buff[i]);
                    sg += getGreenValueFromPixelValue(buff[i]);
                    sb += getBlueValueFromPixelValue(buff[i]);
                }
                
                /** Save pixel value to outputPixels array */
                int p = getPixelValueFromARGBValue(sa/9, sr/9, sg/9, sb/9);
                outputPixels[x+y*width] = p;
            }
        }
        /** Write the output pixels to the image pixels */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                img.setRGB(x, y, outputPixels[x+y*width]);
            }
        }
    }
    
    public static int getPixelValueFromARGBValue(int a, int r, int g, int b){
        return (a<<24) | (r<<16) | (g<<8) | b;
    }
    
    public static int getAlphaValueFromPixelValue(int pixelVal){
        return (pixelVal>>24) & 0xFF;
    }
    
    public static int getRedValueFromPixelValue(int pixelVal){
        return (pixelVal>>16) & 0xFF;
    }
    
    public static int getGreenValueFromPixelValue(int pixelVal){
        return (pixelVal>>8) & 0xFF;
    }
    
    public static int getBlueValueFromPixelValue(int pixelVal){
        return pixelVal & 0xFF;
    }
    public static void outputFile(){
        
        try{
                        f=new File("D:\\Images\\Output3.jpg");
                        ImageIO.write(img, "jpg", f);
             
                    }catch(IOException e){
                        System.out.println("Error"+e);
                    }
    }
}
    

