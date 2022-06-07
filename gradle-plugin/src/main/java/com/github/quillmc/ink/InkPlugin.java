package com.github.quillmc.ink;

import com.alexsobiek.async.util.Lazy;
import com.github.quillmc.tinymcp.TinyMCP;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.slf4j.Logger;

public class InkPlugin implements Plugin<Project> {

    private static InkPlugin plugin;
    private static Logger logger;
    protected static final Lazy<TinyMCP> tinymcp = Lazy.delayed();

    private Project project;

    @Override
    public void apply(Project project) {
        plugin = this;
        logger = project.getLogger();
        this.project = project;
        // RemapModTask.register(project);
        InkExtension inkExtension = InkExtension.register(project);
        RemapModTask.register(project);
        project.afterEvaluate(a -> {
            RemapModTask remapModTask = (RemapModTask) a.getTasks().getByName("remapModTask");
            Task jarTask = a.getTasks().getByName("jar");
            remapModTask.defaultInputFiles = jarTask.getOutputs().getFiles();
            jarTask.finalizedBy(remapModTask);
        });
    }

    public Project getProject() {
        return project;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static TinyMCP getTinyMCP() {
        return tinymcp.get();
    }

    public static InkPlugin get() {
        return plugin;
    }
}

