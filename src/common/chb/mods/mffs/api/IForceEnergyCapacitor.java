/*  
    Copyright (C) 2012 Thunderdark

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Contributors:
    Thunderdark - initial implementation
*/

package chb.mods.mffs.api;

public interface IForceEnergyCapacitor {
	public int getcapacity();
	// Charging status in %

	public int getMaxforcepower();
	//Max ForcePower for this Generator

	public int getForcepower();
	// ForcePower left in his Generator

	public int getTransmitrange();
	// Range of maximum transmit

	public Short getLinketprojektor();
	 //count of paired device

}