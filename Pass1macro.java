import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Iterator;
public class Pass1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader input = new BufferedReader(new FileReader("/E:/macro/Macro/input.txt"));
		
		FileWriter mnt = new FileWriter("mnt.txt");//macro name, pp, kp, mdtp, kpdtp
		FileWriter mdt = new FileWriter("mdt.txt");// macro definiton excluding macro name vala line and including mend
		FileWriter pntab = new FileWriter("pntab.txt");//paramter names
		FileWriter kpdtab = new FileWriter("kpdtab.txt");//keyword paramter default vle table
		FileWriter ic = new FileWriter("ic.txt");//start ke baad ka
		
		LinkedHashMap<String, Integer> pntb = new LinkedHashMap<>();//variable is put in this, assigns value to 
		
		int pp = 0, kp=0 , mdtp = 1, kpdtp = 0, macrop = 1, flag = 0;
		
		String line;
		String macroname = null;
		
		while((line = input.readLine()) != null)
		{
			String parts[] = line.split("\\s+");//space ke idhar split
			if(parts[0].equals("MACRO"))
			{
				line = input.readLine();
				flag = 1;
				parts = line.split("\\s+");
				macroname = parts[0];
				if(parts.length<=1)
				{
					mnt.write(macroname+"\t"+pp+"\t"+kp+"\t"+mdtp+"\t"+((kpdtp==0)?kpdtp:(kpdtp+1))+"\n");
					continue;
				}
				
				for(int i = 1;i<parts.length;i++) {
					parts[i] = parts[i].replaceAll("[&,]", "");
					if(parts[i].contains("=")) {
						kp++;
						String keyp[] = parts[i].split("=");
						pntb.put(keyp[0], macrop++);
						if(keyp.length!=2) {
							kpdtab.write(keyp[0]+"\t-\n");
						}
						else {
							kpdtab.write(keyp[0]+"\t"+keyp[1]+"\n");
							
						}
					}
					else {
						pntb.put(parts[i],macrop++);
						pp++;
					}
				}
				
				mnt.write(macroname+"\t"+pp+"\t"+kp+"\t"+mdtp+"\t"+((kp==0)?kpdtp:(kpdtp+1))+"\n");
				kpdtp+=kp;
			}
			else if(parts[0].equals("MEND")) {
				mdt.write(parts[0]);
				flag = 0;
				kp = 0;
				pp = 0;
				macrop = 1;
				mdtp++;
				pntab.write(macroname+":\t");
				Iterator<String> it = pntb.keySet().iterator();
				while(it.hasNext()) {
					pntab.write(it.next()+"\t");
				}
				pntab.write("\n");
				pntb.clear();
				mdt.write("\n");
			}
			else if(flag == 1) {
				for(int i = 0;i<parts.length;i++) {
					if(parts[i].contains("&")) {
						parts[i] = parts[i].replaceAll("[&,]","");
						mdt.write("(p,"+pntb.get(parts[i])+")"+"\t");
					}
					else {
						mdt.write(parts[i]+"\t");
					}
				}
				mdt.write("\n");
				mdtp++;
			}
			else {
				ic.write(line+"\n");
			}
		}
		
		mnt.close();
		mdt.close();
		pntab.close();
		kpdtab.close();
		ic.close();
		pntb.clear();
		input.close();
		System.out.println("MACRO PASS 1 PROCESSSING DONE");
		
	}
}
