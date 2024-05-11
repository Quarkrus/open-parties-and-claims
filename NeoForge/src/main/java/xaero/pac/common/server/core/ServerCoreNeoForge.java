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

package xaero.pac.common.server.core;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public class ServerCoreNeoForge {

	public static boolean isCreateGlueSelectionAllowed(BlockPos from, BlockPos to, IPayloadContext ctx){
		if(ctx.player() == null)
			return true;
		return ServerCore.isCreateGlueSelectionAllowed(from, to, (ServerPlayer) ctx.player());
	}

	public static boolean isCreateGlueRemovalAllowed(int entityId, IPayloadContext ctx){
		if(ctx.player() == null)
			return true;
		return ServerCore.isCreateGlueRemovalAllowed(entityId, (ServerPlayer) ctx.player());
	}

	public static boolean isCreateTileEntityPacketAllowed(BlockPos pos, IPayloadContext ctx){
		if(ctx.player() == null)
			return true;
		ServerPlayer player = (ServerPlayer) ctx.player();
		return ServerCore.isCreateTileEntityPacketAllowed(pos, player);
	}

	public static boolean isCreateContraptionInteractionPacketAllowed(int contraptionId, InteractionHand interactionHand, IPayloadContext ctx){
		if(ctx.player() == null)
			return true;
		return ServerCore.isCreateContraptionInteractionPacketAllowed(contraptionId, interactionHand, (ServerPlayer) ctx.player());
	}

	public static boolean isCreateTrainRelocationPacketAllowed(int contraptionId, BlockPos pos, IPayloadContext ctx){
		if(ctx.player() == null)
			return true;
		return ServerCore.isCreateTrainRelocationPacketAllowed(contraptionId, pos, (ServerPlayer) ctx.player());
	}

	public static boolean isCreateTrainControlsPacketAllowed(int contraptionId, IPayloadContext ctx){
		if(ctx.player() == null)
			return true;
		return ServerCore.isCreateTrainControlsPacketAllowed(contraptionId, (ServerPlayer) ctx.player());
	}

	public static FluidStack onCreatePipeCollectBlock(Level level, BlockPos from, BlockPos to, boolean simulate){
		if(ServerCore.canCreatePipeAffectBlock(level, from, to, simulate))
			return null;
		return FluidStack.EMPTY;
	}

	public static List<? extends Entity> onPressurePlateEntityCount(List<? extends Entity> entities) {
		if (ServerCore.DETECTING_ENTITY_BLOCK_COLLISION != null)
			ServerCore.onEntitiesPushBlock(entities, ServerCore.DETECTING_ENTITY_BLOCK_COLLISION, ServerCore.DETECTING_ENTITY_BLOCK_COLLISION_POS);
		return entities;
	}

}