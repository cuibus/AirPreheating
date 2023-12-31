package utils;

public class SimulationOutput {
	public double[] airTemp;
	public double[] pipeTemp;

	public double[] ref_airTemp;
	public double[] ref_pipeTemp;

	public double maxError_air;
	public double maxError_pipe;
	public double sumErrorSquareAir;
	public double sumErrorSquarePipe;

	public SimulationOutput(int length){
		this.airTemp = new double[length];
		this.pipeTemp = new double[length];
		this.ref_airTemp = new double[length];
		this.ref_pipeTemp = new double[length];
	}
}
