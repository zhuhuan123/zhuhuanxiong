

import java.util.ArrayList;
import java.util.List;

public class MInfo {
//	private String fir;
//	private String sec;
//	private String thir;
	private List<String> at = new ArrayList<String>();
	
	public List<String> getAt() {
		return at;
	}
	public void setAt(List<String> at) {
		this.at = at;
	}

	private int id;
	@Override
	public String toString() {
		return "MInfo [at=" + at + ", id=" + id + ", selind=" + selind
				+ ", lastid=" + lastid + "]";
	}

	private int selind;
	
	public int getSelind() {
		return selind;
	}
	public void setSelind(int selind) {
		this.selind = selind;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	public String getFir() {
//		return fir;
//	}
//	@Override
//	public String toString() {
//		return "MInfo [fir=" + fir + ", sec=" + sec + ", thir=" + thir
//				+ ", id=" + id + ", lastid=" + lastid + "]";
//	}
//	public void setFir(String fir) {
//		this.fir = fir;
//	}
//	public String getSec() {
//		return sec;
//	}
//	public void setSec(String sec) {
//		this.sec = sec;
//	}
//	public String getThir() {
//		return thir;
//	}
//	public void setThir(String thir) {
//		this.thir = thir;
//	}
	
	private int lastid;
	
	
	public void setLastid(int lastid) {
		this.lastid = lastid;
	}
	public ReI getReI(int id){
		ReI reI = new ReI();
		reI.setShangid(id-1<0?0:id-1);
		reI.setXiaid(id+1>lastid?lastid:id+1);
		return reI;
	}
	public int getLastid() {
		return lastid;
	}
//	public boolean isNeig(MInfo mInfo){
//		int seid = mInfo.getSelind();
//		if(seid==1){
//			if(mInfo.getSec().equals(this.getSec())&& mInfo.getThir().equals(this.thir)){
//				return true;
//			}
//		}else if(seid==2){
//			if(mInfo.getFir().equals(this.getFir())&& mInfo.getThir().equals(this.thir)){
//				return true;
//			}
//		}else if(seid==3){
//			if(mInfo.getFir().equals(this.getFir())&& mInfo.getSec().equals(this.sec)){
//				return true;
//			}
//		}
//		return false;
//	}
}
