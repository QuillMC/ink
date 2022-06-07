package com.github.quillmc.ink.examplemod;

import net.minecraft.client.Minecraft;

import java.util.Arrays;

public class ExampleMod {
    void test() {
        System.out.println(Arrays.toString(Minecraft.tickTimes)); // Test to ensure correct remapping
    }
}
