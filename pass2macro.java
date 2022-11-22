import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;
public class pass {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader ic = new BufferedReader(new FileReader("/E:/macro/Macro/src/ic.txt"));
		BufferedReader kpdtb = new BufferedReader(new FileReader("/E:/macro/Macro/src/kpdtab.txt"));
		BufferedReader mdtb = new BufferedReader(new FileReader("/E:/macro/Macro/src/mdt.txt"));
		BufferedReader mntb = new BufferedReader(new FileReader("/E:/macro/Macro/src/mnt.txt"));
		
		FileWriter aptab = new FileWriter("apt.txt");
		FileWriter pass2 = new FileWriter("pass2.txt");
		
		HashMap<Integer,String> apt = new HashMap<>();
		HashMap<String,Integer> Inverseapt = new HashMap<>();
		HashMap<String,MNTEntry> mnt = new HashMap<>();
		
		Vector<String> mdt = new Vector();
		Vector<String> kpdt = new Vector();
		
		int pp,kp,mdtp,kpdtp,macrop;
		String line;
		
//		while((line = mdtb.readLine())!=null) {
//			mdt.addElement(line);
//		}
		while((line = kpdtb.readLine())!=null) {
			kpdt.addElement(line);
		}
		while((line = mntb.readLine())!=null) {
			String parts[] = line.split("\\s+");
			mnt.put(parts[0],new MNTEntry(parts[0],Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4])));
		}
		while((line = ic.readLine())!=null) {
			
			String parts[] = line.split("\\s+");
			if(mnt.containsKey(parts[0])) {
				pp = mnt.get(parts[0]).getpp();
				kp = mnt.get(parts[0]).getkp();
				kpdtp = mnt.get(parts[0]).getkpdtp();
				mdtp = mnt.get(parts[0]).getmdtp();
				macrop = 1;
				for(int i = 0;i<pp;i++) {
					parts[macrop] = parts[macrop].replaceAll(",", "");
					apt.put(macrop, parts[macrop]);
					Inverseapt.put(parts[macrop], macrop);
					macrop++;
				}
				int j = kpdtp-1;
				for(int i = 0;i<kp;i++) {
					String temp[] = kpdt.get(j).split("\\s+");
					apt.put(macrop, temp[1]);
					Inverseapt.put(temp[0], macrop);
					macrop++;
					j++;
				}
				
				for(int i = pp+1;i<parts.length;i++) {
					parts[i] = parts[i].replaceAll("[&,]", "");
					String temp[] = parts[i].split("=");
					apt.put(Inverseapt.get(temp[0]), temp[1]);
					
				}
				int i = mdtp-1;
				while((line = mdtb.readLine())!=null) {
					if(line.contains("MEND")){
						continue;
					}
					pass2.write("+");
					String temp[] = line.split("\\s+");
					for(int k = 0;k<temp.length;k++) {
						if(temp[k].contains("(p,")) {
							temp[k] = temp[k].replaceAll("[^0-9]","");
							pass2.write(apt.get(Integer.parseInt(temp[k]))+"\t");
						}
						else {
							pass2.write(temp[k]+"\t");
						}
					}
					pass2.write("\n");
				}
				
				Iterator<String> it = apt.values().iterator();
				while(it.hasNext()) {
					aptab.write(it.next()+"\t");
				}
				aptab.write("\n");
				apt.clear();
				Inverseapt.clear();
			}
			else {
				pass2.write(line+"\n");
			}
		}
		
		ic.close();
		kpdtb.close();
		mdtb.close();
		mntb.close();
		aptab.close();
		pass2.close();
		
		
	}

}
