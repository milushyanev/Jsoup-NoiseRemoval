
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;



public class NoiseRemoval {
	
	private static void println(Object line) {
		System.out.println(line);
	}
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
	
	public static void main(String[] args) throws IOException {
		//put the path of the file folder where the crawler downloads are located
		String path="src/htmlDownloads/";
		File testing = new File(path);
		for(int i=0;i<getFilesCount(testing);i++) {
		File input = new File(path+getList(path).get(i));	
		Document doc = Jsoup.parse(input, "UTF-8", "test");
		String title = doc.title();
		System.out.println(title);
		Elements myEs = doc.body().select("body p");
		for (Element e: myEs) {
					println(e.text());
			}
			println("\n");
		}
	}
}
