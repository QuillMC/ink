package com.github.quillmc.ink;

import com.github.quillmc.tinymcp.Mappings;
import net.fabricmc.tinyremapper.TinyRemapper;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Path;

public abstract class RemapModTask extends DefaultTask {
    public static void register(Project project) {
        project.getTasks().register("remapModTask", RemapModTask.class);
    }

    protected FileCollection defaultInputFiles;

    @Optional
    @Input
    abstract Property<FileCollection> getInputFiles();

    @TaskAction
    public void run() {
        FileCollection files = getInputFiles().isPresent() ? getInputFiles().get() : defaultInputFiles;
        TinyRemapper remapper = Mappings.MCPtoNotch(InkPlugin.getTinyMCP().getMappings());

        files.filter(f -> f.getName().endsWith(".jar")).forEach(jar -> {
            System.out.println("Remapping " + jar);
            Path out = jar.toPath();
            File devJar = new File(jar.getAbsolutePath().replace(".jar", "-dev.jar"));
            boolean renamed = jar.renameTo(devJar);
            if (renamed) Mappings.remap(devJar.toPath(), out, remapper);
            else throw new GradleException("Failed to set artifact jar classifier to dev");
        });
    }
}
