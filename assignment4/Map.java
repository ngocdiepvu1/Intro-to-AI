import java.io.BufferedReader;
//import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.util.Scanner;
import java.util.Vector;

public class Map {
	Vector<Border> borders;
	Vector<String> states;
	
	public Map() {
		borders = new Vector<Border>();
		states = new Vector<String>();
	}

	public void readFromFile(String filename) throws IOException{
		//File file = new File(filename);
		//Scanner scan = new Scanner(file);
		BufferedReader br = new BufferedReader(new FileReader(filename));
		//String line = scan.nextLine();
		String line = null;

		// Read the file once to get all the state in alphabetical order
		while ((line = br.readLine()) != null) {
			String values[] = line.split(",");
			states.add(values[0]);
		}
		br.close();

		// Read again to tget the borders
		BufferedReader br1 = new BufferedReader(new FileReader(filename));
		while ((line = br1.readLine()) != null) {
			String values[] = line.split(",");
			for (int i = 1; i < values.length; i++) {
				//System.out.println(values[0]);
				Border border1 = new Border( states.indexOf(values[0]), states.indexOf(values[i]) );
				
				boolean containsDup = false;

				for (int k = 0; k < borders.size(); k++) {
					if (borders.get(k).index1 == border1.index2 && borders.get(k).index2 == border1.index1)
						containsDup = true;
				}

				if (!containsDup) {
					borders.add(border1);
					//System.out.println(border1.index1 + " " + border1.index2);
				}
			}
		}

		//scan.close();
		System.out.println("Total borders: " + borders.size());
		br1.close();
	  }
}
