package com.laop.plugin

import com.laop.inter.IAspect
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

class JavaAspect extends   IAspect{
    Project project

    JavaAspect( Project project){
        this.project = project
    }
    @Override
    void doAsepct(String taskName, JavaCompile javaCompile, String aspectpath, List<String> javaAspectInPath) {
        javaCompile.doLast {
            println("inpath=="+javaAspectInPath)
            def inpath = ""
            int l = javaAspectInPath.size()
            for (int i = 0; i < l; i++) {
                inpath = inpath + javaCompile.destinationDir.toString() + "/" + javaAspectInPath[i].replace(".", "/")
                if (i < l - 1)
                    inpath = inpath + File.pathSeparator
            }
            if(javaAspectInPath.size==0){
                inpath = javaCompile.destinationDir.toString()
            }
            println("linlog    javaInPath=="+inpath)
            String[] javaArgs = ["-showWeaveInfo",
                                 "-1.8",
                                 "-inpath", inpath ,
                                 "-aspectpath", aspectpath,
                                 "-d", javaCompile.destinationDir.toString(),
                                 "-classpath", javaCompile.classpath.asPath,
                                 "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
            MessageHandler handler = new MessageHandler(true)
            new Main().run(javaArgs, handler)
        }
    }
}