

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;


class Mi{
	private int i;
	private int j;
	private double omean;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Mi(int i,int j){
		this.i = i;
		this.j = j;
	}
	public Mi(int i,int j,double omean){
		this.i = i;
		this.j = j;
		this.omean = omean;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	public double getOmean() {
		return omean;
	}
	public void setOmean(double omean) {
		this.omean = omean;
	}
	@Override
	public String toString() {
		return "Mi [i=" + i + ", j=" + j + ", omean=" + omean + ", name="
				+ name + "]";
	}
}


public class BeeOutlierDetectionImprove {
	
	static int SN=20; /* The number of colony size (employed bees+onlooker bees)*/
	static int FoodNumber = SN/2; /*The number of food sources equals the half of the colony size*/
	int limit = 5; 
	int D = 3;
	double Temp = 1000;
	static Mi [] mis = new Mi[FoodNumber];
	static int []trail = new int[FoodNumber];
	private static String nsiz= "10";
	static{
		String fileName = "F:\\daoxiangmu\\data\\"+nsiz+"W.txt";
		int[] int_ = new int[]{0,1,2};
		int intNum=3;
		try {
			BeeColony.Readfile(fileName, int_, intNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static ArrayList<MIFoCount> mIFoCounts = BeeColony.mIFoCounts;
	double Foods[][]=new double[mIFoCounts.size()][D];
	static double [] fitness = new double[FoodNumber];
	public double [][] getFoods(){
		return Foods;
	}
	
	public void beforLead(){
		int[] int_ = new int[]{0,1,2};
		for(int i =0;i<FoodNumber;i++){
			Mi mi = ranSel(int_);
			double o_mea = calO_measure(mi.getI(), mi.getJ(), int_);
			mi.setOmean(o_mea);
			fitness[i] = o_mea;
			mis[i] = mi;
		}
		
	}
	
	public void OnLook(){
		Random random = new Random();
		double nera = random.nextInt();
		int t =0,ind = 0;
		int [] int_ = new int[]{0,1,2};
		while(t<FoodNumber){
			ind++;
			if(nera<prob[t]){
				t++;
				if(t==FoodNumber){
					break;
				}
//				int v = ind;
//				MIFoCount mInfo= mIFoCounts.get(v-1>=0?v-1:0);
//				MIFoCount mInfo1= mIFoCounts.get(v+1<=mIFoCounts.size()?v+1:mIFoCounts.size());
				Mi mi = mis[t];
				int oldi = mi.getI();
				int oldj = mi.getJ();
				
//				int neI= (int)(oldi+Math.random()*(random.nextInt(mIFoCounts.size())-oldi));
//				int neJ= (int)(oldj+Math.random()*(random.nextInt(D)-oldj));
				int neI,neJ ;
				if(Math.random()>0.5){
				 neI = oldi-1>=0?oldi-1:0;
				 neJ = oldj-1>=0?oldj-1:0;
				}else{
					neI = oldi+1<mIFoCounts.size()?oldi+1:mIFoCounts.size()-1;
					neJ = oldj+1<D?oldj+1:D-1;
				}
				
//				System.out.println(oldi+" "+oldj+"  "+neI+"  "+neJ);
				double oldmea = mi.getOmean();
				double o_mea = calO_measure(mi.getI(), mi.getJ(), int_);
				double val = Math.exp((oldmea-o_mea)/Temp);
				if(val>Math.random()){
					mi.setI(neI);
					mi.setJ(neJ);
					mi.setName(mIFoCounts.get(neI).getAr().get(neJ));
					fitness[t] = o_mea;
					mi.setOmean(o_mea);
				}else if(o_mea<oldmea){   //
					mi.setI(neI);
					mi.setJ(neJ);
					mi.setName(mIFoCounts.get(neI).getAr().get(neJ));
					fitness[t] = o_mea;
					mi.setOmean(o_mea);
					
				}else{
					trail[t] = trail[t]+1;
				}
				mis[t] = mi;
				
			}
			nera = random.nextInt();
			if(t==FoodNumber){
				t=0;
			}
		}
	}
	
	static double [] prob = new double[FoodNumber]; 
	static void CalculateProbabilities(){
	     int i;
	     double maxfit;
	     maxfit=fitness[0];
		 for (i=1;i<FoodNumber;i++){
		      if (fitness[i]>maxfit){
		           maxfit=fitness[i];
		      }
		 }
		 for (i=0;i<FoodNumber;i++){
		       prob[i]=(0.9*(fitness[i]/maxfit))+0.1;
		 }
	}
	public void leader(){
		
		int[] int_ = new int[]{0,1,2};
		Random random = new Random();
		for(int i =0;i<FoodNumber;i++){
			num_eval++;
			if(num_eval==MFE){
				System.exit(0);
			}
			Mi mi = mis[i];
			int oldi = mi.getI();
			int oldj = mi.getJ();
			
//			int neI= (int)(oldi+Math.random()*(random.nextInt(mIFoCounts.size())-oldi));
//			int neJ= (int)(oldj+Math.random()*(random.nextInt(D)-oldj));
			int neI,neJ ;
			if(Math.random()>0.5){
			 neI = oldi-1>=0?oldi-1:0;
			 neJ = oldj-1>=0?oldj-1:0;
			}else{
				neI = oldi+1<mIFoCounts.size()?oldi+1:mIFoCounts.size()-1;
				neJ = oldj+1<D?oldj+1:D-1;
			}
			double CR = Math.random();
			if(CR>0.5){
				neI = oldi+2<mIFoCounts.size()?oldi+2:mIFoCounts.size()-2;
				neJ = oldj+2<D?oldj+2:D-2;
			}
			double oldmea = mi.getOmean();
			double o_mea = calO_measure(mi.getI(), mi.getJ(), int_);
			double val = Math.exp((oldmea-o_mea)/Temp);
			if(val>Math.random()){
				mi.setI(neI);
				mi.setJ(neJ);
				mi.setName(mIFoCounts.get(neI).getAr().get(neJ));
				fitness[i] = o_mea;
				mi.setOmean(o_mea);
			}else if(o_mea<oldmea){   //
				mi.setI(neI);
				mi.setJ(neJ);
				mi.setName(mIFoCounts.get(neI).getAr().get(neJ));
				fitness[i] = o_mea;
				mi.setOmean(o_mea);
//				mi.setOmean(o_mea);
			}else{
				trail[i] = trail[i]+1;
			}
			mis[i] = mi;
		}
		
	}
	static Mi ranSel(int [] int_){
		Random random = new Random();
		ArrayList<Mi> arrayList = new ArrayList<>();
		Mi mi =  null;
		for(int t=0;t<FoodNumber;t++){
			int sel = random.nextInt(mIFoCounts.size());
			MIFoCount mInfo = mIFoCounts.get(sel);
//			sel = random.nextInt(mIFoCounts.size());
			int j = random.nextInt(int_.length);
			mi = new Mi(sel,j);
			mi.setName(mIFoCounts.get(sel).getAr().get(j));
		}
		return mi;
	}
	Mi neSel(Mi mi,int [] int_){
		
		return  mi;
	}
	
	/*
	 * @param i
	 * @param j
	 * @param int_
	 * @return
	 */
	public double calO_measure(int i,int j,int []int_){
		double d = 0;
		double nu = 0;
		ArrayList<Double> allsupnums = new ArrayList<Double>();
		MIFoCount mInfo = mIFoCounts.get(i);
//		System.out.println("���Ϊ    "+mInfo);
		
		for(int o =0;o<int_.length;o++){
			nu=0;
			for(MIFoCount alMinfo:mIFoCounts){
				if(alMinfo.isNeMin(mInfo, o)){
					nu+=alMinfo.getCount();
				}
			}
			allsupnums.add(nu);
		}
		double to = 0;
		for(int g=0;g<allsupnums.size();g++){
			to+=allsupnums.get(g);
		}
		ArrayList<Double> doubles = new ArrayList<Double>();
		for(int tt =0;tt<int_.length;tt++){
			double om = (to-allsupnums.get(tt))/allsupnums.get(tt);
			doubles.add(om);
		}
		Double max = new Double(Double.MAX_VALUE);
		int mind = 0;
//		for(int t =0;t<doubles.size();t++){
//			if(doubles.get(t)<=max){
//				max = doubles.get(t);
//				mind= t;
//			}
//		}
		return doubles.get(j);
	}
	static HashSet<Table> tables = new HashSet<Table>();
	void Menorize(){
		Table table = new Table();
//		SortListUtil.sort(Arrays.asList(mis),"omean");
		double max = Double.MAX_VALUE;
		int mz=0;
		for(int t =0;t<FoodNumber;t++){
			if(mis[t].getOmean()<max){
				max = mis[t].getOmean();
				mz = t;
			}
//			table.setId(id);
		}
		table.setId(mis[mz].getI());
		table.setPos(mis[mz].getJ());
		table.setMean(mis[mz].getOmean());
		table.setValue(mis[mz].getName());
		tables.add(table);
	}
	
	public void senOnScout(){
		Random random = new Random();
		int [] int_= new int[]{0,1,2};
		for(int t=0;t<FoodNumber;t++){
			if(trail[t]>limit){
				Mi mi =ranSel(int_);
				double me = calO_measure(mi.getI(), mi.getJ(), int_);
				mi.setOmean(me);
				mi.setName(mIFoCounts.get(mi.getI()).getAr().get(mi.getJ()));
				mis[t] = mi;
				trail[t] =0;
			}
		}
	}
	static void prin(ArrayList<Table> tables,int p){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		String da = simpleDateFormat.format(new Date());
		File file = new File("F:\\data\\"+da+""+p+".txt");
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
//			System.out.println("-----------------------------");

			for(int t=0;t<tables.size();t++){//
				bufferedWriter.write(tables.get(t).toString());
				bufferedWriter.newLine();
//				System.out.println(tables.get(t));
//				if(tables.get(t).getMean()<0.01){
//					bufferedWriter.write(tables.get(t).toString());
//					bufferedWriter.newLine();
//				}
			}
			
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	Map<String, Table> map = new HashMap<String, Table>();
	void  neFullSearch() throws FileNotFoundException{
		int [] _int=  new int[]{0,1,2};
		BufferedWriter bufferedWriter =null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		String da = simpleDateFormat.format(new Date());
		File file = new File("F:\\data\\"+da+""+".txt");
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		try {		
			
			for(int i = 0;i<mIFoCounts.size();i++){
				for(int j =0;j<_int.length;j++){
					double val = calO_measure(i, j, _int);
					
					List<String> arrayList = mIFoCounts.get(i).getAr();
					String vass = arrayList.get(j);
					
					bufferedWriter.write(""+val+":  "+vass);
					bufferedWriter.newLine();
					
					Table ol = map.get(vass);
					if(ol!=null && val<ol.getMean()){
						ol.setMean(val);
						ol.setId(i);
						ol.setPos(j);
						
					}else if(ol==null){
						Table table = new Table();
						table.setId(i);
						table.setMean(val);
						table.setPos(j);
						table.setValue(vass);
						tables.add(table);
						map.put(vass, table);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<Table> tablesA = new ArrayList<Table>();
		tablesA.addAll(map.values());
		tablesA= (ArrayList<Table>) SortListUtil.sort(tablesA, "mean");
		prin(tablesA,0);
		
	}
	
	double MFE = 10000;
	double num_eval = SN;
	public static void main(String[] args) throws FileNotFoundException {
		BeeOutlierDetectionImprove beeOutlierDetectionImprove = new BeeOutlierDetectionImprove();
		beeOutlierDetectionImprove.oldBeeExtra();
	}

	private  void oldBeeExtra() {
		BeeColony beeColony = new BeeColony();
		String fileName = "F:\\daoxiangmudata\\"+nsiz+"W.txt";
		int[] int_ = new int[]{0,1,2};
		int intNum=3;
		double Foods[][]= new BeeOutlierDetectionImprove().getFoods();
		BeeOutlierDetectionImprove beeTes = new BeeOutlierDetectionImprove();
		beeTes.beforLead();
		while(num_eval!=MFE){
			beeTes.leader();
			beeTes.CalculateProbabilities();
			double p = Math.random();
			double T = p*SN;
			for(int i=0;i<T;i++){
				beeTes.OnLook();
			}
			beeTes.Menorize();
			beeTes.senOnScout();
			Temp = 0.9*Temp;  //temperature  changed
		}
		ArrayList<Table> tablesA = new ArrayList<Table>();
		tablesA.addAll(tables);
		tablesA= (ArrayList<Table>) SortListUtil.sort(tablesA, "mean");
		prin(tablesA,0);
	}

}
