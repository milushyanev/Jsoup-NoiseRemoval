import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;

public class NoiseRemoval {
	
	public static int getFilesCount(File file) {
		  File[] files = file.listFiles();
		  int count = 0;
		  for (File f : files)
		    if (f.isDirectory())
		      count += getFilesCount(f);
		    else
		      count++;
		  return count;
		}	
	public static List<String> getList(String path) {

		List<String> results = new ArrayList<String>();
		File[] files = new File(path).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		return results;
	}
	private static void finalNoiseRemSave(String file, List<String> arrData)
            throws IOException {
        FileWriter writer = new FileWriter(file + ".txt");
        int size = arrData.size();
        for (int i=0;i<size;i++) {
            String str = arrData.get(i).toString();
            writer.write(str);
            if(i < size-1)//This prevent creating a blank like at the end of the file**
                writer.write("\n");
        }
        writer.close();
    }

	public static void main(String[] args) throws IOException {
		//put the path of the file folder where the crawler downloads are located
		String path="src/htmlDownloads/";
		List<String> finalNoiceRemList = new ArrayList<String>();
		String saveRes = "afterNoiseRedResult";
		File fileCount = new File(path);
		for(int i=0;i<getFilesCount(fileCount);i++) {
		File input = new File(path+getList(path).get(i));	
		Document doc = Jsoup.parse(input, "UTF-8", "test");
		String title = doc.title();
		//System.out.println(title);
		Elements myEs = doc.body().select("body p, body h1, body h2, body h3, body h4, body h5, body h6");
		for (Element e: myEs) {
			finalNoiceRemList.add(e.text());
			//println(e.text());
			}
			finalNoiceRemList.add("\nEnd - HTML "+(i+1)+"\n");
			//println("\n");
			
		}
		finalNoiseRemSave(saveRes,finalNoiceRemList);
		//System.out.println(ls);	
	}
}
