package resource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tomcat.util.codec.binary.Base64;

import beans.ProductBean;

public class ConvertStringToImage {

       
//    private static String convertImageToString(){
//        
//        InputStream inputStream = null;
//        
//        String imageString = null;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try {
//
//            inputStream = new FileInputStream("resources"
//                    + "\\Spring.png");
//
//            byte[] buffer = new byte[1024];
//            baos = new ByteArrayOutputStream();
//
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                baos.write(buffer, 0, bytesRead);
//            }
//
//            byte[] imageBytes = baos.toByteArray();
//
//            imageString = Base64.encodeBase64String(imageBytes);
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally{
//            try {
//                baos.close();
//                inputStream.close();                
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            
//        }
//        return imageString;
//    }
//    
    public String convertStringToImageByteArray(ProductBean productBean)
    {
        String directory="S:/Programming/JAVA/WS/MobileProBackend/src/main/resource/static/"+ productBean.getCategoryName()+"_"+productBean.getProductName()+".png";
        OutputStream outputStream = null;
        byte [] imageInByteArray = Base64.decodeBase64(
        		productBean.getImageString());
        try {
            outputStream = new FileOutputStream(directory);
            outputStream.write(imageInByteArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return productBean.getCategoryName()+"_"+productBean.getProductName()+".png";
        
    }


}