package GA.scenario0;

public class Solution {
	public String tableT4,tableT6;
	public double[] w;
	public double initialGroundInertia;
	Solution(String t4, String t6, double w74, double w24, double w45, double w26, double initialGroundInertia){
		this.tableT4=t4;
		this.tableT6=t6;
		this.w=new double[] {w74, w24, w45, w26};
		this.initialGroundInertia = initialGroundInertia;
	}
}
