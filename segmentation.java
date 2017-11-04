package segmentation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Segmentation{
        static BufferedImage img=null;
        static File f=null;
        
        static ArrayList<Integer> yCoordinates=new ArrayList();
        static ArrayList<Integer> xCoordinates=new ArrayList();
        
        static ArrayList<ArrayList<Integer>> yCoordinatesTwo=new ArrayList();
        static ArrayList<ArrayList<Integer>> xCoordinatesTwo=new ArrayList();
        
        static ArrayList<ArrayList<Integer>> yCoordinatesThree=new ArrayList();
        static ArrayList<ArrayList<Integer>> xCoordinatesThree=new ArrayList();
        
        static ArrayList<BufferedImage> ImagesOne= new ArrayList<BufferedImage>();
        static ArrayList<BufferedImage> ImagesTwo= new ArrayList<BufferedImage>();
        static ArrayList<BufferedImage> ImagesThree= new ArrayList<BufferedImage>();
        
        public static void main(String[] args)throws IOException{
            f=new File("D:\\Images\\Output0.jpg");
            img=ImageIO.read(f);
            
            for(int y=0; y<img.getHeight();y++){
                
                int x=0;
                while(x<img.getWidth()){
                    int rgb =img.getRGB(x,y);
                    int r = (rgb >> 16 ) & 0xff;
                    if(r<255){
                        yCoordinates.add(y);
                        break;
                    }
                        
                    x++;
                
                }
            }
            
            System.out.println(yCoordinates.toString());
            
            for(int i=0; i< yCoordinates.size()-2; i++){
            if(yCoordinates.get(i+1)-yCoordinates.get(i)==1 && yCoordinates.get(i+2)-yCoordinates.get(i+1)==1){
            xCoordinates.add(yCoordinates.get(i+1));
                }
            }
            System.out.println(yCoordinates.toString());
            System.out.println(xCoordinates.toString());
            
            yCoordinates.removeAll(xCoordinates);
            System.out.println(yCoordinates.toString());
            System.out.println(yCoordinates.size());
            
            for(int j=0; j<yCoordinates.size();j++){
                if(j%2==0){
                formImage(0,yCoordinates.get(j),img.getWidth(),yCoordinates.get(j+1)-yCoordinates.get(j));
                }
            }
            
            for(int i=0;i<ImagesOne.size();i++){
                BufferedImage Vertical=ImagesOne.get(i);
                yCoordinatesTwo.add(new ArrayList());
                for(int x=0; x<Vertical.getWidth();x++){
                
                int y=0;
                while(y<Vertical.getHeight()){
                    int rgb =Vertical.getRGB(x,y);
                    int r = (rgb >> 16 ) & 0xff;
                    if(r<150){
                                               
                        yCoordinatesTwo.get(i).add(x);
                        break;
                        
                    }
  
                    y++;
                }
            }
                
            }
            
            for(int t=0; t<ImagesOne.size();t++){
                
                xCoordinatesTwo.add(new ArrayList());
                for(int c=0; c< yCoordinatesTwo.get(t).size()-2; c++){
                    if(yCoordinatesTwo.get(t).get(c+1)-yCoordinatesTwo.get(t).get(c)==1 && yCoordinatesTwo.get(t).get(c+2)-yCoordinatesTwo.get(t).get(c+1)==1){
                    xCoordinatesTwo.get(t).add(yCoordinatesTwo.get(t).get(c+1));
                        
                    }
                }
            }
            System.out.println(yCoordinatesTwo);
            System.out.println(xCoordinatesTwo);
            
            
            for(int t=0; t<ImagesOne.size();t++){
                yCoordinatesTwo.get(t).removeAll(xCoordinatesTwo.get(t));
                
            }
            
            
            System.out.println(yCoordinatesTwo);
            for(int u=0; u<yCoordinatesTwo.size();u++){
                for(int j=0; j<yCoordinatesTwo.get(u).size()-1;j++){
                    if(j%2==0){
                    formImage2(yCoordinatesTwo.get(u).get(j),yCoordinates.get(2*u),yCoordinatesTwo.get(u).get(j+1)-yCoordinatesTwo.get(u).get(j)+1,ImagesOne.get(u).getHeight());
                        
                    }
                }
            }
           
            
            for(int i=0;i<ImagesTwo.size();i++){
                BufferedImage Horizontal=ImagesTwo.get(i);
                yCoordinatesThree.add(new ArrayList());
                for(int y=0; y<Horizontal.getHeight();y++){
                
                int x=0;
                while(x<Horizontal.getWidth()){
                    int rgb =Horizontal.getRGB(x,y);
                    int r = (rgb >> 16 ) & 0xff;
                    if(r<150){
                                               
                        yCoordinatesThree.get(i).add(y);
                        break;
                        
                    }
  
                    x++;
                }
            }
         }
            System.out.println(yCoordinatesThree.toString());
            for(int t=0; t<ImagesTwo.size();t++){
                
                xCoordinatesThree.add(new ArrayList());
                for(int p=0; p< yCoordinatesThree.get(t).size()-2; p++){
                    if(yCoordinatesThree.get(t).get(p+1)-yCoordinatesThree.get(t).get(p)==1 && yCoordinatesThree.get(t).get(p+2)-yCoordinatesThree.get(t).get(p+1)==1){
                    xCoordinatesThree.get(t).add(yCoordinatesThree.get(t).get(p+1));
                        
                    }
                }
            }
            
            for(int t=0; t<ImagesTwo.size();t++){
                yCoordinatesThree.get(t).removeAll(xCoordinatesThree.get(t));
                
            }
                System.out.println(xCoordinatesThree.toString());
                System.out.println(yCoordinatesThree.toString());
                
            
            for(int u=0; u<yCoordinatesThree.size();u++){
                    if(yCoordinatesThree.get(u).get(yCoordinatesThree.get(u).size()-1)-yCoordinatesThree.get(u).get(0)>0)
                    formImage3(0,yCoordinatesThree.get(u).get(0),ImagesTwo.get(u).getWidth(),yCoordinatesThree.get(u).get(yCoordinatesThree.get(u).size()-1)-yCoordinatesThree.get(u).get(0)+1,ImagesTwo.get(u));
                       // System.out.println(yCoordinatesThree.get(u).get(yCoordinatesThree.get(u).size()-1)-yCoordinatesThree.get(u).get(0));
                    
                
            }
            
            outputImage();
            
            
        }
        
        public static void formImage(int x, int y, int width,int height ){
            
            BufferedImage subImage=img.getSubimage(x, y, width, height);
            ImagesOne.add(subImage);
            
            
        }
        
        public static void formImage2(int x, int y, int width,int height ){
            
            BufferedImage subImage=img.getSubimage(x, y, width, height);
            ImagesTwo.add(subImage);
            
        }
        
        public static void formImage3(int x, int y, int width,int height,BufferedImage io ){
            
            BufferedImage subImage=io.getSubimage(x, y, width, height);
            ImagesThree.add(subImage);
            
            
        }
        
        public static void outputImage(){
            BufferedImage a=ImagesThree.get(30);
            System.out.println("s");
             try{
                        f=new File("D:\\Images\\Output57.jpg");
                        ImageIO.write(a, "jpg", f);
             
                    }catch(IOException e){
                        System.out.println("Error"+e);
                    }
        
        }
         
            
         
        }
        
