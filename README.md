# Ink
Gradle plugin for developing Quill mods.

## Usage
*No public builds have been created yet, you must build and publish to your local maven repository to use for now*

```kotlin
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
```

### Building
Running the normal gradle build task will create two artifacts, one with the `dev` classifier which has NOT been 
remapped and should not be distributed. The other JAR will be remapped and ready to distribute.