package winecraft;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGrapeCrop extends TileEntity {
	
	private int angle = 0;
	private int rainCounter = 0;
	private boolean inVineyard = false;

	@Override
    public void readFromNBT(NBTTagCompound tagCompound) {
            super.readFromNBT(tagCompound);
            this.angle = tagCompound.getShort("angle");
            this.rainCounter = tagCompound.getShort("rainCounter");
            this.inVineyard = tagCompound.getBoolean("inVineyard");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);
           tagCompound.setShort("angle", (short)this.angle);
           tagCompound.setShort("rainCounter", (short)this.rainCounter);
           tagCompound.setBoolean("inVineyard", this.inVineyard);
    }
	
	public int getAngle() {
		return angle;
	}

	public void addRain() {
		rainCounter++;
		System.out.println(this.blockMetadata+"Rain counter: " + rainCounter);
	}
	
	public void setAngle(int angle) {
		this.angle = angle;
		System.out.println("Angle of the current Vineyard: " + angle);
	}

	public boolean isInVineyard() {
		return inVineyard;
	}

	public void setInVineyard(boolean inVineyard) {
		this.inVineyard = inVineyard;
	}
}
