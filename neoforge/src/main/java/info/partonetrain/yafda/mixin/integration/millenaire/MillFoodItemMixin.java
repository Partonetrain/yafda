package info.partonetrain.yafda.mixin.integration.millenaire;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import info.partonetrain.yafda.integration.BrewinAndChewinIntegration;
import info.partonetrain.yafda.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import org.millenaire.item.MillFoodItem;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;


@Mixin(MillFoodItem.class)
public class MillFoodItemMixin extends Item {

    @Mutable
    @Final
    @Shadow
    private final int regenerationSeconds;

    @Mutable
    @Final
    @Shadow
    private final int drunkSeconds;

    @Mutable
    @Final
    @Shadow
    private final int speedSeconds;

    @Mutable
    @Final
    @Shadow
    private final boolean drink;

    @Mutable
    @Final
    @Shadow
    private final int healAmount;

    @Mutable
    @Final
    @Shadow
    private final List<MobEffectInstance> extraEffects;

    @Unique
    private final ResourceKey<MobEffect> TIPSY = ResourceKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath("brewinandchewin", "tipsy"));

    public MillFoodItemMixin(Properties properties, int regenerationSeconds, int drunkSeconds, int speedSeconds, boolean drink, int healAmount, List<MobEffectInstance> extraEffects) {
        super(properties);
        this.regenerationSeconds = regenerationSeconds;
        this.drunkSeconds = drunkSeconds;
        this.speedSeconds = speedSeconds;
        this.drink = drink;
        this.healAmount = healAmount;
        this.extraEffects = extraEffects;
    }

    //right after nauseau effect is added - drunkSecond check already done
    @Inject(method = "finishUsingItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z", ordinal = 1))
    public void yafda$finishUsingItem(ItemStack stack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir){
        if(Services.PLATFORM.isModLoaded("brewinandchewin")){
            BrewinAndChewinIntegration.tipsyStacking(entity);
            Holder<MobEffect> intoxication = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(ResourceKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath("brewinandchewin", "intoxication")));
            entity.addEffect(new MobEffectInstance(intoxication, 1800));
            Holder<MobEffect> tipsy = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(ResourceKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath("brewinandchewin", "tipsy")));
            entity.addEffect(new MobEffectInstance(tipsy, 2400));
        }
    }

    //most of this method is adapted from TextUtils.addFoodEffectTooltip
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
        MillFoodItem self = (MillFoodItem) (Object) this;

        List<MobEffectInstance> instancesToAdd = new ArrayList<>();

        if(healAmount > 1){
            int instantHealthLevel = (healAmount / 4) - 1;
            if(instantHealthLevel < 0){
                instancesToAdd.add(new MobEffectInstance(MobEffects.HEAL, 1, instantHealthLevel));
            }
            else{
                tooltip.add(Component.translatable("tooltip.farmersdelight.melon_juice").withStyle(MobEffectCategory.BENEFICIAL.getTooltipFormatting())); //"Minor Instant Health"
            }
        }
        if(regenerationSeconds > 0){
            instancesToAdd.add(new MobEffectInstance(MobEffects.REGENERATION, regenerationSeconds * 20));
        }
        if(drunkSeconds > 0){
            instancesToAdd.add(new MobEffectInstance(MobEffects.CONFUSION, drunkSeconds * 20));
            if(Services.PLATFORM.isModLoaded("brewinandchewin")){
                Holder<MobEffect> intoxication = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(ResourceKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath("brewinandchewin", "intoxication")));
                Holder<MobEffect> tipsy = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(TIPSY);
                instancesToAdd.add(new MobEffectInstance(intoxication, 1800));
                instancesToAdd.add(new MobEffectInstance(tipsy, 2400));
            }
        }
        if(speedSeconds > 0){
            instancesToAdd.add(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, drunkSeconds * 20, 1));
        }
        instancesToAdd.addAll(extraEffects);

        List<Pair<Holder<Attribute>, AttributeModifier>> attributeList = Lists.newArrayList();

        for(MobEffectInstance instance : instancesToAdd) {
            MutableComponent mutableComponent = Component.translatable(instance.getDescriptionId());
            MobEffect effect = (MobEffect) instance.getEffect().value();
            effect.createModifiers(instance.getAmplifier(), (attributeHolder, attributeModifier) -> attributeList.add(new Pair<>(attributeHolder, attributeModifier)));
            if (instance.getAmplifier() > 0) {
                mutableComponent = Component.translatable("potion.withAmplifier", mutableComponent, Component.translatable("potion.potency." + instance.getAmplifier()));
            }

            if (instance.getDuration() > 20) {
                mutableComponent = Component.translatable("potion.withDuration", mutableComponent, MobEffectUtil.formatDuration(instance, 1.0F, context.tickRate()));
            }

            if(instance.getEffect().is(TIPSY)){ //tipsy is showing up blue here for some reason
                tooltip.add(mutableComponent.withStyle(MobEffectCategory.HARMFUL.getTooltipFormatting()));
            }
            else{
                tooltip.add(mutableComponent.withStyle(effect.getCategory().getTooltipFormatting()));
            }

        }

        if (!attributeList.isEmpty()) {
            tooltip.add(CommonComponents.EMPTY);
            tooltip.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

            for(Pair<Holder<Attribute>, AttributeModifier> pair : attributeList) {
                AttributeModifier attributemodifier = pair.getSecond();
                double amount = attributemodifier.amount();
                double formattedAmount;
                if (attributemodifier.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_BASE && attributemodifier.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                    formattedAmount = attributemodifier.amount();
                } else {
                    formattedAmount = attributemodifier.amount() * 100.0D;
                }

                if (amount > 0.0D) {
                    tooltip.add(Component.translatable("attribute.modifier.plus." + attributemodifier.operation().id(), new Object[]{ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(((pair.getFirst()).value()).getDescriptionId())}).withStyle(ChatFormatting.BLUE));
                } else if (amount < 0.0D) {
                    formattedAmount *= -1.0D;
                    tooltip.add(Component.translatable("attribute.modifier.take." + attributemodifier.operation().id(), new Object[]{ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(((pair.getFirst()).value()).getDescriptionId())}).withStyle(ChatFormatting.RED));
                }
            }
        }
    }
}
