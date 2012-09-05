package chb.mods.mffs.common.api;

public interface IForcePowerGenerator {
	public int getcapacity();
	// Charging status in %

	public int getMaxforcepower();
	//Max ForcePower in EU

	public int getForcepower();
	// ForcePower level in EU

	public int getTransmitrange();
	// Range of maximum transmit

	public Short getLinketprojektor();
	 //count of paired device

}
