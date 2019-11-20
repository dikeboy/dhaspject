package com.laop.plugin

import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

import java.util.regex.Matcher
import java.util.regex.Pattern

class LaopUtils{
    public static String AOP_TYPE_DEBUG = "debug"
    public static String AOP_TYPE_CLOSE = "close"
    public static String AOP_TYPE_DEFAULT ="default"

    public static String AOP_LOG_KEY = "linlog======="
    // aspectj 扫包注入 同时实现kotlin hook
    static String  getCurrentFlavor(Project project) {
        def  gradle = project.getGradle()
        String  tskReqStr = gradle.getStartParameter().getTaskRequests().toString()
        Pattern pattern;
        if( tskReqStr.contains( "assemble" ) )
            pattern = Pattern.compile("assemble(\\w+)(Release|Debug)")
        else
            pattern = Pattern.compile("generate(\\w+)(Release|Debug)")
        Matcher matcher = pattern.matcher( tskReqStr )
        if( matcher.find() )
            return matcher.group(1).toLowerCase()
        else
        {
            println "NO MATCH FOUND"
            return "";
        }
    }

    // aspectj 扫包注入 同时实现kotlin hook
    static String  getVariantName( variant) {
        def name = ""
        for(int i=0;i<variant.productFlavors.size();i++){
            name = name +variant.productFlavors[i].name
        }
        return name
    }

    static  String  getAspectPath(Project mProject,JavaCompile javaCompile,LaopConfig laopConfig){
        def file = javaCompile.classpath.toList()
        def aspectFiles
        if(!laopConfig.aopModule.isEmpty()){
            for (int i = 0; i < file.size(); i++) {
                if (file[i].getAbsolutePath().contains(laopConfig.aopModule) && file[i].getAbsolutePath().contains("classes.jar")) {
                    aspectFiles = mProject.files(file[i].getAbsolutePath())
                    break;
                }
            }
            if(aspectFiles==null){
                aspectFiles = mProject.files(javaCompile.destinationDir)
            }
        }
        if (aspectFiles == null) {
            aspectFiles = mProject.files(javaCompile.classpath,javaCompile.destinationDir)
        }
        println(AOP_LOG_KEY+"   aspectFiles=="+aspectFiles.asPath)
        return aspectFiles.asPath
    }


    static Boolean  isDebug(Project project) {
        def  gradle = project.getGradle()
        String  tskReqStr = gradle.getStartParameter().getTaskRequests().toString()
        Pattern pattern;
        if( tskReqStr.contains( "assembleDebug" ) )
            return true
        else if(tskReqStr.contains( "assembleRelease"))
            return false
        if( tskReqStr.contains( "assemble" ) )
            pattern = Pattern.compile("assemble(\\w+)(Debug)")
        else
            pattern = Pattern.compile("generate(\\w+)(Debug)")
        Matcher matcher = pattern.matcher( tskReqStr )
        if( matcher.find() )
            return true
        return false
    }

}