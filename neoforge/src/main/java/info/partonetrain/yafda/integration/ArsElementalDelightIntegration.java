package info.partonetrain.yafda.integration;

import alexthw.ars_elemental.registry.ModItems;
import com.hollingsworth.arsnouveau.api.block.IPrismaticBlock;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import dev.xkmc.arsdelight.content.jelly.JellyAttachment;
import dev.xkmc.arsdelight.content.jelly.JellyBlockEntity;
import dev.xkmc.arsdelight.content.jelly.JellyMethod;
import dev.xkmc.arsdelight.init.food.BlockFoodType;
import dev.xkmc.l2modularblock.core.DelegateEntityBlockImpl;
import dev.xkmc.l2modularblock.impl.BlockEntityBlockMethodImpl;
import dev.xkmc.l2modularblock.type.BlockMethod;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.item.ConsumableEffectDrinkItem;
import info.partonetrain.yafda.item.ConsumableEffectItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.item.FuelItem;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.function.Supplier;

//Integration for Ars Elemental + Ars Delight
public class ArsElementalDelightIntegration {
    public static final DeferredHolder<Item, Item> FLASHING_BARK = YafdaNeoForge.ITEMS.register(
            "flashing_bark",
            () -> new FuelItem(new Item.Properties(), 200)
    );

