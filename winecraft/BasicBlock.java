/**
 * Copyright (C) 2013  Philippe Babin<philippe.babin@gmail.com> and François Drouin-Morin<>

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
 */

package tutorial.winecraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicBlock extends Block{

	public BasicBlock(int id, int texture, Material material) {
		super(id, texture, material);
	}
	
	 @Override
     public String getTextureFile () {
             return CommonProxy.BLOCK_PNG;
     }
}
