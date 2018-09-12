
public class Pixel {
	int r;
	int g;
	int b;
	
	Pixel(int r,int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	void invert() {
		int maxRGB = 255; //max value on RGB scale
		this.r = maxRGB - this.r;
		this.g = maxRGB - this.g;
		this.b = maxRGB - this.b;
	}
	
	void grayscale(){
		int gray = (this.r + this.b + this.g)/3;
		
		this.r = gray;
		this.g = gray;
		this.b = gray;
	}
	
	public String toString() {
		return this.r + "\n" + this.g + "\n" + this.b + "\n";
	}
	
	/*public static void main(String args[]) { //TEST
		Pixel p1 = new Pixel(0, 240, 127);
		Pixel p2 = new Pixel(25, 230, 122);
		p1.invert();
		p2.grayscale(); 
		System.out.println(p1.toString());
		System.out.println(p2.toString());
		
	}*/
}


