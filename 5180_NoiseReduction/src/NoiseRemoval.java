import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;

public class NoiseRemoval {
	public static void println(Object line) {
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
		List<String> finalNoiseRemList = new ArrayList<String>();
		String saveRes = "afterNoiseRedResult";
		File fileCount = new File(path);
		List<String> removeF= new LinkedList <String>();
		List<String> rmvAddFtr = new LinkedList <String>();
		List<String> indexes= new LinkedList<String>();
		//removeF.add("Avenue");
		removeF.add("©");
		removeF.add("All Rights Reserved");
		removeF.add("Contact Us");
		removeF.add("Copyright");
		rmvAddFtr.add("Avenue");
		rmvAddFtr.add("Blvd.");
		rmvAddFtr.add("Boulevard");
		rmvAddFtr.add("Street");
		rmvAddFtr.add("Ave.");
		rmvAddFtr.add("St.");
		rmvAddFtr.add("Circle");
		rmvAddFtr.add("Cir.");
		for(int i=0;i<getFilesCount(fileCount);i++) {
		File input = new File(path+getList(path).get(i));	
		Document doc = Jsoup.parse(input, "UTF-8", "test");
		String title = doc.title();
		finalNoiseRemList.add("Title -> "+title);
		Elements myEs = doc.select("p, h1,  h2, h3, h4, h5, h6, h1 class");
		for (Element e: myEs) {
			finalNoiseRemList.add(e.text());
			}
			finalNoiseRemList.add("\nEnd - HTML "+(i+1)+"\n");
		}
		for(int i=0;i<finalNoiseRemList.size();i++) {
			for(int j=0;j<removeF.size();j++) {
			if(finalNoiseRemList.get(i).contains(removeF.get(j))==true ) {
				finalNoiseRemList.remove(i);
				}
			}
		}
		for (int i=0; i<finalNoiseRemList.size();i++) {
			if(finalNoiseRemList.get(i).contains("End - HTML")==true) {
				indexes.add(Integer.toString(i));
				}
			for(int ind=0;ind<indexes.size();ind++) {
				for(int j=0;j<rmvAddFtr.size();j++) {
					if(finalNoiseRemList.get((Integer.parseInt(indexes.get(ind))-1)).contains(rmvAddFtr.get(j))==true){
						finalNoiseRemList.remove((Integer.parseInt(indexes.get(ind))-1));
						}
					}
				}
			}
		finalNoiseRemSave(saveRes,finalNoiseRemList);
	}
}
