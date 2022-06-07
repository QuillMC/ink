import com.github.quillmc.tinymcp.Version
import com.github.quillmc.ink.ModType;

plugins {
    `java-library`
    id("com.github.quillmc.ink") version "1.0-SNAPSHOT"
}

ink {
    modType.set(ModType.CLIENT)
    gameVersion.set(Version.BETA1_1__02)
}

