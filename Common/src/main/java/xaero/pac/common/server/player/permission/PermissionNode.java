/*
 * Open Parties and Claims - adds chunk claims and player parties to Minecraft
 * Copyright (C) 2023, Xaero <xaero1996@gmail.com> and contributors
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

package xaero.pac.common.server.player.permission;

import net.minecraft.network.chat.Component;
import xaero.pac.common.server.player.permission.api.IPermissionNodeAPI;

import java.util.Map;
import java.util.function.Supplier;

public class PermissionNode implements IPermissionNodeAPI {

	private final String defaultNode;
	private final boolean isInt;
	private final Component name;
	private final Component comment;
	private final Supplier<String> nodeStringSupplier;

	public PermissionNode(String defaultNode, boolean isInt, Supplier<String> nodeStringSupplier, Component name, Component comment, Map<String, IPermissionNodeAPI> all) {
		this.defaultNode = defaultNode;
		this.isInt = isInt;
		this.nodeStringSupplier = nodeStringSupplier;
		this.name = name;
		this.comment = comment;
		all.put(defaultNode, this);
	}

	@Override
	public String getDefault() {
		return defaultNode;
	}

	@Override
	public String getNodeString() {
		return nodeStringSupplier.get();
	}

	@Override
	public Component getName() {
		return name;
	}

	@Override
	public Component getComment() {
		return comment;
	}

	@Override
	public boolean isInt() {
		return isInt;
	}

}
