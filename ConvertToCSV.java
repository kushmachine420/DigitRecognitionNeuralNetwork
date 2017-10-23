
package converttocsv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;


public class ConvertToCSV {
    private static BufferedImage img;
    private static File f;

    public static void main(String[] args)throws IOException{
        
        PrintWriter pw = new PrintWriter(new File("D:\\Images\\train.csv"));
        StringBuilder sb = new StringBuilder();
        
        String path="C:\\New folder\\handwrittenmathsymbols (1)\\data\\extracted_images";
        
        
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        
        File contentsOfFile;
        File[] contentNames;
        
        for(int i=0; i<listOfFiles.length ;i++){
            contentsOfFile=new File(path.concat("//"+listOfFiles[i].getName()));
            
            contentNames=contentsOfFile.listFiles();
            
            System.out.println(contentsOfFile.getName());
            for(int j=0; j<1000 && j<contentNames.length ;j++){
            try{
                System.out.println(j);
                f=new File(contentNames[j].getPath());
                System.out.println(contentNames[j].getName());
                img=ImageIO.read(f);
                }catch(IOException e){
                System.out.println("Error"+e);
                }
                
            for(int y = 0; y < img.getHeight(); y++){
                for(int x = 0; x < img.getWidth(); x++){
                    int rgb =img.getRGB(x,y);
                    
                    int r =255- ((rgb >> 16 ) & 0xff);
                    sb.append(""+r);
                    sb.append(",");
                    
                }
            }
            
            sb.append("/n");
            pw.write(sb.toString());
            
            }
        }
        
        pw.close();
       /* for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
       /* PrintWriter pw = new PrintWriter(new File("D:\\Images\\train.csv"));
        StringBuilder sb = new StringBuilder();
        
        try{
            f=new File("D:\\Images\\-_ (1).jpg");
            img=ImageIO.read(f);
            }catch(IOException e){
            System.out.println("Error"+e);
        }
        
        for(int y = 0; y < img.getHeight(); y++){
                for(int x = 0; x < img.getWidth(); x++){
                    int rgb =img.getRGB(x,y);
                    
                    int r = (rgb >> 16 ) & 0xff;
                    sb.append(""+r);
                    sb.append(",");
                    
                }
        }
        pw.write(sb.toString());
        pw.close();*/
        /*
        
        
       
        sb.append(',');
        
        sb.append('\n');

        
        System.out.println("done!");*/
    }
    
}
