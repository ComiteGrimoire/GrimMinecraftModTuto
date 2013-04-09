package tutorial.winecraft;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGrapeCrop extends TileEntity {
	
	private double angle = 0;
	private boolean inVineyard = false;

	@Override
    public void readFromNBT(NBTTagCompound tagCompound) {
            super.readFromNBT(tagCompound);
            //this.offsetX = tagCompound.getShort("offsetX");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);
           //tagCompound.setShort("offsetX", (short)this.offsetX);
    }
	
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
		System.out.println(angle + " work god dammit");
	}

	public boolean isInVineyard() {
		return inVineyard;
	}

	public void setInVineyard(boolean inVineyard) {
		this.inVineyard = inVineyard;
	}
}
