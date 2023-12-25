package GA.scenario1;

public class Solution {
	public String tableT0;
	public String tableT2;
	public double[] w;
	public double initialAirInertia;
	Solution(String t0, String t2, double w60, double w00, double w11, double w02, double w22, double w33, double initialAirInertia){
		this.tableT0=t0;
		this.tableT2=t2;
		this.w=new double[] {w60, w00, w11, w02, w22, w33};
		this.initialAirInertia = initialAirInertia;
	}
}
