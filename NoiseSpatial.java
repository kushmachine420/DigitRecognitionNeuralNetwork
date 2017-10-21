/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication7;

import java.awt.image.BufferedImage;

public class NoiseSpatial {
    public static void spatialFilter_RGB(BufferedImage img, int maskSize){
        
        /** 
         * This array will store the output of the spatial filter operation which will
         * be later written back to the original image pixels.
         */
        int outputPixels[] = new int[img.getHeight()*img.getWidth()];
        
        //image dimension
        int width = img.getWidth();
        int height = img.getHeight();
        
        /**
         * red, green and blue are a 2D square of odd size like 3x3, 5x5, 7x7, ...
         * For simplicity storing it into 1D array.
         */
        int red[], green[], blue[];
        
        /** spatial Filter operation */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                red = new int[maskSize * maskSize];
                green = new int [maskSize * maskSize];
                blue = new int [maskSize * maskSize];
                int count = 0;
                for(int r = y - (maskSize / 2); r <= y + (maskSize / 2); r++){
                    for(int c = x - (maskSize / 2); c <= x + (maskSize / 2); c++){
                        if(r < 0 || r >= height || c < 0 || c >= width){
                            /** Some portion of the mask is outside the image. */
                            continue;
                        }else if(x == c && y == r){
                            /** pixel below the center of the mask */
                            continue;
                        }else{
                            int rgb =img.getRGB(c,r);
                            
                            red[count]= (rgb >> 16 ) & 0xff;
                            green[count]= (rgb >> 8 )& 0xff;
                            blue[count]= (rgb >> 0) & 0xff;
                            count++;
                        }
                    }
                }
                
                /** sort red, green, blue array */
                java.util.Arrays.sort(red);
                java.util.Arrays.sort(green);
                java.util.Arrays.sort(blue);
                
                //RGB value of image pixel
                int rgb =img.getRGB(x,y);
                            
                int pixelRED = (rgb >> 16 ) & 0xff;
                int pixelGREEN = (rgb >> 8 )& 0xff;
                int pixelBLUE =  (rgb >> 0) & 0xff;
                
                //final RGB value
                int fRED, fGREEN, fBLUE;
                
                //compute final RGB value
                if(pixelRED > red[maskSize*maskSize - 1]){
                    fRED = red[maskSize*maskSize - 1];
                }else if(pixelRED < red[maskSize*maskSize - count]){
                    fRED = red[maskSize*maskSize - count];
                }else{
                    fRED = pixelRED;
                }
                
                if(pixelGREEN > green[maskSize*maskSize - 1]){
                    fGREEN = green[maskSize*maskSize - 1];
                }else if(pixelGREEN < green[maskSize*maskSize - count]){
                    fGREEN = green[maskSize*maskSize - count];
                }else{
                    fGREEN = pixelGREEN;
                }
                
                if(pixelBLUE > blue[maskSize*maskSize - 1]){
                    fBLUE = blue[maskSize*maskSize - 1];
                }else if(pixelBLUE < blue[maskSize*maskSize - count]){
                    fBLUE = blue[maskSize*maskSize - count];
                }else{
                    fBLUE = pixelBLUE;
                }
                
                /** save spatial value in outputPixels array */
                int p = getPixelValueFromARGBValue(255, fRED, fGREEN, fBLUE);
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
}
