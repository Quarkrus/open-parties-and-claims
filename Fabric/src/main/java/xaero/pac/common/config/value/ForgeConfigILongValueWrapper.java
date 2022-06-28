/*
 * Open Parties and Claims - adds chunk claims and player parties to Minecraft
 * Copyright (C) 2022, Xaero <xaero1996@gmail.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of version 3 of the GNU Lesser General Public License
 * (LGPL-3.0-only) as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received copies of the GNU Lesser General Public License
 * and the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package xaero.pac.common.config.value;

import net.minecraftforge.common.ForgeConfigSpec;
import xaero.pac.common.config.ForgeConfigSpecBuilderWrapper;
import xaero.pac.common.config.IForgeConfigSpecBuilder;

import java.util.List;

public class ForgeConfigILongValueWrapper implements IForgeConfigLongValue{

	private final ForgeConfigSpec.LongValue longValue;

	public ForgeConfigILongValueWrapper(ForgeConfigSpec.LongValue longValue) {
		this.longValue = longValue;
	}

	@Override
	public List<String> getPath() {
		return longValue.getPath();
	}

	@Override
	public Long get() {
		return longValue.get();
	}

	@Override
	public IForgeConfigSpecBuilder next() {
		return new ForgeConfigSpecBuilderWrapper(longValue.next());
	}

	@Override
	public void save() {
		longValue.save();
	}

	@Override
	public void set(Long value) {
		longValue.set(value);
	}

	@Override
	public void clearCache() {
		longValue.clearCache();
	}
}