package com.laop.plugin

import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

import java.util.regex.Matcher
import java.util.regex.Pattern

class LaopUtils{
    public static String AOP_TYPE_DEBUG = "debug"
    public static String AOP_TYPE_CLOSE = "close"
    public static String AOP_TYPE_DEFAULT ="default"

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

    static  String  getAspectPath(Project mProject,JavaCompile javaCompile,LaopConfig laopConfig){
        def file = javaCompile.classpath.toList()
        def aspectFiles
        for (int i = 0; i < file.size(); i++) {
            if (file[i].getAbsolutePath().contains(laopConfig.aopModule) && file[i].getAbsolutePath().contains("classes.jar")) {
                aspectFiles = mProject.files(file[i].getAbsolutePath())
                break;
            }
        }
        if (aspectFiles == null) {
            aspectFiles = javaCompile.classpath
        }
        return aspectFiles.asPath
    }

}