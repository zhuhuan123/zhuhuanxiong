

public class Table {
	private int id;
	private int pos;
	private String value;
	private double mean;
	private boolean isval = true;
	
	public boolean isIsval() {
		return isval;
	}

	public void setIsval(boolean isval) {
		this.isval = isval;
	}

	
	
	@Override
	public String toString() {
		return "Table [id=" + id + ", pos=" + pos + ", value=" + value
				+ ", mean=" + mean + "]";
	}
	
	public String toString2() {
		return "" + id + "\t" + pos + "\t" + value + "\t" + mean + "";
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(mean);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + pos;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Table other = (Table) obj;
		if (Double.doubleToLongBits(mean) != Double.doubleToLongBits(other.mean))
			return false;
		if (pos != other.pos)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String mytoString() {
		String idString = String.valueOf(this.getId());
		String posString = String.valueOf(this.getPos());
		String valueString = String.valueOf(this.getValue());
		String measString = String.valueOf(this.getMean());
		if(idString.length()< 9)
			idString = idString +"\t\t";
		else if(idString.length() >= 9 && idString.length() < 18)
			idString = idString + "\t";
		if(posString.length()< 9)
			posString = posString +"\t\t";
		else if(posString.length() >= 9 && posString.length() < 18)
			posString = posString + "\t";
		if(valueString.length()< 9)
			valueString = valueString +"\t\t";
		else if(valueString.length() >= 9 && valueString.length() < 18)
			valueString = valueString + "\t";
		return idString + posString + valueString + measString;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public double getMean() {
		return mean;
	}
	public void setMean(double mean) {
		this.mean = mean;
	} 
}
