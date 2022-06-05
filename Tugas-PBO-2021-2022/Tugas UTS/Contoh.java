import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class VendorLampuA{
	public void setOn(){
		//menyalakan vendor lampu A
		System.out.println("VendorLampuA nyala");
	}
	
	public void setOff(){
		//mematikan vendor lampu A
		System.out.println("VendorLampuA mati");
	}
}

class VendorLampuB{
	public boolean turnOn(boolean isOn){
		//mematikan dan menyalakan lampu B
		if(isOn)
		System.out.println("VendorLampuB menyala");
		else System.out.println("VendorLampuB mati");
		
		return isOn;
	}
}

interface AdapterLampu{
	public void on();
	public void off();
}

class ControllerLampuA implements AdapterLampu{
	private String warna;
	public VendorLampuA lampuA;
	
	public ControllerLampuA(String warna){
		lampuA = new VendorLampuA();
		this.warna = warna;
	}
	
	public void on(){
		System.out.print(String.format("lampu warna %s- ",this.warna));
		lampuA.setOn();
	}
	
	public void off(){
		System.out.print(String.format("lampu warna %s- ",this.warna));
		lampuA.setOff();
	}
}

class ControllerLampuB implements AdapterLampu{
	private String warna;
	public VendorLampuB lampuB;
	
	public ControllerLampuB(String warna){
		lampuB = new VendorLampuB();
		this.warna = warna;
	}
	
	public void on(){
		System.out.print(String.format("lampu warna %s- ",this.warna));
		lampuB.turnOn(true);
	}
	
	public void off(){
		System.out.print(String.format("lampu warna %s- ",this.warna));
		lampuB.turnOn(false);
	}
}

class ManajerLampu{
	public List<AdapterLampu>daftarLampu;
	public int index_Lampu = 0;
	
	public ManajerLampu(){
		
		daftarLampu = new ArrayList();
	}
	
	public int getNextIndex(){
		return(index_Lampu + 1) % 3;
	}
	
	public int getPreviousIndex(){
		if(index_Lampu == 0)
		return 2;
		
		return index_Lampu - 1;
	}
	
	public void run(){
		AdapterLampu lampu_merah = new ControllerLampuA("Merah");
		AdapterLampu lampu_kuning = new ControllerLampuB("Kuning");
		AdapterLampu lampu_hijau = new ControllerLampuB("Hijau");
		
		daftarLampu.add(lampu_merah);
		daftarLampu.add(lampu_kuning);
		daftarLampu.add(lampu_hijau);
		
		while(true){
			daftarLampu.get(getPreviousIndex()).off();
			daftarLampu.get(index_Lampu).on();
			index_Lampu = getNextIndex();
			
			try{
				TimeUnit.SECONDS.sleep(5);
				System.out.println("delay 5 detik");
			}catch(Exception e){}
		}
	}
}
class Contoh{
	public static void main(String[]args){
		ManajerLampu manajer = new ManajerLampu();
		manajer.run();
	}
}

