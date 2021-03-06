package com.laop.plugin

import com.laop.inter.IAspect
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

class KotlinAspect extends   IAspect{
    Project project

    KotlinAspect( Project project){
        this.project = project
    }
    @Override
    void doAsepct(String fullName,JavaCompile javaCompile,LaopConfig laopConfig,List<String> kotlinAspectInPath,String aspectpath) {
        def kotlinTaskName = "compile" + fullName.charAt(0).toUpperCase()+ fullName.substring(1) + "Kotlin"
        def kotlinPath = project.buildDir.path + "/tmp/kotlin-classes/" + fullName
        def kotlinCompileTask = project.tasks.findByName(kotlinTaskName)

        if (kotlinCompileTask != null) {

//            def classPath = javaCompile.classpath.filter {
////                !it.canonicalPath.contains("play-service")
//                true
//            }
            def totalPath = project.files(kotlinCompileTask.destinationDir, javaCompile.destinationDir, javaCompile.classpath).asPath
            def  kotlinInPath = ""
            int l =  kotlinAspectInPath.size()
            for (int i = 0; i < l; i++) {
                kotlinInPath =  kotlinInPath +kotlinPath + "/"+ kotlinAspectInPath[i].replace(".","/")
                if(i<l-1)
                    kotlinInPath = kotlinInPath +File.pathSeparator
            }
            if(kotlinAspectInPath.size==0){
                kotlinInPath = kotlinPath
            }
            println(LaopUtils.AOP_LOG_KEY+"    kotlinInPath before=="+kotlinInPath)
            boolean  isAop = false
            kotlinCompileTask.doLast {
                isAop = true
                println(LaopUtils.AOP_LOG_KEY+"    kotlinInPath after=="+kotlinInPath)
                String[] kotlinArgs = ["-showWeaveInfo",
                                       "-1.8",
                                       "-inpath", kotlinInPath,
                                       "-aspectpath", aspectpath,
                                       "-d", kotlinPath,
                                       "-classpath", totalPath,
                                       "-bootclasspath", project.android.bootClasspath.join(
                        File.pathSeparator)]
                MessageHandler handler = new MessageHandler(true)
                new Main().run(kotlinArgs, handler)
            }
            if(laopConfig.useJavaTask){
                javaCompile.doLast {
                    if(!isAop){
                        println(LaopUtils.AOP_LOG_KEY+"    kotlinInPath after=="+kotlinInPath)
                        String[] kotlinArgs = ["-showWeaveInfo",
                                               "-1.8",
                                               "-inpath", kotlinInPath,
                                               "-aspectpath", aspectpath,
                                               "-d", kotlinPath,
                                               "-classpath", totalPath,
                                               "-bootclasspath", project.android.bootClasspath.join(
                                File.pathSeparator)]
                        MessageHandler handler = new MessageHandler(true)
                        new Main().run(kotlinArgs, handler)
                    }
                }
            }
        }

    }
}