    public static final DeferredHolder<Block, Block> FLASHPINE_CRATE_BLOCK = YafdaNeoForge.BLOCKS.register(
            "flashpine_crate",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD))
    );
    public static final DeferredHolder<Item, Item> FLASHPINE_CRATE_ITEM = YafdaNeoForge.ITEMS.register(
            "flashpine_crate",
            () -> new BlockItem(FLASHPINE_CRATE_BLOCK.get(), new Item.Properties())
    );

    public static final DeferredHolder<Block, Block> FLASHPINE_JELLY_BLOCK = YafdaNeoForge.BLOCKS.register(
            "flashpine_jelly",
            () -> new FlashpineJellyBlock(Block.Properties.of().instabreak().pushReaction(PushReaction.DESTROY).mapColor(DyeColor.BROWN).sound(SoundType.WOOL)
                    .noOcclusion())
    );
    public static final Supplier<BlockEntityType<FlashpineJellyBlockEntity>> FLASHPINE_JELLY_BE = YafdaNeoForge.BLOCK_ENTITY_TYPES.register(
            "flashpine_jelly_block_entity",
            () -> BlockEntityType.Builder.of(
                    FlashpineJellyBlockEntity::new,
                    FLASHPINE_JELLY_BLOCK.get()
            ).build(null)
    );

    public static final DeferredHolder<Item, Item> FLASHPINE_JELLY = YafdaNeoForge.ITEMS.register(
            "flashpine_jelly",
            () -> {
                var builder = new FoodProperties.Builder();
                builder.nutrition(4).saturationModifier(0.6f);
                for (var e : ModItems.FLASHPINE_FOOD.effects()) {
                    builder.effect(() ->
                            new MobEffectInstance(e.effect().getEffect(), (int) (e.effect().getDuration() * (double) 1), e.effect().getAmplifier() + 1), e.probability()
                    );
                }
                return BlockFoodType.FAST_BOWL.build(
                        FLASHPINE_JELLY_BLOCK.get(),
                        new Item.Properties(),
                        builder
                );
            }
    );

    public static final FoodProperties NEUTRALIZED_FLASHPINE_JAM_PROPERTIES = (new FoodProperties.Builder()).nutrition(0).alwaysEdible().saturationModifier(0.0F).effect(() -> {
        return new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0);
    }, 1.0F).build();
    public static final DeferredHolder<Item, Item> NEUTRALIZED_FLASHPINE_JAM = YafdaNeoForge.ITEMS.register(
            "neutralized_flashpine_jam",
            () -> new ConsumableEffectDrinkItem(NEUTRALIZED_FLASHPINE_JAM_PROPERTIES, true)
    );

    public static final FoodProperties FLASHPINE_PIE_SLICE_PROPERTIES = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.3F).fast().effect(() -> {
        return new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0);
    }, 1.0F).build();
    public static final DeferredHolder<Item, Item> FLASHPINE_PIE_SLICE = YafdaNeoForge.ITEMS.register(
            "flashpine_pie_slice",
            () -> new ConsumableEffectItem(vectorwing.farmersdelight.common.registry.ModItems.foodItem(FLASHPINE_PIE_SLICE_PROPERTIES))
    );

    public static final DeferredHolder<Block, Block> FLASHPINE_PIE_BLOCK = YafdaNeoForge.BLOCKS.register(
            "flashpine_pie",
            () -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), FLASHPINE_PIE_SLICE)
    );

    public static final DeferredHolder<Item, Item> FLASHPINE_PIE = YafdaNeoForge.ITEMS.register(
            "flashpine_pie",
            () -> new BlockItem(FLASHPINE_PIE_BLOCK.get(), vectorwing.farmersdelight.common.registry.ModItems.basicItem())
    );

    public static final FoodProperties FLASHPINE_TEA_PROPERTIES = (new FoodProperties.Builder()).nutrition(0).alwaysEdible().fast().saturationModifier(0.0F).effect(() -> {
        return new MobEffectInstance(MobEffects.NIGHT_VISION, 1800, 0);
    }, 1.0F).build();
    public static final DeferredHolder<Item, Item> FLASHPINE_TEA = YafdaNeoForge.ITEMS.register(
            "flashpine_tea",
            () -> new ConsumableEffectDrinkItem(FLASHPINE_TEA_PROPERTIES, false)
    );

    public static final FoodProperties FLASHPINE_HORNBEER_PROPERTIES = (new FoodProperties.Builder()).fast().alwaysEdible().nutrition(0).saturationModifier(0.0F).effect(() -> {
        return new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 1);
    }, 1.0F).build();
    public static final DeferredHolder<Item, Item> FLASHPINE_HORNBEER = YafdaNeoForge.ITEMS.register(
            "flashpine_hornbeer",
            () -> new ConsumableEffectDrinkItem(FLASHPINE_HORNBEER_PROPERTIES, false, BuiltInRegistries.ITEM.get(ResourceLocation.parse("arsdelight:chimera_horn")))
    );

    public static final FoodProperties FLASHPINE_FISH_PROPERTIES = (new FoodProperties.Builder()).nutrition(10).saturationModifier(0.8F)
            .effect(
                    () -> {
                        return new MobEffectInstance(MobEffects.NIGHT_VISION, 2400, 0);
                    }, 1.0F)
            .effect(() -> {
                return new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0);
            }, 1.0F).build();
    public static final DeferredHolder<Item, Item> FLASHPINE_FISH = YafdaNeoForge.ITEMS.register(
            "flashpine_fish",
            () -> new ConsumableEffectItem(vectorwing.farmersdelight.common.registry.ModItems.bowlFoodItem(FLASHPINE_FISH_PROPERTIES))
    );


    public static void load() {
        Constants.LOG.info("ArsElementalDelight integration loaded");
    }

    @SerialClass //l2serial
    public static class FlashpineJellyBlockEntity extends JellyBlockEntity {
        public FlashpineJellyBlockEntity(BlockPos pos, BlockState state) {
            super(FLASHPINE_JELLY_BE.get(), pos, state);
        }
    }

    //we have to reimpl the block class and its methods because the block entity is a different class
    public static class FlashpineJellyBlock extends DelegateEntityBlockImpl implements IPrismaticBlock {

        private static final BlockMethod INS = new JellyMethod();
        private static final BlockMethod TE = new BlockEntityBlockMethodImpl<>(FLASHPINE_JELLY_BE, FlashpineJellyBlockEntity.class);

        public FlashpineJellyBlock(Properties properties) {
            super(properties, INS, TE);
        }

        @Override
        public void onHit(ServerLevel world, BlockPos pos, EntityProjectileSpell spell) {
            if (world.getBlockEntity(pos) instanceof FlashpineJellyBlockEntity be) {
                spell.spellResolver.spellContext.attachments.put(JellyAttachment.ID, new JellyAttachment(be.getId().toString()));
                var v = spell.getDeltaMovement().normalize();
                be.makeWiggle(Direction.getNearest(v.x, v.y, v.z));
            }
        }

        @Override
        public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile pProjectile) {
            if (!level.isClientSide() && level.getBlockEntity(hit.getBlockPos()) instanceof FlashpineJellyBlockEntity be) {
                be.makeWiggle(hit.getDirection());
            }
        }

        public void updateEntityAfterFallOn(BlockGetter level, Entity e) {
            if (e.isSuppressingBounce()) {
                super.updateEntityAfterFallOn(level, e);
            } else {
                this.bounceUp(e);
            }
        }

        private void bounceUp(Entity entity) {
            Vec3 v = entity.getDeltaMovement();
            if (v.y < 0) {
                double r = 0.8;
                if (entity instanceof LivingEntity) {
                    r = 1;
                }
                entity.setDeltaMovement(v.x, -v.y * r, v.z);
            }

        }

        public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
            return false;
        }

    }

}
