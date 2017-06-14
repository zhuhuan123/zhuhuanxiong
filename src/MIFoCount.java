

import java.util.ArrayList;
import java.util.List;

public class MIFoCount {
	private List<String> ar= new ArrayList<String>();
	private int id;
	private int count;
	public List<String> getAr() {
		return ar;
	}
	public void setAr(List<String> ar) {
		this.ar = ar;
	}
	@Override
	public String toString() {
		return "MIFoCount [ar=" + ar + ", id=" + id + ", count=" + count + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ar == null) ? 0 : ar.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MIFoCount other = (MIFoCount) obj;
		if (ar == null) {
			if (other.ar != null)
				return false;
		} else if (!ar.equals(other.ar))
			return false;
		return true;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setCountAddOne() {
		// TODO Auto-generated method stub
		this.count += 1;
	}
	public boolean isNe(MInfo other,int pos){
		if(this.getAr().size()!=other.getAt().size()){
			return false;
		}
		for(int t =0;t<this.getAr().size();t++){
			if(t==pos){
				continue;
			}
			if(!this.getAr().get(t).equals(other.getAt().get(t))){
				return false;
			}
		}
		return true;
	}
	public boolean isNeMin(MIFoCount other,int pos){
		if(this.getAr().size()!=other.getAr().size()){
			return false;
		}
		for(int t =0;t<this.getAr().size();t++){
			if(t==pos){
				continue;
			}
			if(!this.getAr().get(t).equals(other.getAr().get(t))){
				return false;
			}
		}
		return true;
	}
}
