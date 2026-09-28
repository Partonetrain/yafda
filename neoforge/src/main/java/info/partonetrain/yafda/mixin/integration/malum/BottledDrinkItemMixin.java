package info.partonetrain.yafda.mixin.integration.malum;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.common.item.food.BottledDrinkItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.List;


@Mixin(BottledDrinkItem.class)
public class BottledDrinkItemMixin extends Item {

    //MalumItems.RUNIC_SAP

    public BottledDrinkItemMixin(Properties properties) {
        super(properties);
    }

    //adapted from TextUtils.addFoodEffectTooltip
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
        List<MobEffectInstance> instancesToAdd = new ArrayList<>();

        FoodProperties food = stack.get(DataComponents.FOOD);

        if(food == null){
            return;
        }

        for(FoodProperties.PossibleEffect effect : food.effects()){
            instancesToAdd.add(effect.effect());
        }

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

            tooltip.add(mutableComponent.withStyle(effect.getCategory().getTooltipFormatting()));

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
