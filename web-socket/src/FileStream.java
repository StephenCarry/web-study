import java.io.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileStream {
    public static void main(String[] args) {
        String path = "C:\\Users\\Yaoo\\Desktop\\jdk-8u181-windows-x64.exe";
        try {
            Date start = new Date();
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            File zipFile = new File("C:\\Users\\Yaoo\\Desktop\\1.zip");
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            BufferedOutputStream bos = new BufferedOutputStream(zos);
            zos.putNextEntry(new ZipEntry("java1.8.exe"));
            int len;
            byte[] bytes = new byte[1024];
            while ((len=fis.read(bytes))!=-1) zos.write(bytes,0,len);
            Date end = new Date();
            System.out.println((end.getTime()-start.getTime())/1000.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
