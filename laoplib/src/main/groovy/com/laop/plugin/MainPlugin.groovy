package com.laop.plugin

import com.laop.plugin.LaopUtils
import org.apache.tools.ant.taskdefs.Java
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

public class MainPlugin implements Plugin<Project> {


    void apply(Project project) {
        def currentFlavtor
        project.extensions.create("laop", LaopConfig)

        project.afterEvaluate {
//            def flavor = utils.getCurrentFlavor(project)
//            println("flavor name = "+ flavor)
            LaopConfig laopConfig = project.laop

            println("kotlinPath" + laopConfig.kotlinFiles)
            println("javaPath" + laopConfig.javaFiles)
            if (laopConfig.aopType == LaopUtils.AOP_TYPE_CLOSE)
                return
            def variants
            def type = 0;
            if (!project.android.toString().contains("Library")) {
                 variants = project.android.applicationVariants
                 type = 1
             }
            else{
                variants = project.android.libraryVariants
                type = 2
            }
            variants.all { variant ->
                if (!variant.buildType.isDebuggable()&&laopConfig.aopType==LaopUtils.AOP_TYPE_DEBUG) {
                    return;
                }
                variant.outputs.all { output ->
                    if (type == 1) {
                        if (currentFlavtor == null) {
                            currentFlavtor = LaopUtils.getCurrentFlavor(project)
                            if (currentFlavtor.size() > 2) {
                                currentFlavtor = currentFlavtor.charAt(0).toLowerCase().toString() + currentFlavtor.substring(1)
                            }
                        }
                        if (!(LaopUtils.getVariantName(variant).equalsIgnoreCase(currentFlavtor)))
                            return;
                    }
                    println("linlog   current =" + currentFlavtor)
                    def fullName = ""
                    output.name.tokenize('-').eachWithIndex { token, index ->
                        fullName = fullName + (index == 0 ? token : token.capitalize())
                    }
                    JavaCompile javaCompile = variant.getJavaCompileProvider().get()
                    def aspectFiles = LaopUtils.getAspectPath(project,javaCompile,laopConfig)

                    println("linlog   file="+aspectFiles)
                    //do kotlin aspject
                    def kotlinAspect = new KotlinAspect(project)
                    kotlinAspect.doAsepct(fullName,javaCompile,aspectFiles,laopConfig.kotlinFiles)

                    //do java aspject
                    def javaAspject = new JavaAspect(project)
                    javaAspject.doAsepct(fullName,javaCompile,aspectFiles,laopConfig.javaFiles)
                }
            }
        }
    }

}


