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

package xaero.pac.common.event;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xaero.pac.OpenPartiesAndClaims;

import java.util.List;

public class CommonEventsFabric extends CommonEvents {

	public CommonEventsFabric(OpenPartiesAndClaims modMain) {
		super(modMain);
	}

	public void registerFabricAPIEvents(){
		ServerPlayerEvents.AFTER_RESPAWN.register(this::onPlayerRespawn);
		ServerPlayerEvents.COPY_FROM.register(this::onPlayerClone);
		ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarting);
		ServerLifecycleEvents.SERVER_STOPPED.register(this::onServerStopped);
		ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(this::onPlayerChangedDimension);
		ServerTickEvents.START_SERVER_TICK.register(server -> onServerTick(true));
		ServerTickEvents.END_SERVER_TICK.register(server -> onServerTick(false));
		CommandRegistrationCallback.EVENT.register(this::onRegisterCommands);
		AttackBlockCallback.EVENT.register(this::onLeftClickBlock);
		PlayerBlockBreakEvents.BEFORE.register(this::onDestroyBlock);
		UseBlockCallback.EVENT.register(this::onRightClickBlock);
		UseItemCallback.EVENT.register(this::onItemRightClick);
		UseEntityCallback.EVENT.register(this::onEntityInteract);
		ServerEntityEvents.ENTITY_LOAD.register(this::onEntityJoinWorld);
	}

	public void onServerAboutToStart(MinecraftServer server) throws Throwable {
		super.onServerAboutToStart(server);
	}

	private void onServerStarting(MinecraftServer minecraftServer) {
		super.onServerStarting();
	}

	private void onPlayerRespawn(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
		super.onPlayerRespawn(newPlayer);
	}

	private void onPlayerChangedDimension(ServerPlayer serverPlayer, ServerLevel serverLevel, ServerLevel serverLevel1) {
		super.onPlayerChangedDimension(serverPlayer);
	}

	public void onPlayerLogIn(ServerPlayer player) {
		super.onPlayerLogIn(player);
	}

	private void onPlayerClone(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
		super.onPlayerClone(oldPlayer, newPlayer);
	}

	public void onPlayerLogOut(ServerPlayer player) {
		super.onPlayerLogOut(player);
	}

	public void onServerTick(boolean isTickStart) {
		try {
			super.onServerTick(isTickStart);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public void onPlayerTick(boolean isTickStart, Player player) {
		try {
			super.onPlayerTick(isTickStart, player instanceof ServerPlayer, player);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public void onServerStopped(MinecraftServer server) {
		super.onServerStopped(server);
	}

	private void onRegisterCommands(CommandDispatcher<CommandSourceStack> dispatcher, boolean dedicated) {
		super.onRegisterCommands(dispatcher, dedicated ? Commands.CommandSelection.DEDICATED : Commands.CommandSelection.INTEGRATED);
	}

	private InteractionResult onLeftClickBlock(Player player, Level level, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
		if(super.onLeftClickBlock(level instanceof ServerLevel, level, blockPos, player))
			return InteractionResult.FAIL;
		return InteractionResult.PASS;
	}

	private boolean onDestroyBlock(Level level, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
		return !super.onDestroyBlock(level, blockPos, player);
	}

	private InteractionResult onRightClickBlock(Player player, Level level, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		if(super.onRightClickBlock(level instanceof ServerLevel, level, blockHitResult.getBlockPos(), player, interactionHand, blockHitResult))
			return InteractionResult.FAIL;
		return InteractionResult.PASS;
	}

	private InteractionResultHolder<ItemStack> onItemRightClick(Player player, Level level, InteractionHand interactionHand) {
		ItemStack stack = player.getItemInHand(interactionHand);
		if(super.onItemRightClick(level instanceof ServerLevel, level, player.blockPosition(), player, interactionHand, stack))
			return InteractionResultHolder.fail(stack);
		return InteractionResultHolder.pass(stack);
	}

	public boolean onMobGrief(Entity entity) {
		return super.onMobGrief(entity);
	}

	public boolean onLivingHurt(DamageSource source, Entity target) {
		return super.onLivingHurt(source, target);
	}

	private InteractionResult onEntityInteract(Player player, Level level, InteractionHand interactionHand, Entity entity, EntityHitResult entityHitResult) {
		if(super.onEntityInteract(player, entity, interactionHand))
			return InteractionResult.FAIL;
		return InteractionResult.PASS;
	}

	public void onExplosionDetonate(Explosion explosion, List<Entity> entities, Level level) {
		super.onExplosionDetonate(level, explosion, entities, explosion.getToBlow());
	}

	public boolean onChorusFruit(Entity entity, Vec3 target){
		return super.onChorusFruit(entity, target);
	}

	public void onEntityJoinWorld(Entity entity, Level world){
		super.onEntityJoinWorld(entity, world);
	}

	public MinecraftServer getLastServerStarted(){
		return lastServerStarted;
	}

}