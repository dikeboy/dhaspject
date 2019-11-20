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
    void doAsepct(String fullName,JavaCompile javaCompile,LaopConfig laopConfig,List<String> kotlinAspectInPath) {
        def kotlinTaskName = "compile" + fullName.charAt(0).toUpperCase()+ fullName.substring(1) + "Kotlin"
        def kotlinPath = project.buildDir.path + "/tmp/kotlin-classes/" + fullName
        def kotlinCompileTask = project.tasks.findByName(kotlinTaskName)

        if (kotlinCompileTask != null) {
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
            kotlinCompileTask.doLast {
                def aspectpath = LaopUtils.getAspectPath(project,javaCompile,laopConfig)

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