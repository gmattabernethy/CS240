
public class Image {
	int width;
	int height;
	int numOfPixels;
	Pixel[][] pixels;

	Image(int width, int height){
		this.width = width;
		this.height = height;
		this.numOfPixels = 0;
		this.pixels = new Pixel[height][width];
	}

	public void addPixel(Pixel p) {
		int h = this.numOfPixels/this.width;
		int w = this.numOfPixels%this.width;
		pixels[h][w] = p;
		this.numOfPixels++;
	}

	public void invert() {
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				this.pixels[i][j].invert();
			}
		}
	}

	public void grayscale() {
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				this.pixels[i][j].grayscale();
			}
		}
	}

	public void emboss() {
		for(int i = this.height-1; i >= 0; i--) {
			for(int j = this.width-1; j >= 0; j--) {
				int maxDiff = 0;
				Pixel p = this.pixels[i][j];
				int gray;

				//Be sure to account for the situation where r-1 or c-1 is less than 0. gray should be 128 in this case

				if(i == 0 || j == 0) gray = 128;
				else {
					/*Calculate the differences between red, green, and blue values for the pixel and the
					pixel to its upper left.*/
					int redDiff = p.r-pixels[i-1][j-1].r;
					int greenDiff = p.g-pixels[i-1][j-1].g;
					int blueDiff = p.b-pixels[i-1][j-1].b;

					//Find the largest difference (positive or negative). We will call this maxDiff
					if(Math.abs(redDiff) > Math.abs(maxDiff)) maxDiff = redDiff;
					if(Math.abs(greenDiff) > Math.abs(maxDiff)) maxDiff = greenDiff;
					if(Math.abs(blueDiff) > Math.abs(maxDiff)) maxDiff = blueDiff;

					/*We then add 128 to maxDiff. If there are multiple equal differences with differing signs
					(e.g. -3 and 3), favor the red difference first, then green, then blue.*/
					gray = 128 + maxDiff;

					/*If needed, we then scale gray to be between 0 and 255*/
					if(gray < 0) gray = 0;
					else if(gray > 255) gray = 255;
				}

				//The pixels red, green, and blue values are all set to v.
				p.r = gray;
				p.g = gray;
				p.b = gray;

			}
		}
	}

	public void blur(int blurNum) {
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				Pixel p = this.pixels[i][j];
				int avgR = 0;
				int avgG = 0;
				int avgB = 0;
				int numOfPixels = blurNum;

				for(int n = 0; n < blurNum; n++) {
					if(j+n >= this.width) {
						numOfPixels = n;
						break;
					}

					avgR += this.pixels[i][j+n].r;
					avgG += this.pixels[i][j+n].g;
					avgB += this.pixels[i][j+n].b;
				}

				p.r = avgR/numOfPixels;
				p.g = avgG/numOfPixels;
				p.b = avgB/numOfPixels;

			}
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("P3\n");
		builder.append(this.width + " " + this.height + "\n255\n");
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				builder.append(this.pixels[i][j].toString());
			}
		}
		return builder.toString();
	}

	/*public static void main(String args[]) { //TEST
		Pixel p1 = new Pixel(0, 240, 127);
		Pixel p2 = new Pixel(25, 230, 122);
		Pixel p3 = new Pixel(0, 0, 0);
		Pixel p4 = new Pixel(255, 255, 255);

		Pixel p5 = new Pixel(0, 240, 127);
		Pixel p6 = new Pixel(25, 230, 122);
		Pixel p7 = new Pixel(0, 0, 0);
		Pixel p8 = new Pixel(255, 255, 255);

		Image img1 = new Image(2,2);
		img1.pixels[0][0] = p1;
		img1.pixels[0][1] = p2;
		img1.pixels[1][0] = p3;
		img1.pixels[1][1] = p4;

		Image img2 = new Image(2,2);
		img2.pixels[0][0] = p5;
		img2.pixels[0][1] = p6;
		img2.pixels[1][0] = p7;
		img2.pixels[1][1] = p8;

		img1.emboss();
		img2.blur(2);
		System.out.println(img1.toString());
		System.out.println(img2.toString());

	}*/
}
