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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class WinecraftItem extends Item {

	public WinecraftItem (int id) {
        super(id);

        // Constructor Configuration
        maxStackSize = 64;
        setCreativeTab(CreativeTabs.tabMisc);
        setIconIndex(0);
        setItemName("genericItem");
    }

    public WinecraftItem (int id, int maxStackSize, CreativeTabs tab, int texture, String name) {
        super(id);
        setMaxStackSize(maxStackSize);
        setCreativeTab(tab);
        setIconIndex(texture);
        setItemName(name);
    }
	
	public String getTextureFile() {
        return CommonProxy.ITEMS_PNG;
	}
}
