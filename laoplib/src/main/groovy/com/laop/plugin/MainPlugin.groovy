package com.laop.plugin

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

            println(LaopUtils.AOP_LOG_KEY+"kotlinPath" + laopConfig.kotlinFiles)
            println(LaopUtils.AOP_LOG_KEY+"javaPath" + laopConfig.javaFiles)
            if (laopConfig.aopType.trim().equals(LaopUtils.AOP_TYPE_CLOSE))
                return

            if(project.gradle.getStartParameter().getTaskRequests().toString().contains("Release")){
                if(laopConfig.aopType.trim().equals(LaopUtils.AOP_TYPE_DEBUG)){
                    return
                }
            }
            def isAndroidLibrary = project.plugins.hasPlugin("com.android.library")
            def variants = isAndroidLibrary ? project.android.libraryVariants : project.android.applicationVariants

            variants.all { variant ->
                variant.outputs.all { output ->
                    if (laopConfig.hasFlavors) {
                        if (currentFlavtor == null) {
                            currentFlavtor = LaopUtils.getCurrentFlavor(project)
                            if (currentFlavtor.size() > 2) {
                                currentFlavtor = currentFlavtor.charAt(0).toLowerCase().toString() + currentFlavtor.substring(1)
                            }
                        }
                        if (!(LaopUtils.getVariantName(variant).equalsIgnoreCase(currentFlavtor)))
                            return;
                    }
                    if(variant.buildType.isDebuggable()!=LaopUtils.isDebug(project))
                        return;
                    println(LaopUtils.AOP_LOG_KEY+"   current =" + currentFlavtor)
                    def fullName = ""
                    output.name.tokenize('-').eachWithIndex { token, index ->
                        fullName = fullName + (index == 0 ? token : token.capitalize())
                    }
                    JavaCompile javaCompile = variant.getJavaCompileProvider().get()

                    def all = laopConfig.kotlinFiles.size()==0&&laopConfig.javaFiles.size()==0
                    def aspectpath = LaopUtils.getAspectPath(project,javaCompile,laopConfig,fullName)

                    //do kotlin aspject
                    if(laopConfig.kotlinFiles.size()>0||all){
                        def kotlinAspect = new KotlinAspect(project)
                        kotlinAspect.doAsepct(fullName,javaCompile,laopConfig,laopConfig.kotlinFiles,aspectpath)
                    }

                    //do java aspject
                    if(laopConfig.javaFiles.size()>0||all){
                        def javaAspject = new JavaAspect(project)
                        javaAspject.doAsepct(fullName,javaCompile,laopConfig,laopConfig.javaFiles,aspectpath)
                    }
                }
            }
        }
    }

}


