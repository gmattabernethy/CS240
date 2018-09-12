import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.regex.Pattern;

public class ImageEditor {
	Image img;

	public ImageEditor() {}

	Pixel BuildPixel(int r, int g, int b) {
		Pixel p = new Pixel(r, g, b);
		return p;
	}

	void importFile(String fileName) {
		try {
			File file = new File(fileName);
            Scanner in = new Scanner(file);
            String str = in.next();
						int width = 0;
						int height = 0;

						while (in.hasNext()) {
							str = in.next();
							if(str.substring(0, 1).equals("#")){
								str = in.nextLine();
								continue;
							}
							if(width == 0) width = Integer.parseInt(str);
							else{
								height = Integer.parseInt(str);
								break;
							}
						}

						this.img = new Image(width, height);

						int rgbCount = 0;
						int r = 0;
						int g = 0;
						int b = 0;

					  str = in.next();

            while (in.hasNext()) {
                str = in.next();

								if(str.substring(0, 1).equals("#")){
									str = in.nextLine();
									continue;
								}
								rgbCount++;

                if(rgbCount == 1) r = Integer.parseInt(str);
                else if(rgbCount == 2) g = Integer.parseInt(str);
                else if(rgbCount == 3) {
                	b = Integer.parseInt(str);
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
