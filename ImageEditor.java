import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class ImageEditor {
	Image img;
	
	public ImageEditor() {}
	
	void BuildImage(String HeightAndWidth) {
		int height = 0;
		int width = 0;
		
		for(int i = 0; i < HeightAndWidth.length(); i++) {
			if(Character.isWhitespace(HeightAndWidth.charAt(i))) {
				width = Integer.parseInt(HeightAndWidth.substring(0, i));
				height = Integer.parseInt(HeightAndWidth.substring(i+1, HeightAndWidth.length()));
			}
		}
		
		this.img = new Image(width, height);
	}
	
	Pixel BuildPixel(int r, int g, int b) {
		Pixel p = new Pixel(r, g, b);
		return p;
	}
		
	void importFile(String fileName) {
		try {
			File file = new File(fileName);
            Scanner in = new Scanner(file);
            String line = in.nextLine();
            String HeightAndWidth = "";
            int rgbCount = 0;
            int r = 0;
            int g = 0;
            int b = 0;
            
            while(in.hasNextLine()) { //grab height and width
            	line = in.nextLine();
            	if(!line.substring(0, 1).equals("#")) {
            		HeightAndWidth = line;
            		line = in.nextLine(); // jump to the max color value
            		break;
            	}
            }
            
            this.BuildImage(HeightAndWidth);
            
            while (in.hasNextLine()) {
                line = in.nextLine();
                
                if(line.substring(0, 1).equals("#")) continue;
                
                rgbCount++;
                if(rgbCount == 1) r = Integer.parseInt(line);
                else if(rgbCount == 2) g = Integer.parseInt(line);
                else if(rgbCount == 3) {
                	b = Integer.parseInt(line); 
                	this.img.addPixel(BuildPixel(r, g, b));                	
                	rgbCount = 0;
                }
            }
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	void exportFile(String fileName) {
		try {
			File file = new File(fileName);
			PrintWriter out;		
			out = new PrintWriter(file);
			out.print(this.img.toString());
			out.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		ImageEditor imgEd = new ImageEditor();
		imgEd.importFile(args[0]);
		if(args[2].equals("grayscale")) imgEd.img.grayscale();
		if(args[2].equals("invert")) imgEd.img.invert();
		if(args[2].equals("emboss")) imgEd.img.emboss();
		if(args[2].equals("motionblur")) imgEd.img.blur(Integer.parseInt(args[3]));
		imgEd.exportFile(args[1]);
	}
}
