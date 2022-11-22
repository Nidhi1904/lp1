
public class MNTEntry {
	String name;
	int pp,kp,mdtp,kpdtp;
	MNTEntry(String name, int pp, int kp, int mdtp, int kpdtp){
		this.name = name;
		this.pp = pp;
		this.kp = kp;
		this.mdtp = mdtp;
		this.kpdtp = kpdtp;
	}
	
	String getname() {
		return name;
	}
	int getpp() {
		return pp;
	}
	int getkp() {
		return kp;
	}
	int getmdtp() {
		return mdtp;
	}
	int getkpdtp() {
		return kpdtp;
	}

}
