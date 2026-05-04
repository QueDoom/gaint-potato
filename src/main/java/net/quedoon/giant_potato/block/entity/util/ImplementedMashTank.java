package net.quedoon.giant_potato.block.entity.util;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;

@FunctionalInterface
public interface ImplementedMashTank {

    SingleVariantStorage<FluidVariant> getMashStorage();




}
