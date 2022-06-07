package com.github.quillmc.ink;

import com.github.quillmc.tinymcp.TinyMCP;
import com.github.quillmc.tinymcp.Version;
import org.gradle.api.Project;
import org.gradle.api.artifacts.DependencyResolutionListener;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.provider.Property;

import java.io.File;

public abstract class InkExtension {

    abstract public Property<Version> getGameVersion();

    abstract public Property<ModType> getModType();

    protected static InkExtension register(Project project) {
        InkExtension ext = project.getExtensions().create("ink", InkExtension.class);
        System.setProperty("TINYMCP_CACHE", Constants.CACHE.resolve("tinymcp").toAbsolutePath().toString());
        project.getGradle().addListener(ext.dependencyResolutionListener);
        return ext;
    }

    protected DependencyResolutionListener dependencyResolutionListener = new DependencyResolutionListener() {
        @Override
        public void beforeResolve(ResolvableDependencies dependencies) {
            Project project = InkPlugin.get().getProject();
            project.getLogger().info("RESOLVING DEPENDENCIES");

            ModType type = getModType().get();
            Version v = getGameVersion().get();

            System.out.printf("Setting up development environment for Minecraft %s %s%n", type, v);

            TinyMCP mcp = type == ModType.CLIENT
                    ? v.client()
                    : v.server();

            InkPlugin.tinymcp.setIfAbsent(mcp);

            File mappedJar = mcp.getMappedJar();

            DependencySet deps = project.getConfigurations().getByName("api").getDependencies();
            DependencyHandler dh = project.getDependencies();

            System.out.println("Adding dependency on " + mappedJar);
            deps.add(dh.create(project.files(mappedJar)));

            project.getGradle().removeListener(this);
        }

        @Override
        public void afterResolve(ResolvableDependencies dependencies) {
        }
    };
}
